package com.github.webrsync.sftp;

@Deprecated(forRemoval = true)
public class SftpHeader {
    protected int length = 0;
    protected final SftpPacketType type;

    protected SftpHeader(SftpPacketType type) {
        this.type = type;
        incrementBy(SftpPacketType.BYTES);
    }

    public int length() {
        return length;
    }

    protected void incrementBy(int i) {
        length += i;
    }

    public SftpPacketType type() {
        return type;
    }
}
