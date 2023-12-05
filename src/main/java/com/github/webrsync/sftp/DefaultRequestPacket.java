package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;

public class DefaultRequestPacket extends AbstractSftpPacket implements RequestPacket {
    private final int requestId;

    public DefaultRequestPacket(SftpPacketType type, int requestId, ByteBuf content) {
        super(type, content);
        checkType(type);
        checkRequestId(requestId);
        this.requestId = requestId;
    }

    //TODO: These "checkSomething()" methods should be represented with some Validator class(es) that
    //  validates packets.(23.10.01)
    private void checkRequestId(int requestId) throws IllegalArgumentException {
        if (requestId < 0) {
            throw new IllegalArgumentException("request-id cannot be negative. Given: " + requestId);
        }
    }

    private void checkType(SftpPacketType type) throws IllegalArgumentException {
        if ((type.value() == 1 || type.value() == 2)) {
            String msg = String.format("Both SSH_FXP_INIT and SSH_FXP_VERSION cannot be the type of %s.",
                    this.getClass().getName());
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public int requestId() {
        return requestId;
    }

    @Override
    public DefaultRequestPacket copy() {
        return (DefaultRequestPacket) super.copy(); //Using super satisfies open/closed principle
    }

    @Override
    public DefaultRequestPacket duplicate() {
        return (DefaultRequestPacket) super.duplicate();
    }

    @Override
    public DefaultRequestPacket retainedDuplicate() {
        return (DefaultRequestPacket) super.retainedDuplicate();
    }

    @Override
    public DefaultRequestPacket replace(ByteBuf content) {
        return (DefaultRequestPacket) super.replace(content);
    }

    @Override
    public DefaultRequestPacket retain() {
        return (DefaultRequestPacket) super.retain();
    }

    @Override
    public DefaultRequestPacket retain(int increment) {
        return (DefaultRequestPacket) super.retain(increment);
    }

    @Override
    public DefaultRequestPacket touch() {
        return (DefaultRequestPacket) super.touch();
    }

    @Override
    public DefaultRequestPacket touch(Object hint) {
        return (DefaultRequestPacket) super.touch(hint);
    }
}
