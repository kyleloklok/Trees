import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AVLTree<T> implements Tree<T>{
    private int size;
    private Node<T> head;

    public static void main(String[] args){
        AVLTree<Integer> tree = new AVLTree<>();
        for(int i = 500; i < 1000; i++){
            tree.add(i);
        }
        for(int i = 0; i < 500; i++){
            tree.add(i);
        }
        System.out.print(tree.stressTest());
    }

    private class Node<E>{
        Node<E> left, right;
        E item;
        int height, itemCount;
        Node(E item){
            this.item = item;
            height = 0;
            itemCount = 1;
        }
        @Override
        public String toString(){
            String result = "";
            result += item;
            return result;
        }
        void copyItemCount(Node<E> node){
            this.itemCount = node.itemCount;
        }
    }

    private int getBalance(Node<T> node){
        return getHeight(node.right) - getHeight(node.left);
    }

    private int getHeight(Node<T> node){
        return node != null ? node.height : -1;
    }

    @Override
    public void clear(){
        head = null;
        size = 0;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getHeight() {
        return head.height;
    }

    @Override
    public boolean contains(T item) {
        return contains(head, item);
    }

    private boolean contains(Node<T> current, T item){
        if(current == null) return false;
        if(current.item == item) return true;
        if(greaterThan(item, current.item)) return contains(current.right, item);
        else return contains(current.left, item);
    }
    @Override
    public void remove(){
        if(remove(head, head.item) == null){
            System.out.println("ERROR: EMPTY TREE");
            return;
        }
        size--;
    }

    public void remove(T item){
        if(remove(head, item) == null){
            System.out.print("ERROR: ITEM NOT FOUND IN TREE");
            return;
        }
        size--;
    }

    private Node<T> remove(Node<T> node, T item){
        Node<T> current = node;
        Node<T> parent = null;
        Stack<Node<T>> pathTraceStack = new Stack<>();
        while(current != null && current.item != item){
            parent = current;
            pathTraceStack.push(parent);
            if(greaterThan(item, current.item)) current = current.right;
            else if(lessThan(item, current.item)) current = current.left;
        }
        if(current == null) return null;
        if(--current.itemCount > 0) return current;
        if(current.left == null && current.right == null){ //NO CHILDREN; CURRENT IS A LEAF NODE
            if(parent == null){
                Node<T> temp = head;
                head = null;
                return temp;
            }
            if(current == parent.right) parent.right = null;
            else parent.left = null;
        }
        else if(current.right != null && current.left == null){ //1 RIGHT CHILD
            if(parent == null) {
                Node<T> temp = head;
                head = head.right;
                return temp;
            }
            if(current == parent.right) parent.right = current.right;
            else parent.left = current.right;
        }
        else if(current.right == null){ //1 LEFT CHILD
            if(parent == null){
                Node<T> temp = head;
                head = head.left;
                return temp;
            }
            if(current == parent.right) parent.right = current.left;
            else parent.left = current.left;
        }
        else{ //2 CHILDREN
            Node<T> successor = getInOrderSuccessor(current.right);
            T newItem = successor.item;
            current.copyItemCount(successor);
            successor.itemCount = 1;
            Node<T> temp = remove(current, newItem);
            temp.item = current.item;
            current.item = newItem;
            current.height = Math.max(getHeight(current.right), getHeight(current.left)) + 1;
            current = temp;
        }
        //REBALANCING
        rebalance(pathTraceStack);
        return current;
    }

    private Node<T> getInOrderSuccessor(Node<T> current){
        while(current != null && current.left != null){
            current = current.left;
        }
        return current;
    }

    private void rebalance(Stack<Node<T>> stack){
        while(!stack.isEmpty()){
            Node<T> checkBalanceNode = stack.pop();
            checkBalanceNode.height = Math.max(getHeight(checkBalanceNode.right), getHeight(checkBalanceNode.left)) + 1;
            if(getBalance(checkBalanceNode) == 2){//RIGHT HEAVY
                if(getHeight(checkBalanceNode.right.right) > getHeight(checkBalanceNode.right.left)){
                    if(stack.isEmpty()) head = leftRotate(checkBalanceNode);
                    else if(checkBalanceNode == stack.peek().right) stack.peek().right = leftRotate(checkBalanceNode);
                    else stack.peek().left = leftRotate(checkBalanceNode);
                }
                else{
                    if(stack.isEmpty()) head = rightLeftRotate(checkBalanceNode);
                    else if(checkBalanceNode == stack.peek().right) stack.peek().right = rightLeftRotate(checkBalanceNode);
                    else stack.peek().left = rightLeftRotate(checkBalanceNode);
                }
            }
            else if(getBalance(checkBalanceNode) == -2){//LEFT HEAVY
                if(getHeight(checkBalanceNode.left.left) > getHeight(checkBalanceNode.left.right)){
                    if(stack.isEmpty()) head = rightRotate(checkBalanceNode);
                    else if(checkBalanceNode == stack.peek().right) stack.peek().right = rightRotate(checkBalanceNode);
                    else stack.peek().left = rightRotate(checkBalanceNode);
                }
                else{
                    if(stack.isEmpty()) head = leftRightRotate(checkBalanceNode);
                    else if(checkBalanceNode == stack.peek().right) stack.peek().right = leftRightRotate(checkBalanceNode);
                    else stack.peek().left = leftRightRotate(checkBalanceNode);
                }
            }
        }
    }

    @Override
    public void add(T item) {
        head = add(head, item);
    }

    private Node<T> add(Node<T> node, T item){
        //BST INSERTION
        if(node == null){
            size++;
            return new Node<>(item);
        }
        if(greaterThan(item, node.item)) node.right = add(node.right, item);
        else if(lessThan(item, node.item)) node.left = add(node.left, item);
        else{
            node.itemCount++;
            size++;
            return node;
        }
        //RIGHT HEAVY
        if(getBalance(node) == 2){
            if(getHeight(node.right.right) > getHeight(node.right.left)) return leftRotate(node);
            else return rightLeftRotate(node);
        }
        //LEFT HEAVY
        else if(getBalance(node) == -2){
            if(getHeight(node.left.left) > getHeight(node.left.right)) return rightRotate(node);
            else return leftRightRotate(node);
        }
        //UPDATE HEIGHT
        node.height = Math.max(getHeight(node.right), getHeight(node.left)) + 1;
        return node;
    }

    private Node<T> rightRotate(Node<T> node){
        Node<T> temp = node.left;
        node.left = temp.right;
        temp.right = node;
        node.height = Math.max(getHeight(node.right), getHeight(node.left)) + 1;
        temp.height = Math.max(getHeight(temp.right), getHeight(temp.left)) + 1;
        return temp;
    }

    private Node<T> leftRotate(Node<T> node){
        Node<T> temp = node.right;
        node.right = temp.left;
        temp.left = node;
        node.height = Math.max(getHeight(node.right), getHeight(node.left)) + 1;
        temp.height = Math.max(getHeight(temp.right), getHeight(temp.left)) + 1;
        return temp;
    }

    private Node<T> rightLeftRotate(Node<T> node){
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    private Node<T> leftRightRotate(Node<T> node){
        node.left = leftRotate(node.left);
        return rightRotate(node);
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
    public void inOrder() {
        inOrder(head);
    }

    private void inOrder(Node<T> node){
        if(node == null) return;
        inOrder(node.left);
        System.out.print(node + " ");
        inOrder(node.right);
    }

    @Override
    public void levelOrder() {
        levelOrder(head);
    }

    private void levelOrder(Node<T> node){
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()){
            if(queue.peek().left != null) queue.add(queue.peek().left);
            if(queue.peek().right != null) queue.add(queue.peek().right);
            System.out.print(queue.poll() + " ");
        }
    }

    private boolean greaterThan(T a, T b){
        return ((Comparable<T>) a).compareTo(b) > 0;
    }

    private boolean lessThan(T a, T b){
        return ((Comparable<T>) a).compareTo(b) < 0;
    }

    public boolean stressTest(){
        return stressTest(head);
    }

    private boolean stressTest(Node<T> node){
        if(node == null) return true;
        if(Math.abs(getHeight(node.left) - getHeight(node.right)) > 1) return false;
        return stressTest(node.left) && stressTest(node.right);
    }
}
