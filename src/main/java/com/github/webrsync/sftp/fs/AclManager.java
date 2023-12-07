package com.github.webrsync.sftp.fs;

import com.github.webrsync.sftp.internal.LibLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AclManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AclManager.class);

    static {
        try {
            LibLoader.load("acl");
        } catch (NoSuchFieldException e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }

    private AclManager() {
        //Helper class does not have constructor
    }

    public static native int setSftpAcl(String path, AccessControlList acl, SetXattrFlag flag);

    public static native AccessControlList getSftpAcl(String path);

    public static native boolean doesExist(String path);
}
