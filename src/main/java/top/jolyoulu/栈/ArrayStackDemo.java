package top.jolyoulu.栈;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/13 9:45
 * @Version 1.0
 * 栈-数组实现
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(5);
        System.out.println("===============入栈===============");
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println("===============打印栈内元素===============");
        stack.print();
        System.out.println("===============出栈===============");
        for (int i = 0; i < 5; i++) {
            System.out.println(stack.pop());
        }
    }
}
//定义一个栈结构
class ArrayStack{
    private int maxSize; //栈最大size
    private int[] stack; //栈的数据存放的数组
    private int top = -1; //top表示栈顶，初始化为-1

    //构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[this.maxSize];
    }

    //栈满
    public boolean isFull(){
      return top == maxSize - 1;
    }

    //栈空
    public boolean isEmpty(){
        return top == -1;
    }

    //入栈-push
    public void push(int value){
        //判断是否栈满
        if (isFull()){
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    //出栈-pop
    public int pop(){
        //判断是否空栈
        if (isEmpty()){
            throw new RuntimeException("栈空");
        }
        int val = stack[top];
        top--;
        return val;
    }

    //打印栈信息
    public void print(){
        //判断是否空栈
        if (isEmpty()){
            throw new RuntimeException("栈空");
        }
        for (int i = top; i > -1; i--) {
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }
}
