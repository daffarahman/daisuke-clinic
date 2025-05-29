public abstract class LinkedList<T> {
    protected Node<T> head;

    protected void insertFront(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    protected void insertBack(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    protected T removeFirst() {
        if (head == null) {
            return null;
        } else {
            T removed = head.data;
            head = head.next;
            return removed;
        }
    }

    protected T removeLast() {
        if (head == null) {
            return null;
        } else {
            Node<T> current = head;
            while (current.next != null && current.next.next != null) {
                current = current.next;
            }

            T removed = current.next.data;
            current.next = null;
            return removed;
        }
    }
}
