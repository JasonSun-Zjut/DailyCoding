import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author
 * @date /10/26
 * 使用两个队列构造栈
 */
public class StackByQueue {
    private Queue<Integer> dataqueue = new PriorityQueue<>();
    private Queue<Integer> tempqueue = new PriorityQueue<>();

    public void push(int x) {
        dataqueue.add(x);
    }

    public int pop()
    {
        while(dataqueue.size()>1)
        {
            tempqueue.add(dataqueue.poll());
        }
        int value=dataqueue.poll();
        swap();
        return value;
    }

    private void swap() {
        Queue<Integer> temp=dataqueue;
        dataqueue=tempqueue;
        tempqueue=temp;
    }

    public int peek()
    {
        while(dataqueue.size()>1)
        {
            tempqueue.add(dataqueue.poll());
        }
        int top=dataqueue.peek();
        tempqueue.add(dataqueue.poll());
        swap();
        return top;
    }

}
