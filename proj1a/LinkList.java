public class LinkList<Item>{
    private class ItemNode{
        Item first;
        ItemNode pre;
        ItemNode res;

        ItemNode(Item var, ItemNode next){
            first = var;
            pre = null;
            res = next;
            if(next != null){
                next.pre = this;
            }
        }
    }

    int size;
    ItemNode sentinel;

    public LinkList(){
        sentinel = new ItemNode(null, null);
        sentinel.res = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }

    public LinkList(Item var){
        sentinel = new ItemNode(null, null);
        sentinel.res = sentinel;
        sentinel.pre = sentinel;
        ItemNode first = new ItemNode(var, sentinel);
        first.pre = sentinel;
        sentinel.res = first;
        size = 1;
    }

    public void addLast(Item var){
        ItemNode lastTwo = sentinel.pre;
        ItemNode last = new ItemNode(var, sentinel);
        lastTwo.res = last;
        last.pre = lastTwo;
        size++;
    }

    public Item getLast(){
        if(size != 0){
            return sentinel.pre.first;
        }
        else{
            return null;
        }
    }

    public void removeLast(){
        sentinel.pre.pre.res = sentinel;
        sentinel.pre = sentinel.pre.pre;

        if(size > 0){
            size--;
        }
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        System.out.println("ok");
        LinkList<Integer> list = new LinkList<>();
        list.addLast(10);
        list.addLast(15);
        list.removeLast();
        System.out.println(list.size());
    }
}