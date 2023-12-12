package sftp.attrib;

import com.github.webrsync.sftp.attrib.SftpFileAttributes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSftpFileAttributes {
    private static String expectedOwner;
    private static String regularFile;
    private static String timeModifiedFile;
    private static final int EXPECTED_TYPE = 0x8000;
    private static final int EXPECTED_ALLOC_SIZE = 4096 * 8;
    private static final int EXPECTED_PERMS = 644;

    @BeforeAll
    public static void setup() {
        String propsPath = System.getProperty("user.dir") + "/src/main/resources/app.properties";
        try (InputStream in = new FileInputStream(propsPath)) {
            Properties props = new Properties();
            props.load(in);
            String cmd = "touch -am --date \"1969-12-31 23:59:59\" " + timeModifiedFile;
            Runtime.getRuntime().exec(cmd);
            regularFile = props.getProperty("regular.file");
            timeModifiedFile = props.getProperty("modified.timestamp.file");
            expectedOwner = props.getProperty("owner");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testStat() {
        SftpFileAttributes attrs = new SftpFileAttributes(regularFile);
        assertTrue(attrs.isReg());
        assertEquals(expectedOwner, attrs.owner());
        assertEquals(EXPECTED_TYPE, attrs.type());
        System.out.println(attrs.type());
        assertEquals(EXPECTED_ALLOC_SIZE, attrs.allocationSize());
        assertEquals(EXPECTED_PERMS, attrs.permissions());
    }

    @Test
    void testStatOfFileWithPreUnixTime() {
        SftpFileAttributes attrs = new SftpFileAttributes(timeModifiedFile);
        assertTrue(attrs.mtime() < 0);
        assertEquals(attrs.mtime() * -1, attrs.mtimeNSeconds());
    }
}
