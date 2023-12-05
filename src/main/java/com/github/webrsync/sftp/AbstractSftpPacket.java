package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

/**
 * Skeletal implementation of {@link ByteBufHolder}.
 */
public class AbstractSftpPacket implements ByteBufHolder {
    protected final SftpPacketType type;
    protected final ByteBuf content;

    protected AbstractSftpPacket(SftpPacketType type, ByteBuf content) {
        this.type = type;
        this.content = content;
    }

    public SftpPacketType type() {
        return type;
    }

    @Override
    public ByteBuf content() {
        return content;
    }

    @Override
    public AbstractSftpPacket copy() {
        return replace(content.copy());
    }

    @Override
    public AbstractSftpPacket duplicate() {
        return replace(content.duplicate());
    }

    @Override
    public AbstractSftpPacket retainedDuplicate() {
        return replace(content.retainedDuplicate());
    }

    @Override
    public AbstractSftpPacket replace(ByteBuf content) {
        return new AbstractSftpPacket(type, content);
    }

    @Override
    public AbstractSftpPacket retain() {
        content.retain();
        return this;
    }

    @Override
    public AbstractSftpPacket retain(int increment) {
        content.retain(increment);
        return this;
    }

    @Override
    public AbstractSftpPacket touch() {
        content.touch();
        return this;
    }

    @Override
    public AbstractSftpPacket touch(Object hint) {
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
