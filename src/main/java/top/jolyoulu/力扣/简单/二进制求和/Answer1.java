package top.jolyoulu.力扣.简单.二进制求和;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/28 9:21
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.addBinary("0", "0"));
        System.out.println(solution.addBinary("11", "1"));
        System.out.println(solution.addBinary("1010", "1011"));
        System.out.println(solution.addBinary("1111", "1111"));
        System.out.println(solution.addBinary("1110001", "110100100"));
    }
}
//给你两个二进制字符串，返回它们的和（用二进制表示）。
// 输入为 非空 字符串且只包含数字 1 和 0。
//
// 示例 1:
// 输入: a = "11", b = "1"
//输出: "100"
//
// 示例 2:
// 输入: a = "1010", b = "1011"
//输出: "10101"
//
// 提示：
// 每个字符串仅由字符 '0' 或 '1' 组成。
// 1 <= a.length, b.length <= 10^4
// 字符串如果不是 "0" ，就都不含前导零。
//
// Related Topics 位运算 数学 字符串 模拟

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder builder;
        StringBuilder bb = new StringBuilder(b);
        //补0让2个字符串长度一致
        if (a.length() < b.length()){
            builder = new StringBuilder(a);
            while (builder.length() < bb.length()){
                builder.insert(0,'0');
            }
            a = builder.toString();
        }else {
            builder = new StringBuilder(b);
            while (builder.length() < a.length()){
                builder.insert(0,'0');
            }
            b = builder.toString();
        }
        //计算
        builder = new StringBuilder();
        int carry = 0; //是否需进位
        for (int i = a.length() - 1; i >= 0 ; i--) {
            int v1 = (int) a.charAt(i) - 48;
            int v2 = (int) b.charAt(i) - 48;
            if (v2 + v1 + carry == 2){
                builder.insert(0,0);
                carry = 1;
            }else if (v2 + v1 + carry == 3){
                builder.insert(0,1);
                carry = 1;
            }else {
                builder.insert(0,v2 + v1 + carry);
                carry = 0;
            }
        }
        if (carry == 1){
            builder.insert(0,1);
        }
        return builder.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
