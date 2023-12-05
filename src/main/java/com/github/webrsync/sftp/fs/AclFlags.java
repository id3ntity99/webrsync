package com.github.webrsync.sftp.fs;

public class AclFlags {
    public static final AclFlags SFX_ACL_CONTROL_INCLUDED = new AclFlags(0x00000001);
    public static final AclFlags SFX_ACL_CONTROL_PRESENT = new AclFlags(0x00000002);
    public static final AclFlags SFX_ACL_CONTROL_INHERIT = new AclFlags(0x00000004);
    public static final AclFlags SFX_ACL_AUDIT_ALARM_INCLUDED = new AclFlags(0x00000010);
    public static final AclFlags SFX_ACL_AUDIT_ALARM_INHERITED = new AclFlags(0x00000020);
    private final int value;

    private AclFlags(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
