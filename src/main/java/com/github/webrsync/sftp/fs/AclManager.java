package com.github.webrsync.sftp.fs;

public class AclManager {
    private static final String LIB_PATH = System.getProperty("user.dir") + "/src/main/native/webrsync_sftp_acl/"
            + "libacl.so";

    static {
        System.load(LIB_PATH);
    }

    private AclManager() {
        //Helper class does not have constructor
    }

    public static native int setSftpAcl(String path, AccessControlList acl);

    public static native AccessControlList getSftpAcl(String path);

    public static native boolean doesExist(String path);
}
