package com.github.webrsync.sftp;

public abstract class RequestPacket extends SftpPacket<Object, Object> {
    @Override
    public RequestHeader header() {
        return (RequestHeader) header;
    }

    @Override
    public AttributeTable<Object, Object> attributes() {
        return attributes;
    }
}
