import com.github.webrsync.ChecksumParser;
import com.github.webrsync.data.ChecksumHolder;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Deprecated
class TestChecksumParser {
    private final ChannelInboundHandler parser = new ChecksumParser();
    private final EmbeddedChannel channel = new EmbeddedChannel(parser);

    @Test
    void testParse() {
        String path = System.getProperty("user.dir") + "/src/test/java/testfile.txt";
        File targetFile = new File(path);
        channel.writeInbound(targetFile);
        ChecksumHolder holder = channel.readOutbound();
        assertInstanceOf(ChecksumHolder.class, holder);
        assertNotNull(holder.getWeakChecksum());
        assertNotNull(holder.getStrongChecksum());
    }
}
