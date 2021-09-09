package top.jolyoulu.queue;

import sun.security.provider.ConfigFile;

import java.util.Scanner;

/**
 * @Author: JolyouLu
 * @Date: 2021/8/24 21:22
 * @Version 1.0
 * 环形队列-数组实现
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(4);
        char key = ' '; //接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(show):退出程序");
            System.out.println("a(show):添加数据到队列");
            System.out.println("g(show):从队列取出数据");
            System.out.println("h(show):查看队头数据");
            key = scanner.next().charAt(0); //接收一个字符
            try {
                switch (key){
                    case 's':
                        queue.showQueue();
                        break;
                    case 'a':
                        System.out.println("请输入一个数");
                        int value = scanner.nextInt();
                        queue.addQueue(value);
                        break;
                    case 'g':
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n",res);
                        break;
                    case 'h':
                        queue.showHead();
                        break;
                    case 'e':
                        scanner.close();
                        loop = false;
                        break;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("程序退出");
    }
}
//使用数组模拟队列实现
class ArrayQueue{
    private int maxSize; //数组最大容量
    private int front; //队头指针
    private int rear; //队尾指针
    private int[] arr; //数组

    //构造器初始化队列
    public ArrayQueue(int maxSize){
        this.maxSize = maxSize;
        this.front = 0; //始终指向队列头的数据
        this.rear = 0; //始终指向队尾的数据,的后一个位置
        this.arr = new int[maxSize];
    }

    //判断队列是否满
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n){
        //判断队列是否满
        if (isFull()){
            throw new RuntimeException("队列满了，不能加入数据");
        }
        arr[rear] = n;
        rear = (rear + 1) % maxSize; //rear指针+1，取模防止越界
    }

    //数据出队
    public int getQueue(){
        //判断队列是否空
        if (isEmpty()){
            throw new RuntimeException("队列空，不能获取数据");
        }
        //获取队头数据
        int temp = arr[front];
        front = (front + 1) % maxSize; //front指针+1，取模防止越界
        return temp;
    }

    //打印队列中数据
    public void showQueue(){
        //判断队列是否空
        if (isEmpty()){
            throw new RuntimeException("队列空，不能获取数据");
        }
        //从front开始，遍历有效元素数量
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n",i % maxSize,arr[i % maxSize]);
        }
    }

    //计算读取队列有效数据个数
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    //打印队头数据
    public void showHead(){
        //判断队列是否空
        if (isEmpty()){
            throw new RuntimeException("队列空，不能获取数据");
        }
        System.out.printf("队列头的数据是%d\n",arr[front]);
    }
}
