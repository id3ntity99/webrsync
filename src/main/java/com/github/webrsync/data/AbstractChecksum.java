package com.github.webrsync.data;

import io.netty.buffer.AbstractByteBuf;

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
        byte[] medBytes = new byte[3];
        nioBuffer.get(medBytes, 0, 3);
        return 0;
    }

    @Override
    protected int _getUnsignedMediumLE(int index) {
        return 0;
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

    }

    @Override
    protected void _setMedium(int index, int value) {

    }

    @Override
    protected void _setMediumLE(int index, int value) {

    }

    @Override
    protected void _setIntLE(int index, int value) {

    }

    @Override
    protected void _setLong(int index, long value) {

    }

    @Override
    protected void _setLongLE(int index, long value) {

    }

    @Override
    protected void _setInt(int index, int value) {

    }
}
