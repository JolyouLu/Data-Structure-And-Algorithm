package top.jolyoulu.数据结构.链表.约瑟夫问题;

import lombok.Data;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/11 15:24
 * @Version 1.0
 * 单向环形链表
 * 解决约瑟夫问题，及有一圈小孩手拉手围成圈，随便选择一个小孩并且给一个指定数n
 * 从这个小孩开始，一直数n个小孩立刻圆环，以此类推算出出对小孩的顺序
 */
public class JosepfuDemo {
    public static void main(String[] args) {
        CircleSingleLinkedList linkedList = new CircleSingleLinkedList();
        linkedList.addBoy(5);
        linkedList.print();
        System.out.println("从第1个开始，每2个打印一次");
        linkedList.cuntBoy(1,2);
    }
}
class CircleSingleLinkedList{
    //创建一个first节点，始终标记的第一个
    private Boy first = new Boy(-1);

    //给这个环形添加指定数量的小孩，构成一个环形链表
    public void addBoy(int nums){
        //数据校验
        if (nums < 1){
            System.out.println("nums的值必须>0");
        }
        //辅助节点标记着环形的最后一个节点
        Boy curBoy = null;
        //使用for循环创建环形链表
        for (int i = 1; i <= nums; i++) {
            //根据编号创建小孩节点
            Boy boy = new Boy(i);

            //如果是第一个小孩，直接赋值给first
            if (i == 1){
                first = boy;
                first.setNext(boy);
                curBoy = first;
            }

            curBoy.setNext(boy);
            boy.setNext(first);
            curBoy = curBoy.getNext();
        }
    }

    /**
     * 根据指定间隔读取节点
     * @param starNo 从那个节点开始
     * @param countNum 每隔几个节点读取一次
     */
    public void cuntBoy(int starNo,int countNum){
        if (first == null || starNo < 1){
            System.out.println("参数输入有误请重新输入");
            return;
        }
        //创建辅助节点
        Boy helper = first;
        //获取到最后一个节点
        while (true){
            if (helper.getNext() == first){
                break;
            }
            helper = helper.getNext();
        }
        //找到开始小孩
        for (int i = 0; i < starNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //开始报数
        while (true){
            //圈中只有一个人
            if (helper == first){
                break;
            }
            //移动countNum次，删除节点
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            //出圈
            System.out.println(first);
            first = first.getNext();
            helper.setNext(first);
        }
        //最后在圈中的节点
        System.out.println("最后在圈中Boy"+first.getNext());
    }

    //打印链表内容
    public void print(){
        if (first.getNo() == -1){
            System.out.println("链表为空");
        }
        //辅助指针，标记当前遍历的位置
        Boy curBoy = first;
        while (true){
            //打印节点
            System.out.println(curBoy);
            //如果节点下一个节点是头节点退出程序
            if (curBoy.getNext() == first){
                break;
            }
            curBoy = curBoy.getNext();
        }
    }

}
//创建一个Boy表示每个节点
@Data
class Boy{
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Boy{");
        sb.append("no=").append(no);
        sb.append('}');
        return sb.toString();
    }
}
