package com.github.webrsync.data;

import java.math.BigInteger;

public class StrongChecksum extends AbstractChecksum {
    public StrongChecksum(byte[] byteValue) {
        super(byteValue);
    }

    @Override
    public BigInteger value() {
        byte[] tmpArr = new byte[16];
        buffer.getBytes(0, tmpArr, 0, 16);
        return new BigInteger(tmpArr);
    }

    @Override
    public boolean isEqualTo(AbstractChecksum cks) {
        if (cks instanceof StrongChecksum) {
            StrongChecksum strongCks = (StrongChecksum) cks;
            return value().equals(strongCks.value());
        }
        return false;
    }
}
