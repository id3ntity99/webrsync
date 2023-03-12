package sftp;

import com.github.webrsync.sftp.DefaultSftpPacket;
import com.github.webrsync.sftp.SftpPacket;
import com.github.webrsync.sftp.SftpPacketType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultSftpPacketTest {
    @Test
    void testLengthCorrectness() {
        final int expectedLen = 5;
        ByteBuf payload = Unpooled.buffer().writeInt(6);
        SftpPacket packet = new DefaultSftpPacket(SftpPacketType.SSH_FXP_INIT, payload);
        assertEquals(expectedLen, packet.length());
    }

    @Test
    void testType() {
        ByteBuf payload = Unpooled.buffer().writeInt(6);
        SftpPacket packet = new DefaultSftpPacket(SftpPacketType.SSH_FXP_INIT, payload);
        assertEquals(SftpPacketType.SSH_FXP_INIT.value(), packet.type().value());
    }
}
