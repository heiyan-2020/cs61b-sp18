public class LinkedListDeque<T> {
    private class StuffNode {
        T first;
        StuffNode prev;
        StuffNode next;

        StuffNode(T var, StuffNode front, StuffNode res) {
            first = var;
            prev = front;
            next = res;
        }
    }
    private int sizeOfDeque;
    private StuffNode sentinel;
    public LinkedListDeque() {
        sizeOfDeque = 0;
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    public int size() {
        return sizeOfDeque;
    }

    public void addFirst(T item) {
        StuffNode firstNode = new StuffNode(item, sentinel, sentinel.next);
        sentinel.next.prev = firstNode;
        sentinel.next = firstNode;
        sizeOfDeque++;
    }

    public void addLast(T item) {
        StuffNode lastNode = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = lastNode;
        sentinel.prev = lastNode;
        sizeOfDeque++;
    }

    public boolean isEmpty() {
        return sizeOfDeque == 0;
    }

    public void printDeque() {
        StuffNode iter = sentinel;
        StringBuilder builder = new StringBuilder();
        while (iter.next != sentinel) {
            builder.append(iter.next.first + " ");
            iter = iter.next;
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder.toString());
    }

    public T removeFirst() {
        if (sizeOfDeque == 0) {
            return null;
        }
        StuffNode firstNode = sentinel.next;
        sentinel.next = firstNode.next;
        sentinel.next.prev = sentinel;
        sizeOfDeque--;
        return firstNode.first;
    }

    public T removeLast() {
        if (sizeOfDeque == 0) {
            return null;
        }
        StuffNode lastNode = sentinel.prev;
        sentinel.prev = lastNode.prev;
        sentinel.prev.next = sentinel;
        sizeOfDeque--;
        return lastNode.first;
    }
    /**
     * if index > size, then return the last element.
     */
    public T get(int index) {
        StuffNode iter = sentinel;
        while (iter.next != sentinel) {
            if (index == 0) {
                break;
            }
            index--;
            iter = iter.next;
        }
        return iter.next.first;
    }

    private T getHelper(StuffNode lst, int index) {
        if (lst.next == sentinel || index == 0) {
            return lst.next.first;
        }
        return getHelper(lst.next, index - 1);
    }

    public T getRecursive(int index) {
        return getHelper(sentinel, index);
    }

    private static void main(String[] args) {
        LinkedListDeque<Integer> testQueue = new LinkedListDeque();
        testQueue.addLast(1);
        testQueue.addFirst(2);
        testQueue.addFirst(3);
        testQueue.addFirst(4);
        testQueue.removeLast();
        testQueue.removeFirst();
        testQueue.addFirst(7);
        System.out.println(testQueue.get(0));
    }
}
