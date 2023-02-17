package com.github.webrsync.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Skeletal implementation of {@link Holder}. This abstract class contains internal {@link ByteBuf} whose reader index is 0.
 * Instantiation of the subclasses will write data into the buffer, causing the writer index to be incremented, but will not cause
 * the reader index to be incremented since the read() operation of the ByteBuf will not be used here.
 */
public abstract class AbstractChecksum implements Holder {
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

    public boolean isEqualTo(Holder checksum) {
        if (checksum.getClass() == this.getClass()) {
            int targetValue = checksum.content().getInt(0);
            return value().equals(targetValue);
        }
        return false;
    }

}
