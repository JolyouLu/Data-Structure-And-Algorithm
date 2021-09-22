package top.jolyoulu.数据结构.树.顺序存储二叉树;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/22 9:54
 * @Version 1.0
 * 顺序存储二叉树，即将数据用数组方式顺序存储利用二叉树前序遍历遍历数组
 *
 *                             [1]
 *                          /      \
 *  [1,2,3,4,5,6]  ==>    [2]      [3]
 *                       /   \    /   \
 *                     [4]  [5] [6]  [7]
 *
 * 顺序存储二叉树特点
 *  1、顺序二叉树通常只考虑完全二叉树
 *  2、节点规律
 *       n:表示二叉树的第几个元素（即数组下标）
 *      第n个元素的左子节点为2*n+1
 *      第n个元素的右子节点为2*n+2
 *      第n个元素的父节点为(n-1)/2
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        new ArrayBinaryTree(arr).preOrder(0);
    }
}
class ArrayBinaryTree{
    private int[] arr;//存储数据节点的数组


    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    //前序遍历打印顺序存储二叉树
    public void preOrder(int index){
        if (arr == null || arr.length == 0){
            System.out.println("顺序存储二叉树为空");
        }
        //输出当前元素
        System.out.println(arr[index]);
        //向左递归遍历，判断即使后下标是否越界
        if ((2*index+1) < arr.length){
            preOrder(2*index+1);
        }
        //向由递归遍历，判断即使后下标是否越界
        if ((2*index+2) < arr.length){
            preOrder(2*index+2);
        }
    }
}
