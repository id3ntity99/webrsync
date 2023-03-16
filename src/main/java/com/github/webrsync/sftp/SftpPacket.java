package com.github.webrsync.sftp;

import io.netty.buffer.ByteBufHolder;

public interface SftpPacket extends ByteBufHolder {
    int length();

    SftpPacketType type();
}
