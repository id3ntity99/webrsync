package com.github.webrsync.data;

import com.github.webrsync.ChecksumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Deprecated
public class ChecksumHolder implements Holder {
    private final WeakChecksum weakChecksum;
    private final StrongChecksum strongChecksum;
    private final int hash16;

    public ChecksumHolder(WeakChecksum weakChecksum, StrongChecksum strongChecksum) {
        this.weakChecksum = weakChecksum;
        this.strongChecksum = strongChecksum;
        this.hash16 = ChecksumUtil.computeHash16(weakChecksum);
    }

    public WeakChecksum getWeakChecksum() {
        return weakChecksum;
    }

    public StrongChecksum getStrongChecksum() {
        return strongChecksum;
    }

    public ByteBuf content() {
        ByteBuf hash = Unpooled.buffer(4, 4).writeInt(hash16);
        ByteBuf weakBuf = weakChecksum.content();
        ByteBuf strongBuf = strongChecksum.content();
        return Unpooled.wrappedBuffer(hash, weakBuf, strongBuf).asReadOnly();
    }

    @Override
    public boolean isEqualTo(Holder holder) {
        if (holder.getClass() == ChecksumHolder.class) {
            int targetHash = holder.content().getInt(0);
            return this.hash16 == targetHash;
        }
        return false;
    }
}
