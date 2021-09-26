package top.jolyoulu.常用10种算法.二分查找非递归版;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/26 13:32
 * @Version 1.0
 */
public class BinarySearchNoRecur {

    public static void main(String[] args) {
        int[] arr = {1,3,8,10,11,67,100};
        System.out.println("找到2下标="+binarySearch(arr, 3));
    }

    /**
     * 二分查找非递归
     *
     * @param arr    代查找数组
     * @param target 需要查找的树
     * @return 返回相应下标，-1没找到
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0; //左下标
        int right = arr.length - 1; //右下标
        while (left <= right) { //还没找出来
            int mid = (left + right) / 2;
            if (arr[mid] == target) { //找到了
                return mid;
            } else if (target < arr[mid]) {//要找的数再mid左边，向左边找
                right = mid - 1;
            } else {//要找的数再mid右边，向右边找
                left = mid + 1;
            }
        }
        return -1;
    }
}
