package com.github.webrsync.sftp;

import io.netty.buffer.ByteBufHolder;

public interface InitPacket extends ByteBufHolder {
    ExtensionTable extensions();
    int version();
}
