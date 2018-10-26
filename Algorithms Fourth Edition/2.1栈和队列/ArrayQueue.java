/**
 * @author
 * @date /10/26
 * 使用单个数组构成队列
 */
public class ArrayQueue {
    /**
     * size:队列元素个数
     * head:指向队列头元素
     * head:指向队列的尾元素
     * */
    private int[] array;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int length) {
        array = new int[length];
    }

    public void push(int i) {
        if (size <array.length) {
            array[tail] = i;
            tail = tail == array.length - 1 ? 0 : ++tail;
            size++;
        } else {
            throw new ArrayIndexOutOfBoundsException("队列已经满了");
        }
    }

    public int pop() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException("队列为空");
        }
        int pop = array[head];
        size--;
        head = head == array.length - 1 ? 0: ++head;
        return pop;
    }

    public Integer peek() {
        if(size==0)
        {
            return null;
        }
        return array[head];
    }

}
