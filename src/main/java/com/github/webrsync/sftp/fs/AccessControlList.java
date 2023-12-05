package com.github.webrsync.sftp.fs;

public class AccessControlList {
    private final AclFlags aclFlags;
    private final int aceCount;
    private final AccessControlEntry[] aces;

    public AccessControlList(AclFlags aclFlags, int aceCount, AccessControlEntry[] aces) {
        this.aclFlags = aclFlags;
        this.aceCount = aceCount;
        this.aces = aces;
    }

    public AclFlags aclFlags() {
        return aclFlags;
    }

    public int aceCount() {
        return aceCount;
    }

    public AccessControlEntry[] aces() {
        return aces;
    }
}
