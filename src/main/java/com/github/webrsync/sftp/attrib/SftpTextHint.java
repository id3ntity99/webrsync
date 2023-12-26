package com.github.webrsync.sftp.attrib;

public class SftpTextHint {
    public static final SftpTextHint SSH_FILEXFER_ATTR_KNOWN_TEXT = new SftpTextHint((byte) 0x00);
    public static final SftpTextHint SSH_FILEXFER_ATTR_GUESSED_TEXT = new SftpTextHint((byte) 0x01);
    public static final SftpTextHint SSH_FILEXFER_ATTR_KNOW_BINARY = new SftpTextHint((byte) 0x02);
    public static final SftpTextHint SSH_FILEXFER_ATTR_GUESSED_BINARY = new SftpTextHint((byte) 0x03);
    private final byte value;

    private SftpTextHint(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }
}