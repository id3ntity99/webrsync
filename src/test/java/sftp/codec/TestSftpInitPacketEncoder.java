package sftp.codec;

import com.github.webrsync.sftp.DefaultRequestPacket;
import com.github.webrsync.sftp.SftpExtensionNames;
import com.github.webrsync.sftp.SftpPacket;
import com.github.webrsync.sftp.SftpPacketType;
import com.github.webrsync.sftp.codec.SftpInitPacketEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSftpInitPacketEncoder {
    private static EmbeddedChannel channel;
    private static final int INIT_CONTENT = 13;
    private static final String INIT_ATTR_KEY = SftpExtensionNames.FILENAME_CHARSET;
    private static final String INIT_ATTR_VAL = "utf8";

    @BeforeAll
    static void init() {
        //MessageToByteEncoder<SftpPacket> encoder = new SftpInitPacketEncoder();
        //channel = new EmbeddedChannel(encoder);
    }

    int estimateLength(SftpPacket packet) {
        /* The initial length of backing byte array of ByteBuf is always equal to 256.
            Thus, using array to calculate length is bad idea.
         */
        //int len = SftpPacketType.BYTES + packet.content().readableBytes();
        //Iterator<Map.Entry<String, String>> iterator = packet.attributes().iterator();
        //while (iterator.hasNext()) {
        //    Map.Entry<String, String> current = iterator.next();
        //    len += current.getKey().length();
        //    len += current.getValue().length();
        //}
        //return len;
        return 0;
    }

    //TODO: Refactor testSendInitPacket()
    @Test
    void testSendInitPacket() {
        //ByteBuf content = Unpooled.buffer().writeInt(INIT_CONTENT);
        //SftpPacket<String, String> initPacket = new DefaultRequestPacket<>(SftpPacketType.SSH_FXP_INIT, content);
        //initPacket.attributes().add(INIT_ATTR_KEY, INIT_ATTR_VAL);
        //int estimatedLen = estimateLength(initPacket);
        //assert (channel.writeOutbound(initPacket));
        //channel.flushOutbound();
        //ByteBuf out = channel.readOutbound();
        //assertEquals(estimatedLen, out.readInt());
        //assertEquals(SftpPacketType.SSH_FXP_INIT.value(), out.readByte());
        //assertEquals(13, out.readInt());
        //assertEquals(INIT_ATTR_KEY, out.readCharSequence(16, CharsetUtil.UTF_8));
        //assertEquals(INIT_ATTR_VAL, out.readCharSequence(4, CharsetUtil.UTF_8));
    }
}
