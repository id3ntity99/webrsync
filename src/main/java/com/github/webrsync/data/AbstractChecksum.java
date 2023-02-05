package com.github.webrsync.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public abstract class AbstractChecksum {
    protected final ByteBuf buffer;

    protected AbstractChecksum(int weakChecksum) {
        buffer = Unpooled.buffer(4, 4);
        buffer.setInt(0, weakChecksum);
    }

    protected AbstractChecksum(byte[] strongChecksum) {
        buffer = Unpooled.buffer(16, 16);
        buffer.setBytes(0, strongChecksum);
    }

    public ByteBuf buffer() {
        return buffer;
    }

    public abstract Number value();

    public abstract boolean isEqualTo(AbstractChecksum checksum);

}
