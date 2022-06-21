import java.util.Stack;

//NOT SELF BALANCING
public class BinarySearchTree<T> implements Tree<T>{
    private Node<T> head;
    private int size;

    private class Node<E>{
        E item;
        Node<E> left;
        Node<E> right;
        Node(E item){
            this.item = item;
        }
        public String toString(){
            String result = "";
            result += item;
            return result;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear(){
        head = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T item) {
        return contains(head, item);
    }

    private boolean contains(Node node, T item){
        if(node == null) return false;
        if(node.item == item) return true;
        if(!greaterThan(item, (T) node.item)) return contains(node.left, item);
        else return contains(node.right, item);
    }

    @Override
    public int getHeight() {
        return getHeight(head);
    }

    private int getHeight(Node<T> node){
        if(node == null) return -1;
        int left = getHeight(node.left);
        int right = getHeight(node.right);
        return Math.max(left, right) + 1;
    }

    @Override
    public void add(T item) {
        head = add(item, head);
        size++;
    }

    private Node<T> add(T item, Node<T> node){
        if(node == null) return new Node<>(item);
        if(greaterThan(item, node.item)) node.right = add(item, node.right);
        else node.left = add(item, node.left);
        return node;
    }

    @Override
    public void remove(){
        T temp = head.item;
        head = remove(head, head.item);
    }

    public Node<T> remove(T item){
        head = remove(head, item);
        return head;
    }

    private Node<T> remove(Node<T> node, T item){
        if(node == null) return null;
        if(greaterThan(item, node.item)) node.right = remove(node.right, item);
        else if(lessThan(item, node.item)) node.left = remove(node.left, item);
        else{
            if(node.right == null && node.left == null) return null;
            if(node.right != null && node.left == null) return node.right;
            if(node.right == null) return node.left;
            Node<T> successor = getInOrderSuccessor(node.right);
            T val = successor.item;
            node = remove(node, successor.item);
            node.item = val;
            return node;
        }
        return node;
    }

    private Node<T> getInOrderSuccessor(Node<T> node){
        while(node != null && node.left != null){
            node = node.left;
        }
        return node;
    }

    private boolean greaterThan(T a, T b){
        return ((Comparable<T>) a).compareTo(b) > 0;
    }

    private boolean lessThan(T a, T b){
        return ((Comparable<T>) a).compareTo(b) < 0;
    }

    @Override
    public void preOrder() {
        preOrder(head);
    }

    private void preOrder(Node<T> node){
        if(node == null) return;
        System.out.print(node + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    @Override
    public void postOrder() {
        postOrder(head);
    }

    private void postOrder(Node<T> node){
        if(node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node + " ");
    }

    @Override
    public void inOrder(){
        inOrder(head);
    }

    @Override
    public void levelOrder() {

    }

    private void inOrder(Node<T> node){
        if(node == null) return;
        inOrder(node.left);
        System.out.print(node + " ");
        inOrder(node.right);
    }

    public void iterativeInOrder(){
        iterativeInOrder(head);
    }

    private void iterativeInOrder(Node<T> node){
        if(node == null) return;
        Node<T> current = node;
        Stack<Node<T>> stack = new Stack<>();
        while(true){
            if(current != null){
                stack.push(current);
                current = current.left;
            }
            else{
                if(stack.isEmpty()) return;
                current = stack.pop();
                System.out.print(current + " ");
                current = current.right;
            }
        }
    }

    public void iterativePreOrder(){
        iterativePreOrder(head);
    }

    private void iterativePreOrder(Node<T> node){
        if(node == null) return;
        Node<T> current = node;
        Stack<Node<T>> stack = new Stack<>();
        while(true){
            if(current != null){
                System.out.print(current + " ");
                stack.push(current);
                current = current.left;
            }
            else{
                if(stack.isEmpty()) return;
                current = stack.pop().right;
            }
        }
    }

    public void iterativePostOrder(){
        iterativePostOrder(head);
    }

    private void iterativePostOrder(Node<T> node){
        if(node == null) return;
        Node<T> current = node;
        Stack<Node<T>> stack = new Stack<>();
        Stack<Node<T>> result = new Stack<>();
        stack.push(current);
        while(!stack.isEmpty()){
            current = stack.pop();
            result.push(current);
            if(current.left != null) stack.push(current.left);
            if(current.right != null) stack.push(current.right);
        }
        while(!result.isEmpty()){
            System.out.print(result.pop() + " ");
        }
    }
}
