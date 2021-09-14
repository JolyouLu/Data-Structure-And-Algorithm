package top.jolyoulu.递归算法.八皇后;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/14 13:55
 * @Version 1.0
 * 八皇后问题：在8×8格的国际象棋上摆放八个皇后，使其不能互相攻击，
 * 即任意两个皇后都不能处于同一行、同一列或同一斜线上，问一共有多少种摆法。
 *
 * 递归-回溯算法解决八皇后的问题
 * 1)、将第一个皇后，放到第一行第一列
 * 2)、将第二个皇后，放到第二行第一列，判断一下位置是否OK，如果不OK就向右边一点一个格子继续判断
 * 3)、以此类推从第三、四、五..直到第八个皇后放入与其它皇后都不冲突，记为一个准确的揭发
 * 4)、得到一个正确的解法后，开始回溯，回退到上一个皇后去移动下剩下皇后的棋子
 * 5)、直到所有回溯到第一个皇后，第一个皇后向右移动一位，继续1-4步骤
 */
public class EightQueen {

    //定义皇后的数量
    int max = 8;
    //定义一个保存结果的数组，格式array[i]=x,棋盘的第i+1个皇后，放在第i+1行的x+1列
    int[] array = new int[max];
    static int count = 0;
    static int judge = 0;
    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        eightQueen.check(0);
        System.out.printf("共有%d个解法\n",count);
        System.out.printf("共进行了%d次冲突判断\n",judge);
    }

    //放置第n个皇后
    private void check(int n){
        //8个皇后已经放完了
        if (n == max){
            print();
            return;
        }
        //依次放入皇后，并且判断冲突
        //每一层check都会循环8次，形成回溯
        for (int i = 0; i < max; i++) {
            //先把当当前皇后，放到该行的第1列
            array[n] = i;
            //放完后判断是否冲突
            //不冲突
            if (judge(n)){
                //递归放下一个皇后
                check(n+1);
            }
            //如果冲突会继续执行for，将n皇后在本行向右边移动1个格子
        }
    }

    //查看当放第n个皇后时，是否与前面摆放的皇后冲突
    private boolean judge(int n){
        judge++;
        for (int i = 0; i < n; i++) {
            //array[i] == array[n] 在同一列
            //Math.abs(n-i) == Math.abs(array[n] - array[i]) 是否在同一斜线上
            if (array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    //打印皇后摆放的位置
    private void print(){
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+"");
        }
        System.out.println();
    }

}
