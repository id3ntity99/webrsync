package com.github.webrsync;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;
import java.util.Properties;

public class TargetFileProcessor extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final Properties prop;

    public TargetFileProcessor(Properties prop) {
        super();
        this.prop = prop;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
        String reqUri = req.uri().replaceFirst("/", ""); // Remove first separator
        String targetUri = prop.getProperty("basePath") + reqUri;
        File targetFile = new File(targetUri);
        ctx.fireChannelRead(targetFile);
    }
}
