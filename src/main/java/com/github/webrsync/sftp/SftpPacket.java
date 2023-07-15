package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.DefaultByteBufHolder;

public abstract class SftpPacket<K, V> implements ByteBufHolder {
    protected SftpHeader header;
    protected ByteBuf content;
    protected AttributeTable<K, V> attributes;

    protected void incrementLengthBy(Object o) {
        //TODO: Implement this
    }

    protected void incrementLengthBy(ByteBuf content) {
        header.incrementBy(content.array().length);
    }

    public SftpHeader header() {
        return header;
    }

    public abstract AttributeTable<K, V> attributes();

    @Override
    public ByteBuf content() {
        return ByteBufUtil.ensureAccessible(content);
    }

    @Override
    public ByteBufHolder copy() {
        return replace(content.copy());
    }

    @Override
    public ByteBufHolder duplicate() {
        return replace(content.duplicate());
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return replace(content.retainedDuplicate());
    }

    @Override
    public ByteBufHolder replace(ByteBuf content) {
        return new DefaultByteBufHolder(content);
    }

    @Override
    public ByteBufHolder retain() {
        content.retain();
        return this;
    }

    @Override
    public ByteBufHolder retain(int increment) {
        content.retain(increment);
        return this;
    }

    @Override
    public ByteBufHolder touch() {
        content.touch();
        return this;
    }

    @Override
    public ByteBufHolder touch(Object hint) {
        content.touch(hint);
        return this;
    }

    @Override
    public boolean release() {
        return content.release();
    }

    @Override
    public boolean release(int decrement) {
        return content.release(decrement);
    }

    @Override
    public int refCnt() {
        return content.refCnt();
    }
}
