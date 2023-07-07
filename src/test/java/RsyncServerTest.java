import com.github.webrsync.ChecksumHolderProcessor;
import com.github.webrsync.ChecksumParser;
import com.github.webrsync.TargetFileProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Deprecated
class RsyncServerTest {
    private FullHttpRequest getRequest(String uri) {
        return new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri);
    }

    private EmbeddedChannel initSpyChannel() {
        Properties prop = new Properties();
        prop.setProperty("basePath", System.getProperty("user.dir") + "/src/test/java");
        ChannelOutboundHandler holderProcessor = spy(new ChecksumHolderProcessor());
        ChannelInboundHandler fileProcessor = spy(new TargetFileProcessor(prop));
        ChannelInboundHandler parser = spy(new ChecksumParser());
        return new EmbeddedChannel(holderProcessor, fileProcessor, parser);
    }

    private EmbeddedChannel initChannel() {
        Properties prop = new Properties();
        prop.setProperty("basePath", System.getProperty("user.dir") + "/src/test/java");
        ChannelOutboundHandler holderProcessor = new ChecksumHolderProcessor();
        ChannelInboundHandler fileProcessor = new TargetFileProcessor(prop);
        ChannelInboundHandler parser = new ChecksumParser();
        return new EmbeddedChannel(holderProcessor, fileProcessor, parser);
    }

    private void checkOutboundMessages(Queue<Object> outboundMsgs) {
        for (Object outboundMsg : outboundMsgs) {
            if (outboundMsg instanceof FullHttpResponse) {
                FullHttpResponse res = (FullHttpResponse) outboundMsg;
                assertEquals(200, res.status().code());
            } else if (outboundMsg instanceof WebSocketFrame) {
                WebSocketFrame frame = (WebSocketFrame) outboundMsg;
                int readableBytes = frame.content().readableBytes();
                assertEquals(24, readableBytes);
            }
        }
    }

    private void checkMessageTypes(Queue<Object> outboundMsgs) {
        boolean doesResExist = outboundMsgs.stream().anyMatch(a -> a instanceof FullHttpResponse);
        boolean doesFrameExist = outboundMsgs.stream().anyMatch(a -> a instanceof WebSocketFrame);
        assertTrue(doesResExist);
        assertTrue(doesFrameExist);
    }


    @Test
    void testReceivingWebSocketFrame() {
        EmbeddedChannel channel = initChannel();
        FullHttpRequest req = getRequest("/testfile.txt");
        channel.writeInbound(req);
        Queue<Object> outboundQ = channel.outboundMessages();
        checkMessageTypes(outboundQ);
        checkOutboundMessages(outboundQ);
    }

    @Test
    void testParsingThrowsException() throws Exception {
        EmbeddedChannel channel = initSpyChannel();
        ChecksumParser parser = channel.pipeline().get(ChecksumParser.class);
        doThrow(new IOException()).when(parser)
                .channelRead(any(ChannelHandlerContext.class), any(File.class));
        FullHttpRequest req = getRequest("/testfile.txt");
        channel.writeInbound(req);
        FullHttpResponse res = channel.readOutbound();
        assertEquals(500, res.status().code());
    }
}
