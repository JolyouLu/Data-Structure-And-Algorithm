package top.jolyoulu.排序算法.希尔排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/16 9:44
 * @Version 1.0
 * 希尔排序算法(插入排序优化版)
 *
 * 希尔排序是插入排序的一个优化版本，其概念着是在插入排序基础上进行分组后在排序，分组公式grp=length/2
 *
 * 例子：无序列表[8,9,1,7,2,3,5,4,6,0]排序过程
 * 第1次分组+排序：
 *      当前数组：[8,9,1,7,2,3,5,4,6,0]
 *      分组：10/2 = 5，每个元素相隔5下标，分5组 => [8,3] [9,5] [1,4] [7,6] [2,0]
 *      插入排序：[3,8] [5,9] [1,4] [6,7] [0,2] => [3,5,1,6,0,8,9,4,7,2]
 * 第2次分组+排序：
 *      当前数组：[3,5,1,6,0,8,9,4,7,2]
 *      分组：5/2 = 2，每个元素相隔2下标，分2组 => [3,1,0,9,7] [5,6,8,4,2]
 *      插入排序：[0,1,3,7,9] [2,4,5,6,8] => [0,2,1,4,3,5,7,6,9,8]
 * 第3次分组+排序：
 *      当前数组：[0,2,1,4,3,5,7,6,9,8]
 *      分组：2/2 = 1，无法分组直接排序 => [0,2,1,4,3,5,7,6,9,8]
 *      插入排序：[0,2,1,4,3,5,7,6,9,8] => [0,1,2,3,4,5,6,7,8,9]
 */
public class ShellSort {

    public static void main(String[] args) {
        System.out.println("============================简单测试============================");
        int[] arr = {8,9,1,7,2,3,5,4,6,0};
        gressionShellSort(arr);
        System.out.println(Arrays.toString(arr));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                gressionShellSort(array);
            }
        });
    }

    //交换法希尔排序，效率低
    public static void swapShellSort(int[] arr){
        //临时遍历，交换时使用
        int temp;
        int count = 0;
        //gap间隔，每次遍历后间隔都会/2
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从gap开始，例：第1次分组+排序举例，从3开始分别读取[3,5,4,6,0]
            for (int i = gap; i < arr.length; i++) {
                //例：与[8,9,1,7,2]一对一比较
                for (int j = i - gap; j >= 0; j -= gap) {
                    //如果当前数据大于间隔数据，交换位置
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
//            System.out.println("第" + (++count) + "轮排序后数组");
//            System.out.println(Arrays.toString(arr));
        }
    }

    //移位法希尔排序，效率高
    public static void gressionShellSort(int[] arr){
        //临时遍历，交换时使用
        int temp;
        int count = 0;
        //gap间隔，每次遍历后间隔都会/2
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //读取分组的后数据
            for (int i = gap; i < arr.length; i++) {
                //保存一份需插入数据
                int insertVal = arr[i];
                int insertIndex = i;
                //从3开始分别读取[3,5,4,6,0]与[8,9,1,7,2]一对一比较
                if (arr[insertIndex] < arr[insertIndex - gap]){
                    while (insertIndex - gap >=0 && arr[insertIndex - gap] > insertVal){
                        //把大的数往后移动
                        arr[insertIndex] = arr[insertIndex - gap];
                        insertIndex -= gap;
                    }
                    arr[insertIndex] = insertVal;
                }
            }
//            System.out.println("第" + (++count) + "轮排序后数组");
//            System.out.println(Arrays.toString(arr));
        }
    }
}
