package daisukeclinic.model.datastructure;

public class BST<T extends Comparable<T>> {
    private BSTNode<T> root;

    public void insert(BSTNode<T> node) {
        root = insertHelper(root, node);
    }

    private BSTNode<T> insertHelper(BSTNode<T> parent, BSTNode<T> node) {
        T data = node.data;

        if (parent == null) {
            parent = node;
            return parent;
        } else if (data.compareTo(parent.data) < 0) {
            parent.left = insertHelper(parent.left, node);
        } else {
            parent.right = insertHelper(parent.right, node);
        }

        return parent;
    }

    public void display() {
        displayHelper(root);
    }

    private void displayHelper(BSTNode<T> parent) {
        if (root != null) {
            displayHelper(parent.left);
            System.out.println(root.data);
            displayHelper(parent.right);
        }
    }

}