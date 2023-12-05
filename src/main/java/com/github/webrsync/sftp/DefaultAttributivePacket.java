package com.github.webrsync.sftp;

import com.github.webrsync.sftp.attrib.SftpFileAttributes;
import io.netty.buffer.ByteBuf;

public class DefaultAttributivePacket extends DefaultRequestPacket implements AttributivePacket {
    private final SftpFileAttributes attributes;
    private final ExtensionTable extensions = new DefaultExtensionTable();

    public DefaultAttributivePacket(SftpPacketType type, int requestId, ByteBuf content, SftpFileAttributes attributes) {
        super(type, requestId, content);
        this.attributes = attributes;
    }

    @Override
    public SftpFileAttributes attributes() {
        return attributes;
    }

    @Override
    public ExtensionTable extensions() {
        return extensions;
    }
}
