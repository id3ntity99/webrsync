package com.github.webrsync;

import com.github.webrsync.data.ChecksumHolder;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChecksumParser extends SimpleChannelInboundHandler<File> {
    private ChannelHandlerContext handlerCtx;

    private void parse(File file) throws IOException {
        try (InputStream stream = new FileInputStream(file)) {
            while (stream.available() != 0) {
                byte[] buffer = stream.readNBytes(512);
                WeakChecksum weakChecksum = ChecksumUtil.computeWeak(buffer);
                StrongChecksum strongChecksum = ChecksumUtil.computeStrong(buffer);
                ChecksumHolder holder = new ChecksumHolder(weakChecksum, strongChecksum);
                handlerCtx.write(holder);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        ctx.write(res);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, File targetFile) throws Exception {
        handlerCtx = ctx;
        parse(targetFile);
    }
}
