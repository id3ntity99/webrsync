package com.github.webrsync.sftp.attrib;

public class AttribBits {
    public static final AttribBits READ_ONLY = new AttribBits(0x00000001);
    public static final AttribBits SYSTEM = new AttribBits(0x00000002);
    public static final AttribBits HIDDEN = new AttribBits(0x00000004);
    public static final AttribBits CASE_INSENSITIVE = new AttribBits(0x00000008);
    public static final AttribBits ARCHIVE = new AttribBits(0x00000010);
    public static final AttribBits ENCRYPTED = new AttribBits(0x00000020);
    public static final AttribBits COMPRESSED = new AttribBits(0x00000040);
    public static final AttribBits SPARSE = new AttribBits(0x00000080);
    public static final AttribBits APPEND_ONLY = new AttribBits(0x00000100);
    public static final AttribBits IMMUTABLE = new AttribBits(0x00000200);
    public static final AttribBits SYNC = new AttribBits(0x00000400);
    public static final AttribBits TRANSLATION_ERR = new AttribBits(0x00000800);
    private static final int SIZE = 12;
    private static int cnt = 0;
    private final int value;
    private final int[] validBits = new int[SIZE];

    private AttribBits(int value) {
        this.value = value;
        validBits[cnt] = value;
        cnt++;
    }

    public int value() {
        return value;
    }
}
