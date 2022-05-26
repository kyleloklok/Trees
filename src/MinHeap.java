//PRIORITY HEAP

public class MinHeap<T> {
    private T[] items;
    private int lastPos = -1;
    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap(){
        items = (T[]) new Object[DEFAULT_CAPACITY];
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
        T item = items[0];
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

    private void heapify(int n, boolean up){
        if(up){
            int parent = (int) Math.floor((n - 1) / 2.0);
            if(n == 0 || isGreaterThan(n, parent)) return;
            swap(parent, n);
            heapify(parent, true);
        } else{
            int left = 2 * n + 1;
            int right = 2 * n + 2;
            if(n > lastPos || left > lastPos) return;
            if(right > lastPos){
                swap(left, n);
                heapify(left, false);
            }
            if(isGreaterThan(left, n) && isGreaterThan(right, n)) return;
            if(isGreaterThan(left, right)){
                swap(right, n);
                heapify(right,false);
            } else {
                swap(left, n);
                heapify(left, false);
            }
        }
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

    private void swap(int to, int from){
        T temp = items[to];
        items[to] = items[from];
        items[from] = temp;
    }

    private boolean isGreaterThan(int a, int b){
        return ((Comparable<T>) items[a]).compareTo(items[b]) >= 0;
    }

    public void inOrderTraverse(){
        inOrderTraverse(0);
    }

    public void preOrderTraverse(){
        preOrderTraverse(0);
    }

    public void postOrderTraverse(){
        postOrderTraverse(0);
    }

    private void inOrderTraverse(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        inOrderTraverse(left);
        System.out.print(items[n] + " ");
        inOrderTraverse(right);
    }

    private void preOrderTraverse(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        System.out.print(items[n] + " ");
        preOrderTraverse(left);
        preOrderTraverse(right);
    }

    private void postOrderTraverse(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
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
