package com.github.webrsync.sftp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

@Deprecated(forRemoval = true)
public interface SftpPacket extends ByteBufHolder {

    SftpPacketType type();

    @Override
    ByteBuf content();

    @Override
    SftpPacket copy();

    @Override
    SftpPacket duplicate();

    @Override
    SftpPacket retainedDuplicate();

    @Override
    SftpPacket replace(ByteBuf content);

    @Override
    SftpPacket retain();

    @Override
    SftpPacket retain(int increment);

    @Override
    SftpPacket touch();

    @Override
    SftpPacket touch(Object hint);

    @Override
    boolean release();

    @Override
    boolean release(int decrement);

    @Override
    int refCnt();
}
