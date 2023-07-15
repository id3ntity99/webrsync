package com.github.webrsync.sftp;

public interface SftpHeader {
    int length();

    void incrementBy(int i);

    SftpPacketType type();
}
