package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

/**
 * @deprecated Use {@link InitPacket} instead.
 */
@Deprecated(forRemoval = true)
public class DefaultInitPacket extends InitPacket {
    public DefaultInitPacket(SftpHeader header, ByteBuf content) {
        super(header, content);
    }
}
