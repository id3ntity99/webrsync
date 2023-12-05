package com.github.webrsync.sftp;

import io.netty.buffer.ByteBufHolder;

public interface RequestPacket extends ByteBufHolder {
    int requestId();
}
