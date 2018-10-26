
import java.util.Stack;

/**
 * @author
 * @date /10/26
 * 使用栈构造队列
 */
public class QueueByStack {
    Stack<Integer> pushStack=new Stack<>();
    Stack<Integer> popStack=new Stack<>();
    public void push(Integer i)
    {
       pushStack.push(i);
    }
    public int pop()
    {
        if(!popStack.isEmpty())
        {
           return popStack.pop();
        }
        while(!pushStack.isEmpty())
        {
            popStack.push(pushStack.pop());
        }
        return popStack.pop();
    }
    public int peek()
    {
        if(!popStack.isEmpty())
        {
            return popStack.peek();
        }
        while(!pushStack.isEmpty())
        {
            popStack.push(pushStack.pop());
        }
        return popStack.peek();
    }
}
