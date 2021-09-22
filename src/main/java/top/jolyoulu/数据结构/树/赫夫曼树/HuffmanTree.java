package top.jolyoulu.数据结构.树.赫夫曼树;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/22 16:33
 * @Version 1.0
 * 赫夫曼树
 * wpl最小的树(即所有叶子节点带权路径和最小树)
 *
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(arr);
        preOrder(huffmanTree);
    }

    //创建赫夫曼树
    public static Node createHuffmanTree(int[] arr){
        //将arr元素构建成Node对象ArrayList
        List<Node> nodes = new ArrayList<>();
        for (int value:arr){
            nodes.add(new Node(value));
        }
        //循环处理所有节点，最终之后留下一个组合好的树
        while (nodes.size() > 1){
            //使用工具类对容器排序
            Collections.sort(nodes);
//            System.out.println("nodes = "+nodes);
            //取出根节点最小的2颗二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //组合成一个新的二叉树
            Node parent = new Node(leftNode.getValue() + rightNode.getValue());
            parent.setLeft(leftNode);
            parent.setRight(rightNode);
            //从list删除组合后的2个节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将组合好的节点加入到node中
            nodes.add(parent);
        }
        //返回赫夫曼树的root节点
        return nodes.get(0);
    }

    //前序遍历
    public static void preOrder(Node root){
        root.preOrder();
    }
}
//创建节点
@Data
class Node implements Comparable<Node>{
    //节点权值
    private int value;
    //左子节点
    private Node left;
    //右子节点
    private Node right;

    public Node(int value) {
        this.value = value;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Node{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    //实现比较方法
    @Override
    public int compareTo(Node o) {
        //从小大大排序
        return this.value - o.value;
    }
}
