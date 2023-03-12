package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

public class DefaultSftpPacket extends DefaultByteBufHolder implements SftpPacket {
    private final int length;
    private final SftpPacketType type;

    public DefaultSftpPacket(SftpPacketType type, ByteBuf payload) {
        super(payload);
        this.type = type;
        this.length = 1 + payload.readableBytes(); // length of the packet as byte, excluding length field itself.
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public SftpPacketType type() {
        return type;
    }
}
