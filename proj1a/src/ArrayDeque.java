public class ArrayDeque<Type>{
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

    private void enlargeByTwice(){
        int length = items.length;
        int capacity = length * 2;
        Type[] des = (Type[])new Object[capacity];
        Type[] leftHalf = (Type[])new Object[length];
        Type[] rightHalf = (Type[]) new Object[length];

        if(lastIndex >= length){
            System.arraycopy(items, length/2, rightHalf, 0, lastIndex - length + 1);
            lastIndex = capacity/2 + lastIndex - length;
        }
        else{
            System.arraycopy(items,length/2,rightHalf,0,length/2);
            System.arraycopy(items,0,rightHalf,length/2,lastIndex+1);
            lastIndex = capacity/2 + length/2 + lastIndex;
        }

        if(firstIndex < length){
            System.arraycopy(items,firstIndex,leftHalf,0,length/2-firstIndex);
            firstIndex = capacity/2 - length/2 + firstIndex;
        }
        else{
            System.arraycopy(items,0,leftHalf,0,length/2);
            System.arraycopy(items,firstIndex,leftHalf,length/2,length-firstIndex);
            firstIndex = capacity/2 - length/2 - length + firstIndex;
        }

        System.arraycopy(rightHalf,0,des,capacity/2,rightHalf.length);
        System.arraycopy(leftHalf,0,des,firstIndex,capacity/2-firstIndex);
        items = des;
    }

    /**
     * When size/items.length <= 0.25, Then reduce the items by Half.
     * Particularly, firstIndex < items.length/2 && lastIndex >= items.length/2 in both former array and present array.
     */
    private void reduceByHalf(){
        Type[] des = (Type[]) new Object[items.length/2];
        System.arraycopy(items,items.length/2,des,des.length/2,lastIndex-items.length+1);
        System.arraycopy(items,firstIndex,des,des.length/2 - (items.length/2-firstIndex),items.length/2-firstIndex);
        lastIndex = des.length/2 + lastIndex - items.length;
        firstIndex = des.length/2 - items.length/2 + firstIndex;
        items = des;
    }

    public void addFirst(Type item){
        if(firstIndex == lastIndex){
            enlargeByTwice();
        }
        items[firstIndex] = item;
        firstIndex = (firstIndex - 1) % items.length;
        size++;
    }

    public void addLast(Type item){
        if(firstIndex == lastIndex){
            enlargeByTwice();
        }
        items[lastIndex] = item;
        lastIndex = (lastIndex + 1)%items.length;
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void printDeque(){
        StringBuilder builder = new StringBuilder();
        for(int i = firstIndex+1;i != items.length/2;i = (i + 1)%items.length){
            builder.append(items[i] + " ");
        }
        for(int j = items.length/2;j != lastIndex;j = (j + 1)%items.length){
            builder.append(items[j] + " ");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder.toString());
    }

    public Type removeFirst(){
        if(size == 0){
            return null;
        }
        Type last;
        int index;
        if(firstIndex == items.length/2 - 1){
            items[items.length/2] = null;
            System.arraycopy(items,items.length/2+1,items,items.length/2,items.length/2-1);

        }
        else{
            index = (firstIndex + 1) % items.length;
            last = items[index];
            items[index] = null;
            firstIndex = index;
        }
        size--;
        if(size / items.length < 0.25 && items.length > 8){
            reduceByHalf();
        }
        return last;
    }

    public Type removeLast(){
        if(size == 0){
            return null;
        }
        Type last;
        int index;
        if(lastIndex == items.length/2){

        }
        size--;
        if(size / items.length < 0.25 && items.length > 8){
            reduceByHalf();
        }
        return last;
    }

    public int size(){
        return size;
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
        System.out.println(testQueue.size());
        testQueue.printDeque();
    }
}
