package com.github.webrsync.sftp;

import java.util.List;
import java.util.Map;

public interface ExtensionTable extends Iterable<Map.Entry<String, String>> {
    ExtensionTable add(String key, String value);

    boolean contains(String key, String value);

    boolean contains(String key);

    String get(String key);

    String remove(String key);

    List<Map.Entry<String, String>> getAll();

    int size();
}
