package com.github.webrsync.data;

import io.netty.buffer.ByteBuf;

@Deprecated
public interface Holder {
    ByteBuf content();

    boolean isEqualTo(Holder target);
}
