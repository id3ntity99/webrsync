package com.github.webrsync;

import com.github.webrsync.data.ChecksumHolder;
import com.github.webrsync.data.StrongChecksum;
import com.github.webrsync.data.WeakChecksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChecksumParser extends AbstractChecksumCalculator {
    public ChecksumParser() {
        super();
    }

    public ChecksumParser(int mod, String algorithm) {
        super(mod, algorithm);
    }

    public List<ChecksumHolder> parse(File file) throws IOException {
        List<ChecksumHolder> checksumHolders = new ArrayList<>();
        try (InputStream stream = new FileInputStream(file)) {
            while (stream.available() != 0) {
                byte[] buffer = stream.readNBytes(512);
                WeakChecksum weakChecksum = super.computeWeak(buffer);
                StrongChecksum strongChecksum = super.computeStrong(buffer);
                ChecksumHolder set = new ChecksumHolder(weakChecksum, strongChecksum);
                checksumHolders.add(set);
            }
        }
        return checksumHolders;
    }
}
