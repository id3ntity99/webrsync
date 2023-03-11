package com.github.webrsync.sftp;

public class SftpPacketType {
    public static final SftpPacketType SSH_FXP_INIT = new SftpPacketType((byte) 1);
    public static final SftpPacketType SSH_FXP_VERSION = new SftpPacketType((byte) 2);
    public static final SftpPacketType SSH_FXP_OPEN = new SftpPacketType((byte) 3);
    public static final SftpPacketType SSH_FXP_CLOSE = new SftpPacketType((byte) 4);
    public static final SftpPacketType SSH_FXP_READ = new SftpPacketType((byte) 5);
    public static final SftpPacketType SSH_FXP_WRITE = new SftpPacketType((byte) 6);
    public static final SftpPacketType SSH_FXP_LSTAT = new SftpPacketType((byte) 7);
    public static final SftpPacketType SSH_FXP_FSTAT = new SftpPacketType((byte) 8);
    public static final SftpPacketType SSH_FXP_SETSTAT = new SftpPacketType((byte) 9);
    public static final SftpPacketType SSH_FXP_FSETSTAT = new SftpPacketType((byte) 10);
    public static final SftpPacketType SSH_FXP_OPENDIR = new SftpPacketType((byte) 11);
    public static final SftpPacketType SSH_FXP_READDIR = new SftpPacketType((byte) 12);
    public static final SftpPacketType SSH_FXP_REMOVE = new SftpPacketType((byte) 13);
    public static final SftpPacketType SSH_FXP_MKDIR = new SftpPacketType((byte) 14);
    public static final SftpPacketType SSH_FXP_RMDIR = new SftpPacketType((byte) 15);
    public static final SftpPacketType SSH_FXP_REALPATH = new SftpPacketType((byte) 16);
    public static final SftpPacketType SSH_FXP_STAT = new SftpPacketType((byte) 17);
    public static final SftpPacketType SSH_FXP_RENAME = new SftpPacketType((byte) 18);
    public static final SftpPacketType SSH_FXP_READLINK = new SftpPacketType((byte) 19);
    public static final SftpPacketType SSH_FXP_LINK = new SftpPacketType((byte) 21);
    public static final SftpPacketType SSH_FXP_BLOCK = new SftpPacketType((byte) 22);
    public static final SftpPacketType SSH_FXP_UNBLOCK = new SftpPacketType((byte) 23);
    public static final SftpPacketType SSH_FXP_STATUS = new SftpPacketType((byte) 101);
    public static final SftpPacketType SSH_FXP_HANDLE = new SftpPacketType((byte) 102);
    public static final SftpPacketType SSH_FXP_DATA = new SftpPacketType((byte) 103);
    public static final SftpPacketType SSH_FXP_NAME = new SftpPacketType((byte) 104);
    public static final SftpPacketType SSH_FXP_ATTRS = new SftpPacketType((byte) 105);
    public static final SftpPacketType SSH_FXP_EXTENDED = new SftpPacketType((byte) 200);
    public static final SftpPacketType SSH_FXP_EXTENDED_REPLY = new SftpPacketType((byte) 201);

    private final byte value;

    public SftpPacketType(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }

    public String name() {
        //TODO: Impl This
        return null;
    }
}
