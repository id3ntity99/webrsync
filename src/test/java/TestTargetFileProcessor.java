import com.github.webrsync.TargetFileProcessor;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class TestTargetFileProcessor {
    private static EmbeddedChannel channel;

    public void initChannelWithCustomProps() throws IOException {
        String basePath = System.getProperty("user.dir");
        Properties prop = new Properties();
        prop.setProperty("basePath", basePath);
        ChannelInboundHandler fileProcessor = new TargetFileProcessor(prop);
        channel = new EmbeddedChannel(fileProcessor);
    }

    public void initChannelWithAppProps() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream propStream = loader.getResourceAsStream("app.properties");
        Properties prop = new Properties();
        prop.load(propStream);
        ChannelInboundHandler fileProcessor = new TargetFileProcessor(prop);
        channel = new EmbeddedChannel(fileProcessor);
    }

    @Test
    void testBadRequestForFileTargeting() throws IOException {
        initChannelWithAppProps();
        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/path/to/target.txt");
        channel.writeInbound(req);
        FullHttpResponse res = channel.readOutbound();
        assertEquals(400, res.status().code());
    }

    @Test
    void testOkForClassPathFileTargeting() throws IOException {
        initChannelWithCustomProps();
        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/build.gradle");
        channel.writeInbound(req);
        FullHttpResponse res = channel.readOutbound();
        assertEquals(200, res.status().code());
    }

    @Test
    void testOkForNativeFileSystemTargeting() throws IOException {
        initChannelWithAppProps();
        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bin/pydoc3.10");
        channel.writeInbound(req);
        FullHttpResponse res = channel.readOutbound();
        assertEquals(200, res.status().code());
    }

    @Test
    void testSendingReqUsingWrongMethod() throws IOException{
        initChannelWithAppProps();
        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, "/bin/pydoc3.10");
        channel.writeInbound(req);
        FullHttpResponse res = channel.readOutbound();
        assertEquals(400, res.status().code());
    }
}
