package com.github.webrsync.sftp.fs;

public class AceMask {
    public static final AceMask ACE4_READ_DATA = new AceMask(0x00000001);
    public static final AceMask ACE4_LIST_DIRECTORY = new AceMask(0x00000001);
    public static final AceMask ACE4_WRITE_DATA = new AceMask(0x00000002);
    public static final AceMask ACE4_ADD_FILE = new AceMask(0x00000002);
    public static final AceMask ACE4_APPEND_DATA = new AceMask(0x00000004);
    public static final AceMask ACE4_ADD_SUBDIRECTORY = new AceMask(0x00000004);
    public static final AceMask ACE4_READ_NAMED_ATTRS = new AceMask(0x00000008);
    public static final AceMask ACE4_WRITE_NAMED_ATTRS = new AceMask(0x00000010);
    public static final AceMask ACE4_EXECUTE = new AceMask(0x00000020);
    public static final AceMask ACE4_DELETE_CHILD = new AceMask(0x00000040);
    public static final AceMask ACE4_READ_ATTRIBUTES = new AceMask(0x00000080);
    public static final AceMask ACE4_WRITE_ATTRIBUTES = new AceMask(0x00000100);
    public static final AceMask ACE4_DELETE = new AceMask(0x00010000);
    public static final AceMask ACE4_READ_ACL = new AceMask(0x00020000);
    public static final AceMask ACE4_WRITE_ACL = new AceMask(0x00040000);
    public static final AceMask ACE4_WRITE_OWNER = new AceMask(0x00080000);
    public static final AceMask ACE4_SYNCHRONIZE = new AceMask(0x00100000);

    private final int value;

    private AceMask(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
