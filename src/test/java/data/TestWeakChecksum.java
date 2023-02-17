package data;

import com.github.webrsync.data.AbstractChecksum;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TestWeakChecksum {
    private byte[] getRandom16Bytes() {
        byte[] randomBytes = new byte[16];
        new Random().nextBytes(randomBytes);
        return randomBytes;
    }
    @Test
    void testWeakChecksumByteBufIndices() {
        WeakChecksum weakChecksum = new WeakChecksum(1);
        int weakReaderIdx = weakChecksum.content().readerIndex();
        int weakWriterIdx = weakChecksum.content().writerIndex();
        assertEquals(0, weakReaderIdx);
        assertEquals(4, weakWriterIdx);
    }

    @Test
    void testStrongChecksumByteBufIndices() {
        byte[] randomBytes = getRandom16Bytes();
        StrongChecksum strongChecksum = new StrongChecksum(randomBytes);
        int strongReaderIdx = strongChecksum.content().readerIndex();
        int strongWriterIdx = strongChecksum.content().writerIndex();
        assertEquals(0, strongReaderIdx);
        assertEquals(16, strongWriterIdx);
    }

    @Test
    void testFalseEqualityBetweenWeakAndStrong() {
        AbstractChecksum weak = new WeakChecksum(1);
        byte[] randomBytes = getRandom16Bytes();
        AbstractChecksum strong = new StrongChecksum(randomBytes);
        assertFalse(weak.isEqualTo(strong));
    }

    @Test
    void testTrueEqualityBetweenWeakChecksums() {
        AbstractChecksum weak1 = new WeakChecksum(1);
        AbstractChecksum weak2 = new WeakChecksum(1);
        assertTrue(weak1.isEqualTo(weak2));
    }

    @Test
    void testFalseEqualityBetweenWeakChecksums() {
        AbstractChecksum weak1 = new WeakChecksum(1);
        AbstractChecksum weak2 = new WeakChecksum(2);
        assertFalse(weak1.isEqualTo(weak2));
    }
}
