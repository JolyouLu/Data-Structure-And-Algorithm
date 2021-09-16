package top.jolyoulu.排序算法.快速排序;

import top.jolyoulu.utils.IntGenerate;
import top.jolyoulu.utils.TimerUtils;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/16 14:41
 * @Version 1.0
 * 快速排序算法
 *
 * 1)、在列表中取出中元素，已中间元素值为参照，交换左右2变元素，使得左边元素始终小于中间元素，右边元素始终大于中间元素
 * 2)、递归使用1的方式，对中间元素左边元素操作直到无元素可交换为止，中间值右边元素也是一样
 *
 * 例子:[-9,78,0,-23,-567,70]
 * 排序流程：
 *      1)、取出中间元素0，最左元素下标0，最右元素下标5
 *      2)、循环移动左下标，直到找到大于中间元素的值(表示该值应该放到中间值的右边)，或者左下标到达中间元素位置，退出 => 左元素下标 1
 *      3)、循环移动右下标，直到找到小于中间元素的值(表示该值应该放到中间值的左边)，或者右下标到达中间元素位置，退出 => 右元素下标 4
 *      4)、交换左右下标元素 => [-9,-567,0,-23,78,70]
 *      5)、判断左右下标是等于中间元素上：
 *          a、若左下标值与中间元素相等，表示原左下标在中间元素位置，右边下标在中间元素后一个位置，相互进行交换了，右下标可向左移动1位
 *          b、若右下标值与中间元素相等，表示原右下标在中间元素位置，左边下标在中间元素前一个位置，相互进行交换了，左下标可向右移动1位
 *      6)、判断左右下标是否相等，左下标左移动1，右下标右移1，否则会溢出
 *      7)、判断中间元素左边部分是否需要迭代
 *          a、若左边下标大于数组最左元素下标(即0)，表示需要截取左边剩余元素使用快速排序算法继续排序
 *      8)、判断中间元素右边部分是否需要迭代
 *          a、若右边下标小于数组最右元素下标(arr.len - 1)，表示需要截取右边剩余元素使用快速排序算法继续排序
 */
public class QuickSort {
    public static void main(String[] args) {
        System.out.println("============================简单测试============================");
        int[] arr = {-9,78,0,-23,-567,70};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));

        //构建8W随机数组
        System.out.println("============================8W随机数排序耗时============================");
        int[] array = new IntGenerate().array(80000);
        TimerUtils.timer(new TimerUtils.Task() {
            @Override
            public void run() {
                quickSort(array,0,array.length-1);
            }
        });
    }

    public static void quickSort(int[] arr,int left,int right){
        int l = left; //左下标
        int r = right; //右下标
        int pivot = arr[(l + r) / 2]; //中间值
        int temp = 0;
        //如果右边下标，比左边下标大，表示还需要遍历交换
        while (l < r) {
            //在pivot的左边向右一直找，直到找到一个大于pivot值退出，表示这个值需要放在pivot右边
            while (arr[l] < pivot) {
                l += 1;
            }
            //在pivot的右向左边一直找，直到找到一个小于pivot值退出，表示这个值需要放在pivot左边
            while (arr[r] > pivot) {
                r -= 1;
            }
            //如果左下标已经大于右下标，表示已经遍历交换完成了，当前pivot左边的值一定大于pivot右边的值
            if (l >= r) {
                break;
            }
            //交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            //如果交换完成后，发现左下标的值 == pivot 让右下标--，向左移动
            if (arr[l] == pivot) {
                r -= 1;
            }
            //如果交换完成后，发现右下标的值 == pivot 让左下标++，向右移动
            if (arr[r] == pivot) {
                l += 1;
            }
        }
        //如果左右坐标相等那么需要错开位置，否则出现栈溢出
        //如果如果l==r，必须l++，r==
        if (l == r) {
            l += 1;
            r -= 1;
        }
        //如果右下标未达到最左边，那么表示左边的还需要递归排序
        if (left < r){
            //最左开始直到右下标的元素需要排序
            quickSort(arr,left,r);
        }
        //如果左坐标问达到最右边，那么表示右边的还需要递归排序
        if (right > l){
            //左下标开始直到最右的元素需要排序
            quickSort(arr,l,right);
        }
    }
}
