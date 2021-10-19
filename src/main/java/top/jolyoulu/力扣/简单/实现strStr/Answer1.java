package top.jolyoulu.力扣.简单.实现strStr;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/19 9:14
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.strStr("hello", "ll"));
        System.out.println(solution.strStr("aaaaa", "bba"));
        System.out.println(solution.strStr("", ""));
    }
}
//实现 strStr() 函数。
//
// 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如
//果不存在，则返回 -1 。
//
// 说明：
// 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
// 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java 的 indexOf() 定义相符。
//
// 示例 1：
//输入：haystack = "hello", needle = "ll"
//输出：2
//
// 示例 2：
//输入：haystack = "aaaaa", needle = "bba"
//输出：-1
//
// 示例 3：
//输入：haystack = "", needle = ""
//输出：0
//
// 提示：
// 0 <= haystack.length, needle.length <= 5 * 104
// haystack 和 needle 仅由小写英文字符组成


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int strStr(String haystack, String needle) {
        if (haystack.length() <= 0) {
            if (needle.length() <= 0) {
                return 0;
            }
            return -1;
        }
        return kmpSearch(haystack, needle, kmpNext(needle));
    }

    public static int kmpSearch(String haystack, String needle, int[] next) {
        for (int i = 0, j = 0; i < haystack.length(); i++) {
            //i与j不相同时，需要调整j的位置
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    //获取部分匹配表
    public static int[] kmpNext(String needle) {
        //保存部分匹配表
        int[] next = new int[needle.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < needle.length(); i++) { //遍历所有元素
            //后面值与前面发生不相等，一直获取前一位部分匹配值，直到dest.charAt(i) == dest.charAt(j) 或 j<0停止
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                //将j的值设为匹配到元素下标-1
                j = next[j - 1];
            }
            //后面值与前面发生相等，部分匹配+1
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
