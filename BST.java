class Node<T extends Comparable<T>>{
    T data;
    Node<T> left;
    Node<T> right;

    public Node(T data){
        this.data = data;
    }
}

class BST<T extends Comparable<T>>{
    Node<T> root;

    public void insert(Node<T> node){
        root = insertHelper(root, node);
    }

    private Node<T> insertHelper(Node<T> root, Node<T> node){
        T data = node.data;

        if(root == null){
            root = node;
            return root;
        }
        else if(data.compareTo(root.data) < 0){
            root.left = insertHelper(root.left, node);
        }
        else{
            root.right = insertHelper(root.right, node);
        }

        return root;
    }

    public void display(){
        displayHelper(root);
    }

    private void displayHelper(Node<T> root){
        if(root != null){
            displayHelper(root.left);
            System.out.println(root.data);
            displayHelper(root.right);
        }
    }

    public boolean search(T data){
        return searchHelper(root, data);
    }

    private boolean searchHelper(Node<T> root, T data){
        if(root == null){
            return false;
        } 

        int cmp = data.compareTo(root.data);

        if(cmp ==  0){
            return true;
        }
        else if(cmp < 0){
            return searchHelper(root.left, data);
        }
        else{
            return searchHelper(root.right, data);
        }
    }

    // public T search(T data){
    //     return searchHelper(root, data);
    // }

    // private T searchHelper(Node<T> root, T data){
    //     if(root == null){
    //         return null;
    //     } 

    //     int cmp = data.compareTo(root.data);

    //     if(cmp ==  0){
    //         return root.data;
    //     } else if(cmp < 0){
    //         return searchHelper(root.left, data);
    //     } else {
    //         return searchHelper(root.right, data);
    //     }
    // }

    public void remove(T data){
        if(search(data)){
            removeHelper(root, data);
        }
        else {
            System.out.println(data + " could not found in database");
        }
    }

    private Node<T> removeHelper(Node<T> root, T data){
        if(root == null){
            return root;
        }
        
        int cmp = data.compareTo(root.data);
        
        if(cmp < 0){
            root.left = removeHelper(root.left, data);
        }
        else if(cmp > 0){
            root.right = removeHelper(root.right, data);
        }
        else {
            if(root.left == null && root.right == null){
                root = null;
            }
            else if(root.right != null){
                root.data = successor(root);
                root.right = removeHelper(root.right, root.data);
            }
            else{
                root.data = predecessor(root);
                root.left = removeHelper(root.left, root.data);
            }
        }
        return root;
    }

    private T successor(Node<T> root){
        root = root.right;
        while(root.left != null){
            root = root.left;
        }
        return root.data;
    }

    private T predecessor(Node<T> root){
        root = root.left;
        while(root.right != null){
            root = root.right;
        }
        return root.data;
    }
}