import java.util.Stack;

//PRIORITY HEAP
public class MinHeap<T> implements Tree<T>{
    private T[] items;
    private int lastPos = -1;
    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap(){
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void clear(){
        items = null;
        items = (T[]) new Object[DEFAULT_CAPACITY];
        lastPos = -1;
    }

    @Override
    public int getSize(){
        return lastPos + 1;
    }

    @Override
    public boolean isEmpty(){
        return lastPos < 0;
    }

    @Override
    public boolean contains(T item) {
        return false;
    }

    @Override
    public int getHeight() {
        return getHeight(0);
    }

    private int getHeight(int n){
        if(n > lastPos) return -1;
        int left = getHeight(2 * n + 1);
        int right = getHeight(2 * n + 2);
        return Math.max(left, right) + 1;
    }

    @Override
    public void add(T item){
        ensureCapacity();
        items[++lastPos] = item;
        heapify(lastPos, true);
    }

    @Override
    public void remove(){
        ensureCapacity();
        items[0] = items[lastPos];
        items[lastPos--] = null;
        heapify(0, false);
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

    @Override
    public void inOrder(){
        inOrder(0);
    }

    @Override
    public void levelOrder() {
        for(int i = 0; i <= lastPos; i++){
            System.out.print(items[i] + " ");
        }
    }

    @Override
    public void preOrder(){
        preOrder(0);
    }

    @Override
    public void postOrder(){
        postOrder(0);
    }

    private void inOrder(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        inOrder(left);
        System.out.print(items[n] + " ");
        inOrder(right);
    }

    private void preOrder(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        System.out.print(items[n] + " ");
        preOrder(left);
        preOrder(right);
    }

    private void postOrder(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        postOrder(left);
        postOrder(right);
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

    public void iterativePreOrder(){
        iterativePreOrder(0);
    }

    private void iterativePreOrder(int n){
        Stack<Integer> stack = new Stack<>();
        while(true){
            if(n <= lastPos){
                System.out.print(items[n] + " ");
                stack.push(n);
                n = 2 * n + 1;
            }
            else{
                int parent = (int) Math.floor((n - 1) / 2.0);
                if(2 * parent + 1 == n && !stack.isEmpty()){
                    stack.pop();
                }
                if(stack.isEmpty()) return;
                n = stack.pop() * 2 + 2;
            }
        }
    }

    public void iterativeInOrder(){
        iterativeInOrder(0);
    }

    private void iterativeInOrder(int n){
        Stack<Integer> stack = new Stack<>();
        while(true){
            if(n <= lastPos){
                stack.push(n);
                n = 2 * n + 1;
            }
            else{
                if(stack.isEmpty()) return;
                int parent = (int) Math.floor((n - 1) / 2.0);
                int temp = n;
                n = stack.pop();
                System.out.print(items[n] + " ");
                if(parent * 2 + 1 == temp && !stack.isEmpty()){
                    n = stack.pop();
                    System.out.print(items[n] + " ");
                }
                n = 2 * n + 2;
            }
        }
    }

    public void iterativePostOrder(){
        iterativePostOrder(0);
    }

    private void iterativePostOrder(int n){
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> result = new Stack<>();
        stack.push(n);
        while(!stack.isEmpty()){
            n = stack.pop();
            result.push(n);
            int left = 2 * n + 1;
            int right = 2 * n + 2;
            if(left <= lastPos) stack.push(left);
            if(right <= lastPos) stack.push(right);
        }
        while(!result.isEmpty()){
            System.out.print(items[result.pop()] + " ");
        }
    }
}
