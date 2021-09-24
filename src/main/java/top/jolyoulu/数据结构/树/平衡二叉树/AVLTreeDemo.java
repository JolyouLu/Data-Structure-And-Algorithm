package top.jolyoulu.数据结构.树.平衡二叉树;

import lombok.Data;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/24 9:59
 * @Version 1.0
 * 二叉平衡树(AVL平衡算法)，二叉顺序树的优化版本
 *
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        AVLTree tree = null;
        System.out.println("=====================左旋测试=====================");
        tree = new AVLTree();
        int[] arr1 = {4,3,6,5,7,8};
        for (int i : arr1){
            tree.add(new Node(i));
        }
        tree.infixOrder();
        System.out.println("树最大高度 = "+tree.getRoot().height());
        System.out.println("左子树高度 = "+tree.getRoot().leftHeight());
        System.out.println("右子树高度 = "+tree.getRoot().rightHeight());
        System.out.println("=====================右旋测试=====================");
        tree = new AVLTree();
        int[] arr2 = {10,12,8,9,7,6};
        for (int i : arr2){
            tree.add(new Node(i));
        }
        tree.infixOrder();
        System.out.println("树最大高度 = "+tree.getRoot().height());
        System.out.println("左子树高度 = "+tree.getRoot().leftHeight());
        System.out.println("右子树高度 = "+tree.getRoot().rightHeight());
        System.out.println("=====================双旋测试=====================");
        tree = new AVLTree();
        int[] arr3 = {10,11,7,6,8,9};
        for (int i : arr3){
            tree.add(new Node(i));
        }
        tree.infixOrder();
        System.out.println("树最大高度 = "+tree.getRoot().height());
        System.out.println("左子树高度 = "+tree.getRoot().leftHeight());
        System.out.println("右子树高度 = "+tree.getRoot().rightHeight());
    }
}
@Data
class AVLTree{
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

    //以传入node作为根节点循环向左节点查找返回最小节点的值
    public int delRightTreeMin(Node node){
        Node target = node;
        //一直向左边获取，直到最左叶子节点，即最小节点
        while (target.getLeft() != null){
            target = target.getLeft();
        }
        //删除最小节点
        delNode(target.getValue());
        return target.getValue();
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
                //已当前节点右子节点为根节点，找到最小值
                int min = delRightTreeMin(targetNode.getRight());
                targetNode.setValue(min);
                //如果只有1个子树的节点
            } else {
                //如果targetNode只有左子节点
                if (targetNode.getLeft() != null) {
                    if (parent != null){
                        //如果targetNode是parent的左子节点
                        if (parent.getLeft().getValue() == value) {
                            parent.setLeft(targetNode.getLeft());
                            //如果targetNode是parent的右子节点
                        } else {
                            parent.setRight(targetNode.getLeft());
                        }
                        //当前节点没有父节点，那么表示该节点是root节点
                    }else {
                        root = targetNode.getLeft();
                    }
                    //如果targetNode只有右子节点
                } else {
                    if (parent != null){
                        //如果targetNode是parent的左子节点
                        if (parent.getLeft().getValue() == value) {
                            parent.setLeft(targetNode.getRight());
                            //如果targetNode是parent的右子节点
                        } else {
                            parent.setRight(targetNode.getRight());
                        }
                        //当前节点没有父节点，那么表示该节点是root节点
                    }else {
                        root = targetNode.getRight();
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
    //返回左子树高度
    public int leftHeight(){
        if (left == null){
            return 0;
        }
        return left.height();
    }

    //返回右子树高度
    public int rightHeight(){
        if (right == null){
            return 0;
        }
        return right.height();
    }

    //返回以当前节点为根节点树的高度
    public int height(){
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    /**
     * 左旋转(当节点右子树高度 - 左子树高度 > 1，需左旋转降低右子树高度)
     *     旋转过程=>        1.拷贝一个当前节点值   2.将新的节点左子树       3.将新节点是右子树              4.将当前节点的值          5.将当前节点的右子树        6.将当前节点的左子树
     *                       作为新节点           设置为当前节点的左子树     设置为当前节点的右子树的左子树     替换成右边子节点          设置为右子树的右子树        设置为新节点
     *      [4]              [4新]   [4]            [4新] [4]              [4新]  [4]                [4新]  [6]                [4新]  [6]                  [6]
     *      / \                     / \            /      \              /  \     \                /  \     \                /  \     \                  / \
     *    [3] [6]     ==>       [3] [6]    ==>   [3]      [6]    ==>   [3] [5]    [6]      ==>   [3] [5]    [6]      ==>   [3] [5]    [7]    ==>      [4新] [7]
     *        / \                  / \                    / \                       \                         \                         \             / \    \
     *      [5] [7]              [5] [7]                [5] [7]                     [7]                       [7]                       [8]        [3]  [5]  [8]
     *           \                    \                      \                        \                         \
     *           [8]                  [8]                    [8]                      [8]                       [8]
     */
    public void leftRotate(){
        //拷贝一个当前节点值，作为新节点
        Node newNode = new Node(value);
        //将新的节点左子树，设置为当前节点的左子树
        newNode.setLeft(left);
        //将新节点是右子树，设置为当前节点的右子树的左子树
        newNode.setRight(right.getLeft());
        //将当前节点的值替换成右边子节点
        value = right.getValue();
        //将当前节点的右子树设置为右子树的右子树
        right = right.getRight();
        //将当前节点的左子树设置为新节点
        left = newNode;
    }

    /**
     * 右旋转(当节点左子树高度 - 右子树高度 > 1，需右旋转降低左子树高度)
     *     旋转过程=>        1.拷贝一个当前节点值     2.将新的节点右子树           3.将新节点是左子树               4.将当前节点的值           5.将当前节点的左子树         6.将当前节点的右子树
     *                       作为新节点            设置为当前节点的右子树        设置为当前节点的左子树的右子树      替换成左边子节点            设置为左子树的左子树         设置为新节点
     *         [10]             [10]    [10新]          [10]    [10新]           [10]    [10新]                [8]    [10新]            [8]    [10新]          [8]
     *         / \              / \                    /         \              /       / \                  /       / \              /       / \            / \
     *      [8] [12]  ==>    [8] [12]        ==>    [8]          [12] ==>    [8]     [9]  [12]     ==>    [8]     [9]  [12] ==>    [7]     [9]  [12] ==>  [7]  [10新]
     *      / \              / \                    / \                      /                            /                        /                      /    / \
     *    [7] [9]          [7] [9]                [7] [9]                  [7]                          [7]                      [6]                    [6]  [9] [12]
     *    /                /                      /                        /                            /
     *  [6]              [6]                    [6]                      [6]                          [6]
     */
    public void rightRotate(){
        //拷贝一个当前节点值，作为新节点
        Node newNode = new Node(value);
        //将新的节点右子树，设置为当前节点的右子树
        newNode.setRight(right);
        //将新节点是左子树，设置为当前节点的左子树的右子树
        newNode.setLeft(left.getRight());
        //将当前节点的值替换成左边子节点
        value = left.getValue();
        //将当前节点的左子树设置为左子树的左子树
        left = left.getLeft();
        //将当前节点的右子树设置为新节点
        right = newNode;
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

        //添加节点后判断右子树高度 - 左子树高度 > 1，左选择
        if (rightHeight() - leftHeight() > 1){
            //如果当前节点的右子节点，左子树高度大于右子树高度
            //那么需要让右子节点右旋转，在让当前节点左旋
            if (right != null && left.rightHeight() > left.leftHeight()){
                //先对右子树进行右旋转
                right.rightRotate();
                leftRotate();
            }else {
                leftRotate();
            }
            return; //平衡后可以退出了，继续往下走可能会出问题
        }

        //添加节点后判断左子树高度 - 右子树高度 > 1，右选择
        if (leftHeight() - rightHeight() > 1){
            //如果当前节点的左子节点，右子树高度大于左子树高度
            //那么需要让左子节点左旋转，在让当前节点右旋
            if (left != null && left.rightHeight() > left.leftHeight()){
                //先对左子树进行左旋转
                left.leftRotate();
                rightRotate();
            }else {
                rightRotate();
            }
            return;
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