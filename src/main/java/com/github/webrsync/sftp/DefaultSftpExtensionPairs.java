package com.github.webrsync.sftp;

import java.util.*;

/**
 *
 * Implementation of DefaultSftpExtensionPairs.
 */
@Deprecated
public class DefaultSftpExtensionPairs implements SftpExtensionPairs {
    private static final int SIZE = 128;
    private static final byte HASH_MASK = (byte) (SIZE - 1);
    private final ExtensionPair[] entries = new ExtensionPair[SIZE];
    private final ExtensionPair fakeHead = new ExtensionPair();

    public DefaultSftpExtensionPairs() {
        // Empty Constructor
        // TODO: Adjust size of table.
    }

    protected int doHash(CharSequence key) {
        return key.hashCode() & HASH_MASK;
    }

    private void validate(CharSequence name) {
        if (get(name) != null) { // Prevent duplicated key.
            String msg = String.format("The key '%s' already exists.", name);
            throw new UnsupportedOperationException(msg);
        }
    }

    @Override
    public SftpExtensionPairs add(CharSequence name, CharSequence data) {
        Objects.requireNonNull(name);
        validate(name);
        int idx = doHash(name);
        ExtensionPair newExtension = new ExtensionPair(name, data, entries[idx]);
        ExtensionPair tmp = fakeHead.nextEntry();
        newExtension.setNextEntry(tmp);
        fakeHead.setNextEntry(newExtension);
        entries[idx] = newExtension;
        return this;
    }

    @Override
    public SftpExtensionPairs remove(CharSequence name) {
        Objects.requireNonNull(name);
        int idx = doHash(name);
        ExtensionPair target = entries[idx];
        while (target != null) {
            if (name.equals(target.key)) {
                entries[idx] = null;
            }
            target = target.nextNode();
        }
        return this;
    }

    @Override
    public CharSequence get(CharSequence name) {
        Objects.requireNonNull(name);
        int idx = doHash(name);
        ExtensionPair current = entries[idx];
        CharSequence value = null;
        while (current != null) {
            if (name.equals(current.key)) {
                value = current.value;
            }
            current = current.nextNode;
        }
        return value;
    }

    @Override
    public boolean contains(CharSequence name) {
        Objects.requireNonNull(name);
        return get(name) != null;
    }

    @Override
    public boolean contains(CharSequence name, CharSequence data) {
        Objects.requireNonNull(name);
        CharSequence value = get(name);
        if (value != null)
            return value.equals(data);
        return false;
    }

    @Override
    public List<Map.Entry<CharSequence, CharSequence>> getAll() {
        List<Map.Entry<CharSequence, CharSequence>> extensionList = new ArrayList<>();
        for (ExtensionPair entry : entries) {
            if (entry == null)
                continue;
            extensionList.add(entry);
            if (entry.nextNode() != null) {
                Iterator<ExtensionPair> iterator = new NodeIterator(entry);
                while (iterator.hasNext())
                    extensionList.add(iterator.next());
            }
        }
        return extensionList;
    }

    @Override
    public int length() {
        return 0;
    }

    private static class NodeIterator implements Iterator<ExtensionPair> {
        private ExtensionPair currentNode;

        public NodeIterator(ExtensionPair headNode) {
            currentNode = headNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode.nextNode != null;
        }

        @Override
        public ExtensionPair next() {
            if (!hasNext())
                throw new NoSuchElementException();
            currentNode = currentNode.nextNode;
            return currentNode;
        }

        @Override
        public void remove() {
            String msg = String.format("You cannot remove node using {%s}", this.getClass().getName());
            throw new UnsupportedOperationException(msg);
        }
    }

    private static class ExtensionPair implements Map.Entry<CharSequence, CharSequence> {
        private final CharSequence key;
        private CharSequence value;
        private ExtensionPair nextNode = null;
        private ExtensionPair nextEntry = null;

        public ExtensionPair(CharSequence key, CharSequence value, ExtensionPair nextNode) {
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }

        public ExtensionPair() {
            key = null;
        }

        @Override
        public CharSequence getKey() {
            return key;
        }

        @Override
        public CharSequence getValue() {
            return value;
        }

        @Override
        public CharSequence setValue(CharSequence newValue) {
            CharSequence oldVal = value;
            value = newValue;
            return oldVal;
        }

        public void setNextEntry(ExtensionPair nextEntry) {
            this.nextEntry = nextEntry;
        }

        public ExtensionPair nextNode() {
            return nextNode;
        }

        public ExtensionPair nextEntry() {
            return nextEntry;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry))
                return false;
            Map.Entry<CharSequence, CharSequence> target = (Map.Entry<CharSequence, CharSequence>) obj;
            return (getKey() == null ? target.getKey() == null : getKey().equals(target.getKey())) &&
                    (getValue() == null ? target.getValue() == null : getValue().equals(target.getValue()));
        }

        @Override
        public int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode()) ^
                    (getValue() == null ? 0 : getValue().hashCode());
        }

    }
}
