package com.github.webrsync.sftp;

public abstract class InitPacket extends SftpPacket<String, String> {
    @Override
    public InitHeader header() {
       return (InitHeader) header;
    }

    @Override
    public AttributeTable<String , String> attributes() {
        return attributes;
    }
}
