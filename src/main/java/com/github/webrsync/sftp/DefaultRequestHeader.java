package com.github.webrsync.sftp;

@Deprecated(forRemoval = true)
public class DefaultRequestHeader extends RequestHeader {
    public DefaultRequestHeader(SftpPacketType type, int requestId) {
        super(type, requestId);
    }
}
