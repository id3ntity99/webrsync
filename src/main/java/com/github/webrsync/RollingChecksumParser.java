package com.github.webrsync;

import com.github.webrsync.data.ChecksumHolder;
import com.github.webrsync.data.RollingWindowBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class RollingChecksumParser extends AbstractChecksumCalculator {
    private final Map<Integer, Integer> idxMap = new HashMap<>();
    private final List<ChecksumHolder> checksumHolders;

    public RollingChecksumParser(List<ChecksumHolder> checksumHolders) {
        this.checksumHolders = checksumHolders;
    }

    public RollingChecksumParser(List<ChecksumHolder> checksumHolders, int mod, String algorithm) {
        super(mod, algorithm);
        this.checksumHolders = checksumHolders;
    }

    private void initIndexMap() {
        Objects.requireNonNull(checksumHolders);
        for (int i = 0; i < checksumHolders.size(); i++) {
            ChecksumHolder checksumHolder = checksumHolders.get(i);
            int hash = checksumHolder.getWeakChecksum().getHash16();
            idxMap.putIfAbsent(hash, i);
        }
    }

    private ChecksumHolder findMatchingBlock() {
        return null;
    }

    public void rollingParse(File file) throws IOException {
        try (InputStream fileStream = new FileInputStream(file)) {
            RollingWindowBuffer buffer = new RollingWindowBuffer(fileStream);
            while (buffer.offset()<= buffer.offset()) {

            }
        }
    }
}