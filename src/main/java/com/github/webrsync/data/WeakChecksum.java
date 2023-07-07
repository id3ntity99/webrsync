package com.github.webrsync.data;

@Deprecated
public class WeakChecksum extends AbstractChecksum {
    public WeakChecksum(int value) {
        super(value);
    }

    @Override
    public Integer value() {
        return buffer.getInt(0);
    }
}
