package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

/**
 * A packet used for initialization of SFTP.
 */
public class DefaultInitPacket extends AbstractSftpPacket implements InitPacket {
    private final int version;
    private final ExtensionTable extensions = new DefaultExtensionTable();

    public DefaultInitPacket(SftpPacketType type, ByteBuf content) {
        super(type, content);
        checkType(type);
        this.version = content.getInt(0);
    }

    private void checkType(SftpPacketType type) throws IllegalArgumentException {
        if (type.value() != 1 && type.value() != 2) {
            String msg = String.format("%s uses either SSH_FXP_INIT or SSH_FXP_VERSION type",
                    this.getClass().getName());
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public ExtensionTable extensions() {
        return extensions;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public DefaultInitPacket copy() {
        return (DefaultInitPacket) super.copy();
    }

    @Override
    public DefaultInitPacket duplicate() {
        return (DefaultInitPacket) super.duplicate();
    }

    @Override
    public DefaultInitPacket retainedDuplicate() {
        return (DefaultInitPacket) super.retainedDuplicate();
    }

    @Override
    public DefaultInitPacket replace(ByteBuf content) {
        return (DefaultInitPacket) super.replace(content);
    }

    @Override
    public DefaultInitPacket retain() {
        return (DefaultInitPacket) super.retain();
    }

    @Override
    public DefaultInitPacket retain(int increment) {
        return (DefaultInitPacket) super.retain(increment);
    }

    @Override
    public DefaultInitPacket touch() {
        return (DefaultInitPacket) super.touch();
    }

    @Override
    public DefaultInitPacket touch(Object hint) {
        return (DefaultInitPacket) super.touch(hint);
    }
}
