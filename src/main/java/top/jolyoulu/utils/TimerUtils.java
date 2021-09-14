package top.jolyoulu.utils;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/14 17:26
 * @Version 1.0
 */
public class TimerUtils {
    public static void timer(Task task){
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        System.out.println("程序耗时："+(end-start)+"毫秒");
    }

    public interface Task {
        void run();
    }
}
