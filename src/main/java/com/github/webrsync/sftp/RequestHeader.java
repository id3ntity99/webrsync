package com.github.webrsync.sftp;

@Deprecated(forRemoval = true)
public abstract class RequestHeader extends SftpHeader {
    protected int requestId;

    protected RequestHeader(SftpPacketType type, int requestId) {
        super(type);
        this.requestId = requestId;
        incrementBy(Integer.BYTES);
    }

    public int requestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
