package com.github.webrsync.sftp.fs;

public class AceType {
    public static final AceType ACE4_ACCESS_ALLOWED_ACE_TYPE = new AceType(0x00000000);
    public static final AceType ACE4_ACCESS_DENIED_ACE_TYPE = new AceType(0x00000001);
    public static final AceType ACE_SYSTEM_AUDIT_ACE_TYPE = new AceType(0x00000002);
    private static final AceType ACE_SYSTEM_ALARM_ACE_TYPE = new AceType(0x00000003);

    private final int value;
    private AceType(int value) {
       this.value = value;
    }

    public int value() {
        return value;
    }
}
