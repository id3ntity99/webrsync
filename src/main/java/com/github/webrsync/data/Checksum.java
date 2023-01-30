package com.github.webrsync.data;

public interface Checksum {
    Number getValue();

    boolean equals(Checksum checksum);
}
