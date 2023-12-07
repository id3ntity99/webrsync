package com.github.webrsync.sftp.fs;

public class SetXattrFlag {
    public static final SetXattrFlag XATTR_CREATE = new SetXattrFlag(1);
    public static final SetXattrFlag XATTR_REPLACE = new SetXattrFlag(2);
    private final int value;
    private SetXattrFlag(int value) {
       this.value = value;
    }

    public int value() {
       return value;
    }
}
