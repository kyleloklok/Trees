import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T> implements Tree<T> {
    private Node<T> head;
    private int size;
    private boolean doubleBlack;

    public static void main(String[] args){
        
    }

    private class Node<E>{
        Node<E> left, right;
        E item;
        boolean isRed;
        Node(E item){
            isRed = true;
            this.item = item;
        }
        public String toString(){
            String result = "";
            result += item + "; Color: ";
            if(isRed) result += "Red";
            else result += "Black";
            return result;
        }
        String toNoColorString(){
            String result = "";
            result += item;
            return result;
        }
    }

    private boolean isRed(Node<T> node){
        return node != null && node.isRed;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public int getHeight(){
        return getHeight(head);
    }

    private int getHeight(Node<T> node){
        if(node == null) return -1;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean contains(T item){
        return contains(head, item);
    }

    private boolean contains(Node<T> node, T item){
        if(node == null) return false;
        if(greaterThan(item, node.item)) return contains(node.right, item);
        else return contains(node.left, item);
    }

    @Override
    public void remove() {
        if(head == null){
            System.out.println("ERROR: EMPTY TREE");
            return;
        }
        head = remove(head, head.item);
        size--;
    }

    public void remove(T item) {
        head = remove(head, item);
        size--;
    }

    private Node<T> remove(Node<T> node, T item){
        if(node == null){
            doubleBlack = false;
            System.out.println("ERROR: ITEM NOT FOUND IN TREE");
            return null;
        }
        if(greaterThan(item, node.item)){
            if(!isRed(node.right)) doubleBlack = true;
            node.right = remove(node.right, item);
            if(doubleBlack) return fixDoubleBlack(node, true);
        }
        else if(lessThan(item, node.item)){
            if(!isRed(node.left)) doubleBlack = true;
            node.left = remove(node.left, item);
            if(doubleBlack) return fixDoubleBlack(node, false);
        }
        else{
            if(node.right == null && node.left == null){
                if(node.isRed) doubleBlack = false;
                return null;
            }
            if(node.right != null && node.left == null){
                node.right.isRed = false;
                doubleBlack = false;
                return node.right;
            }
            if(node.right == null){
                node.left.isRed = false;
                doubleBlack = false;
                return node.left;
            }
            Node<T> temp = getInOrderSuccessor(node.right);
            T replacement = temp.item;
            node.right = remove(node.right, replacement);
            node.item = replacement;
            if(doubleBlack) return fixDoubleBlack(node, true);
        }
        return node;
    }

    private Node<T> fixDoubleBlack(Node<T> node, boolean doubleBlackInRightChild){
        if(doubleBlackInRightChild){
            if(!isRed(node.left) && !isRed(node.left.left) && !isRed(node.left.right)){
                node.left.isRed = true;
                if(node.isRed){
                    node.isRed = false;
                    doubleBlack = false;
                }
                return node;
            }
            if(!isRed(node.left) && isRed(node.left.left)){
                boolean oldParentColor = node.isRed;
                Node<T> newParent = rightRotate(node);
                newParent.right.isRed = false;
                newParent.left.isRed = false;
                newParent.isRed = oldParentColor;
                doubleBlack = false;
                return newParent;
            }
            if(!isRed(node.left) && isRed(node.left.right)){
                boolean oldParentColor = node.isRed;
                Node<T> newParent = leftRightRotate(node);
                newParent.right.isRed = true;
                newParent.left.isRed = true;
                newParent.isRed = oldParentColor;
                doubleBlack = false;
                return newParent;
            }
            if(isRed(node.left) && isRed(node.left.right.left)){
                Node<T> newParent = rightLeftRotate(node);
                newParent.isRed = false;
                newParent.right.isRed = false;
                newParent.left.isRed = true;
                newParent.left.right.isRed = false;
                doubleBlack = false;
                return newParent;
            }
            if(isRed(node.left) && isRed(node.left.right.right)){
                Node<T> temp = node.left.right.left;
                node.left.right.left = node.left.right.right;
                node.left.right.right = temp;
                Node<T> newParent = leftRightRotate(node);
                newParent.isRed = false;
                newParent.right.isRed = false;
                newParent.left.isRed = true;
                newParent.left.right.isRed = false;
                doubleBlack = false;
                return newParent;
            }
            if(isRed(node.left) && !isRed(node.left.right) && !isRed(node.left.left)){
                Node<T> newParent = rightRotate(node);
                newParent.right.left.isRed = true;
                doubleBlack = false;
                return newParent;
            }
        }
        else{
            if(!isRed(node.right) && !isRed(node.right.right) && !isRed(node.right.left)){
                node.right.isRed = true;
                if(node.isRed){
                    doubleBlack = false;
                    node.isRed = false;
                }
                return node;
            }
            if(!isRed(node.right) && isRed(node.right.right)){
                boolean oldParentColor = node.isRed;
                Node<T> newParent = leftRotate(node);
                newParent.right.isRed = false;
                newParent.left.isRed = false;
                newParent.isRed = oldParentColor;
                doubleBlack = false;
                return newParent;
            }
            if(!isRed(node.right) && isRed(node.right.left)){
                boolean oldParentColor = node.isRed;
                Node<T> newParent = rightLeftRotate(node);
                newParent.right.isRed = true;
                newParent.left.isRed = true;
                newParent.isRed = oldParentColor;
                doubleBlack = false;
                return newParent;
            }
            if(isRed(node.right) && isRed(node.right.left.right)){
                Node<T> newParent = rightLeftRotate(node);
                newParent.isRed = false;
                newParent.left.isRed = false;
                newParent.right.isRed = true;
                newParent.right.left.isRed = false;
                doubleBlack = false;
                return newParent;
            }
            if(isRed(node.right) && isRed(node.right.left.left)){
                Node<T> temp = node.right.left.right;
                node.right.left.right = node.right.left.left;
                node.right.left.left = temp;
                Node<T> newParent = rightLeftRotate(node);
                newParent.isRed = false;
                newParent.left.isRed = false;
                newParent.right.isRed = true;
                newParent.right.left.isRed = false;
                doubleBlack = false;
                return newParent;
            }
            if(isRed(node.right) && !isRed(node.right.left) && !isRed(node.right.right)){
                Node<T> newParent = leftRotate(node);
                newParent.isRed = false;
                newParent.left.right.isRed = true;
                doubleBlack = false;
                return newParent;
            }
        }
        return node;
    }

    public Node<T> getInOrderSuccessor(Node<T> node){
        while(node != null && node.left != null){
            node = node.left;
        }
        return node;
    }

    @Override
    public void add(T item) {
        head = add(head, item);
        head.isRed = false;
        size++;
    }

    private Node<T> add(Node<T> node, T item){
        if(node == null) return new Node<>(item);
        if(greaterThan(item, node.item)){
            node.right = add(node.right, item);
            if(isRed(node) && isRed(node.right)) return node;
            if(isRed(node.right) && (isRed(node.right.right) || isRed(node.right.left))) return rebalance(node);
        }
        else{
            node.left = add(node.left, item);
            if(isRed(node) && isRed(node.left)) return node;
            if(isRed(node.left) && (isRed(node.left.right) || isRed(node.left.left))) return rebalance(node);
        }
        return node;
    }

    private Node<T> rebalance(Node<T> node){
        if(isRed(node.right)){ //node.right is red
            if(isRed(node.right.right)){ //double-red violation in node's right child and right grandchild
                if(!isRed(node.left)) return rotationColorFlip(leftRotate(node)); //black aunt
                else return colorFlip(node); //red aunt
            }
            else{ //double-red violation in node's right child and left grandchild
                if(!isRed(node.left)) return rotationColorFlip(rightLeftRotate(node)); //black aunt
                else return colorFlip(node); //red aunt
            }
        } else{ //node.left is red
            if(isRed(node.left.right)){ //double-red violation in node's left child and right grand child
                if(!isRed(node.right)) return rotationColorFlip(leftRightRotate(node)); //black aunt
                else return colorFlip(node); //red aunt
            }
            else{ //double-red violation in node's left child and left grandchild
                if(!isRed(node.right)) return rotationColorFlip(rightRotate(node)); //black aunt
                else return colorFlip(node); //red aunt
            }
        }
    }

    private Node<T> colorFlip(Node<T> node){ //end result: node = red; node.left = node.right = black
        node.isRed = true;
        node.right.isRed = false;
        node.left.isRed = false;
        return node;
    }

    private Node<T> rotationColorFlip(Node<T> node){ //end result: node = black; node.left = node.right = red
        node.isRed = false;
        node.right.isRed = true;
        node.left.isRed = true;
        return node;
    }

    private Node<T> rightRotate(Node<T> node){
        Node<T> temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }

    private Node<T> leftRotate(Node<T> node){
        Node<T> temp = node.right;
        node.right = temp.left;
        temp.left = node;
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
        System.out.print(node.toNoColorString() + " ");
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
        System.out.print(node.toNoColorString() + " ");
    }

    @Override
    public void inOrder() {
        inOrder(head);
    }

    private void inOrder(Node<T> node){
        if(node == null) return;
        inOrder(node.left);
        System.out.print(node.toNoColorString() + " ");
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
            Node<T> current = queue.peek();
            if(current.left != null) queue.add(current.left);
            if(current.right != null) queue.add(current.right);
            System.out.print(queue.poll().toNoColorString() + " ");
        }
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    private boolean greaterThan(T a, T b){
        return ((Comparable<T>) a).compareTo(b) > 0;
    }

    private boolean lessThan(T a, T b){
        return ((Comparable<T>) a).compareTo(b) < 0;
    }
}
