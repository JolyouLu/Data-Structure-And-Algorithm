package top.jolyoulu.常用10种算法.分治算法;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/26 13:51
 * @Version 1.0
 * 分治算法，解汉诺塔问题
 * 思想：在分治算法中的每一层递归上都有3个步骤
 * 1)、分解：将原问题分解为若干个规模较小，相互独立，与原问题形式相同的子问题
 * 2)、解决：若子问题规模较小而容易被解决则直接解决，否则递归解决各子问题
 * 3)、合并：将各子问题的解合并为原问题的解
 */
public class HanoiTower {

    public static void main(String[] args) {
        hanoiTower(5,'A','B','C');
    }

    //汉诺塔移动的方法
    public static void hanoiTower(int num, char a, char b, char c) {
        if (num == 1) { //如果只有一个直接移动到c
            System.out.println("第1个盘从 " + a + "->" + c);
        } else {
            //如果n>=2，将上面的盘递归
            //将最上面的所有盘移动到b
            hanoiTower(num - 1, a, c, b);
            //把最下边的盘移动到c
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            //把b塔的所有盘移动到c塔
            hanoiTower(num - 1, b, a, c);
        }
    }
}
