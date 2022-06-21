import java.util.Stack;

//PRIORITY HEAP
public class MaxHeap<T> implements Tree<T>{
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
        T  item = items[0];
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

    @Override
    public void preOrder(){
        preOrder(0);
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
    public void postOrder(){
        postOrder(0);
    }

    public void iterativeInOrder(){
        iterativeInOrder(0);
    }

    private void iterativeInOrder(int n){
        Stack<Integer> stack = new Stack<>();
        for(;;){
            if(n <= lastPos){
                stack.push(n);
                n = 2 * n + 1;
            } else{
                if(stack.isEmpty()) return;
                n = stack.pop();
                System.out.print(items[n] + " ");
                n = 2 * n + 2;
            }
        }
    }

    public void iterativePreOrder(){
        iterativePreOrder(0);
    }

    private void iterativePreOrder(int n){
        Stack<Integer> stack = new Stack<>();
        for(;;){
            if(n <= lastPos){
                System.out.print(items[n] + " ");
                stack.push(n);
                n = 2 * n + 1;
            }
            else{
                if(stack.isEmpty()) return;
                n = 2 * stack.pop() + 2;
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
            if(2 * n + 1 <= lastPos) stack.push(2 * n + 1);
            if(2 * n + 2 <= lastPos) stack.push(2 * n + 2);
        }
        while(!result.isEmpty()){
            System.out.print(items[result.pop()] + " ");
        }
    }

    public void oneStackPostOrder(){
        oneStackPostOrder(0);
    }

    private void oneStackPostOrder(int n) {
        Stack<Integer> stack = new Stack<>();
        while(n <= lastPos || !stack.isEmpty()){
            if(n <= lastPos){
                stack.push(n);
                n = 2 * n + 1;
            }
            else{
                n = stack.peek() * 2 + 2;
                if( n <= lastPos) continue;
                n = stack.pop();
                System.out.print(items[n] + " ");
                while(!stack.isEmpty() && n == stack.peek() * 2 + 2){
                    n = stack.pop();
                    System.out.print(items[n] + " ");
                }
                if(stack.isEmpty()) return;
                n = stack.peek() * 2 + 2;
            }
        }
    }

    private void preOrder(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos) return;
        System.out.print(items[n] + " ");
        preOrder(left);
        preOrder(right);
    }

    private void inOrder(int n){
        if(n > lastPos || items[n] == null) return;
        inOrder(2 * n + 1);
        System.out.print(items[n] + " ");
        inOrder(2 * n + 2);
    }

    private void postOrder(int n){
        int left = 2 * n + 1;
        int right = 2 * n + 2;
        if(n > lastPos ||items[n] == null) return;
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
}
