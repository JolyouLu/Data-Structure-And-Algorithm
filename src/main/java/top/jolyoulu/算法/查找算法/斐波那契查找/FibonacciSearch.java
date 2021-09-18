package top.jolyoulu.算法.查找算法.斐波那契查找;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/17 16:29
 * @Version 1.0
 */
public class FibonacciSearch {

    public static int maxSeize = 20;

    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 100, 1234};
        System.out.println("========================查找元素匹配单个========================");
        int index = fibonacciSearch(arr, 89);
        if (index == -1){
            System.out.println("未找到");
        }else {
            System.out.println("已经找到，该值对应下标：arr["+index+"]");
        }
    }

    //斐波那契查找算法，非递归
    public static int fibonacciSearch(int[] arr, int key) {
        int left = 0;
        int right = arr.length - 1;
        int k = 0;//斐波那契分割数组下标
        int mid = 0;
        int[] f = fbl(); //获取斐波那契数列
        //获取斐波那契分割数值的下标
        while (right > f[k] - 1) {
            k++;
        }
        //f[k]值，可能大于数值的长度，需要使用Arrays类，构造一个新的数值指向a
        //不足的部分使用0补充
        int[] temp = Arrays.copyOf(arr, f[k]);
        //
        for (int i = right + 1; i < temp.length; i++) {
            temp[i] = arr[right];
        }
        //使用while循环查找key
        while (left <= right) {
            mid = left + f[k - 1] - 1;
            if (key < temp[mid]) { //继续向数组左边查找
                right = mid - 1;
                k--;
            } else if (key > temp[mid]) {//继续向数组右边查找
                left = mid + 1;
                k -= 2;
            }else {
                if (mid <= right){
                    return mid;
                }else {
                    return right;
                }
            }
        }
        return -1;
    }

    //得到一个斐波那契数列
    public static int[] fbl() {
        int[] f = new int[maxSeize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSeize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }
}
