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
}
