package data;

import com.github.webrsync.data.*;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TestChecksumHolder {
    private Holder getHolder(int weakValue) {
        byte[] randomBytes = new byte[16];
        new Random().nextBytes(randomBytes);
        WeakChecksum weak = new WeakChecksum(weakValue);
        StrongChecksum strong = new StrongChecksum(randomBytes);
        return new ChecksumHolder(weak, strong);
    }

    @Test
    void testEquality() {
        Holder cksHolder1 = getHolder(1);
        Holder cksHolder2 = getHolder(1);
        assertTrue(cksHolder1.isEqualTo(cksHolder2));
    }

    @Test
    void testFalseEquality() {
        Holder cksHolder1 = getHolder(1);
        Holder cksHolder2 = getHolder(2);
        assertFalse(cksHolder1.isEqualTo(cksHolder2));
    }

    @Test
    void testWrongTypeOfHolderEquality() {
        Holder holder = getHolder(1);
        AbstractChecksum checksum = new WeakChecksum(1);
        assertFalse(holder.isEqualTo(checksum));
    }

    @Test
    void testContentCorrectness() {
        Holder holder = getHolder(1);
        ByteBuf concat = holder.content();
        int hash16 = concat.readInt();
        assertEquals(20, concat.readableBytes());
        int weak = concat.readInt();
        assertEquals(16, concat.readableBytes());
        byte[] strong = new byte[concat.readableBytes()];
        concat.readBytes(strong);
        assertEquals(0, concat.readableBytes());
        assertEquals(24, concat.writerIndex());
    }
}
