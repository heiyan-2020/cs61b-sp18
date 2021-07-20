public class ArrayDeque<Type>{
    private static int mod(int x, int y){
        return x>=0?x%y:y+x%y;
    }
    private Type[] items;
    private int firstIndex;
    private int lastIndex;
    private int size;

    public ArrayDeque(){
        items = (Type[])new Object[8];
        lastIndex = items.length / 2;
        firstIndex = lastIndex - 1;
        size = 0;
    }

    /**
     * Invoke this method when former items[] is actually full, so the firstIndex
     */
    private void enlargeByTwice(){
        int amount = items.length - firstIndex - 1;
        Type[] des = (Type[])new Object[items.length*2];
        System.arraycopy(items,mod(firstIndex+1,items.length),des,items.length,amount);
        System.arraycopy(items,0,des,items.length+amount,items.length-amount);
        firstIndex = items.length - 1;
        lastIndex = 0;
        items = des;
    }

    /**
     * When size/items.length <= 0.25, Then reduce the items by Half.
     * Particularly, firstIndex < items.length/2 && lastIndex >= items.length/2 in both former array and present array.
     */
    private void reduceByHalf(){
        int amount = mod(lastIndex-firstIndex,items.length) - 1;
        Type[] des = (Type[]) new Object[items.length/2];
        if(lastIndex >= firstIndex){
            System.arraycopy(items,mod(firstIndex+1,items.length),des,des.length/2,amount);
        }
        else{
            int firstAmount = items.length - firstIndex - 1;
            System.arraycopy(items,mod(firstIndex+1,items.length),des,des.length/2,firstAmount);
            System.arraycopy(items,0,des,des.length/2+firstAmount,amount-firstAmount);
        }
        firstIndex = des.length/2 - 1;
        lastIndex = des.length/2 + amount;
        items = des;
    }

    public void addFirst(Type item){
        if(size == items.length){
            enlargeByTwice();
        }
        items[firstIndex] = item;
        firstIndex = mod(firstIndex-1,items.length);
        size++;
    }

    public void addLast(Type item){
        if(size == items.length){
            enlargeByTwice();
        }
        items[lastIndex] = item;
        lastIndex = mod(lastIndex+1,items.length);
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void printDeque(){
        StringBuilder builder = new StringBuilder();
        for(int i = firstIndex + 1; i != lastIndex; i = mod(i+1,items.length)){
            builder.append(items[i]+" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder.toString());
    }

    public Type removeFirst(){
        if(size == 0){
            return null;
        }
        int deleteIndex = mod(firstIndex+1,items.length);
        Type deleteElem = items[deleteIndex];
        firstIndex = deleteIndex;
        items[deleteIndex] = null;
        size--;
        if(((double)size/items.length) < 0.25 && items.length > 8){
            reduceByHalf();
        }
        return deleteElem;
    }

    public Type removeLast(){
        if(size == 0){
            return null;
        }
        int deleteIndex = mod(lastIndex-1,items.length);
        Type deleteElem = items[deleteIndex];
        lastIndex = deleteIndex;
        items[deleteIndex] = null;
        size--;
        if(((double)size/items.length) < 0.25 && items.length > 8){
            reduceByHalf();
        }
        return deleteElem;
    }

    public int size(){
        return size;
    }

    public Type get(int index){
        int i = mod(firstIndex+1, items.length);
        while(i != mod(lastIndex, items.length)){
            if(index == 0){
                break;
            }
            i = mod(i+1,items.length);
            index--;
        }
        return items[i];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> testQueue = new ArrayDeque<>();
        testQueue.addFirst(3);
        testQueue.addFirst(2);
        testQueue.addFirst(1);
        testQueue.addLast(4);
        testQueue.addLast(5);
        testQueue.addLast(6);
        testQueue.addLast(7);
        testQueue.addLast(8);
        testQueue.addLast(9);
        System.out.println(testQueue.get(4));
        System.out.println(testQueue.size());
        testQueue.printDeque();
    }
}
