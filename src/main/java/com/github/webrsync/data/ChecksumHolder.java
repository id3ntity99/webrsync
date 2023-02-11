package com.github.webrsync.data;

import com.github.webrsync.ChecksumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ChecksumHolder implements Holder<ChecksumHolder> {
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

    public ByteBuf content() {
        int hash16 = ChecksumUtil.computeHash16(weakChecksum);
        ByteBuf hash = Unpooled.buffer(4, 4).writeInt(hash16);
        ByteBuf weakBuf = weakChecksum.content();
        ByteBuf strongBuf = strongChecksum.content();
        return Unpooled.wrappedBuffer(hash, weakBuf, strongBuf).asReadOnly();
    }

    @Override
    public boolean isEqualTo(ChecksumHolder holder) {
        return false;
    }
}
