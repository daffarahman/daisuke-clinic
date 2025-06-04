package daisukeclinic.model.datastructure;

public class BSTNode<T extends Comparable<T>> {
    public T data;
    public BSTNode<T> left;
    public BSTNode<T> right;

    public BSTNode(T data) {
        this.data = data;
    }
}
