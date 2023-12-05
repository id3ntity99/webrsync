package com.github.webrsync.sftp.codec;

import com.github.webrsync.sftp.ExtensionTable;
import com.github.webrsync.sftp.SftpPacket;
import com.github.webrsync.sftp.SftpPacketType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

import java.util.Iterator;
import java.util.Map;

public class SftpInitPacketEncoder extends MessageToByteEncoder<SftpPacket> {
    private void encodeType(ByteBuf out, SftpPacketType type) {
        out.writeByte(type.value());
    }

    private void encodeContent(ByteBuf out, ByteBuf content) {
        out.writeBytes(content);
    }

    private void encodeAttributes(ByteBuf out, ExtensionTable attributes) {
        Iterator<Map.Entry<String, String>> iterator = attributes.iterator();
        Map.Entry<String, String> current;
        while (iterator.hasNext()) {
            current = iterator.next();
            out.writeCharSequence(current.getKey(), CharsetUtil.UTF_8);
            out.writeCharSequence(current.getValue(), CharsetUtil.UTF_8);
        }
    }

    private int calculateLength(SftpPacket packet) {
        //int len = SftpPacketType.BYTES + packet.content().readableBytes();
        //for (Map.Entry<String, String> current : packet.attributes()) {
        //    len += current.getKey().getBytes(CharsetUtil.UTF_8).length;
        //    len += current.getValue().getBytes(CharsetUtil.UTF_8).length;
        //}
        //return len;
        return 0;
    }

    private void encodeLength(ByteBuf out, int length) {
        out.writeInt(length);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, SftpPacket msg,
                          ByteBuf out) throws Exception {
        //int len = calculateLength(msg);
        //encodeLength(out, len);
        //encodeType(out, msg.type());
        //encodeContent(out, msg.content());
        //encodeAttributes(out, msg.attributes());
        //ctx.writeAndFlush(out);
    }
}
