package sftp;

import com.github.webrsync.sftp.attrib.SftpFileAttributes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSftpFileAttributes {
    private static final String EXPECTED_OWNER = "lee@(none)";
    private static final String REG_FILE_PATH = System.getProperty("user.dir") + "/src/test/example.txt";
    private static final String PRE_UNIX_TIME_PATH = System.getProperty("user.dir") + "/src/test/before_unix_time.txt";
    private static final int EXPECTED_TYPE = 0x8000;
    private static final int EXPECTED_ALLOC_SIZE = 4096 * 8;
    private static final int EXPECTED_PERMS = 644;

    @Test
    void testStat() {
        SftpFileAttributes attrs = new SftpFileAttributes(REG_FILE_PATH);
        assertTrue(attrs.isReg());
        assertEquals(EXPECTED_OWNER, attrs.owner());
        assertEquals(EXPECTED_TYPE, attrs.type());
        assertEquals(EXPECTED_ALLOC_SIZE, attrs.allocationSize());
        assertEquals(EXPECTED_PERMS, attrs.permissions());
    }

    @Test
    void testStatOfFileWithPreUnixTime() {
        SftpFileAttributes attrs = new SftpFileAttributes(PRE_UNIX_TIME_PATH);
        assertTrue(attrs.atime() < 0);
        assertTrue(attrs.mtime() < 0);
        assertEquals(attrs.atime() * -1, attrs.atimeNSeconds());
        assertEquals(attrs.mtime() * -1, attrs.mtimeNSeconds());
    }
}
