public class Stack<T> {
    private class Node {
        T value;
        Node next;
        Node(T value){
            this.value = value;
        }
    }


    private Node head;

    public Stack() {
        head = null;
    }


    public void push(T newItem) {
        Node newNode = new Node(newItem);
        newNode.next = head;
        head = newNode;
    }

    public T pop() {
        Node temp = head;
        head = head.next;
        return temp.value;
    }

    public T peek() {
        return head.value;
    }

    public boolean isEmpty() {
        if (head == null){
            return true;
        }
        return false;
    }

    public int size() {
        int counter = 0;
        if (head == null){
            return 0;
        }
        else if (head.next == null){
            return 1;
        }
        else{
            Node current = head;
            while (current != null){
                counter++;
                current = current.next;
            }
            return counter;
        }


    }
}