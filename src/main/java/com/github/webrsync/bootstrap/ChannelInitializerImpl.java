package com.github.webrsync.bootstrap;

import com.github.webrsync.ChecksumHolderProcessor;
import com.github.webrsync.ChecksumParser;
import com.github.webrsync.TargetFileProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import java.io.InputStream;
import java.util.Properties;

final class ChannelInitializerImpl extends ChannelInitializer<Channel> {
    private final ChannelHandler[] handlers;

    public ChannelInitializerImpl(ChannelHandler... handlers) {
        this.handlers = handlers;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        for (ChannelHandler handler : handlers) {
           pipeline.addLast(handler);
        }
    }
}
