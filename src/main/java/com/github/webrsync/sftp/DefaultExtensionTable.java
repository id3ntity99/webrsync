package com.github.webrsync.sftp;

import java.util.*;
import java.util.function.Consumer;

/**
 * An implementation of hash table data structure.
 * This class would be used for storing SFTP attributes such as
 */
public class DefaultExtensionTable implements ExtensionTable {
    private int capacity = 128;
    private final byte hashMask = (byte) (capacity - 1);
    private final ExtensionPair[] entries = new ExtensionPair[capacity];
    private final ExtensionPair dummyHead = new ExtensionPair();
    private int size = 0;

    public DefaultExtensionTable() {
        // Empty Constructor.
    }

    public DefaultExtensionTable(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Hash the input key using {@link DefaultExtensionTable#hashMask}
     *
     * @param key A key to hash.
     * @return An integer that will be used as the index number for the {@link DefaultExtensionTable#entries}.
     */
    private int hash(String key) {
        return key.hashCode() & hashMask;
    }

    /**
     * Check duplication of the key that is about to be added.
     *
     * @param key A key to check for duplication.
     */
    private void checkDuplication(String key) {
        if (contains(key))
            throw new IllegalArgumentException("Duplicated key is not allowed.");
    }


    /**
     * Add new key-value pair into this table.
     *
     * @param key   A key to insert.
     * @param value A value to insert.
     * @return The instantiated object that is now containing the key-value pair just inserted.
     */
    @Override
    public ExtensionTable add(String key, String value) {
        Objects.requireNonNull(key);
        checkDuplication(key);
        int i = hash(key);
        entries[i] = new ExtensionPair(key, value, i, dummyHead, entries[i]);
        size++;
        return this;
    }

    /**
     * Check if the table contains the given key-value pair.
     *
     * @param key   A key to find match
     * @param value A value to find match.
     * @return true if there is matching key-value pair and false if there is not.
     */
    @Override
    public boolean contains(String key, String value) {
        Objects.requireNonNull(key);
        String targetVal = get(key);
        return targetVal != null && targetVal.equals(value);
    }

    /**
     * Check if the table contains the given key.
     *
     * @param key A key to find match.
     * @return true if there is matching key with any values and false if there is not.
     */
    @Override
    public boolean contains(String key) {
        Objects.requireNonNull(key);
        return get(key) != null;
    }

    /**
     * Search and return the value that has the same key as the given.
     *
     * @param key A key that is used to find the value.
     * @return A value whose key is matching to the given key. Returns null if there are no matching key.
     */
    @Override
    public String get(String key) {
        Objects.requireNonNull(key);
        int i = hash(key);
        ExtensionPair currentNode = entries[i];
        String value = null;
        while (currentNode != null) {
            if (key.equals(currentNode.getKey())) {
                value = currentNode.getValue();
            }
            currentNode = currentNode.getNext();
        }
        return value;
    }

    @Override
    public String remove(String key) {
        Objects.requireNonNull(key);
        int i = hash(key);
        ExtensionPair current = entries[i];
        String value = null;
        if (current == null)
            return null;
        if (key.equals(current.getKey())) {
            entries[i] = current.getNext();
            current.removeFromInsertionOrder();
            size--;
            return current.getValue();
        }
        ExtensionPair next = current.getNext();
        while (next != null) {
            if (key.equals(next.getKey())) {
                value = next.getValue();
                current.setNext(next.getNext());
                next.removeFromInsertionOrder();
            }
            current = next;
            next = next.getNext();
        }
        size--;
        return value;
    }

    @Override
    public List<Map.Entry<String, String>> getAll() {
        List<Map.Entry<String, String>> entryList = new ArrayList<>();
        ExtensionPair current = dummyHead.getAfter();
        while (current != dummyHead) {
            entryList.add(current);
            current = current.getAfter();
        }
        return entryList;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return new EntryIterator(dummyHead);
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, String>> action) {
        Iterator<Map.Entry<String, String>> iterator = iterator();
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    /**
     * This class iterates the whole entries that are linked by an insertion order.
     */
    private class EntryIterator implements Iterator<Map.Entry<String, String>> {
        private ExtensionPair current;

        public EntryIterator(ExtensionPair head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            ExtensionPair after = current.getAfter();
            return (after != null) && (!after.equals(dummyHead));
        }

        @Override
        public Map.Entry<String, String> next() {
            if (!hasNext())
                throw new NoSuchElementException("No next entries exist");
            ExtensionPair next = current.getAfter();
            current = next;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("ExtensionPair cannot be removed with iterator");
        }
    }

    private static class ExtensionPair implements Map.Entry<String, String> {
        private final String key;
        private String value;
        /**
         * A node next to the current node.
         */
        private ExtensionPair next;
        /**
         * A node before current node of the insertion order.
         */
        private ExtensionPair before;

        /**
         * A node after current node of the insertion order.
         */
        private ExtensionPair after;
        /**
         * The hashcode that is generated by {@link DefaultExtensionTable#hash(String Key)}.
         */
        private final int hashCode;

        /**
         * @param key       The key of this entry.
         * @param value     The value of this entry
         * @param orderHead A dummy head node for the insertion order of the all
         *                  entries inserted into {@link ExtensionTable}. This is used to traverse the linked list of
         *                  insertion order using {@link ExtensionPair#after} and {@link ExtensionPair#before} fields.
         * @param next      A next node of the linked list that is used as a collision resolution
         *                  for the {@link ExtensionTable}.
         */
        public ExtensionPair(String key, String value, int hashCode,
                             ExtensionPair orderHead, ExtensionPair next) {
            this.key = key;
            this.value = value;
            this.hashCode = hashCode;
            this.next = next;
            this.after = orderHead;
            this.before = orderHead.getBefore();
            before.setAfter(this);
            after.setBefore(this);
        }

        /**
         * This constructor is used instantiating the dummy head node(entry).
         * Note that, the {@link ExtensionPair#after} and {@link ExtensionPair#before} fields of the dummy head point
         * the dummy head itself. <br>
         * i.e. {@code this.after = this.before = this}.
         */
        public ExtensionPair() {
            this.key = null;
            this.value = null;
            this.next = null;
            this.before = this.after = this;
            this.hashCode = -1;
        }

        /**
         * Return the key of the entry.
         *
         * @return Key of the entry.
         */
        @Override
        public String getKey() {
            return key;
        }

        /**
         * Return the value of the entry.
         *
         * @return Value of the entry.
         */
        @Override
        public String getValue() {
            return value;
        }

        /**
         * Setting new value. Use this method instead of assigning new value using {@code myEntry.value = newValue}.
         *
         * @param value A new value to set.
         * @return An old value that was just replaced with the new one.
         */
        @Override
        public String setValue(String value) {
            String old = this.value;
            this.value = value;
            return old;
        }

        /**
         * Get next node.
         *
         * @return The next node.
         */
        public ExtensionPair getNext() {
            return this.next;
        }

        public void setNext(ExtensionPair next) {
            this.next = next;
        }

        public void setBefore(ExtensionPair before) {
            this.before = before;
        }

        public ExtensionPair getBefore() {
            return before;
        }

        public void setAfter(ExtensionPair after) {
            this.after = after;
        }

        public ExtensionPair getAfter() {
            return after;
        }

        public void removeFromInsertionOrder() {
            before.setAfter(after);
            after.setBefore(before);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ExtensionPair))
                return false;
            ExtensionPair extensionPair = (ExtensionPair) obj;
            return extensionPair.hashCode() == hashCode;
        }
    }
}
