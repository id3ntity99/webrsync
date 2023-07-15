package com.github.webrsync.sftp;

public class DefaultInitHeader implements InitHeader {
    private int length;
    private final SftpPacketType type;

    public DefaultInitHeader(SftpPacketType type) {
        this.type = type;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public void incrementBy(int i) {
       length += i;
    }

    @Override
    public SftpPacketType type() {
        return type;
    }
}
