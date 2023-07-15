package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

public class DefaultRequestPacket extends RequestPacket {
    public DefaultRequestPacket(RequestHeader header, ByteBuf content) {
        this.header = header;
        this.content = content;
    }
}
