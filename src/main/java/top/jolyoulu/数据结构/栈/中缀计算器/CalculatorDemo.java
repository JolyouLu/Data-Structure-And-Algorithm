package top.jolyoulu.数据结构.栈.中缀计算器;

import java.util.Scanner;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/13 10:41
 * @Version 1.0
 * 栈-中缀表达式-实现计算器
 */
public class CalculatorDemo {
    public static void main(String[] args) {
        boolean pool = true;
        Scanner scanner = new Scanner(System.in);
        while (pool){
            System.out.println("cal：计算器");
            System.out.println("exit：退出程序");
            String next = scanner.next();
            switch (next){
                case "cal":
                    System.out.println("请输入你需要计算的表达式");
                    String str = scanner.next();
                    System.out.println("计算结果: "+new Calculator().cal(str));
                    break;
                case "exit":
                    pool = false;
                    break;
            }
        }
        System.out.println("程序结束~");
    }
}
class Calculator{
    ArrayStack numStack;
    ArrayStack operStack;

    public String cal(String str) {
        this.numStack = new ArrayStack(str.length());
        this.operStack = new ArrayStack(str.length());
        String[] split = str.split("");
        //多位数拼接时使用
        String keepNum = "";
        //解析表达式
        for (int i = 0;i < split.length;i++) {
            char c = split[i].charAt(0);
            //判断是否符合
            if (isOper(c)){
                //如果符号栈不空，需弹出一个符号与当前符号比较
                if (!operStack.isEmpty()){
                    //弹出符号栈，栈顶符号与当前符号比较
                    //优先级计算,如果栈顶符号(*或/) >= 当前符号优(+或-或*或/)，就需要pop，2个数出来运算(先乘除)
                    if (priority(operStack.peek()) >= priority(c)){
                        int num1 = numStack.pop();
                        int num2 = numStack.pop();
                        int oper = operStack.pop();
                        //得到计算结果，继续压入数字栈
                        int cal = cal(num1, num2, oper);
                        numStack.push(cal);
                        //当前符号也要压入栈
                        operStack.push(c);
                    //优先级计算，如果栈顶符号(+或-) <= 当前符号优(*或/)，不用管直接入栈即可
                    }else {
                        operStack.push(c);
                    }
                //如果符号栈空直接插入，否则需要检查优先级
                }else {
                    operStack.push(c);
                }
            }else {
                //keepNum用于多位数临时存放
                keepNum += c;
                //查看当前位置下一位，如果是操作符可以入栈
                if ( (i+1 == split.length) || isOper(split[i+1].charAt(0))){
                    numStack.push(Integer.parseInt(keepNum));
                    //清空keepNum
                    keepNum = "";
                }
            }
        }
        //计算剩下的符号
        while (numStack.size() > 1){
            int num1 = numStack.pop();
            int num2 = numStack.pop();
            int oper = operStack.pop();
            //得到计算结果，继续压入数字栈
            int cal = cal(num1, num2, oper);
            numStack.push(cal);
        }
        return String.valueOf(numStack.pop());
    }

    //返回运算符的优先级，先乘除后加减，目前只支持加减乘除
    //数字越多优先级越高
    private int priority(int oper){
        if (oper == '*' || oper == '/'){
            return 1;
        }else if (oper == '+' || oper == '-'){
            return 0;
        }else {
            return -1;
        }
    }

    //判断传入的是否是运算符
    private boolean isOper(char val){
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    //计算方法，后一个数在最前面
    private int cal(int num1,int num2,int oper){
        int res = 0;
        switch (oper){
            case '+':
                res = num2 + num1;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num2 * num1;
                break;
            case '/':
                res = num2 / num1;
                break;
        }
        return res;
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
    //返回当前栈中元素大小
    public int size(){
        return top+1;
    }

    //返回栈顶元素，不pop
    public int peek(){
        return stack[top];
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
