package top.jolyoulu.常用10种算法.KMP算法;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/28 14:12
 * @Version 1.0
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        System.out.println(kmpSearch(str1,str2,kmpNext(str2)));
    }

    /**
     * kmp搜索算法
     *
     * @param str1 源字符串
     * @param str2 子串
     * @param next 子串部分匹配表
     * @return 第一个匹配的位置，-1未匹配
     */
    public static int kmpSearch(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            //i与j不相同时，需要调整j的位置
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    //获取一个字符串的部分匹配值表
    //ABCDABD
    //0000120
    public static int[] kmpNext(String dest) {
        //保存部分匹配结果数组
        int[] next = new int[dest.length()];
        next[0] = 0; //如果字符串长度是1时，部分匹配值就是0
        for (int i = 1, j = 0; i < dest.length(); i++) {
            //后面值与前面发生不相等，一直获取前一位部分匹配值，直到dest.charAt(i) == dest.charAt(j) 或 j<0停止
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            //后面值与前面发生相等，部分匹配+1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
