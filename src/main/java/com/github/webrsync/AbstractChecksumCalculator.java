package com.github.webrsync;

import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractChecksumCalculator {
    protected int mod = 65536;
    protected String algorithm = "md5";

    protected AbstractChecksumCalculator() {
    }

    protected AbstractChecksumCalculator(int mod, String algorithm) {
        this.mod = mod;
        this.algorithm = algorithm;
    }

    protected final WeakChecksum computeWeak(byte[] buffer) {
        int a = 0;
        int b = 0;
        for (byte word : buffer) {
            a = (a + word) % mod;
            b = (b + a) % mod;
        }
        int checksum = (b << 16) | a;
        int hash16 = checksum % mod;
        return new WeakChecksum(checksum, hash16);
    }

   protected final StrongChecksum computeStrong(byte[] buffer) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] strongHash = md.digest(buffer);
            return new StrongChecksum(strongHash);
        } catch (NoSuchAlgorithmException e) {
            String msg = String.format("Must use MD5 hash algorithm for strong checksum but %s is used.", algorithm);
            throw new WrongAlgorithmException(msg, e);
        }
    }
}
