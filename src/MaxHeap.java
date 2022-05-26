//PRIORITY HEAP

public class MaxHeap<T> {
    private T[] items;
    private int lastPos = -1;
    private static final int DEFAULT_CAPACITY = 10;

    public MaxHeap(){
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public T get(int ind){
        if(ind > lastPos) return null;
        return items[ind];
    }

    public int getSize(){
        return lastPos + 1;
    }

    public boolean isEmpty(){
        return lastPos < 0;
    }

    public void add(T item){
        ensureCapacity();
        items[++lastPos] = item;
        heapify(lastPos, true);
    }

    public T remove(){
        ensureCapacity();
        T  item = items[0];
        items[0] = items[lastPos];
        items[lastPos--] = null;
        heapify(0, false);
        return item;
    }

    public int getIndexOf(T item){
        return findIndexOf(0, item);
    }

    private int findIndexOf(int n, T item){
        if(n > lastPos) return -1;
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(items[n].equals(item)) return n;
        if(left > lastPos) return -1;
        if(right > lastPos) return findIndexOf(left, item);
        int ind = findIndexOf(left, item);
        if(ind < 0) return findIndexOf(right, item);
        else return ind;
    }

    private void heapify(int pos, boolean up){
        if(up){
            if(pos == 0) return;
            int parentInd = (int) Math.floor((pos - 1) / 2.0);
            if(greaterThan(pos, parentInd)){
                swap(parentInd, pos);
                heapify(parentInd, true);
            }
        } else{
            int left = 2 * pos + 1;
            int right = 2 * pos + 2;
            if(left == lastPos && greaterThan(left, pos)){
                swap(left, pos);
                return;
            }
            if(right == lastPos && greaterThan(right, pos) && greaterThan(right, left)){
                swap(right, pos);
                return;
            }
            if(items[left] == null || items[right] == null) return;
            if(greaterThan(pos, left) && greaterThan(pos, right)) return;
            if(greaterThan(left, right)){
                swap(left, pos);
                heapify(left, false);
            } else{
                swap(right, pos);
                heapify(right, false);
            }
        }
    }

    private void swap(int to, int from){
        T temp = items[to];
        items[to] = items[from];
        items[from] = temp;
    }

    private void ensureCapacity(){
        if(getSize() == items.length){
            T[] temp = (T[]) new Object[items.length * 2];
            for(int i = 0; i < getSize(); i++){
                temp[i] = items[i];
            }
            items = temp;
            return;
        }
        if(getSize() <= items.length / 4){
            T[] temp = (T[]) new Object[items.length / 2];
            for(int i = 0; i < getSize(); i++){
                temp[i] = items[i];
            }
            items = temp;
        }
    }

    private boolean greaterThan(int a, int b){
        return ((Comparable<T>) items[a]).compareTo(items[b]) > 0;
    }

    public void preOrderTraverse(){
        preOrderTraverse(0);
    }
    public void inOrderTraverse(){
        inOrderTraverse(0);
    }

    public void postOrderTraverse(){
        postOrderTraverse(0);
    }
    private void preOrderTraverse(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        System.out.print(items[n] + " ");
        //if(right > lastPos && left > lastPos) return;
        preOrderTraverse(left);
        //if(right > lastPos) return;
        preOrderTraverse(right);
    }

    private void inOrderTraverse(int n){
        if(n > lastPos || items[n] == null) return;
        inOrderTraverse(2 * n + 1);
        System.out.print(items[n] + " ");
        inOrderTraverse(2 * n + 2);
    }

    private void postOrderTraverse(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos ||items[n] == null) return;
        postOrderTraverse(left);
        postOrderTraverse(right);
        System.out.print(items[n] + " ");
    }

    public String toString(){
        String result = "[";
        for(int i = 0; i < getSize(); i++){
            if(i == getSize() - 1){
                result += items[i] + "]";
                break;
            }
            result += items[i] + ", ";
        }
        return result;
    }

}
