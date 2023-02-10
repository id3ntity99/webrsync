package com.github.webrsync;

import com.github.webrsync.data.ChecksumHolder;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class ChecksumParser extends SimpleChannelInboundHandler<File> {
    private ChannelHandlerContext handlerCtx;

    private void parse(File file) throws IOException {
        Queue<ChecksumHolder> holderQue = new LinkedList<>();
        try (InputStream stream = new FileInputStream(file)) {
            while (stream.available() != 0) {
                byte[] buffer = stream.readNBytes(512);
                WeakChecksum weakChecksum = ChecksumUtil.computeWeak(buffer);
                StrongChecksum strongChecksum = ChecksumUtil.computeStrong(buffer);
                ChecksumHolder holder = new ChecksumHolder(weakChecksum, strongChecksum);
                holderQue.add(holder);
            }
            handlerCtx.write(holderQue);
        }
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
