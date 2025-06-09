package daisukeclinic.model.datastructure;

import java.io.Serializable;

public class BSTNode<T extends Comparable<T>> implements Serializable {
    public T data;
    public BSTNode<T> left;
    public BSTNode<T> right;
    int height;

    public BSTNode(T data) {
        this.data = data;
        this.height = 1;
    }
}
