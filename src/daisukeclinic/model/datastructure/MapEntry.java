package daisukeclinic.model.datastructure;

public class MapEntry<K extends Comparable<K>, V> implements Comparable<MapEntry<K, V>> {
    public K key;
    public V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(MapEntry<K, V> other) {
        return key.compareTo(other.key);
    }
}
