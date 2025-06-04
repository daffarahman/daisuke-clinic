package daisukeclinic.model.datastructure;

public class LinkedList<T extends Comparable<T>> {
    protected Node<T> head;
    protected Node<T> last;
    protected int size;

    public LinkedList() {
        last = head;
        size = 0;
    }

    public void insertFront(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void insertBack(T data) {
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
        size++;
    }

    public void remove(T data) {
        if (head == null)
            return;

        if (head.data.compareTo(data) == 0) {
            head = head.next;
            size--;
            return;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.compareTo(data) == 0) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    public T removeFirst() {
        if (head == null) {
            return null;
        } else {
            T removed = head.data;
            head = head.next;
            size--;
            return removed;
        }
    }

    public T removeLast() {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            T removed = head.data;
            head = null;
            size--;
            return removed;
        }

        Node<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }

        T removed = current.next.data;
        current.next = null;
        size--;
        return removed;

    }

    public T get(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.compareTo(data) == 0) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public T getFirst() {
        if (head != null) {
            return head.data;
        }
        return null;
    }

    public Node<T> getHead() {
        return head;
    }

    public T getIndex(int targetIndex) {
        if (head == null) {
            return null;
        }

        int i = 0;
        Node<T> current = head;
        while (current != null) {
            if (i == targetIndex)
                return current.data;
            i++;
            current = current.next;
        }

        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
    }

    public void clear() {
        head = null;
        last = head;
        size = 0;
    }
}
