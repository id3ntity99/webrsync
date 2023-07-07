package com.github.webrsync;

import com.github.webrsync.data.ChecksumHolder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class RollingChecksumParser {
    private final Map<Integer, Integer> idxMap = new HashMap<>();
    private final List<ChecksumHolder> checksumHolders;

    public RollingChecksumParser(List<ChecksumHolder> checksumHolders) {
        this.checksumHolders = checksumHolders;
    }

    public RollingChecksumParser(List<ChecksumHolder> checksumHolders, int mod, String algorithm) {
        this.checksumHolders = checksumHolders;
    }

    private void initIndexMap() {
    }

    public void rollingParse(File file) throws IOException {
    }
}