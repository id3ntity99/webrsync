package data;

import com.github.webrsync.data.*;
import org.junit.jupiter.api.Assertions;
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
}
