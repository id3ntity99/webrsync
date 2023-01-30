package com.github.webrsync.data;

import java.math.BigInteger;

public class StrongChecksum implements Checksum {
    private final byte[] byteValue;
    private final BigInteger bigIntValue;

    public StrongChecksum(byte[] byteValue) {
        this.byteValue = byteValue;
        this.bigIntValue = new BigInteger(byteValue);
    }

    @Override
    public BigInteger getValue() {
        return bigIntValue;
    }

    public byte[] getBytes() {
        return byteValue;
    }

    @Override
    public boolean equals(Checksum cks) {
        if (cks instanceof StrongChecksum) {
            StrongChecksum strongCks = (StrongChecksum) cks;
            return this.bigIntValue.equals(strongCks.getValue());
        }
        return super.equals(cks);
    }
}
