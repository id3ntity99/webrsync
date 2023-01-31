package com.github.webrsync.data;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AbstractChecksum extends AbstractByteBuf implements Checksum {
    private final ByteBuffer nioBuffer;

    protected AbstractChecksum(int weakChecksum) {
        super(4);
        this.nioBuffer = ByteBuffer.allocate(4);
        nioBuffer.putInt(0, weakChecksum);
    }

    protected AbstractChecksum(byte[] strongChecksum) { // Use byte array since the ByteBuffer doesn't support BigInteger type
        super(16);
        this.nioBuffer = ByteBuffer.allocate(16);
        nioBuffer.put(strongChecksum);
    }

    @Override
    protected byte _getByte(int index) {
        return nioBuffer.get(index);
    }

    @Override
    protected int _getInt(int index) {
        return nioBuffer.getInt(index);
    }

    @Override
    protected int _getIntLE(int index) {
        return nioBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt(index);
    }

    @Override
    protected int _getUnsignedMedium(int index) {
        int leftMost = (nioBuffer.get(index) & 0xff) << 16; // Note that, the bitwise "&" will auto-promotes the result to int type
        int mid = (nioBuffer.get(index + 1) & 0xff) << 8;
        int rightMost = (nioBuffer.get(index + 2) & 0xff);
        return leftMost | mid | rightMost;
    }

    @Override
    protected int _getUnsignedMediumLE(int index) {
        int leftMost = (nioBuffer.get(index + 2) & 0xff) << 16;
        int mid = (nioBuffer.get(index + 1) & 0xff) << 8;
        int rightMost = (nioBuffer.get(index) & 0xff);
        return leftMost | mid | rightMost;
    }

    @Override
    protected long _getLong(int index) {
        return nioBuffer.getLong(index);
    }

    @Override
    protected long _getLongLE(int index) {
        return nioBuffer.order(ByteOrder.LITTLE_ENDIAN).getLong(index);
    }

    @Override
    protected short _getShort(int index) {
        return nioBuffer.getShort(index);
    }

    @Override
    protected short _getShortLE(int index) {
        return nioBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort(index);
    }

    @Override
    protected void _setByte(int index, int value) {
        nioBuffer.put(index, (byte) value);
    }

    @Override
    protected void _setShort(int index, int value) {
        nioBuffer.putShort(index, (short) value);
    }

    @Override
    protected void _setShortLE(int index, int value) {
        short shortLE = ByteBufUtil.swapShort((short) value);
        nioBuffer.putShort(index, shortLE);
    }

    @Override
    protected void _setMedium(int index, int value) {
        byte leftMostByte = (byte) (value >>> 16);
        byte midByte = (byte) (value >>> 8);
        byte rightMostByte = (byte) (value);
        nioBuffer.put(index, leftMostByte);
        nioBuffer.put(index + 1, midByte);
        nioBuffer.put(index + 2, rightMostByte);
    }

    @Override
    protected void _setMediumLE(int index, int value) {
        byte leftMostByte = (byte) (value >>> 16);
        byte midByte = (byte) (value >>> 8);
        byte rightMostByte = (byte) (value);
        nioBuffer.put(index, rightMostByte);
        nioBuffer.put(index + 1, midByte);
        nioBuffer.put(index + 2, leftMostByte);
    }

    @Override
    protected void _setIntLE(int index, int value) {
        nioBuffer.putInt(index, ByteBufUtil.swapInt(value));
    }

    @Override
    protected void _setLong(int index, long value) {
        nioBuffer.putLong(index, value);
    }

    @Override
    protected void _setLongLE(int index, long value) {
        nioBuffer.putLong(index, ByteBufUtil.swapLong(value));
    }

    @Override
    protected void _setInt(int index, int value) {
        nioBuffer.putInt(index, value);
    }
}
