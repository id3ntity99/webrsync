package com.github.webrsync.sftp.attrib;

import java.util.Objects;

public class SftpFileAttributes {
    private static final String LIB_PATH = System.getProperty("user.dir") +
            "/src/main/native/" + "com_github_webrsync_sftp_SftpFileAttributes.so";
    private static final int S_IFMT = 0xF000;
    private static final int S_IRWXU = 0x01c0;          // 0000 0001 1100 0000
    private static final int S_IRWXG = S_IRWXU >>> 3;   // 0000 0000 0011 1000
    private static final int S_IRWXO = S_IRWXG >>> 3;   // 0000 0000 0000 0111
    private final int stDev;
    private final int stIno;
    private final int stMode;
    private final int stNLinks;
    private final int stUid;
    private final int stGid;
    private final int stRDev;
    private final int stSize;
    private final int stBlkSize;
    private final int stBlocks;
    private final long stATime;
    private final long stMTime;
    private final long stCTime;

    static {
        System.load(LIB_PATH);
    }

    public SftpFileAttributes(String uri) {
        Objects.requireNonNull(uri);
        int[] statValues = stat(uri);
        this.stDev = statValues[0];
        this.stIno = statValues[1];
        this.stMode = statValues[2];
        this.stNLinks = statValues[3];
        this.stUid = statValues[4];
        this.stGid = statValues[5];
        this.stRDev = statValues[6];
        this.stSize = statValues[7];
        this.stBlkSize = statValues[8];
        this.stBlocks = statValues[9];
        this.stATime = statValues[10];
        this.stMTime = statValues[11];
        this.stCTime = statValues[12];
    }

    //TODO: Delete these native methods refactored into NativeStat
    private native int[] stat(String uri);

    private native byte[] getLocalDomainName();

    private native byte[] getOwnerName(int uid);

    public int type() {
        return stMode & S_IFMT;
    }

    public boolean isFifo() {
        return type() == 0x1000;
    }

    public boolean isChr() {
        return type() == 0x2000;
    }

    public boolean isDir() {
        return type() == 0x4000;
    }

    public boolean isBlk() {
        return type() == 0x6000;
    }

    public boolean isReg() {
        return type() == 0x8000;
    }

    public boolean isLnk() {
        return type() == 0xA000;
    }

    public boolean isSock() {
        return type() == 0xC000;
    }

    public long size() {
        return stSize;
    }

    public long allocationSize() {
        return (long) stBlocks * stBlkSize;
    }

    public String owner() {
        byte[] domainNameBytes = getLocalDomainName();
        byte[] ownerNameBytes = getOwnerName(stUid);
        String domain = new String(domainNameBytes);
        String owner = new String(ownerNameBytes);
        return owner + "@" + domain;
    }

    /**
     * Extract permission-bits from the ST_MODE. <br>
     * <br>
     * The locations of ones from the bit string [0000 0001 1100 0000] represent the "owner permission". <br>
     * The locations of ones from the bit string [0000 0000 0011 1000] represent the "group permission". <br>
     * The locations of ones from the bit string [0000 0000 0000 0111] represent the "other permission". <br>
     * <br>
     * Note that this method returns the {@code int} type decimal value as the chmod.
     * For example, if this method returned 644, then it means the file has read/write owner permission, read group
     * permission, and read other permission. Thus, <i><b>-rw-r--r--</b></i>. <br>
     * <br>
     * It is user's task to divide and use the returned value.
     *
     * @return Discretionary access control permission number. An integer with 3-digit, each of which represents owner,
     * permission, group permission, other permission respectively from the highest digit.
     */
    public int permissions() {
        int ownerPerm = (stMode & S_IRWXU) >>> 6; // Extract owner permission-bits and reduce length
        int groupPerm = (stMode & S_IRWXG) >>> 3;
        int otherPerm = (stMode & S_IRWXO);
        return ownerPerm * 100 + groupPerm * 10 + otherPerm;
    }

    private int toPositiveInt(long n) {
        return ((int) n) * -1;
    }

    public long atime() {
        return stATime;
    }

    public int atimeNSeconds() {
        if (stATime < 0)
            return toPositiveInt(stATime);
        return 0;
    }

    public long mtime() {
        return stMTime;
    }

    public int mtimeNSeconds() {
        if (stMTime < 0)
            return toPositiveInt(stMTime);
        return 0;
    }

    public long ctime() {
        return stCTime;
    }

    public int ctimeNSeconds() {
        if (stCTime < 0)
            return toPositiveInt(stCTime);
        return 0;
    }
}
