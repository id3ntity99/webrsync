package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

@Deprecated
public class DefaultSftpPacket implements SftpPacket {
    private final int length;
    protected final SftpPacketType type;
    protected final ByteBuf content;
    private final SftpExtensionPair[] extensions;
    //private final EnumMap<String, String> extensionPairs;

    public DefaultSftpPacket(SftpPacketType type, ByteBuf content) {
        this(type, content, null);
    }

    public DefaultSftpPacket(SftpPacketType type, ByteBuf content, SftpExtensionPair... extensions) {
        this.type = type;
        this.content = content;
        this.length = 1 + content.readableBytes(); // length of the packet as byte, excluding length field itself.
        this.extensions = extensions;
        if (extensions != null) {
            // this.extensionPairs = new EnumMap();
        } else {
            // extensionPairs = null;
        }
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public SftpPacketType type() {
        return type;
    }

    @Override
    public ByteBuf content() {
        return content;
    }

    @Override
    public SftpPacket copy() {
        ByteBuf copied = content.copy();
        return new DefaultSftpPacket(type, copied);
    }

    @Override
    public SftpPacket duplicate() {
        ByteBuf duplicated = content.duplicate();
        return new DefaultSftpPacket(type, duplicated);
    }

    @Override
    public SftpPacket replace(ByteBuf content) {
        return new DefaultSftpPacket(type, content);
    }

    @Override
    public SftpPacket retain() {
        content.retain();
        return this;
    }

    @Override
    public SftpPacket retain(int increment) {
        content.retain(increment);
        return this;
    }

    @Override
    public SftpPacket retainedDuplicate() {
        return null;
    }

    @Override
    public SftpPacket touch() {
        content.touch();
        return this;
    }

    @Override
    public SftpPacket touch(Object hint) {
        content.touch(hint);
        return this;
    }

    @Override
    public int refCnt() {
        return content.refCnt();
    }

    @Override
    public boolean release() {
        return content.release();
    }

    @Override
    public boolean release(int decrement) {
        return content.release(decrement);
    }
}
