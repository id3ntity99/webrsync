package com.github.webrsync.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Skeletal implementation of {@link Holder}. This abstract class contains internal {@link ByteBuf} whose reader index is 0.
 * Instantiation of the subclasses will write data into the buffer, causing the writer index to be non-zero.
 */
public abstract class AbstractChecksum implements Holder<AbstractChecksum> {
    protected final ByteBuf buffer;

    protected AbstractChecksum(int weakChecksum) {
        buffer = Unpooled.buffer(4, 4);
        buffer.writeInt(weakChecksum);
    }

    protected AbstractChecksum(byte[] strongChecksum) {
        buffer = Unpooled.buffer(16, 16);
        buffer.writeBytes(strongChecksum);
    }

    public ByteBuf content() {
        return buffer;
    }

    public abstract Number value();

    public abstract boolean isEqualTo(AbstractChecksum checksum);

}
