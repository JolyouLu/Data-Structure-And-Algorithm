package top.jolyoulu.数据结构.树.线索化二叉树;

import lombok.Data;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/22 10:46
 * @Version 1.0
 * 线索化二叉树，可提高二叉树遍历效率
 * 线索化二叉树，是对未无左节点或右边节点的主节点添加，前驱或后继节点
 * 线索化二叉树也分前序线索化，中序线索化，后序线索化
 * 线索化二叉树遍历无法使用普通的方式
 *
 * 如当前有二叉树，结构如下
 *          [1]
 *       /        \
 *     [3]       [6]
 *   /    \    /
 *  [8] [10] [14]
 * 使用中序线索化二叉树
 * 中序遍历结果=> [8,3,10,1,14,6]
 * 8无左右节点 => 为8添加后继节点3
 * 10无左右节点 => 为10添加前驱节点3，后继节点1
 * 14无左右节点 => 为14添加前驱节点1，后继节点6
 *
 * 实现思路：
 * 1)、每一次遍历节点时需保留上个的节点信息
 * 2)、前驱节点处理：若发现当前节点无左节点，将当前节点左节点指向上一个节点
 * 3)、后继节点处理：若发现保留的上个节点无右节点，将上一个节点右节点指向当前节点
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "1");
        HeroNode node2 = new HeroNode(3, "3");
        HeroNode node3 = new HeroNode(6, "6");
        HeroNode node4 = new HeroNode(8, "8");
        HeroNode node5 = new HeroNode(10, "10");
        HeroNode node6 = new HeroNode(14, "14");

        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        ThreadedBinaryTree tree = new ThreadedBinaryTree(root);
//        System.out.println("=================前序线索二叉树=================");
//        //前序线索化
//        tree.preThreadedNode(root);
//        //测试线索化
//        System.out.println("10号节点前驱节点 = "+node5.getLeft());
//        System.out.println("10号节点后继节点 = "+node5.getRight());
//        System.out.println("=================前序线索二叉树遍历=================");
//        //前序线索化
//        tree.preThreadedList();

//        System.out.println("=================中序线索二叉树=================");
//        //中序线索化
//        tree.infixThreadedNode(root);
//        //测试线索化
//        System.out.println("10号节点前驱节点 = "+node5.getLeft());
//        System.out.println("10号节点后继节点 = "+node5.getRight());
//        System.out.println("=================中序线索二叉树遍历=================");
//        //中序线索化
//        tree.preThreadedList();

        System.out.println("=================后序线索二叉树=================");
        //后序线索化
        tree.postThreadedNode(root);
        //测试线索化
        System.out.println("10号节点前驱节点 = "+node5.getLeft());
        System.out.println("10号节点后继节点 = "+node5.getRight());
        System.out.println("=================后序线索二叉树遍历=================");
        //后序线索化

    }
}
//定义二叉树，实现了线索化功能的二叉树
class ThreadedBinaryTree {
    private HeroNode root;

    //为了实现线索化，需要创建一个临时节点，保留当前节点的前一个节点
    private HeroNode pre = null;

    public ThreadedBinaryTree(HeroNode root) {
        this.root = root;
    }
    //前序线索化，传入节点对指定节点线索化
    public void preThreadedNode(HeroNode node){
        if (node == null){
            return;
        }
        //线索化当前节点
        //前驱节点处理
        if (node.getLeft() == null){
            //让当前节点左指针指向前驱节点
            node.setLeft(pre);
            //修改当前节点左指针类型
            node.setLeftType(1);
        }
        //处理当前节点的后驱节点
        if (pre != null && pre.getRight() == null && pre.getLeft() != node){
            //让前驱节点右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        //每处理节点后让当前节点是下一个节点的前驱节点
        pre = node;
        //线索化左子树，最左侧叶子节点
        if (node.getLeftType() == 0){
            preThreadedNode(node.getLeft());
        }
        //线索化右子树
        preThreadedNode(node.getRight());
    }

    //前序遍历线索二叉树
    public void preThreadedList(){
        //定义一个临时变量，存储当前遍历的节点
        HeroNode node = root;
        while (node !=null){
            //打印当前节点
            System.out.println(node);
            //循环找到leftType == 1的节点，即使最左叶子节点，即8
            while (node.getLeftType() == 0){
                node = node.getLeft();
                System.out.println(node);
            }
            //如果当前节点右指针指向后继节点，一直输出
            if (node.getRightType()==1){
                node=node.getRight();
            }else if (node.getRight()==null){
                //线索化前序遍历的最后一个结点的right一定为null,所以遍历完毕 退出循环
                break;
            }
        }
    }

    //编写对中序线索化方法，传入节点对指定节点线索化
    public void infixThreadedNode(HeroNode node){
        if (node == null){
            return;
        }
        //线索化左子树
        infixThreadedNode(node.getLeft());
        //线索化当前节点
        //处理当前主节点前驱节点
        if (node.getLeft() == null){
            //让当前节点左指针指向前驱节点
            node.setLeft(pre);
            //修改当前节点左指针类型
            node.setLeftType(1);
        }
        //处理当前节点的后驱节点
        if (pre != null && pre.getRight() == null){
            //让前驱节点右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        //每处理节点后让当前节点是下一个节点的前驱节点
        pre = node;
        //线索化右子树
        infixThreadedNode(node.getRight());
    }

    //遍历中序线索二叉树
    public void infixThreadedList(){
        //定义一个临时变量，存储当前遍历的节点
        HeroNode node = root;
        while (node !=null){
            //循环找到leftType == 1的节点，即使最左叶子节点，即8
            while (node.getLeftType() == 0){
                node = node.getLeft();
            }
            //打印当前节点
            System.out.println(node);
            //如果当前节点右指针指向后继节点，一直输出
            while (node.getRightType() == 1){
                //获取当前节点的后继节点，并输出
                node = node.getRight();
                System.out.println(node);;
            }
            //替换遍历的节点
            node = node.getRight();
        }
    }

    //编写对后序线索化方法，传入节点对指定节点线索化
    public void postThreadedNode(HeroNode node){
        if (node == null){
            return;
        }
        //线索化左子树
        postThreadedNode(node.getLeft());
        //线索化右子树
        postThreadedNode(node.getRight());
        //线索化当前节点
        //处理当前主节点前驱节点
        if (node.getLeft() == null){
            //让当前节点左指针指向前驱节点
            node.setLeft(pre);
            //修改当前节点左指针类型
            node.setLeftType(1);
        }
        //处理当前节点的后驱节点
        if (pre != null && pre.getRight() == null){
            //让前驱节点右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        //每处理节点后让当前节点是下一个节点的前驱节点
        pre = node;
    }


    //删除节点
    public void delNode(int no){
        if (root != null){
            //判断删除的节点是否是root
            //如果是就把树设置空
            if (root.getNo() == no){
                root = null;
                //否则就执行查找删除方法
            }else {
                root.delNode(no);
            }
        }else {
            System.out.println("二叉树根节点为空无法删除");
        }
    }
}
//创建节点
@Data
class HeroNode{
    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    //如果type=0表示指向左子树，type=1表示前驱节点
    private int leftType;
    //如果type=0表示指向右子树，type=1表示后继节点
    private int rightType;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HeroNode{");
        sb.append("no=").append(no);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }



    //节点删除
    public void delNode(int no){
        //先查看左节点是否匹配
        if (this.left != null && this.left.getNo() == no){
            this.left = null;
            return;
        }
        //左节点找不到看一下右节点是否匹配
        if (this.right != null && this.right.getNo() == no){
            this.right = null;
            return;
        }
        //若左右都没匹配着向左右递归查找需要删除的节点
        if (this.left != null){
            this.left.delNode(no);
        }
        if (this.right != null){
            this.right.delNode(no);
        }
    }

}