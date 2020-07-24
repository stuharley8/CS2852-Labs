/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 8 - Morse Code Encoder
 * Name: Stuart Harley
 * Created: 4/30/19
 */

package harleys;

import java.util.*;

/**
 * Class implements the Map<K, V> interface that will be used to store a morse code table
 * @param <K> the key, represents the character to be encoded
 * @param <V> the value, represents the code associated with the key
 */
public class LookupTable<K extends Comparable<? super K>, V> implements Map<K, V> {

    /**
     * Main method that tests the methods in the class
     * Runs a variety of private test methods
     * @param args args
     */
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        LookupTable<String, String> table = new LookupTable<>();
        map.put("A", ".-");
        map.put("Z", "--..");
        map.put("I", "..");
        table.put("A", ".-");
        table.put("Z", "--..");
        table.put("I", "..");
        System.out.println("Passed test put(): " + (testPut(map, table)));
        System.out.println("Passed test get(): " + (testGet(map, table)));
        System.out.println("Passed test size(): " + (testSize(map, table)));
        System.out.println("Passed test containsKey(): " + (testContainsKey(map, table)));
        System.out.println("Passed test remove(): " + (testRemove(map, table)));
        System.out.println("Passed test isEmpty(): " + (testClearAndIsEmpty(map, table)));
    }

    /**
     * Tests remove()
     * @param map the baseline map
     * @param table our LookupTable implementation
     * @return true if passed
     */
    private static boolean testRemove(TreeMap<String, String> map,
                                      LookupTable<String, String> table) {
        boolean passed = false;
        if (map.remove("I").equals(table.remove("I"))) {
            if (map.size() == table.size()) {
                passed = true;
            }
        }
        return passed;
    }

    /**
     * Tests containsKey()
     * @param map the baseline map
     * @param table our LookupTable implementation
     * @return true if passed
     */
    private static boolean testContainsKey(TreeMap<String, String> map,
                                           LookupTable<String, String> table) {
        boolean passed = false;
        if (map.containsKey("Z") == table.containsKey("Z") &&
                map.containsKey("foo") == table.containsKey("foo")) {
            passed = true;
        }
        return passed;
    }

    /**
     * Tests clear() and isEmpty()
     * @param map the baseline map
     * @param table our LookupTable implementation
     * @return true if passed
     */
    private static boolean testClearAndIsEmpty(TreeMap<String, String> map,
                                               LookupTable<String, String> table) {
        boolean passed = false;
        if (map.isEmpty() == table.isEmpty()) {
            map.clear();
            table.clear();
            if (map.isEmpty() && table.isEmpty()) {
                passed = true;
            }
        }
        return passed;
    }

    /**
     * Tests size()
     * @param map the baseline map
     * @param table our LookupTable implementation
     * @return true if passed
     */
    private static boolean testSize(TreeMap<String, String> map,
                                    LookupTable<String, String> table) {
        boolean passed = false;
        if (map.size() == table.size()) {
            passed = true;
        }
        return passed;
    }

    /**
     * Tests put()
     * @param map the baseline map
     * @param table our LookupTable implementation
     * @return true if passed
     */
    private static boolean testPut(TreeMap<String, String> map,
                                   LookupTable<String, String> table) {
        boolean passed = false;
        if (map.toString().equals(table.toString())) {
            passed = true;
        }
        return passed;
    }

    /**
     * Tests get()
     * @param map the baseline map
     * @param table our LookupTable implementation
     * @return true if passed
     */
    private static boolean testGet(TreeMap<String, String> map,
                                   LookupTable<String, String> table) {
        boolean passed = false;
        if (map.get("A").equals(table.get("A")) &&
                map.get("Z").equals(table.get("Z")) &&
                map.get("I").equals(table.get("I")) &&
                map.get("notcontained") == table.get("notcontained")) {
            passed = true;
        }
        return passed;
    }

    /**
     * Returns a string representation of this map.
     * @return a string representation of this map
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Entry entry : table) {
            builder.append(entry.key + "=" + entry.value + ", ");
        }
        return (builder.toString().length() > 2) ?
                builder.replace(builder.length() - 2, builder.length(), "}").toString() :
                builder.append("}").toString();
    }

    /**
     * Class represents an entry in the lookup table and contains a key and a value
     * @param <K> the key, represents the character to be encoded
     * @param <V> the value, represents the code associated with the key
     */
    private static class Entry<K extends Comparable<? super K>, V>
            implements Comparable<Entry<K, V>>, Map.Entry<K, V> {

        private K key;
        private V value;

        /**
         * Constructor that sets the value of value to null
         * @param key the key
         */
        private Entry(K key) {
            this(key, null);
        }

        /**
         * Constructor
         * @param key the key
         * @param value the value
         */
        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         */
        @Override
        public int compareTo(Entry<K, V> o) {
            return key.compareTo(o.key);
        }

        /**
         * Returns the key corresponding to this entry.
         * @return the key corresponding to this entry
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         * @return the value corresponding to this entry
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the value to the specified value
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         */
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private List<Entry<K, V>> table;

    /**
     * Constructor
     */
    public LookupTable() {
        table = new ArrayList<>();
    }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        table.clear();
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     * key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     */
    @Override
    public boolean containsKey(Object key) {
        if(key == null) {
            throw new NullPointerException("Lookup Table does not permit null entries");
        }
        int index = Collections.binarySearch(table, new Entry<>((K)key));
        return index >= 0;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     */
    @Override
    public V get(Object key) {
        if(key == null) {
            throw new NullPointerException("Lookup Table does not permit null entries");
        }
        int index = Collections.binarySearch(table, new Entry<>((K)key));
        return (index >= 0) ? table.get(index).getValue() : null;
    }

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * {@code m} is said to contain a mapping for a key {@code k} if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * {@code true}.)
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * @throws NullPointerException          if the specified key or value is null
     *                                       and this map does not permit null keys or values
     */
    @Override
    public V put(K key, V value) {
        if(key == null || value == null) {
            throw new NullPointerException("Lookup Table does not permit null entries");
        }
        int index = Collections.binarySearch(table, new Entry<>(key));
        V returnValue = null;
        if(index > 0) {
            returnValue = table.get(index).setValue(value);
        } else {
            table.add(-index - 1, new Entry<>(key, value));
        }
        return returnValue;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * The map will not contain a mapping for the specified key once the
     * call returns.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * @throws ClassCastException            if the key is of an inappropriate type for
     *                                       this map
     * @throws NullPointerException          if the specified key is null and this
     *                                       map does not permit null keys
     */
    @Override
    public V remove(Object key) {
        if(key == null) {
            throw new NullPointerException("Lookup Table does not permit null entries");
        }
        V previousValue = null;
        int index = Collections.binarySearch(table, new Entry<>((K)key));
        if(index > 0) {
            previousValue = table.remove(index).getValue();
        }
        return previousValue;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return table.size();
    }

    /**
     * Unsupported Operation
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    /**
     * Unsupported Operation
     * @param value value whose presence in this map is to be tested
     * @return {@code true} if this map maps one or more keys to the
     */
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    /**
     * Unsupported Operation
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    /**
     * Unsupported Operation
     * @return a collection view of the values contained in this map
     */
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    /**
     * Unsupported Operation
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Unsupported Operation");
    }
}
