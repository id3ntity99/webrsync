package com.github.webrsync.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

import java.math.BigInteger;

public class ChecksumHolder implements Comparable<ChecksumHolder>, ByteBufHolder {
    private final WeakChecksum weakChecksum;
    private final StrongChecksum strongChecksum;

    public ChecksumHolder(WeakChecksum weakChecksum, StrongChecksum strongChecksum) {
        this.weakChecksum = weakChecksum;
        this.strongChecksum = strongChecksum;
    }

    public WeakChecksum getWeakChecksum() {
        return weakChecksum;
    }

    public StrongChecksum getStrongChecksum() {
        return strongChecksum;
    }

    // TODO: This method will return the concatenated bytes of weak checksum and strong checksum
    @Override
    public ByteBuf content() {
        BigInteger bigInteger = strongChecksum.getValue();
        return null;
    }

    @Override
    public ByteBufHolder copy() {
        return null;
    }

    @Override
    public ByteBufHolder duplicate() {
        return null;
    }

    @Override
    public ByteBufHolder replace(ByteBuf content) {
        return null;
    }

    @Override
    public ByteBufHolder retain() {
        return null;
    }

    @Override
    public ByteBufHolder retain(int increment) {
        return null;
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return null;
    }

    @Override
    public ByteBufHolder touch() {
        return null;
    }

    @Override
    public ByteBufHolder touch(Object hint) {
        return null;
    }

    @Override
    public int refCnt() {
        return 0;
    }

    @Override
    public boolean release() {
        return false;
    }

    @Override
    public boolean release(int decrement) {
        return false;
    }

    @Override
    public int compareTo(ChecksumHolder checksumHolder) {
        return weakChecksum.getHash16() - checksumHolder.getWeakChecksum().getHash16();
    }
}
