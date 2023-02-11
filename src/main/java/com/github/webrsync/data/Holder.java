package com.github.webrsync.data;

import io.netty.buffer.ByteBuf;

public interface Holder<T> {
    ByteBuf content();

    boolean isEqualTo(T target);
}
