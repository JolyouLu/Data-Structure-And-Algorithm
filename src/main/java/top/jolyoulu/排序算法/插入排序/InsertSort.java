package top.jolyoulu.排序算法.插入排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/15 17:25
 * @Version 1.0
 * 插入排序算法，时间复杂度O(n^2)
 *
 * 1)、将当前列表拆分成有序列表与无序列表，有序列表默认只有1个元素即列表的第1位元素，无序列表是除第1位元素后所有元素
 * 2)、遍历从第2位元素开始，获取元素与有序列表元素比较，插入合适位置
 *
 * 例子:[1,5,3,2]
 * 拆分列表：[(1),{5,3,2}] =说明=> ()表示有序列表 {}表示无序列表
 * 第一次：从无序列表中获取1个元素5，与有序列表中每一个元素比较，放入合适位置，结果[(1,5) {3,2}]
 * 第二次：从无序列表中获取1个元素3，与有序列表中每一个元素比较，放入合适位置，结果[(1,3,5) {2}]
 * 第三次：从无序列表中获取1个元素2，与有序列表中每一个元素比较，放入合适位置，结果[1,2,3,5]
 */
public class InsertSort {
    public static void main(String[] args) {
        //简单测试
        System.out.println("============================简单测试============================");
        int[] arr = {101,34,115,40};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                insertSort(array);
            }
        });
    }

    public static void insertSort(int[] arr){
        int insertVal;
        int insertIndex;
        //遍历无序数组
        for (int i = 1; i < arr.length; i++) {
            //取出需要插入的元素，即无序数组的第一个元素
            insertVal = arr[i];
            //取出插入比较的开始位置，即有序数组的最后一个元素下标
            insertIndex = i - 1;
            //如果当前插入下标>=0,并且当前需要插入的元素小于有序列表下标位置元素进入
            while (insertIndex >= 0 && insertVal < arr[insertIndex]){
                //将较大的数向后移动一位
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //如果(insertIndex+1) = i 表示当前需插入数是有序列表中最大的，位置不用改变
            if (insertIndex + 1 != i ){
                //到这有2种情况
                // 1、insertIndex移动到了-1，表示insertVal比有序列表中所有的数都小
                // 2、当前insertVal > arr[insertIndex]，在while中已经把比insertVal大的数后移动了一位以及将insertIndex减少
                // 所以需要在arr[insertIndex + 1]插入insertVal
                arr[insertIndex + 1] = insertVal;
            }
        }
    }
}
