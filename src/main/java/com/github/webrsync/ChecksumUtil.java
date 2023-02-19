package com.github.webrsync;

import com.github.webrsync.data.AbstractChecksum;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.buffer.ByteBuf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtil {
    private static final int MOD = 65536;
    private static final String ALGORITHM = "md5";

    private ChecksumUtil() {
    }

    /**
     * Compute 32-bit weak checksum from the given byte array. <br>
     * @param buffer A byte array containing bytes to be computed.
     * @return {@link WeakChecksum} containing {@link ByteBuf}.
     */
    public static WeakChecksum computeWeak(byte[] buffer) {
        int a = 0;
        int b = 0;
        for (byte word : buffer) {
            a = (a + word) % MOD;
            b = (b + a) % MOD;
        }
        int checksum = (b << 16) | a;
        return new WeakChecksum(checksum);
    }

    /**
     * Compute 32-bit weak checksum from the given {@link ByteBuf}. <br>
     * Note that this method uses {@link ByteBuf#readBytes(byte[] dst)} and {@link ByteBuf#release()}.
     * That means, the given buffer cannot be used anymore.
     * @param buffer A {@link ByteBuf} containing bytes to be computed.
     * @return {@link WeakChecksum} containing {@link ByteBuf}.
     */
    public static WeakChecksum computeWeak(ByteBuf buffer) {
        int readable = buffer.readableBytes();
        byte[] arr = new byte[readable];
        buffer.readBytes(arr);
        buffer.release();
        return computeWeak(arr);
    }

    /**
     * Compute 128-bit strong checksum using MD5 hashing algorithm from given byte array. <br>
     * @param buffer A byte array containing bytes to be computed.
     * @return {@link StrongChecksum} containing ByteBuf.
     */
    public static StrongChecksum computeStrong(byte[] buffer) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] strongHash = md.digest(buffer);
            return new StrongChecksum(strongHash);
        } catch (NoSuchAlgorithmException e) {
            String msg = String.format("Must use MD5 hash algorithm for strong checksum but %s is used.", ALGORITHM);
            throw new WrongAlgorithmException(msg, e);
        }
    }

    /**
     * Compute 128-bit strong checksum using MD5 hashing algorithm from the given {@link ByteBuf}. <br>
     * Note that this method uses {@link ByteBuf#readBytes(byte[] dest)} and {@link ByteBuf#release()}.
     * That means, the given buffer cannot be used anymore.
     * @param buffer A {@link ByteBuf} containing bytes to be computed.
     * @return {@link StrongChecksum} containing {@link ByteBuf}.
     */
    public static StrongChecksum computeStrong(ByteBuf buffer) {
        int readable = buffer.readableBytes();
        byte[] arr = new byte[readable];
        buffer.readBytes(arr);
        buffer.release();
        return computeStrong(arr);
    }

    /**
     * Compute 16-bit hash using the value of the given checksum. <br>
     * This method uses very simple calculation for the hash -- {@code checksum.value() % 2^16}.
     * @param checksum A checksum from which the value is retrieved and, ultimately, the 16-bit hash is calculated.
     * @return A 16-bit hash as int type.
     */
    public static int computeHash16(AbstractChecksum checksum) {
        return checksum.value().intValue() % MOD;
    }
}
