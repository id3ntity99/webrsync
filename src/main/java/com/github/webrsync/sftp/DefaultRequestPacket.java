package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

/**
 * @deprecated Use {@link RequestPacket} instead.
 */
@Deprecated(forRemoval = true)
public class DefaultRequestPacket extends RequestPacket {
    public DefaultRequestPacket(RequestHeader header, ByteBuf content) {
        super(header, content);
    }
}
