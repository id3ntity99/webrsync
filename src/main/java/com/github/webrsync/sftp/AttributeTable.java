package com.github.webrsync.sftp;

import java.util.List;
import java.util.Map;

public interface AttributeTable<K, V> extends Iterable<Map.Entry<K, V>>{
    AttributeTable<K, V> add(K key, V value);
    boolean contains(K key, V value);
    boolean contains(K key);
    V get(K key);
    V remove(K key);
    List<Map.Entry<K, V>> getAll();
    int size();
}
