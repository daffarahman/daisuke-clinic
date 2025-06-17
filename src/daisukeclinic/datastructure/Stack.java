package daisukeclinic.datastructure;

public class Stack<T extends Comparable<T>> extends LinkedList<T> {

    public Stack() {
        super();
    }

    public void push(T data) {
        insertFront(data);
    }

    public T pop() {
        return removeFirst();
    }

    public T peek() {
        return getFirst();
    }
}
