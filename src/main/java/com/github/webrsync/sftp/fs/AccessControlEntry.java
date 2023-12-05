package com.github.webrsync.sftp.fs;

//TODO: Maybe apply the builder pattern later?
public class AccessControlEntry {
    private AceType type;
    private AceFlag flag;
    private AceMask mask;
    private String who;

    public AccessControlEntry(AceType type, AceFlag flag, AceMask mask, String who) {
        this.type = type;
        this.flag = flag;
        this.mask = mask;
        this.who = who;
    }

    public AceType type() {
        return type;
    }

    public AceFlag flag() {
        return flag;
    }

    public AceMask mask() {
        return mask;
    }

    public String who() {
        return who;
    }
}
