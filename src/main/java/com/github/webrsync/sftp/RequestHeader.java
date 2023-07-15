package com.github.webrsync.sftp;

public interface RequestHeader extends SftpHeader {
    int requestId();
    void setRequestId(int requestId);
}
