package top.jolyoulu.utils;

import java.util.Random;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/14 16:57
 * @Version 1.0
 */
public class IntGenerate implements Generate<Integer>{
    private final static Random random = new Random(47);

    @Override
    public Integer next() {
        return random.nextInt(1000);
    }

    /**
     * 构建一个指定长度的数组
     * @param len 长度
     * @return
     */
    public int[] array(int len){
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = next();
        }
        return arr;
    }
}
