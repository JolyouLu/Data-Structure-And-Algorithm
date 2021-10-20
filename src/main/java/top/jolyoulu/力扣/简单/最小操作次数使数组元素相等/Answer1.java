package top.jolyoulu.力扣.简单.最小操作次数使数组元素相等;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/20 10:54
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.minMoves(new int[]{1, 2, 3}));
        System.out.println(solution.minMoves(new int[]{1, 1, 1}));
    }
}

//给你一个长度为 n 的整数数组，每次操作将会使 n - 1 个元素增加 1 。返回让数组所有元素相等的最小操作次数。
//
// 示例 1：
//输入：nums = [1,2,3]
//输出：3
//解释：
//只需要3次操作（注意每次操作会增加两个元素的值）：
//[1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
//
//
// 示例 2：
//输入：nums = [1,1,1]
//输出：0
//
// 提示：
//
// n == nums.length
// 1 <= nums.length <= 105
// -109 <= nums[i] <= 109
// 答案保证符合 32-bit 整数
//
// 思路：获取集合中最小值，并且轮流与集合中所有元素相减的和
// [1,2,3,4] 总移动次数等于 (2-1)+(3-1)+(4-1) = 6
// 详细过程：[1,2,3,4]  => [2,3,4,4] => [3,4,5,4] => [4,5,5,5]  => [5,6,6,5]  => [6,7,6,6]  => [7,7,7,7]
//
// [1,2,4] 总移动次数等于 (2-1)+(4-1) = 4
// 详细过程：[1,2,4]  =>  [2,3,4]  =>  [3,4,4]  =>  [4,5,4]  =>  [5,5,5]
//
// [2,3,4] 总移动次数等于 (3-2)+(4-2) = 3
// 详细过程：[2,3,4]  =>  [3,4,4]  =>  [4,5,4]  =>  [5,5,5]

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];
        //得到最小值
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min){
                min = nums[i];
            }
        }
        //计算次数
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res+= nums[i] - min;
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
