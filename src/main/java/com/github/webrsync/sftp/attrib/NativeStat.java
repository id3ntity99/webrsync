package com.github.webrsync.sftp.attrib;

public class NativeStat {
    private static final String LIB_PATH = System.getProperty("user.dir") +
            "/src/main/native/" + "com_github_webrsync_sftp_SftpFileAttributes.so";

    static {
        System.load(LIB_PATH);
    }

    private NativeStat() {
        //This helper class doesn't have constructor.
    }

    private static native int[] stat(String uri);

    private static native byte[] getLocalDomainName();

    private static native byte[] getOwnerName(int uid);
}
