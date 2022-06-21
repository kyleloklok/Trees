public interface Tree<T>{
    int getSize();
    int getHeight();
    boolean isEmpty();
    boolean contains(T item);
    void remove();
    void add(T item);
    void preOrder();
    void postOrder();
    void inOrder();
    void levelOrder();
    void clear();
}
