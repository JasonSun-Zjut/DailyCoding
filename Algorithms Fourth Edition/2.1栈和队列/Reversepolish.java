class Solution {
    public int evalRPN(String[] tokens) {
        Stack<String> stack=new Stack<>();
        for(String str:tokens)
        {
            if(str.equals("+"))
            {
                int num1=Integer.parseInt(stack.pop());
                int num2=Integer.parseInt(stack.pop());
                stack.push(String.valueOf(num1+num2));
            }
            else if(str.equals("-"))
            {
                int num1=Integer.parseInt(stack.pop());
                int num2=Integer.parseInt(stack.pop());
                stack.push(String.valueOf(num2-num1));
            }
            else if(str.equals("*"))
            {
                int num1=Integer.parseInt(stack.pop());
                int num2=Integer.parseInt(stack.pop());
                stack.push(String.valueOf(num1*num2));
            }
            else if(str.equals("/"))
            {
                
                int num1=Integer.parseInt(stack.pop());
                int num2=Integer.parseInt(stack.pop());
                if(num2==0)
                {
                    stack.push(String.valueOf(0));
                }
                else{
                stack.push(String.valueOf(num2/num1));}
            }
            else{
                stack.push(str);
            }
        }
        return Integer.parseInt(stack.pop());
    }
}