package sftp;

import com.github.webrsync.sftp.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestSftpPacket {
    private final int version = 13;
    private final ByteBuf initContent = Unpooled.buffer().writeInt(version);
    private final ByteBuf requestContent = Unpooled.buffer().writeInt(1).writeByte(2);

    @Test
    void testInitPacket() {
        InitPacket initPacket = new DefaultInitPacket(SftpPacketType.SSH_FXP_INIT, initContent);
        assertEquals(initContent, initPacket.content());
        assertEquals(version, initPacket.version());
    }

    @Test
    void testInitPacketThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new DefaultInitPacket(SftpPacketType.SSH_FXP_ATTRS, initContent));
    }

    @Test
    void testRequestPacket() {
        RequestPacket packet = new DefaultRequestPacket(SftpPacketType.SSH_FXP_REMOVE, 1, requestContent);
        assertEquals(requestContent, packet.content());
        assertEquals(1, packet.requestId());
    }

    @Test
    void testRequestPacketWithNegativeRequestId() {
        assertThrows(IllegalArgumentException.class,
                () -> new DefaultRequestPacket(SftpPacketType.SSH_FXP_REMOVE, -1, requestContent));

    }

    @Test
    void testRequestPacketWithIllegalType() {
        assertThrows(IllegalArgumentException.class,
                () -> new DefaultRequestPacket(SftpPacketType.SSH_FXP_INIT, 12, requestContent));
    }

    @Test
    void testAttributivePacket() {
    }

    @Test
    void test() throws IOException {
    }
}
