package top.jolyoulu.数据结构.散列表.数组链表实现;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/18 13:44
 * @Version 1.0
 */
public class HashTableDemo {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(5);
        System.out.println("====================插入数据====================");
        hashTable.add(new Emp(1,"test1"));
        hashTable.add(new Emp(2,"test2"));
        hashTable.add(new Emp(6,"test6"));
        hashTable.add(new Emp(11,"test11"));
        hashTable.list();
        System.out.println("====================查找数据====================");
        System.out.println("查找id=6的雇员信息 => "+hashTable.findEmpById(6));
        System.out.println("查找id=3的雇员信息 => "+hashTable.findEmpById(3));
        System.out.println("====================删除数据====================");
        System.out.println("删除id=1的雇员信息");
        hashTable.delEmpById(1);
        System.out.println("删除id=11的雇员信息");
        hashTable.delEmpById(11);
        hashTable.list();
    }
}
//参加哈希表，管理多个链表
class HashTable{
    private EmpLInkedList[] empLInkedListArray;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.empLInkedListArray = new EmpLInkedList[size];
        //数组中的所有元素都要初始化，一定要做，否则空指针异常
        for (int i = 0; i < empLInkedListArray.length; i++) {
            empLInkedListArray[i] = new EmpLInkedList();
        }
    }

    //添加雇员
    public void add(Emp emp){
        //根据员工Id，计算得到员工应该加入那个链表
        int empLinkedListNo = hashFun(emp.getId());
        empLInkedListArray[empLinkedListNo].add(emp);
    }

    //遍历所有链表，遍历hash表
    public void list(){
        for (int i = 0; i < empLInkedListArray.length; i++) {
            empLInkedListArray[i].list(i);
        }
    }

    //根据id查找雇员
    public Emp findEmpById(int id){
        //根据员工Id，计算得到员工应该加入那个链表
        int empLinkedListNo = hashFun(id);
        return empLInkedListArray[empLinkedListNo].findEmpById(id);
    }

    //根据id删除雇员
    public void delEmpById(int id){
        //根据员工Id，计算得到员工应该加入那个链表
        int empLinkedListNo = hashFun(id);
        empLInkedListArray[empLinkedListNo].delEmpById(id);
    }

    //编写散列函数
    public int hashFun(int id){
        return id % size;
    }
}
//参加一个EmpLInkedList
class EmpLInkedList{
    //头指针指向第一个emp对象，默认null
    private Emp head;

    //添加雇员到链表，无序链表，可优化为有序
    public void add(Emp emp){
        //如果是链表第一个，直接覆盖head即可
        if (head == null){
            head = emp;
            return;
        }
        //不是第一个着遍历直到最后，并且插入元素
        Emp curEmp = head;
        while (true){
            if (curEmp.getNext() == null){
                break;
            }
            curEmp = curEmp.getNext();
        }
        curEmp.setNext(emp);
    }

    //遍历链表雇员信息
    public void list(int no){
        //头无元素表示链表空，不打印任何数据
        if (head == null){
            System.out.println("第"+(no+1)+"链表信息为 空");
            return;
        }
        System.out.print("第"+(no+1)+"链表信息为");
        Emp curEmp = head;
        //遍历大于所有id
        while (true){
            System.out.printf(" => id=%d name=%s",curEmp.getId(),curEmp.getName());
            if (curEmp.getNext() == null){
                break;
            }
            curEmp = curEmp.getNext();
        }
        System.out.println();
    }

    //根据id查找雇员
    public Emp findEmpById(int id){
        //判断当前链表是否空
        if (head == null){
            return null;
        }
        //遍历链表查找元素
        Emp curEmp = head;
        while (true){
            //找到了返回
            if (curEmp.getId() == id){
                break;
            }
            //如果到最后一个了表示找不到，返回null
            if (curEmp.getNext() == null){
                curEmp = null;
                break;
            }
            curEmp = curEmp.getNext();
        }
        return curEmp;
    }

    //根据id删除雇员
    public void delEmpById(int id){
        //判断当前链表是否空
        if (head == null){
            return;
        }
        //遍历链表查找元素
        Emp curEmp = head;
        while (true){
            //如果到最后一个了表示找不到，返回null
            if (curEmp.getNext() == null){
                return;
            }
            //如果是头元素
            if (curEmp.getId() == id){
                head = curEmp.getNext();
                return;
            }
            //找到了结束循环
            if (curEmp.getNext().getId() == id){
                curEmp.setNext(curEmp.getNext().getNext());
                return;
            }
            curEmp = curEmp.getNext();
        }
    }
}
//表示一个雇员
@Data
class Emp{
    private int id;
    private String name;
    private Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Emp{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
