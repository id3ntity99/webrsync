package com.github.webrsync.sftp.attrib;

import com.github.webrsync.sftp.internal.LibLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.NoSuchFileException;

public class NativeStat {
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeStat.class);

    static {
        try {
            LibLoader.load("stat");
        } catch (NoSuchFileException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private NativeStat() {
        //Utility class doesn't have constructor.
    }

    private static native int[] stat(String uri);

    private static native byte[] getLocalDomainName();

    private static native byte[] getOwnerName(int uid);
}
