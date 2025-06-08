package daisukeclinic.model.datastructure;

public class Map<K extends Comparable<K>, V> {
    private LinkedList<MapEntry<K, V>> entries;

    public Map() {
        entries = new LinkedList<>();
    }

    public void put(K key, V value) {
        if (!isPresent(key)) {
            entries.insertBack(new MapEntry<K, V>(key, value));
        }
    }

    public V get(K key) {
        if (isPresent(key)) {
            return entries.find(new MapEntry<K, V>(key, null)).value;
        }
        return null;
    }

    public boolean isPresent(K key) {
        return entries.find(new MapEntry<K, V>(key, null)) != null;
    }

    public LinkedList<MapEntry<K, V>> getEntries() {
        return entries;
    }
}
