package top.jolyoulu.算法.排序算法.基数排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/17 9:35
 * @Version 1.0
 * 基数(桶)排序算法
 * 所有排序算法中最快的算法，也是消耗内存最大的算法
 * 算法初始所需使用的内存：数组长度 * 11(初始需要11个数组) * 4(数组中每个int4个字节) / 1024 / 1024 = 所需内存(单位M)
 * 当前算法不支持负数排序，若需支持思路取负数绝对值存入，取出时需要变回负数
 *
 * 1)、首先准备10个桶，桶序号分别是0-9
 * 2)、找出数组中最大元素，并且得出最大元素位数maxLen
 * 3)、需遍历maxLen轮
 *      a.第1轮获取数组每个元素个位数、第2轮十位、第3轮百位以此类推，获取出来的数范围0-9，分别放入对应的桶中
 *      b.从0-9遍历全部桶，读取出元素返回原arr中
 * 4)、经过maxLen轮第3步骤，即得出一个有序数组
 */
public class RadixSort {

    public static void main(String[] args) {
        System.out.println("============================简单测试============================");
        int arr[] = {53, 3, 542, 748, 14, 214};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));


        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                radixSort(array);
            }
        });
    }

    //基数排序
    public static void radixSort(int[] arr) {
        //获取最大数值
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        //通过最大值得到数组中元素最大位数
        int maxLength = (max + "").length();
        //定义一个二维数组表示10个捅，10行(桶)arr.length列(桶容量)
        // 由于不能确定每个捅最大容量，所以生成冗余的捅即每个捅容量arr.len
        int[][] bucket = new int[10][arr.length];
        //利用长度10的1维数组，记录每个桶当前可用数据下标，数组下标对应当前桶序号，值对应捅指针
        int[] bucketElementCounts = new int[10];
        //遍历最大元素位数次
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            for (int j = 0; j < arr.length; j++) {
                //取元素的个、十、百位数
                //个位数获取：arr[j] / 1 % 10;
                //十位数获取：arr[j] / 10 % 10;
                //百位数获取：arr[j] / 100 % 10;
                int digitOfElement = arr[j] / n % 10;
                //放入对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                //对应捅的下标也要+1
                bucketElementCounts[digitOfElement]++;
            }
            //读取所有捅放回到原数组中
            int index = 0;
            for (int k = 0; k < bucket.length; k++) {
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    arr[index] = bucket[k][l];
                    index++;
                }
                bucketElementCounts[k] = 0;
            }
//            System.out.println("第" + (i+1) + "轮排序后数组");
//            System.out.println(Arrays.toString(arr));
        }

    }

}
