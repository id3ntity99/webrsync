package com.github.webrsync.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.channels.AlreadyBoundException;

public class NSync {
    private final ChannelInitializer<Channel> initializer;
    private final ServerBootstrap bootstrap;
    private final NioEventLoopGroup group;
    private boolean isBound = false;

    public NSync(int nThread, ChannelHandler... handlers) {
        bootstrap = new ServerBootstrap();
        group = new NioEventLoopGroup(nThread);
        this.initializer = new ChannelInitializerImpl(handlers);
    }

    private void doBootstrap() {
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer);
    }

    public void start(int port) throws InterruptedException {
        doBootstrap();
        if (!isBound) { // If channel isn't bound yet
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();
            if (future.isSuccess())
                isBound = true;
        } else {
            throw new AlreadyBoundException();
        }
    }

    public void terminate() {
        group.shutdownGracefully().syncUninterruptibly();
        isBound = false;
    }
}
