package top.jolyoulu.力扣.简单.最大子序和;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/25 9:25
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 示例 1：
//输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出：6
//解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
//
// 示例 2：
//输入：nums = [1]
//输出：1
//
// 示例 3：
//输入：nums = [0]
//输出：0
//
// 示例 4：
//输入：nums = [-1]
//输出：-1
//
// 示例 5：
//输入：nums = [-100000]
//输出：-100000
//
// 提示：
// 1 <= nums.length <= 105
// -104 <= nums[i] <= 104
//
// 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
// Related Topics 数组 分治 动态规划
// 👍 3874 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxSubArray(int[] nums) {
        //先认为第一个数为最大数
        int ans = nums[0];
        //用于和
        int sum = 0;
        for (int num : nums) {
            if (sum > 0){ //如果当前和是大于0的，尝试在该基础上继续增加一个数
                sum += num;
            }else { //否则丢弃当前结果，获取当前下班值做为结果
                sum = num;
            }
            ans = Math.max(sum,ans); //将当前结果与目前最大和比较，返回最大和
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)