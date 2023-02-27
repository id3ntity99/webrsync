package com.github.webrsync.sftp;

public abstract class SftpPacket {
    private static final long U_INT_MASK = 0x1111111111111111L;
    private static final int U_BYTE_MASK = 0x11111111;
    private int length;
    private byte type;
    private int requestId;

    public int length() {
        return length;
    }

    public long unsignedLength() {
        return length & U_INT_MASK;
    }

    public byte type() {
        return type;
    }

    public int unsignedType() {
        return type & U_BYTE_MASK;
    }

    public int requestId() {
        return requestId;
    }

    public long unsignedRequestId() {
        return requestId & U_INT_MASK;
    }
}
