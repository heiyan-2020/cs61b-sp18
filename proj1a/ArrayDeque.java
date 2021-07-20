public class ArrayDeque< T > {
    private static int mod(int x, int y) {
        return x >= 0 ? x % y : y + x % y;
    }
    private T[] items;
    private int firstIndex;
    private int lastIndex;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        lastIndex = items.length / 2;
        firstIndex = lastIndex - 1;
        size = 0;
    }

    /**
     * Invoke this method when former items[] is actually full, so the firstIndex
     */
    private void enlargeByTwice() {
        int amount = items.length - firstIndex - 1;
        T[] des = (T[]) new Object[items.length * 2];
        System.arraycopy(items, mod(firstIndex+1, items.length), des, items.length, amount);
        System.arraycopy(items,0, des, items.length + amount, items.length - amount);
        firstIndex = items.length - 1;
        lastIndex = 0;
        items = des;
    }

    /**
     * When size/items.length <= 0.25, Then reduce the items by Half.
     * Particularly, firstIndex < items.length/2 && lastIndex >= items.length/2 \
     * in both former array and present array.
     */
    private void reduceByHalf() {
        int amount = mod(lastIndex-firstIndex, items.length) - 1;
        T[] des = (T[]) new Object[items.length/2];
        if(lastIndex >= firstIndex) {
            System.arraycopy(items, mod(firstIndex + 1,items.length), des,des.length / 2, amount);
        }
        else {
            int firstAmount = items.length - firstIndex - 1;
            System.arraycopy(items, mod(firstIndex + 1,items.length), des, des.length / 2, firstAmount);
            System.arraycopy(items, 0, des, des.length / 2 + firstAmount, amount - firstAmount);
        }
        firstIndex = des.length / 2 - 1;
        lastIndex = des.length / 2 + amount;
        items = des;
    }

    public void addFirst(T item) {
        if(size == items.length) {
            enlargeByTwice();
        }
        items[firstIndex] = item;
        firstIndex = mod(firstIndex - 1,items.length);
        size++;
    }

    public void addLast(T item) {
        if(size == items.length) {
            enlargeByTwice();
        }
        items[lastIndex] = item;
        lastIndex = mod(lastIndex + 1,items.length);
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void printDeque() {
        StringBuilder builder = new StringBuilder();
        if(size == items.length) {
            builder.append(items[mod(firstIndex + 1, items.length)] + " ");
            for(int i = mod(firstIndex + 2, items.length); i != lastIndex; i = mod(i + 1, items.length)){
                builder.append(items[i] + " ");
            }
        }
        else {
            for(int i = mod(firstIndex + 1, items.length); i != lastIndex; i = mod(i + 1, items.length)){
                builder.append(items[i] + " ");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder.toString());
    }

    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        int deleteIndex = mod(firstIndex + 1, items.length);
        T deleteElem = items[deleteIndex];
        firstIndex = deleteIndex;
        items[deleteIndex] = null;
        size--;
        if(((double)size / items.length) < 0.25 && items.length > 8) {
            reduceByHalf();
        }
        return deleteElem;
    }

    public T removeLast() {
        if(size == 0) {
            return null;
        }
        int deleteIndex = mod(lastIndex - 1,items.length);
        T deleteElem = items[deleteIndex];
        lastIndex = deleteIndex;
        items[deleteIndex] = null;
        size--;
        if(((double)size / items.length) < 0.25 && items.length > 8) {
            reduceByHalf();
        }
        return deleteElem;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        int i = mod(firstIndex + 1, items.length);
        if(index > size) {
            return items[mod(lastIndex - 1, items.length)];
        }
        while(true) {
            if(index == 0){
                break;
            }
            i = mod(i + 1, items.length);
            index--;
        }
        return items[i];
    }

    private static void main(String[] args) {
        ArrayDeque<Integer> testQueue = new ArrayDeque<>();
        testQueue.addLast(1);
        testQueue.addLast(2);
        testQueue.addLast(3);
        testQueue.addLast(4);
        testQueue.addLast(5);
        testQueue.addLast(6);
        testQueue.addLast(7);
        testQueue.addLast(8);
        System.out.println(testQueue.get(1));
        System.out.println(testQueue.size());
        testQueue.printDeque();
    }
}
