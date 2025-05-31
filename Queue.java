public class Queue<T> extends LinkedList<T> {
    public void enqueue(T data) {
        insertBack(data);
    }

    public T dequeue() {
        return removeFirst();
    }
}
