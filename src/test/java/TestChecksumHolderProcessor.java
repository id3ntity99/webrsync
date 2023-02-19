import com.github.webrsync.ChecksumHolderProcessor;
import com.github.webrsync.data.AbstractChecksum;
import com.github.webrsync.data.ChecksumHolder;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.Random;

class TestChecksumHolderProcessor {
    private static EmbeddedChannel channel;

    @BeforeEach
    public void initChannel() {
        channel = new EmbeddedChannel(new ChecksumHolderProcessor());
    }

    private AbstractChecksum getRandomChecksum(Class<? extends AbstractChecksum> type) {
        if (type == WeakChecksum.class) {
            int randomInt = new Random().nextInt();
            return new WeakChecksum(randomInt);
        } else if (type == StrongChecksum.class) {
            byte[] randomBytes = new byte[16];
            new Random().nextBytes(randomBytes);
            return new StrongChecksum(randomBytes);
        }
        return null;
    }

    @Test
    void testNumberOfFrames() {
        for (int i = 0; i < 5; i++) {
            WeakChecksum weak = (WeakChecksum) getRandomChecksum(WeakChecksum.class);
            StrongChecksum strong = (StrongChecksum) getRandomChecksum(StrongChecksum.class);
            ChecksumHolder holder = new ChecksumHolder(weak, strong);
            channel.writeOutbound(holder);
        }
        Queue<Object> queue = channel.outboundMessages();
        Assertions.assertEquals(5, queue.size());
    }

    @Test
    void testHttpResponseHandling() {
        FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK);
        channel.writeOutbound(res);
        Queue<Object> queue = channel.outboundMessages();
        Assertions.assertEquals(1, queue.size());
    }
}
