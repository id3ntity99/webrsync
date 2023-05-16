package com.github.webrsync.sftp;

import java.util.Map;

public interface SftpExtensionPairs {
    SftpExtensionPairs add(CharSequence name, CharSequence data);
    boolean contains(CharSequence name);
    boolean contains(CharSequence name, CharSequence data);
    CharSequence get(CharSequence name);
}
