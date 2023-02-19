package com.github.webrsync;

import com.github.webrsync.data.Holder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class ChecksumHolderProcessor extends ChannelOutboundHandlerAdapter {
    private WebSocketFrame makeFrame(Holder holder) {
        ByteBuf concatChecksum = holder.content();
        return new BinaryWebSocketFrame(concatChecksum);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Holder) {
            Holder holder = (Holder) msg;
            WebSocketFrame frame = makeFrame(holder);
            ctx.write(frame);
            ctx.flush();
        } else if(msg instanceof HttpResponse){
            ctx.write(msg);
            ctx.flush();
        }
    }
}
