package top.jolyoulu.数据结构.树.二叉排序树;

import lombok.Data;
import lombok.ToString;

import java.lang.annotation.Target;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/18 16:18
 * @Version 1.0
 * 二叉顺序树
 * 二叉排序树要求每个左节点必须小于父节点，每个右节点必须大于父节点，每个父节点下只有2个子节点
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        BinarySortTree tree = new BinarySortTree();
        int[] arr = {7,3,10,12,5,1,9,2};
        System.out.println("=====================添加数据=====================");
        for (int i : arr){
            tree.add(new Node(i));
        }
        tree.infixOrder();
        System.out.println("=====================删除叶子节点=====================");
        tree.delNode(2);
        tree.infixOrder();
        System.out.println("=====================删除1个子树叶子节点=====================");
        tree.add(new Node(2));
        tree.delNode(1);
        tree.infixOrder();
    }
}
//定义二叉顺序树
class BinarySortTree{
    private Node root;

    //添加节点
    public void add(Node node){
        if (root == null){
            root = node;
        }else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder(){
        if (root != null){
            root.infixOrder();
        }else {
            System.out.println("二叉顺序树为空无法遍历");
        }
    }

    //查找节点
    public Node search(int value){
        if (root == null){
            return null;
        }else {
            return root.search(value);
        }
    }

    //查找父节点
    public Node searchParent(int value){
        if (root == null){
            return null;
        }else {
            return root.searchParent(value);
        }
    }

    /***
     * 删除节点思路
     * 删除节点遇到3种情况
     * 1.删除叶子节点
     *      a.首先找到要删除的节点targetNode
     *      b.确定targetNode的父节点parent
     *      c.确定targetNode是paren的左子节点还是右子节点
     *      d.根据c确定情况设置parent.left=null/parent.right=null
     * 2.删除只有1个子树节点
     *      a.首先找到要删除的节点targetNode
     *      b.确定targetNode的父节点parent
     *      c.确定targetNode的子节点是左子节点还是右子节点
     *      d.targetNode是parent的左子节点还是右边子节点
     *        如果targetNode有左子节点: 1).targetNode是parent左子节点，parent.left=targetNode.left  ||  2).targetNode是parent右子节点，parent.right=targetNode.left
     *                                         parent                                          ||           parent
     *                                         /    \                                          ||           /    \
     *                                  targetNode  right                                      ||        right  targetNode
     *                                       /                                                 ||                /
     *                                   left                                                  ||             left
     *        如果targetNode有右子节点: 1).targetNode是parent左子节点，parent.left=targetNode.right ||   2).targetNode是parent右子节点，parent.right=targetNode.right
     *                                         parent                                          ||            parent
     *                                         /    \                                          ||            /    \
     *                                  targetNode  right                                      ||        right  targetNode
     *                                       \                                                 ||                  \
     *                                       right                                             ||                  right
     * 3.删除有2个子树的节点
     *      a.首先找到要删除的节点targetNode
     *      b.确定targetNode的父节点parent
     *      c.从targetNode右子树找到最小节点(如果从左子树找那么就找到最大节点)
     *      d.用临时变量，将最小节点的值保存到temp中
     *      e.删除最小节点
     *      f.targetNode.value = temp
     */
    public void delNode(int value){
        if (root == null){
            return;
        }else {
            //先去查找要删除的节点
            Node targetNode = search(value);
            if (targetNode == null){
                return;
            }
            //targetNode已经是最后一个节点直接删除
            if (root.getLeft() == null && root.getRight() == null){
                root = null;
                return;
            }
            //查找父节点
            Node parent = searchParent(value);
            //如果删除的节点是叶子节点
            if (targetNode.getLeft() == null && targetNode.getRight() == null){
                //如果targetNode是parent的左子节点
                if (parent.getLeft() != null && parent.getLeft().getValue() == value){
                    parent.setLeft(null);
                //如果targetNode是parent的右子节点
                }else if (parent.getRight() != null && parent.getRight().getValue() == value){
                    parent.setRight(null);
                }
            //如果是有2个子树的节点
            }else if (targetNode.getLeft() != null && targetNode.getRight() != null){

            //如果只有1个子树的节点
            } else {
                //如果targetNode只有左子节点
                if (targetNode.getLeft() != null) {
                    //如果targetNode是parent的左子节点
                    if (parent.getLeft().getValue() == value) {
                        parent.setLeft(targetNode.getLeft());
                    //如果targetNode是parent的右子节点
                    } else {
                        parent.setRight(targetNode.getLeft());
                    }
                //如果targetNode只有右子节点
                } else {
                    //如果targetNode是parent的左子节点
                    if (parent.getLeft().getValue() == value) {
                        parent.setLeft(targetNode.getRight());
                    //如果targetNode是parent的右子节点
                    } else {
                        parent.setRight(targetNode.getRight());
                    }
                }
            }
        }
    }
}
//创建节点
@Data
class Node{
    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
    }

    //递归添加节点添加
    public void add(Node node){
        if (node == null){
            return;
        }
        //传入节点与当前节点值比较
        if (node.value < this.value){
            //如果当前左子节点空，直接添加
            if (this.left == null){
                this.left = node;
            }else { //否则进行向左边找
                this.left.add(node);
            }
        }else {
            //如果当前右子节点空，直接添加
            if (this.right == null){
                this.right = node;
            }else { //否则进行向右边找
                this.right.add(node);
            }
        }
    }


    //查找节点
    public Node search(int value){
        if (value == this.value) { //找到了就返回
            return this;
        } else if (value < this.value) { //如果比当前节点小往左子树找
            //如果左子节点为空，表示找不到
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            //如果右子节点为空，表示找不到
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    //查找节点父节点
    public Node searchParent(int value){
        if ((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)) { //找到了就返回
            return this;
        }else {
            //如果查找值小于当前节点，并且当前节点左子节点不空，向左子树递归查找
            if (value < this.value && this.left != null){
                return this.left.searchParent(value);
            //如果查找值大于当前节点，并且当前节点右子节点不空，向右子树递归查找
            }else if (value >= this.value && this.right != null){
                return this.right.searchParent(value);
            //没有父节点(例：root节点)
            }else {
                return null;
            }
        }
    }

    //中序遍历二叉树
    public void infixOrder(){
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Node{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
