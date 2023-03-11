package com.github.webrsync.sftp;

public interface SftpPacket {
    int length();

    SftpPacketType type();
}
