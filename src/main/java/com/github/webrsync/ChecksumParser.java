package com.github.webrsync;

import com.github.webrsync.data.ChecksumHolder;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChecksumParser extends SimpleChannelInboundHandler<File> {
    private ChannelHandlerContext handlerCtx;

    public ChecksumParser() {
    }

    //TODO: This method will use this.handlerCtx to send the ChecksumHolders
    private void parse(File file) throws IOException {
        List<ChecksumHolder> checksumHolders = new ArrayList<>();
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
    public void channelRead0(ChannelHandlerContext ctx, File targetFile) throws Exception {
        handlerCtx = ctx;
        parse(targetFile);
        // TODO:
        //  3. As soon as the parsing is completed, send the ChecksumHolders so that the ChannelOutboundHandler can
        //  transform the ChecksumHolder to the WebsocketFrame
    }
}
