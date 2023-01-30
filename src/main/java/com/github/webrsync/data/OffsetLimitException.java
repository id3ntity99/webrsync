package com.github.webrsync.data;

public class OffsetLimitException extends RuntimeException {
    public OffsetLimitException(int currentOffset, int offsetLimit) {
        super("Incrementing from current offset exceeds the offset limit. " +
                "current offset = " + currentOffset + " , offset-limit = " + offsetLimit);
    }
}
