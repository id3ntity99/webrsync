package com.github.webrsync.data;

public class WeakChecksum implements Checksum {
    private final int value;
    private final int hash16;

    public WeakChecksum(int value) {
        this.value = value;
        this.hash16 = 0;
    }

    public WeakChecksum(int value, int hash16) {
        this.value = value;
        this.hash16 = hash16;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public int getHash16() {
        return hash16;
    }

    @Override
    public boolean equals(Checksum checksum) {
        if (checksum instanceof WeakChecksum) {
            WeakChecksum weakCh = (WeakChecksum) checksum;
            return this.value == weakCh.getValue();
        }
        return false;
    }
}
