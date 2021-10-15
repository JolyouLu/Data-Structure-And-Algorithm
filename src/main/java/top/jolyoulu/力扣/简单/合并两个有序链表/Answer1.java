package top.jolyoulu.力扣.简单.合并两个有序链表;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/15 11:01
 * @Version 1.0
 * 解答方案1
 * 思路：非递归实现，构建一个新的列表，将元素有序的插入进去
 */
public class Answer1 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
//        Solution.show(l1);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
//        Solution.show(l2);
        Solution solution = new Solution();
        solution.mergeTwoLists(l1, l2);
//        solution.mergeTwoLists(new ListNode(), new ListNode());
    }
}
//将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
//
// 示例 1：
//输入：l1 = [1,2,4], l2 = [1,3,4]
//输出：[1,1,2,3,4,4]
//
// 示例 2：
//输入：l1 = [], l2 = []
//输出：[]
//
// 示例 3：
//输入：l1 = [], l2 = [0]
//输出：[0]
//
// 提示：
// 两个链表的节点数目范围是 [0, 50]
// -100 <= Node.val <= 100
// l1 和 l2 均按 非递减顺序 排列
//
// Related Topics 递归 链表
class Solution {
    /**2列表合并*/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode listNode = new ListNode();
        //将l1数插入到listNode中
        ListNode temp = listNode;
        ListNode tempL1 = l1;
        while (tempL1 != null){
            temp.next = tempL1;
            temp = temp.next;
            tempL1 = tempL1.next;
        }
        Solution.show(listNode);
        //将l2数组有序插入到listNode中
        temp = listNode;
        ListNode tempL2 = l2;
        while (tempL2 != null){
            //获取当前值上一位
            while (temp.next != null){
                if (temp.next.val > tempL2.val){ //找到合适位置的前一位
                    break;
                }
                temp = temp.next; //下一位
            }
            //插入
            ListNode next = temp.next;
            temp.next = new ListNode(tempL2.val);
            temp.next.next = next;
            tempL2 = tempL2.next;
        }
        Solution.show(listNode);
        return listNode.next;
    }

    /**打印列表内容*/
    static void show(ListNode head){
        ListNode temp = head;
        while (temp != null){
            System.out.print(temp.val+" ");
            temp = temp.next;
        }
        System.out.println();
    }
}
