package daisukeclinic.datastructure;

import java.io.Serializable;

public class BST<T extends Comparable<T>> implements Serializable {
    private BSTNode<T> root;

    public static enum TraverseMode {
        TRAVERSE_PREORDER,
        TRAVERSE_INORDER,
        TRAVERSE_POSTORDER
    }

    public void insert(T data) {
        root = insertBalanceHelper(root, new BSTNode<T>(data));
    }

    private BSTNode<T> insertBalanceHelper(BSTNode<T> parent, BSTNode<T> node) {
        if (parent == null) {
            return node;
        }

        if (node.data.compareTo(parent.data) < 0) {
            parent.left = insertBalanceHelper(parent.left, node);
        } else if (node.data.compareTo(parent.data) > 0) {
            parent.right = insertBalanceHelper(parent.right, node);
        } else {
            return parent;
        }

        // update height
        parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) + 1;

        // get bf (not boyfriend but balance factor)
        int bf = getBalanceFactor(parent);

        // Left - Left Case
        if (bf > 1 && node.data.compareTo(parent.left.data) < 0) {
            return rightRotate(parent);
        }

        // Right - Right Case
        if (bf < -1 && node.data.compareTo(parent.right.data) > 0) {
            return leftRotate(parent);
        }

        // Left - Right Case
        if (bf > 1 && node.data.compareTo(parent.left.data) > 0) {
            parent.left = leftRotate(parent.left);
            return rightRotate(parent);
        }

        // Right Left Case
        if (bf < -1 && node.data.compareTo(parent.right.data) < 0) {
            parent.right = rightRotate(parent.right);
            return leftRotate(parent);
        }

        return parent;
    }

    private int getHeight(BSTNode<T> parent) {
        return (parent == null) ? 0 : parent.height;
    }

    private int getBalanceFactor(BSTNode<T> parent) {
        return (parent == null) ? 0 : getHeight(parent.left) - getHeight(parent.right);
    }

    private BSTNode<T> rightRotate(BSTNode<T> y) {
        BSTNode<T> x = y.left;
        BSTNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private BSTNode<T> leftRotate(BSTNode<T> x) {
        BSTNode<T> y = x.right;
        BSTNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    public boolean isBalanced() {
        return checkBalance(root);
    }

    private boolean checkBalance(BSTNode<T> parent) {
        if (parent == null) {
            return true;
        }

        int bf = getBalanceFactor(parent);
        if (Math.abs(bf) > 1) {
            return false;
        }

        return checkBalance(parent.left) && checkBalance(parent.right);
    }

    public void display(TraverseMode traverseMode) {
        displayHelper(root, traverseMode);
    }

    private void displayHelper(BSTNode<T> parent, TraverseMode traverseMode) {
        if (parent != null) {
            switch (traverseMode) {
                case TRAVERSE_PREORDER:
                    System.out.println(parent.data);
                    displayHelper(parent.left, traverseMode);
                    displayHelper(parent.right, traverseMode);
                    break;
                case TRAVERSE_INORDER:
                    displayHelper(parent.left, traverseMode);
                    System.out.println(parent.data);
                    displayHelper(parent.right, traverseMode);
                    break;
                case TRAVERSE_POSTORDER:
                    displayHelper(parent.left, traverseMode);
                    displayHelper(parent.right, traverseMode);
                    System.out.println(parent.data);
                    break;
                default:
                    break;
            }
        }
    }

    public LinkedList<T> getTraversedList(TraverseMode traverseMode) {
        LinkedList<T> result = new LinkedList<>();
        traverseToList(root, traverseMode, result);
        return result;
    }

    private void traverseToList(BSTNode<T> parent, TraverseMode traverseMode, LinkedList<T> list) {
        if (parent != null) {
            switch (traverseMode) {
                case TRAVERSE_PREORDER:
                    list.insertBack(parent.data);
                    traverseToList(parent.left, traverseMode, list);
                    traverseToList(parent.right, traverseMode, list);
                    break;
                case TRAVERSE_INORDER:
                    traverseToList(parent.left, traverseMode, list);
                    list.insertBack(parent.data);
                    traverseToList(parent.right, traverseMode, list);
                    break;
                case TRAVERSE_POSTORDER:
                    traverseToList(parent.left, traverseMode, list);
                    traverseToList(parent.right, traverseMode, list);
                    list.insertBack(parent.data);
                    break;
                default:
                    break;
            }
        }
    }

    public T search(T data) {
        return searchHelper(root, data);
    }

    private T searchHelper(BSTNode<T> root, T data) {
        if (root == null) {
            return null;
        }

        int compareResult = data.compareTo(root.data);
        if (compareResult == 0) {
            return root.data;
        } else if (compareResult < 0) {
            return searchHelper(root.left, data);
        } else {
            return searchHelper(root.right, data);
        }
    }

    public void delete(T data) {
        root = deleteHelper(root, data);
    }

    private BSTNode<T> deleteHelper(BSTNode<T> parent, T data) {
        if (parent == null)
            return null;

        int compareResult = data.compareTo(parent.data);
        if (compareResult < 0) {
            parent.left = deleteHelper(parent.left, data);
        } else if (compareResult > 0) {
            parent.right = deleteHelper(parent.right, data);
        } else {
            // Node found - handle deletion cases

            // Case 1: Leaf node
            if (parent.left == null && parent.right == null) {
                return null;
            }
            // Case 2: Node has only one child
            else if (parent.left == null) {
                return parent.right;
            } else if (parent.right == null) {
                return parent.left;
            }
            // Case 3: Node has two children
            else {
                // Find the minimum value in right subtree (successor)
                parent.data = findSuccessor(parent.right);
                // Delete the successor
                parent.right = deleteHelper(parent.right, parent.data);
            }
        }
        return parent;
    }

    private T findSuccessor(BSTNode<T> node) {
        T minValue = node.data;
        while (node.left != null) {
            minValue = node.left.data;
            node = node.left;
        }
        return minValue;
    }

    // private T findPredecessor(BSTNode<T> node) {
    // // Predecessor is the maximum value in left subtree
    // node = node.left;
    // while (node.right != null) {
    // node = node.right;
    // }
    // return node.data;
    // }
}