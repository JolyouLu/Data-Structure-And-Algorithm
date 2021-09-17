package top.jolyoulu.算法.查找算法.二分查找;

import sun.awt.SunHints;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/17 13:59
 * @Version 1.0
 * 二分查找
 * 查找前提数组必须有序
 *
 * 1)、获取列表最左元素下标left，最右边元素下标right，以及中间元素下标mid
 * 2)、判断需查找的值，存在与那个区间，总是递归的执行直到找到值或遍历所有都找不到为止
 *    a.需查找的值<中间元素值，表示需查找的值位于中间元素左区间，范围缩小到[left,mid-1]
 *    b.需查找的值>中间元素值，表示需查找的值位于中间元素右区间，范围缩小到[mid+1,right]
 *    c.需查找的值=中间元素值，表示找到了即可返回
 *    d.left > right，表示需要查找的元素不在该数组中
 */
public class BinarySearch {

    public static void main(String[] args) {
        int arr[] = {1,8,10,89,100,100,1234};
        System.out.println("========================查找元素匹配单个========================");
        int index = binarySearch(arr, 0, arr.length - 1, 100);
        if (index == -1){
            System.out.println("未找到");
        }else {
            System.out.println("已经找到，该值对应下标：arr["+index+"]");
        }
        System.out.println("========================查找元素匹配多个========================");
        List<Integer> list = binarySearchAll(arr, 0, arr.length - 1, 100);
        if (list.size() == 0){
            System.out.println("未找到");
        }else {
            StringBuilder builder = new StringBuilder().append("已经找到，该值对应下标：");
            for (Integer integer : list) {
                builder.append(" arr[").append(integer).append("]");
            }
            System.out.println(builder.toString());
        }
    }

    //递归实现，二分查找，返回单个结果下标
    public static int binarySearch(int[] arr,int left,int right,int findVal){
        //找不到直接退出递归
        if (left > right){
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        //向左递归
        if (findVal < midVal){
            return binarySearch(arr,left,mid-1, findVal);
        //向右递归
        }else if (findVal > midVal){
            return binarySearch(arr,mid+1,right, findVal);
        //找到了直接返回
        } else{
            return mid;
        }
    }

    //递归实现，二分查找，返回所有找到结果的下标
    public static List<Integer> binarySearchAll(int[] arr,int left,int right,int findVal){
        //找不到直接退出递归
        if (left > right){
            return new ArrayList<>();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        //向左递归
        if (findVal < midVal){
            return binarySearchAll(arr,left,mid-1, findVal);
            //向右递归
        }else if (findVal > midVal){
            return binarySearchAll(arr,mid+1,right, findVal);
            //找到了直接返回
        } else{
            List<Integer> resList = new ArrayList<>();
            //找到后，扫描找到元素左右查看有没有相同元素
            //查找左边是否还存在相同元素，定义一个临时指针
            int temp = mid - 1;
            //如果临时指针大于0(表示还未到数组最左边)，并且当前临时指针指向元素与查到元素一致
            while (temp >= 0 && arr[temp] == findVal) {
                //加入到list中，临时指针左移动
                resList.add(temp);
                temp -= 1;
            }
            //将找到的mind元素添加入list
            resList.add(mid);
            //查找右边边是否还存在相同元素，定义一个临时指针
            temp = mid + 1;
            //如果临时指针小于0(表示还未到数组最右边)，并且当前临时指针指向元素与查到元素一致
            while (temp <= arr.length - 1 && arr[temp] == findVal) {
                //加入到list中，临时指针右移动
                resList.add(temp);
                temp += 1;
            }
            return resList;
        }
    }

}
