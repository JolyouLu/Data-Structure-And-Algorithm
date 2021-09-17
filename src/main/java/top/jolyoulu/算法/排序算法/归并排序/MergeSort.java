package top.jolyoulu.算法.排序算法.归并排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/16 16:42
 * @Version 1.0
 * 归并排序
 *
 * 1)、将数组从中间拆分，拆分后再拆分，以此类推拆分到最小单元(2个元素)，对其进行合并
 *
 * 拆分：利用递归对数组进行拆分
 *                 [8,4,5,7,1,3,6,2]
 *                   /            \
 *              [8,4,5,7]      [1,3,6,2]
 *                /   \          /  \
 *            [8,4]   [5,7]  [1,3]  [6,2]
 *             / \     / \    / \    / \
 * 排序+合并    8  4    5  7   1  3   6  2
 *            \  /    \  /   \  /   \  /
 *           [4,8]   [5,7]  [1,3]  [2,6]
 *             \       /      \     /
 *             [4,5,7,8]      [1,2,3,6]
 *                 \             /
 *                [1,2,3,4,5,6,7,8]
 * 排序+合并：利用中间数组完成
 *
 */
public class MergeSort {

    public static void main(String[] args) {
        int arr[] = {8,4,5,7,1,3,6,2};
        int temp[] = new int[arr.length];
        mergeSort(arr,0,arr.length -1,temp);
        System.out.println(Arrays.toString(temp));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        int temps[] = new int[array.length];
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                mergeSort(array,0,array.length-1,temps);
            }
        });
    }


    //拆分数组
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        //若左元素下标小于右边元素下标，表示该数组还能进行拆分
        if (left < right) {
            //得出数组中间元素下标，将数组一分为二，进行递归分解
            int mid = (left + right) / 2;
            //左递归分解
            mergeSort(arr,left,mid,temp);
            //右递归分解
            mergeSort(arr,mid+1,right,temp);
            //在每层分解都需合并
            merge(arr,left,mid,right,temp);
        }
    }

    /**
     * 合并数组
     * @param arr 待排序数组
     * @param left 左有序列表开始索引
     * @param mid 中间索引
     * @param right 数组最右索引
     * @param temp 中转数组
     */
    public static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int i = left; //左有序列表开始索引
        int j = mid+1; //右有序列表开始索引
        int t = 0;//temp数组的指针

        //先把左右2边有序列表，根据规则填充到temp数组中
        //直到左右两边有序序列，有1边处理完毕后停止
        while (i <= mid && j <= right){
            if (arr[i] <= arr[j]){
                temp[t] = arr[i];
                t += 1;
                i += 1;
            }else {
                temp[t] = arr[j];
                t += 1;
                j += 1;
            }
        }
        //把有剩余数据的有序列表元素填充到temp中
        while (i <= mid){
            temp[t] = arr[i];
            t += 1;
            i += 1;
        }
        while (j <= right){
            temp[t] = arr[j];
            t += 1;
            j += 1;
        }
        //将temp元素拷贝到arr中
        t = 0;
        int temLeft = left;
        while (temLeft <= right) {
            arr[temLeft] = temp[t];
            t += 1;
            temLeft += 1;
        }
    }
}
