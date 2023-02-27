import com.github.webrsync.ChecksumHolderProcessor;
import com.github.webrsync.ChecksumParser;
import com.github.webrsync.TargetFileProcessor;
import com.github.webrsync.bootstrap.NSync;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestNSync {
    private Properties getProps() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream propStream = loader.getResourceAsStream("app.properties");
            Properties props = new Properties();
            props.load(propStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNSync() throws InterruptedException {
        Properties props = getProps();
        NSync nSync = new NSync(1, new ChecksumHolderProcessor(),
                new TargetFileProcessor(props), new ChecksumParser());
        nSync.start(1235);
        assertTrue(nSync.isBound());
        nSync.terminate();
        assertFalse(nSync.isBound());
    }
}
