package top.jolyoulu.linkedlist.bothway;

import lombok.Data;

import javax.swing.table.TableColumnModel;
import java.io.*;
import java.util.Stack;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/9 10:09
 * @Version 1.0
 * 双链表
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        DoubleLinkedList linkedList;
        System.out.println("=======================无序链表=======================");
        linkedList = new DoubleLinkedList();
        linkedList.add(new HeroNode(2,"李四","ls"));
        linkedList.add(new HeroNode(1,"张三","zs"));
        linkedList.add(new HeroNode(3,"王五","ww"));
        linkedList.print();
        System.out.println("=======================修改节点=======================");
        linkedList.update(new HeroNode(3,"赵六","zl"));
        linkedList.print();
        System.out.println("=======================删除节点=======================");
        linkedList.delete(3);
        linkedList.print();
    }
}

//定义一个用于管理HeroNode的链表
@Data
class DoubleLinkedList {
    //初始化一个头节点，作用标记链表的第一个节点，不存储任何数据
    private HeroNode head;
    //初始化一个尾节点，作用标记链表的第一个节点，不存储任何数据
    private HeroNode tail;

    public DoubleLinkedList() {
        this.head = new HeroNode(0,"head","head");
        this.tail = new HeroNode(Integer.MAX_VALUE,"tail","tail");
        this.head.next = tail;
        this.tail.pre = head;
    }

    //添加节点到双向链表最后
    public void add(HeroNode heroNode){
        //因为head节点不能动，所有需要一个临时变量
        HeroNode temp = head;
        //遍历链表
        //若当前节点的next不为空，表示当前节点未到末位
        while (temp.next != tail) {
            //如果next不为空节点向后移动
            temp = temp.next;
        }
        //循环退出后temp已经是最后节点
        //将节点添加到最后节点的next
        //最后一个节点的pre指向temp
        temp.next = heroNode;
        heroNode.pre = temp;

        heroNode.next = tail;
        tail.pre = heroNode;
    }

    //修改节点信息，根据no修改
    public void update(HeroNode heroNode){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点
        HeroNode temp = head;
        boolean flag = false; //如果true表示找到节点
        do {
            if (temp.getNo().equals(heroNode.getNo())){
                flag = true;
                break;
            }
        }while ((temp = temp.next) != tail);
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
        if (head.next == tail){
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点
        HeroNode temp = head.next;
        boolean flag = false; //如果true表示找到节点
        do {
            if (temp.getNo().equals(no)){
                flag = true;
                break;
            }
        }while ((temp = temp.next) != tail);
        //删除当前节点，指向下一个节点
        if (flag){
            temp.pre.next = temp.next;
            temp.next.pre = temp.pre;
        }else {
            System.out.printf("没有找到编号:%d的节点\n",no);
        }
    }

    //遍历，显示链表
    public void print(){
        //判断链表是否为空
        if (head.next == tail){
            System.out.println("链表为空");
            return;
        }
        //因为head节点不能动，所有需要一个临时变量
        HeroNode temp = head.next;
        //遍历链表
        //若当前节点的next不为空，表示当前节点未到末位
        do {
            System.out.println(temp);
        }while ((temp = temp.next) != tail);
    }

    //获取单链表节点个数
    public int size(){
        //判断链表是否为空
        if (head.next == tail){
            System.out.println("链表为空");
            return 0;
        }
        int length = 0;
        //头节点不统计
        HeroNode temp = head.next;
        while (temp != tail){
            length++;
            temp = temp.next;
        }
        return length;
    }
}

//定义节点，每个节点就是一个对象，每个节点中包含下一个节点对象
@Data
class HeroNode implements Cloneable, Serializable {
    public Integer no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点
    public HeroNode pre; //指向上一个节点

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
