package daisukeclinic.model.datastructure;

public class Queue<T extends Comparable<T>> extends LinkedList<T> {
    public Queue() {
        super();
    }

    public void enqueue(T data) {
        insertBack(data);
    }

    public T dequeue() {
        return removeFirst();
    }

    public T peek() {
        return getFirst();
    }
}
