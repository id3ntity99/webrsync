package com.github.webrsync.sftp.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LibLoader {
    private static final Logger logger = LoggerFactory.getLogger(LibLoader.class);

    public static void load(String libName) throws NoSuchFieldException {
        Properties props = new Properties();
        String path = System.getProperty("user.dir") + "/src/main/resources/app.properties";
        try (InputStream in = new FileInputStream(path)) {
            props.load(in);
            if (libName.toLowerCase().contains("acl")) {
                String libPath = props.getProperty("libacl.dev");
                System.load(libPath);
            } else if (libName.toLowerCase().contains("stat")) {
                String libPath = props.getProperty("libastat.dev");
                System.load(libPath);
            } else {
                throw new NoSuchFieldException("There is no such field in app.properties: " + libName);
            }
        } catch (IOException e) {
            logger.error("Exception raised while getting path of app.properties", e);
        }
    }
}