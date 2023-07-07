package com.github.webrsync.sftp;

import java.util.*;
import java.util.function.Consumer;

public class DefaultAttributeTable<K, V> implements AttributeTable<K, V> {
    private static final int SIZE = 128;
    private static final byte HASH_MASK = (byte) (SIZE - 1);
    private final Entry<K, V>[] entries = new Entry[SIZE];
    private final Entry<K, V> dummyHead = new Entry<>();
    private int size = 0;

    public DefaultAttributeTable() {
        // Empty Constructor.
    }

    /**
     * Hash the input key using {@link DefaultAttributeTable#HASH_MASK}
     *
     * @param key A key to hash.
     * @return An integer that will be used as the index number for the {@link DefaultAttributeTable#entries}.
     */
    private int hash(K key) {
        return key.hashCode() & HASH_MASK;
    }

    /**
     * Check duplication of the key that is about to be added.
     *
     * @param key A key to check for duplication.
     */
    private void checkDuplication(K key) {
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
    public AttributeTable<K, V> add(K key, V value) {
        Objects.requireNonNull(key);
        checkDuplication(key);
        int i = hash(key);
        entries[i] = new Entry<>(key, value, dummyHead, entries[i]);
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
    public boolean contains(K key, V value) {
        Objects.requireNonNull(key);
        V targetVal = get(key);
        return targetVal != null && targetVal.equals(value);
    }

    /**
     * Check if the table contains the given key.
     *
     * @param key A key to find match.
     * @return true if there is matching key with any values and false if there is not.
     */
    @Override
    public boolean contains(K key) {
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
    public V get(K key) {
        Objects.requireNonNull(key);
        int i = hash(key);
        Entry<K, V> currentNode = entries[i];
        V value = null;
        while (currentNode != null) {
            if (key.equals(currentNode.getKey())) {
                value = currentNode.getValue();
            }
            currentNode = currentNode.getNext();
        }
        return value;
    }

    @Override
    public V remove(K key) {
        Objects.requireNonNull(key);
        int i = hash(key);
        Entry<K, V> current = entries[i];
        V value = null;
        if (current == null)
            return null;
        if (key.equals(current.getKey())) {
            entries[i] = current.getNext();
            current.removeFromInsertionOrder();
            size--;
            return current.getValue();
        }
        Entry<K, V> next = current.getNext();
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
    public List<Map.Entry<K, V>> getAll() {
        List<Map.Entry<K, V>> entryList = new ArrayList<>();
        Entry<K, V> current = dummyHead.getAfter();
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
    public Iterator<Map.Entry<K, V>> iterator() {
        return new EntryIterator(dummyHead);
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> action) {
        AttributeTable.super.forEach(action);
    }

    /**
     * This class iterates the entries that are linked as an insertion order.
     */
    private class EntryIterator implements Iterator<Map.Entry<K, V>> {
        private Entry<K, V> current;

        public EntryIterator(Entry<K, V> head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current.getAfter() != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext())
                throw new NoSuchElementException("No next entries exist");
            Entry<K, V> next = current.getAfter();
            current = next;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Entry cannot be removed with iterator");
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;
        /**
         * A node next to the current node.
         */
        private Entry<K, V> next;
        /**
         * A node before current node of the insertion order.
         */
        private Entry<K, V> before;

        /**
         * A node after current node of the insertion order.
         */
        private Entry<K, V> after;

        /**
         * @param key       The key of this entry.
         * @param value     The value of this entry
         * @param orderHead A dummy head node for the insertion order of the all
         *                  entries inserted into {@link AttributeTable}. This is used to traverse the linked list of
         *                  insertion order using {@link Entry#after} and {@link Entry#before} fields.
         * @param next      A next node of the linked list that is used as a collision resolution
         *                  for the {@link AttributeTable}.
         */
        public Entry(K key, V value, Entry<K, V> orderHead, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.after = orderHead;
            this.before = orderHead.getBefore();
            before.setAfter(this);
            after.setBefore(this);
        }

        /**
         * This constructor is used instantiating the dummy head node(entry).
         * Note that, the {@link Entry#after} and {@link Entry#before} fields of the dummy head point
         * the dummy head itself. <br>
         * i.e. {@code this.after = this.before = this}.
         */
        public Entry() {
            this.next = null;
            this.before = this.after = this;
        }

        /**
         * Return the key of the entry.
         *
         * @return Key of the entry.
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Return the value of the entry.
         *
         * @return Value of the entry.
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Setting new value. Use this method instead of assigning new value using {@code myEntry.value = newValue}.
         *
         * @param value A new value to set.
         * @return An old value that was just replaced with the new one.
         */
        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        /**
         * Get next node.
         *
         * @return The next node.
         */
        public Entry<K, V> getNext() {
            return this.next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }

        public void setBefore(Entry<K, V> before) {
            this.before = before;
        }

        public Entry<K, V> getBefore() {
            return before;
        }

        public void setAfter(Entry<K, V> after) {
            this.after = after;
        }

        public Entry<K, V> getAfter() {
            return after;
        }

        public void removeFromInsertionOrder() {
            before.setAfter(after);
            after.setBefore(before);
        }
    }
}
