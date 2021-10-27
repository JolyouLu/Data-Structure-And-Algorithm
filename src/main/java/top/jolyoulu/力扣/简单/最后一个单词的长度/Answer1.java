package top.jolyoulu.力扣.简单.最后一个单词的长度;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/27 9:14
 * @Version 1.0
 */
public class Answer1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.lengthOfLastWord("Hello World"));
        System.out.println(solution.lengthOfLastWord("   fly me   to   the moon  "));
        System.out.println(solution.lengthOfLastWord("luffy is still joyboy"));
    }
}
//给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度。
// 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
//
// 示例 1：
//输入：s = "Hello World"
//输出：5
//
// 示例 2：
//输入：s = "   fly me   to   the moon  "
//输出：4
//
// 示例 3：
//输入：s = "luffy is still joyboy"
//输出：6
//
// 提示：
// 1 <= s.length <= 104
// s 仅有英文字母和空格 ' ' 组成
// s 中至少存在一个单词
// Related Topics 字符串
// 👍 391 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int lengthOfLastWord(String s) {
        int len = 0;
        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != ' '){ //如果非空 len + 1
                len++;
            }else if (chars[i] == ' ' && len > 0){
                break;
            }
        }
        return len;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
