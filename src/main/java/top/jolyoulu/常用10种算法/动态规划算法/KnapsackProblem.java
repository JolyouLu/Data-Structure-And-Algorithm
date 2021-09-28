package top.jolyoulu.常用10种算法.动态规划算法;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/27 13:53
 * @Version 1.0
 * 利用动态规划算法解决背包问题
 *
 * 如题：当前有一个背包容量4格，以下是3个物品价值与占位，如何搭配可在有限的空间得到最大的价值
 *   名称 | 重量 |  价值 |
 *  商品1 |   1 | 1500 |
 *  商品2 |   4 | 3000 |
 *  商品3 |   3 | 2000 |
 *
 * 思路：将其构建成二维数组行为物品价值，列为重量
 * 穷举所有重量下商品的组合，每行表示以当以当前价值为底，在不超出容量情况下组合出最高价值
 * 得出规律
 *    如：在3000价值下需要重量(4)，重量1列是无法放入，需取上1行的价值
 *    如：在2000价值下，重量4列是可以通过组合的，2000(重量3)+[4-3(在2000价值下重量1的组合)]的物品=4
 *
 *       重量0  重量1  重量2  重量3  重量4
 *         0     0     0     0      0
 * 1500    0  1500  1500  1500   1500
 * 3000    0  1500  1500  1500   3000
 * 2000    0  1500  1500  2000   3500
 *
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        //物品重量
        int[] w = {1,4,3};
        //物品的价值
        int[] val = {1500,3000,2000};
        //背包容量
        int m = 4;
        //物品数量
        int n = val.length;


        //创建二维记录总额
        int[][] v = new int[n+1][m+1];
        //记录存放商品的组合
        int[][] path = new int[n+1][m+1];
        //初始化第一行第一列
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;
        }
        //初始化第一列第一行
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;
        }
        //动态规划处理
        for (int i = 1; i < v.length; i++) { //第一行默认0不用处理
            for (int j = 1; j < v[i].length; j++) { //不处理第一列
                //公式
                if (w[i-1] > j){
                    v[i][j] = v[i-1][j];
                }else {
                    v[i][j] = Math.max(v[i-1][j],val[i-1]+v[i-1][j-w[i-1]]);
                    if (v[i-1][j] < val[i-1]+v[i-1][j-w[i-1]]){
                        v[i][j] = val[i-1]+v[i-1][j-w[i-1]];
                        //记录最优情况
                        path[i][j] = 1;
                    }else {
                        v[i][j] = v[i-1][j];
                    }
                }
            }
        }
        //遍历打印二维数组内容
        System.out.println("=========打印动态规划表=========");
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.printf("%4d\t",v[i][j]);
            }
            System.out.println();
        }
        System.out.println("=========最佳组合表=========");
        //从后往前遍历
        int i = path.length - 1;
        int j = path[0].length - 1;
        //从末尾获取最优方案
        while (i > 0 && j > 0){
            if (path[i][j] == 1){
                System.out.printf("第%d个商品放入到背包\n",i);
                //减去当前重量剩余
                j -= w[i-1];
            }
            i--;
        }

    }
}
