package top.jolyoulu.常用10种算法.弗洛伊德算法;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/8 10:55
 * @Version 1.0
 * 弗洛伊德算法求最短路径，时间复杂的(n^3)
 */
public class FloydAlgorithm {
    public static void main(String[] args) {
        //创建顶点
        char[] vertex = {'A','B','C','D','E','F','G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535; //表示不可连接
        //创建邻接矩阵
        matrix[0] = new int[]{0,5,7,N,N,N,2};
        matrix[1] = new int[]{5,0,N,9,N,N,3};
        matrix[2] = new int[]{7,N,0,N,8,N,N};
        matrix[3] = new int[]{N,9,N,0,N,4,N};
        matrix[4] = new int[]{N,N,8,N,0,5,4};
        matrix[5] = new int[]{N,N,N,4,5,0,6};
        matrix[6] = new int[]{2,3,N,N,4,6,0};
        //创建图对象
        Graph graph = new Graph(vertex.length, matrix, vertex);
        graph.show();
        graph.floyd();
        System.out.println("===================结果===================");
        graph.show();
    }
}
class Graph{
    private char[] vertex; //存放顶点的数组
    private int[][] dis; //保存，各顶点出发到其它顶点的距离，最后的结果，也是保留在该数组
    private int[][] pre; //保存到达目标顶点的前驱顶点

    /**
     *
     * @param length 大小
     * @param matrix 邻接矩阵
     * @param vertex 顶点数组
     */
    public Graph(int length,int[][] matrix,char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        //对pre数组初始化
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i],i);
        }
    }

    //显示pre数组与dis数组
    public void show(){
        for (int i = 0; i < dis.length; i++) {
            //输出pre数组一行数据
            for (int j = 0; j < dis[0].length; j++) {
                System.out.printf("%6s",vertex[pre[i][j]]);
            }
            System.out.println();
        }
        System.out.println("");
        for (int i = 0; i < dis.length; i++) {
            //输出dis数组一行数据
            for (int j = 0; j < dis[0].length; j++) {
                System.out.printf("%6s",dis[i][j]);
            }
            System.out.println();
        }
    }

    public void floyd(){
        int len = 0; //变量保存距离
        //对中间顶点遍历，k就是中间顶点下标
        for (int k = 0; k < dis.length; k++) {
            //从i顶点开始出发[A,B,C,D,E,F,G]
            for (int i = 0; i < dis.length; i++) {
                for (int j = 0; j < dis.length; j++) {
                    len = dis[i][k] + dis[k][j]; //计算从i顶点出发，经过k中间顶点，到达j顶点的距离
                    if (len < dis[i][j]){ //如果len小于dis[i][j]
                        dis[i][j] = len; //更新距离
                        pre[i][j] = pre[k][j]; //更新前驱顶点
                    }
                }
            }
        }
    }
}
