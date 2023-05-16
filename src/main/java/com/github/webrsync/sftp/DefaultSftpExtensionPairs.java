package com.github.webrsync.sftp;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class DefaultSftpExtensionPairs implements SftpExtensionPairs {
    private static final int SIZE = 128;
    private static final byte HASH_MASK = (byte) (SIZE - 1);
    private final ExtensionPair[] entries = new ExtensionPair[SIZE];

    public DefaultSftpExtensionPairs() {
        // Empty Constructor
    }

    private int doHash(CharSequence key) {
        return key.hashCode() & HASH_MASK;
    }

    @Override
    public SftpExtensionPairs add(CharSequence name, CharSequence data) {
        // TODO: Prevent allowing duplicated keys.
        int idx = doHash(name);
        /* If an entry to put extension pair is null, then the ExtensionPair.next will be the null-pointer.
         * Else, the ExtensionPair.next will be the one that pre-occupied the entry.
         * It is like inserting a new node in front of the head node in the linked list.
         */
        entries[idx] = new ExtensionPair(name, data, entries[idx]);
        return this;
    }

    @Override
    public CharSequence get(CharSequence name) {
        int idx = doHash(name);
        ExtensionPair current = entries[idx];
        CharSequence value = null;
        while(current != null) {
            if (name.equals(current.key)) {
                value = current.value;
            }
            current = current.next;
        }
        return value;
    }

    @Override
    public boolean contains(CharSequence name) {
        return get(name) != null;
    }

    @Override
    public boolean contains(CharSequence name, CharSequence data) {
        CharSequence value = get(name);
        if (value != null)
            return value.equals(data);
        return false;
    }

    private static class NodeIterator implements Iterator<Map.Entry<CharSequence, CharSequence>> {
        private ExtensionPair currentNode;

        public NodeIterator(ExtensionPair headNode) {
            currentNode = headNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode.next != null;
        }

        @Override
        public Map.Entry<CharSequence, CharSequence> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            currentNode = currentNode.next;
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
        private ExtensionPair next;

        public ExtensionPair(CharSequence key, CharSequence value, ExtensionPair next) {
            this.key = key;
            this.value = value;
            this.next = next;
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
