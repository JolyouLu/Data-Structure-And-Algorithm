package top.jolyoulu.力扣.简单.数字的补数;

import sun.nio.cs.ext.MacUkraine;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/18 9:19
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findComplement(5));
        System.out.println(solution.findComplement(2));
    }
}
//给你一个 正 整数 num ，输出它的补数。补数是对该数的二进制表示取反。
//
//
// 示例 1：
//输入：num = 5
//输出：2
//解释：5 的二进制表示为 101（没有前导零位），其补数为 010。所以你需要输出 2 。
//
// 示例 2：
//输入：num = 1
//输出：0
//解释：1 的二进制表示为 1（没有前导零位），其补数为 0。所以你需要输出 0 。
//
// 提示：
// 给定的整数 num 保证在 32 位带符号整数的范围内。
// num >= 1
// 你可以假定二进制数不包含前导零位。

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findComplement(int num) {
        //1000 0000 0000 0000 0000 0000 0000 0000
        int temp = 1 << 31;
        //记录移动的次数
        int index = 0;
        //temp 与运算如果等于 1 << 31 表示找到了正整数num的最左的一个 1 结束循环
        while ((temp & num) != 1 << 31){
            //num继续左移动
            num = num << 1;
            //记录左移的次数
            index++;
        }
        //将num取反，右移动回原来位置
        return ~num >>> index ;
    }
}
//leetcode submit region end(Prohibit modification and deletion)