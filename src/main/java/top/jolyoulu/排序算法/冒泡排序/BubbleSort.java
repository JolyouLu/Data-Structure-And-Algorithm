package top.jolyoulu.排序算法.冒泡排序;

import top.jolyoulu.utils.ArgsGenerate;
import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/14 16:04
 * @Version 1.0
 * 冒泡排序算法，时间复杂度O(n^2)
 *
 * 1)、双指针，向右移动，每2数比较大的数始终放最右边(移动次数是数组长度-1)
 * 2)、每1轮下来后可以得出1个最大的数位于右侧(每次比较次数是上次的1)
 * 冒泡排序优化：记录每轮交换次数，如果在某次排序中一次交换都未发生表示，整个数组已经是有序的了
 *
 * 例子:[1,5,3,2]
 * 第一轮：
 *    第1次比较：取1与5比较，不变，结果[1,5,3,2]
 *    第2次比较：取5与3比较，交换，结果[1,3,5,2]
 *    第3次比较：取5与2比较，不变，结果[1,3,2,5]
 * 第二轮：
 *    第1次比较：取1与3比较，不变，结果[1,3,2,5]
 *    第2次比较：取3与2比较，交换，结果[1,2,3,5]
 * 第三轮：
 *    第1次比较：取1与3比较，不变，结果[1,3,2,5]
 */
public class BubbleSort {
    public static void main(String[] args) {
        //简单测试
        System.out.println("============================简单测试============================");
        int arr[] = {3,9,-1,10,20};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                bubbleSort(array);
            }
        });
    }

    public static void bubbleSort(int[] arr){
        //临时变量交换时需用到
        int temp = 0;
        //需要遍历多少轮
        int length = arr.length - 1;
        //标记本轮比较是否进行过交换
        boolean flag = false;
        //遍历整个数组
        //共需要遍历数组长度-1轮
        for (int i = 0; i < length; i++) {
            //每一轮需要比较 (数组长度-1)-当前第几轮
            for (int j = 0; j < length - i; j++) {
                //如果左比右大那么需要交换位置
                if (arr[j] > arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
//            System.out.println("第"+(i+1)+"轮排序后数组");
//            System.out.println(Arrays.toString(arr));
            //如果flag = false 表示本轮比较未发生过一次，表示该数组已经有序的，直接结束排序
            if (!flag){
                break;
            }else {
                flag = false;
            }
        }
    }
}
