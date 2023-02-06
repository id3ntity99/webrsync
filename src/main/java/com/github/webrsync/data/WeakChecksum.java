package com.github.webrsync.data;

public class WeakChecksum extends AbstractChecksum {
    public WeakChecksum(int value) {
        super(value);
    }

    @Override
    public Integer value() {
        return buffer.getInt(0);
    }

    @Override
    public boolean isEqualTo(AbstractChecksum checksum) {
        if (checksum instanceof WeakChecksum) {
            WeakChecksum weakCh = (WeakChecksum) checksum;
            return value().equals(weakCh.value());
        }
        return false;
    }
}
