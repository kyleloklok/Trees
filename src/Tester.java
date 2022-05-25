public class Tester {
    public static void main(String[] args){
        MinHeap<Integer> minHeap = new MinHeap<>();
        for(int i = 0; i < 10; i++){
            minHeap.add(i);
        }
        minHeap.remove();
        System.out.println(minHeap);
        minHeap.preOrderTraverse();

        /*
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        for(int i = 0; i < 10; i++){
            maxHeap.add(i);
        }
        maxHeap.add(9);
        System.out.println(maxHeap);
        maxHeap.preOrderTraverse();

        System.out.println(maxHeap);
        System.out.println(maxHeap.remove());
        System.out.println(maxHeap);
        System.out.println(maxHeap.remove());
        System.out.println(maxHeap);

        System.out.println(maxHeap.remove());
        System.out.println(maxHeap);

        System.out.println(maxHeap.remove());
        System.out.println(maxHeap);

        System.out.println(maxHeap.remove());
        System.out.println(maxHeap);

        System.out.println(maxHeap.remove());
        System.out.println(maxHeap);
        */

    }
}
