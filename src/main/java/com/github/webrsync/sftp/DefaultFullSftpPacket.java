package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;
import net.bytebuddy.pool.TypePool;

public class DefaultFullSftpPacket extends DefaultSftpPacket implements FullSftpPacket {
    private final int requestId;

    public DefaultFullSftpPacket(int requestId, SftpPacketType type, ByteBuf payload) {
        super(type, payload);
        this.requestId = requestId;
    }

    @Override
    public int requestId() {
        return requestId;
    }
}
