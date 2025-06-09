package daisukeclinic.model.datastructure;

import java.io.Serializable;

public class Node<T> implements Serializable {
    public T data;
    public Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
}
