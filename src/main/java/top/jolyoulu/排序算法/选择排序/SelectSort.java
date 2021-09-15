package top.jolyoulu.排序算法.选择排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/15 11:31
 * @Version 1.0
 * 选择排序算法，时间复杂度O(n^2)
 *
 * 1)、遍历数组从arr[0]至arr[len-1]得到最小值下标，与arr[0]交换
 * 2)、遍历数组从arr[1]至arr[len-1]得到最小值下标，与arr[1]交换
 * 3)、以此类推直到结束，根据规律得出公式：第i次遍历len-1次，从arr[i]至arr[len-1]得到最小值与arr[i]交换
 *
 * 例子:[1,5,3,2]
 * 第一次：arr[0]至arr[len-1]得到最小值1，与arr[0]交换，结果[1,5,3,2]
 * 第二次：arr[1]至arr[len-1]得到最小值2，与arr[1]交换，结果[1,2,3,5]
 * 第三次：arr[2]至arr[len-1]得到最小值3，与arr[2]交换，结果[1,2,3,5]
 */
public class SelectSort {

    public static void main(String[] args) {
        //简单测试
        System.out.println("============================简单测试============================");
        int[] arr = {101,34,115,1};
        selectSort(arr);
        System.out.println(Arrays.toString(arr));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                selectSort(array);
            }
        });
    }

    public static void selectSort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            //最小值下标
            int minIndex = i;
            //最小值
            int min = arr[i];
            //依次比较确定最小值下标
            for (int j = i; j < arr.length; j++) {
                //如果发现更加小的值
                if (min > arr[j]){
                    //设置
                    min = arr[j];
                    minIndex = j;
                }
            }
            //如果最小值下标没变，不进行交换
            if (minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            //arr[i]与mini交换
//            System.out.println("第"+(i+1)+"轮排序后数组");
//            System.out.println(Arrays.toString(arr));
        }
    }

}
