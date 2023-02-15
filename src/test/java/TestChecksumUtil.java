import com.github.webrsync.ChecksumUtil;
import com.github.webrsync.WrongAlgorithmException;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class TestChecksumUtil {
    private final byte[] buffer = "Hello world!".getBytes(StandardCharsets.UTF_8);
    private final int expectedWeak = 486343773;

    private BigInteger getExpectedMD5() {
        try {
            byte[] expectedStrongBytes = MessageDigest.getInstance("md5").digest(buffer);
            return new BigInteger(expectedStrongBytes);
        } catch(NoSuchAlgorithmException e) {
            throw new WrongAlgorithmException("Something's wrong", e);
        }
    }

    @Test
    void testComputeWeakFromByteArray() {
        WeakChecksum weakChecksum = ChecksumUtil.computeWeak(buffer);
        assertEquals(expectedWeak, weakChecksum.value());
    }

    @Test
    void testComputeWeakFromByteBuf() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(buffer);
        WeakChecksum weakChecksum = ChecksumUtil.computeWeak(buf);
        assertEquals(expectedWeak, weakChecksum.value());
    }

    @Test
    void testComputeHash16() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(buffer);
        WeakChecksum weakChecksum = ChecksumUtil.computeWeak(buf);
        int hash16 = ChecksumUtil.computeHash16(weakChecksum);
        assertEquals(1117, hash16);
    }

    @Test
    void testComputeStrongFromByteArray() throws NoSuchAlgorithmException {
        StrongChecksum strongChecksum = ChecksumUtil.computeStrong(buffer);
        BigInteger expectedBigInt = getExpectedMD5();
        assertEquals(expectedBigInt, strongChecksum.value());
    }

    @Test
    void testComputeStrongFromBuf() throws NoSuchAlgorithmException {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(buffer);
        StrongChecksum strongChecksum = ChecksumUtil.computeStrong(buf);
        BigInteger expectedBigInt = getExpectedMD5();
        assertEquals(expectedBigInt, strongChecksum.value());
    }
}
