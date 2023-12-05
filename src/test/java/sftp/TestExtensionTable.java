package sftp;

import com.github.webrsync.sftp.DefaultExtensionTable;
import com.github.webrsync.sftp.ExtensionTable;
import com.github.webrsync.sftp.SftpExtensionNames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestExtensionTable {
    static final String VERSIONS = "1, 2, 3";
    static final String SELECTED_VER = "1";
    static final String CHARSET = "utf8";
    ExtensionTable table;
    Iterator<Map.Entry<String, String>> iterator;

    @BeforeEach
    void setUpTable() {
        this.table = new DefaultExtensionTable();
        table.add(SftpExtensionNames.VERSIONS, VERSIONS)
                .add(SftpExtensionNames.VERSION_SELECT, SELECTED_VER)
                .add(SftpExtensionNames.FILENAME_CHARSET, CHARSET);
        iterator = table.iterator();
    }

    @Test
    void testDuplicatedAdd() {
        assertThrows(IllegalArgumentException.class, () -> table.add(SftpExtensionNames.VERSIONS, "2"));
    }

    @Test
    void testGetAll() {
        List<Map.Entry<String, String>> entries = table.getAll();
        assertEquals(3, entries.size());
        assertEquals(VERSIONS, entries.get(0).getValue());
        assertEquals(SELECTED_VER, entries.get(1).getValue());
        assertEquals(CHARSET, entries.get(2).getValue());
    }

    @Test
    void testGetLength() {
        assertEquals(3, table.size());
    }

    @Test
    void testRemove() {
        String removedVal = table.remove(SftpExtensionNames.VERSIONS);
        assertNull(table.get(SftpExtensionNames.VERSIONS));
        assertEquals(VERSIONS, removedVal);
        List<Map.Entry<String, String>> entries = table.getAll();
        assertEquals(SftpExtensionNames.VERSION_SELECT, entries.get(0).getKey());
        assertEquals(SftpExtensionNames.FILENAME_CHARSET, entries.get(1).getKey());
        assertEquals(2, table.size());
    }

    @Test
    void testRemoveKeyNotExists() {
        assertNull(table.remove("Some key"));
    }

    @Test
    void testContainsKey() {
        assertTrue(table.contains(SftpExtensionNames.VERSION_SELECT));
        assertTrue(table.contains(SftpExtensionNames.VERSIONS));
        assertTrue(table.contains(SftpExtensionNames.FILENAME_CHARSET));
    }

    @Test
    void testContainKeyAndValue() {
        assertTrue(table.contains(SftpExtensionNames.VERSIONS, VERSIONS));
        assertTrue(table.contains(SftpExtensionNames.VERSION_SELECT, SELECTED_VER));
        assertTrue(table.contains(SftpExtensionNames.FILENAME_CHARSET, CHARSET));
    }

    @Test
    void testContainKeyAndValueDoesNotExist() {
        assertFalse(table.contains(SftpExtensionNames.VERSIONS, "1"));
        table.remove(SftpExtensionNames.VERSIONS);
        assertFalse(table.contains(SftpExtensionNames.VERSIONS, VERSIONS));
    }

    @Test
    void testIteratorRemove() {
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void testIteratorHasNext() {
        assertTrue(iterator.hasNext());
    }

    @Test
    void testIteratorGetNext() {
        Map.Entry<String, String> next = iterator.next();
        List<Map.Entry<String, String>> entries = table.getAll();
        assertEquals(entries.get(0), next);
    }

    @Test
    void testIteratorUsingWhileLoop() {
        int cnt = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey());
            cnt++;
        }
        assertEquals(3, cnt);
    }

    @Test
    void testForEach() {
        //TODO: Find how to test forEach statement
        table.forEach((e) -> {
            System.out.println(e.getKey());
            System.out.println(e.getValue());
        });
    }
}