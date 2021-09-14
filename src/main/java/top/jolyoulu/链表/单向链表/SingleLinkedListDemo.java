package top.jolyoulu.链表.单向链表;

import lombok.Data;

import java.io.*;
import java.util.Stack;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/9 10:09
 * @Version 1.0
 * 单链表
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        SingleLinkedList linkedList;
        System.out.println("=======================无序链表=======================");
        linkedList = new SingleLinkedList();
        linkedList.add(new HeroNode(2,"李四","ls"));
        linkedList.add(new HeroNode(1,"张三","zs"));
        linkedList.add(new HeroNode(3,"王五","ww"));
        linkedList.print();
        System.out.println("=======================有序链表=======================");
        linkedList = new SingleLinkedList();
        linkedList.addByOrder(new HeroNode(2,"李四","ls"));
        linkedList.addByOrder(new HeroNode(1,"张三","zs"));
        linkedList.addByOrder(new HeroNode(3,"王五","ww"));
        linkedList.print();
        System.out.println("=======================修改节点=======================");
        linkedList.update(new HeroNode(3,"赵六","zl"));
        linkedList.print();
        System.out.println("=======================删除节点=======================");
        linkedList.delete(3);
        linkedList.print();
        System.out.println("=======================扩展练习=======================");
        System.out.printf("链表有效节点数 = %d\n",linkedList.size());
        System.out.println("获取链表倒数第1个节点 = "+linkedList.findLastIndexNode(1));
        System.out.println("链表节点反转结果");
        linkedList.reversal();
        linkedList.print();
        System.out.println("链表节点反向打印");
        linkedList.reversalPrint();
        System.out.println("链表合并");
        SingleLinkedList a = new SingleLinkedList();
        a.add(new HeroNode(2,"李四","ls"));
        a.add(new HeroNode(1,"张三","zs"));
        SingleLinkedList b = new SingleLinkedList();
        b.add(new HeroNode(8,"李四","ls"));
        b.add(new HeroNode(4,"王五","ww"));
        SingleLinkedList merge = SingleLinkedList.merge(a, b);
        merge.print();
    }
}
//定义一个用于管理HeroNode的链表
@Data
class SingleLinkedList{
    //初始化一个头节点，作用标记链表的第一个节点，不存储任何数据
    private HeroNode head = new HeroNode(0,"","");

    //添加节点到单向链表，无序追加
    public void add(HeroNode heroNode){
        //因为head节点不能动，所有需要一个临时变量
        HeroNode temp = head;
        //遍历链表
        //若当前节点的next不为空，表示当前节点未到末位
        while (temp.next != null) {
            //如果next不为空节点向后移动
            temp = temp.next;
        }
        //循环退出后temp已经是最后节点
        //将节点添加到最后节点的netx
        temp.next = heroNode;
    }

    //有序添加，根据no排序
    public void addByOrder(HeroNode heroNode){
        //因为head节点不能动，所有需要一个临时变量
        HeroNode temp = head;
        boolean flag = false; //添加的编号是否存在
        //遍历查询
        while (true){
            //temp已经遍历到末尾节点了，退出循环
            if (temp.next == null){
                break;
            }
            //如果当前节点的下一个节点序号比当前节点大，那么就结束循环
            //说明这个节点就一个插入到当前节点的下一个节点
            if (temp.next.no > heroNode.no){
                break;
            }else if (temp.next.no == heroNode.no){ //相同说明存在不在添加
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //如果flag=true 表示编号已经存在不插入
        if (flag){
            System.out.println("插入的编号已经存在不能添加: "+heroNode);
        }else {
            //插入数据
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点信息，根据no修改
    public void update(HeroNode heroNode){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点，是上一个节点
        HeroNode temp = head;
        boolean flag = false; //如果true表示找到节点
        do {
            if (temp.getNo().equals(heroNode.getNo())){
                flag = true;
                break;
            }
        }while ((temp = temp.next) != null);
        //修改节点内容
        if (flag){
            temp.setName(heroNode.getName());
            temp.setNickname(heroNode.getNickname());
        }else {
            System.out.printf("没有找到编号:%d的节点\n",heroNode.getNo());
        }
    }

    //根据no删除指定节点
    public void delete(Integer no) {
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点
        HeroNode temp = head;
        boolean flag = false; //如果true表示找到节点
        while (temp.next != null){
            if (temp.next.getNo().equals(no)){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //删除当前节点，指向下一个节点
        if (flag){
            temp.next = temp.next.next;
        }else {
            System.out.printf("没有找到编号:%d的节点\n",no);
        }
    }

    //遍历，显示链表
    public void print(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //因为head节点不能动，所有需要一个临时变量
        HeroNode temp = head.next;
        //遍历链表
        //若当前节点的next不为空，表示当前节点未到末位
        do {
            System.out.println(temp);
        }while ((temp = temp.next) != null);
    }

    //获取单链表节点个数
    public int size(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return 0;
        }
        int length = 0;
        //头节点不统计
        HeroNode temp = head.next;
        while (temp != null){
            length++;
            temp = temp.next;
        }
        return length;
    }

    //获取链表倒数第k个节点
    //思路：先把链表从头到尾遍历一遍，得到总长度，总长度-index得到需返回的节点
    public HeroNode findLastIndexNode(Integer index){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return null;
        }
        //获取总长度
        int size = size();
        //总长度-index=倒数第k节点
        //检查合法性
        if (index <= 0 || index > size){
            return null;
        }
        //头节点不算
        HeroNode temp = head.next;
        for (int i = 0; i < size - index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    //反转链表
    //思路：创建一个新的头节点
    //遍历读取当前节点时把当前节点下的链表保存一份
    //将新头节点下一个节点(原生链表最前的数据)连接到当前节点下一个节点，这样当前节点就变为倒序的节点
    //将新头节点下一个节点连接到，连接到当前节点
    //将当前节点恢复到最初备份的节点链
    public void reversal(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //创建一个新节点
        HeroNode reversalHead = new HeroNode(0,"","");
        HeroNode temp = head.next;
        HeroNode next = null; //当前链表下一个节点
        //遍历所有节点
        while (temp != null){
            //把当前节点往后的所有节点保存下来
            next = temp.next;
            //将新头节点下一个节点，连到当前节点种
            temp.next = reversalHead.next;
            reversalHead.next = temp;
            //那会之前备份好的节点
            temp = next;
        }
        this.head = reversalHead;
    }

    //反向打印节点
    //思路：栈
    public void reversalPrint(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        Stack<HeroNode> stack = new Stack<>();
        //遍历节点压入栈
        while (temp != null){
            stack.add(temp);
            temp = temp.next;
        }
        while (!stack.empty()){
            System.out.println(stack.pop());
        }
    }

    //合并2个有序链表
    public static SingleLinkedList merge(SingleLinkedList aLinked,SingleLinkedList bLinked) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode aNode = aLinked.getHead().next;
        HeroNode bNode = bLinked.getHead().next;
        while (aNode != null){
            HeroNode back = aNode.next;
            aNode.next = null;
            singleLinkedList.addByOrder(aNode);
            aNode = back;
        }
        while (bNode != null){
            HeroNode back = bNode.next;
            bNode.next = null;
            singleLinkedList.addByOrder(bNode);
            bNode = back;
        }
        return singleLinkedList;
    }
}
//定义节点，每个节点就是一个对象，每个节点中包含下一个节点对象
@Data
class HeroNode implements Cloneable, Serializable {
    public Integer no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点

    //构造器
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HeroNode{");
        sb.append("no=").append(no);
        sb.append(", name='").append(name).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    protected HeroNode clone(){
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(this);
            objOut.flush();
            ObjectInputStream objInt = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
            return (HeroNode)objInt.readObject();
        }catch (Exception e){
            return null;
        }
    }
}
