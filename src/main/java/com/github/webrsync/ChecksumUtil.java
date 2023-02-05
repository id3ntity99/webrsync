package com.github.webrsync;

import com.github.webrsync.data.AbstractChecksum;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class AbstractChecksumCalculator {
    private static final int MOD = 65536;
    private static final String ALGORITHM = "md5";

    public final WeakChecksum computeWeak(byte[] buffer) {
        int a = 0;
        int b = 0;
        for (byte word : buffer) {
            a = (a + word) % MOD;
            b = (b + a) % MOD;
        }
        int checksum = (b << 16) | a;
        int hash16 = checksum % MOD;
        return new WeakChecksum(checksum, hash16);
    }

    public final StrongChecksum computeStrong(byte[] buffer) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] strongHash = md.digest(buffer);
            return new StrongChecksum(strongHash);
        } catch (NoSuchAlgorithmException e) {
            String msg = String.format("Must use MD5 hash algorithm for strong checksum but %s is used.", ALGORITHM);
            throw new WrongAlgorithmException(msg, e);
        }
    }

    public ByteBuf computeHash16(AbstractChecksum checksum) {
        int hash16 = checksum.value().intValue() % MOD;
        return Unpooled.buffer().setInt(0, hash16);
    }

    public ByteBuf concat(AbstractChecksum... checksum) {
        ByteBuf hash16 = null;
        WeakChecksum weakChecksum = null;
        StrongChecksum strongChecksum = null;
        for (AbstractChecksum abstractChecksum : checksum) {
            if (abstractChecksum instanceof WeakChecksum) {
                weakChecksum = (WeakChecksum) abstractChecksum;
                hash16 = computeHash16(weakChecksum);
            } else if (abstractChecksum instanceof StrongChecksum) {
                strongChecksum = (StrongChecksum) abstractChecksum;
            }
        }
        return Unpooled.wrappedBuffer(hash16, weakChecksum.buffer(), strongChecksum.buffer());
    }
}
