package com.github.webrsync.sftp;

public class DefaultRequestHeader implements RequestHeader {
    private int length;
    private final SftpPacketType type;
    private int requestId;

    public DefaultRequestHeader(SftpPacketType type, int requestId) {
        this.type = type;
        this.requestId = requestId;
    }

    @Override
    public int requestId() {
        return requestId;
    }

    @Override
    public void setRequestId(int requestId) {
        this.requestId = requestId;
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
        return null;
    }
}
