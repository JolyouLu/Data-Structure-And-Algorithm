package top.jolyoulu.算法.查找算法.插值查找;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/17 15:34
 * @Version 1.0
 * 插值查找，工作流程与二分查找一致，与二分查找相比插值插值对连续数的数组(如：{1,2,3,4,5,6,7,8,9,10})有很高的效率
 * 但在分布交大的数组中插值查找速度不一定比二分查找快
 *
 * 不同之处是定位mid元素的计算方式
 *
 *                          low+high           1
 * 二分查找mid计算公式：mid = ---------- = low + ---(high-low)
 *                            2               2
 *
 *                                   key - a[low]
 * 插值查找mid计算公式：mid = low + ------------------(high-low)
 *                               a[high] - a[low]
 *                  化简 ===> mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
 */
public class InsertValSearch {

    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i;
        }

        System.out.println("========================查找元素匹配单个========================");
        int index = insertValSearch(arr, 0, arr.length - 1, 55);
        if (index == -1){
            System.out.println("未找到");
        }else {
            System.out.println("已经找到，该值对应下标：arr["+index+"]");
        }
    }

    //插值查找，返回单个结果下标
    public static int insertValSearch(int[] arr,int left,int right,int findVal){
        //找不到直接退出递归
        //在插值查找中必须有 findVal < arr[0] || findVal > arr[arr.length - 1] 判断，否则会越界
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]){
            return -1;
        }
        //在插值查找mid计算固定公式
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        //向左递归
        if (findVal < midVal){
            return insertValSearch(arr,left,mid-1, findVal);
            //向右递归
        }else if (findVal > midVal){
            return insertValSearch(arr,mid+1,right, findVal);
            //找到了直接返回
        } else{
            return mid;
        }
    }
}
