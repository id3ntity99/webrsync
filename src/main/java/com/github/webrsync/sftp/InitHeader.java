package com.github.webrsync.sftp;

@Deprecated
public abstract class InitHeader extends SftpHeader {
    //Marker interface
    protected InitHeader(SftpPacketType type) {
       super(type);
    }
}
