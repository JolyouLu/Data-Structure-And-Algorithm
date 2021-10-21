package top.jolyoulu.力扣.简单.加一;

import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/21 9:13
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.plusOne(new int[]{1, 2, 3})));
        System.out.println(Arrays.toString(solution.plusOne(new int[]{9})));
        System.out.println(Arrays.toString(solution.plusOne(new int[]{9, 9})));
        System.out.println(Arrays.toString(solution.plusOne(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0})));
        System.out.println(Arrays.toString(solution.plusOne(new int[]{1,0,0,0,0})));
    }
}
//给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
// 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
// 你可以假设除了整数 0 之外，这个整数不会以零开头。
//
// 示例 1：
//输入：digits = [1,2,3]
//输出：[1,2,4]
//解释：输入数组表示数字 123。
//
// 示例 2：
//输入：digits = [4,3,2,1]
//输出：[4,3,2,2]
//解释：输入数组表示数字 4321。
//
// 示例 3：
//输入：digits = [9,9]
//输出：[1,0,0]
//解释：输入数组表示数字 99。
//
// 示例 3：
//输入：digits = [0]
//输出：[1]
//
// 提示：
// 1 <= digits.length <= 100
// 0 <= digits[i] <= 9
// Related Topics 数组 数学


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] plusOne(int[] digits) {
        byte carry = 1;
        //数组末尾加一
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = digits[i] + carry;
            //若当前值等于10，表示需要进位，前面的数需要+1
            if (digits[i] == 10){
                digits[i] = digits[i] % 10;
                carry = 1;
            }else { //否则无序进位
                carry = 0;
            }
        }
        //遍历后若carry仍为1表示需要扩容数组
        if (carry == 1){
            int[] res = new int[digits.length + 1];
            res[0] = carry;
            for (int i = 1; i < res.length; i++) {
                res[i] = digits[i-1];
            }
            return res;
        }
        return digits;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
