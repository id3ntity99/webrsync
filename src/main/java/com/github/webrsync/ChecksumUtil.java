package com.github.webrsync;

import com.github.webrsync.data.AbstractChecksum;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtil {
    private static final int MOD = 65536;
    private static final String ALGORITHM = "md5";

    private ChecksumUtil() {
    }

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

    public static int computeHash16(AbstractChecksum checksum) {
        return checksum.value().intValue() % MOD;
    }
}
