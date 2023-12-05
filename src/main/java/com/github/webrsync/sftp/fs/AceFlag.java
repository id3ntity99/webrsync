package com.github.webrsync.sftp.fs;

public class AceFlag {
    public static final AceFlag ACE4_FILE_INHERIT_ACE = new AceFlag(0x00000001);
    public static final AceFlag ACE4_DIRECTORY_INHERIT_ACE = new AceFlag(0x00000002);
    public static final AceFlag ACE4_NO_PROPAGATE_INHERIT_ACE = new AceFlag(0x00000004);
    public static final AceFlag ACE4_INHERIT_ONLY = new AceFlag(0x00000008);
    public static final AceFlag ACE4_SUCCESSFUL_ACCESS_ACE_FLAG = new AceFlag(0x00000010);
    public static final AceFlag ACE4_FAILED_ACCESS_ACE_FLAG = new AceFlag(0x00000020);
    public static final AceFlag ACE4_IDENTIFIER_GROUP = new AceFlag(0x00000040);
    public final int value;

    private AceFlag(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}

