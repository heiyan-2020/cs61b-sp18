public class LinkedListDeque<Type>{
    private class StuffNode{
        Type first;
        StuffNode prev;
        StuffNode next;

        StuffNode(Type var, StuffNode front, StuffNode res){
            first = var;
            prev = front;
            next = res;
        }
    }
    private int sizeOfDeque;
    private StuffNode sentinel;
    public LinkedListDeque(){
        sizeOfDeque = 0;
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    public int size(){
        return sizeOfDeque;
    }

    public void addFirst(Type item){
        StuffNode firstNode = new StuffNode(item, sentinel, sentinel.next);
        sentinel.next.prev = firstNode;
        sentinel.next = firstNode;
        sizeOfDeque++;
     }

     public void addLast(Type item){
        StuffNode lastNode = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = lastNode;
        sentinel.prev = lastNode;
        sizeOfDeque++;
     }

     public boolean isEmpty(){
        return sizeOfDeque == 0;
     }

     public void printDeque(){
        StuffNode iter = sentinel;
        StringBuilder builder = new StringBuilder();
        while(iter.next != sentinel){
            builder.append(iter.next.first + " ");
            iter = iter.next;
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder.toString());
     }

     public Type removeFirst(){
        if(sizeOfDeque == 0){
            return null;
        }
        StuffNode firstNode = sentinel.next;
        sentinel.next = firstNode.next;
        sentinel.next.prev = sentinel;
        sizeOfDeque--;
        return firstNode.first;
     }

     public Type removeLast(){
        if(sizeOfDeque == 0){
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
     public Type get(int index){
        int count = 0;
        StuffNode iter = sentinel;
        while(sentinel.next != sentinel){
            iter = iter.next;
            count++;
            if(count == index){
                break;
            }
        }
        return iter.next.first;
     }

     private Type getHelper(StuffNode lst, int index){
        if(lst.next == sentinel || index == 0){
            return lst.first;
        }
        return getHelper(lst.next, index - 1);
     }
     public Type getRecursive(int index){
        return getHelper(sentinel, index);
     }

    public static void main(String[] args) {
        LinkedListDeque<Integer> testQueue = new LinkedListDeque();
        testQueue.addFirst(15);
        testQueue.addFirst(10);
        testQueue.addLast(20);
        System.out.println(testQueue.get(10));
        System.out.println(testQueue.getRecursive(100));
        testQueue.printDeque();
    }
}