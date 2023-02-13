package com.github.webrsync;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.util.Properties;

public class TargetFileProcessor extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final Properties prop;

    public TargetFileProcessor(Properties prop) {
        super();
        this.prop = prop;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (req.method() != HttpMethod.GET) {
            sendHttpResponse(ctx, HttpResponseStatus.BAD_REQUEST);
        }
        File targetFile = handleHttpRequest(req);
        if (targetFile.exists()) {
            ctx.fireChannelRead(targetFile);
            sendHttpResponse(ctx, HttpResponseStatus.OK);
        } else {
            sendHttpResponse(ctx, HttpResponseStatus.BAD_REQUEST);
        }
    }

    private File handleHttpRequest(FullHttpRequest req) {
        // URI format: /BASE_PATH/USER_NAME/path/to/specific/file
        String uri = prop.getProperty("basePath") + req.uri();
        return new File(uri);
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, status);
        ctx.write(res);
    }
}
