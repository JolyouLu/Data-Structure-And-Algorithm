package top.jolyoulu.算法.查找算法.线性查找;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/17 13:39
 * @Version 1.0
 * 线性查找
 * 对数组无要求，可有序可无序，最简单的查找算法，遍历整个数组
 */
public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {1,9,11,-1,34,89};
        int index = seqSearch(arr, 9);
        if (index == -1){
            System.out.println("未找到");
        }else {
            System.out.println("已经找到，该值对应下标：arr["+index+"]");
        }
    }

    public static int seqSearch(int[] arr,int value){
        //遍历整个数组逐一比对
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value){
                return i;
            }
        }
        return -1;
    }
}
