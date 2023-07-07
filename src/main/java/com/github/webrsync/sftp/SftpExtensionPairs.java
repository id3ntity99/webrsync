package com.github.webrsync.sftp;

import java.util.List;
import java.util.Map;

@Deprecated
public interface SftpExtensionPairs {
    SftpExtensionPairs add(CharSequence name, CharSequence data);

    SftpExtensionPairs remove(CharSequence name);

    boolean contains(CharSequence name);

    boolean contains(CharSequence name, CharSequence data);

    CharSequence get(CharSequence name);

    List<Map.Entry<CharSequence, CharSequence>> getAll();

    int length();
}
