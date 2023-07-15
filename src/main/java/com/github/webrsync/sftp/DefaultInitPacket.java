package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

public class DefaultInitPacket extends InitPacket {
    public DefaultInitPacket(InitHeader header, ByteBuf content) {
        this.header = header;
        this.content = content;
    }
}
