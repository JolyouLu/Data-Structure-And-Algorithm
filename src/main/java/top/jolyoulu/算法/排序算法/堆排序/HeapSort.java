package top.jolyoulu.算法.排序算法.堆排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import javax.crypto.KeyGenerator;
import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/22 14:57
 * @Version 1.0
 * 堆排序(选择排序的一种)
 *
 * 将一个数组看做二叉完全树，调整树节点使得树结构为大顶堆，
 *
 *                            [4]                       [4]                   [9]
 *                           /   \                     /   \                 /   \
 * [4,6,8,5,9] =完全二叉=>   [6]  [8]  =非叶子节点调整=>  [9]  [8] =大顶堆=>     [6]  [8]
 *                         / \                       / \                   / \
 *                       [5][9]                     [5][6]               [5][4]
 * 将大顶堆根节点与数组最后节点交换
 * [9,6,8,5,4] ==> [4,6,8,5,9]
 * 重复以上操作排序arr.len-1即可完成
 */
public class HeapSort {
    public static void main(String[] args) {
        System.out.println("============================简单测试============================");
        int[] arr = {4, 6, 8, 5, 9};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                heapSort(array);
            }
        });
    }

    //堆排序
    public static void heapSort(int[] arr) {
        int temp = 0;
        //先将所有非叶子节点调整一遍
        for (int i = arr.length / 2; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
        //开始得到大顶堆，并且每次得到后与最后一个元素交换位置
        for (int i = arr.length - 1; i > 0; i--) {
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, i);
        }
    }

    /**
     * 将一个数组(二叉树)，调整为大顶堆
     *
     * @param arr    代调整的数组
     * @param i      需要调整的子树父节点
     * @param length 对多少元素调整
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        //将子树父节点保存下来
        int temp = arr[i];
        //for循环遍历整个子树，步长是左子节点
        for (int j = (i * 2 + 1); j < length; j = (j * 2 + 1)) {
            //(j+1) < length：当前计算后的节点下标，必须在需排序树内
            //arr[j] < arr[j+1]：如果左子节点小于右子节点
            if ((j + 1) < length && arr[j] < arr[j + 1]) {
                //将下标移动到右子节点位置上
                j++;
            }
            //如果当前节点的值，是否大于保存下来的父节点
            if (arr[j] > temp) {
                //将大的值，覆盖到原始父节点上
                arr[i] = arr[j];
                //将当前大值下标赋给i
                i = j;
            } else {
                break;
            }
            //将父节点调整到i位置上
            arr[i] = temp;
        }
    }
}
