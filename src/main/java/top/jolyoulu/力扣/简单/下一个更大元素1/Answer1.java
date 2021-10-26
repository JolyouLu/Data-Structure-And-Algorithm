package top.jolyoulu.力扣.简单.下一个更大元素1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/26 9:33
 * @Version 1.0
 * 执行耗时:3 ms,击败了91.45% 的Java用户 内存消耗:38.7 MB,击败了37.45% 的Java用户
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
        System.out.println(Arrays.toString(solution.nextGreaterElement(new int[]{2,4}, new int[]{1,2,3,4})));
        System.out.println(Arrays.toString(solution.nextGreaterElement(new int[]{1,3,5,2,4}, new int[]{6,5,4,3,2,1,7})));
    }
}
//给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
// 请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。
// nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。
//
// 示例 1:
//输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
//输出: [-1,3,-1]
//解释:
//    对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
//    对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
//    对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
//
// 示例 2:
//输入: nums1 = [2,4], nums2 = [1,2,3,4].
//输出: [3,-1]
//解释:
//    对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
//    对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
//
// 提示：
// 1 <= nums1.length <= nums2.length <= 1000
// 0 <= nums1[i], nums2[i] <= 104
// nums1和nums2中所有整数 互不相同
// nums1 中的所有整数同样出现在 nums2 中

// 进阶：你可以设计一个时间复杂度为 O(nums1.length + nums2.length) 的解决方案吗？
// Related Topics 栈 数组 哈希表 单调栈


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        //得出每一个元素找到下一个更大的元素的数组
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = nums2.length - 1; i >= 0 ; i--) {
            map.put(nums2[i],-1);
            int index = i;
            while ((index+1) < nums2.length){
                if (nums2[index+1] > nums2[i] && nums2[index+1] > map.get(nums2[i])) {
                    //添加数据
                    map.replace(nums2[i],nums2[index+1]);
                    break;
                }
                index++;
            }
        }
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = map.get(nums1[i]);
        }
        return nums1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)