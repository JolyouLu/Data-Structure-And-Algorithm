package top.jolyoulu.常用10种算法.迪杰斯特拉算法;

import lombok.Data;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/29 10:31
 * @Version 1.0
 * 迪杰斯特拉算法
 *
 * 如：当前存在ABCDEF，6个村庄，从任意一个村庄作为起点，将任意一个村庄作为终点，得最短路径
 *
 *       5
 *    A-----B
 *  7/ \2 3/ \9
 *  /   \ /   \
 * C     G     D
 * \8  4/ \6  /4
 *  \  /   \ /
 *   E------F
 *      5
 *
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A','B','C','D','E','F','G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535; //表示不可连接
        //创建邻接矩阵
        matrix[0] = new int[]{N,5,7,N,N,N,2};
        matrix[1] = new int[]{5,N,N,9,N,N,3};
        matrix[2] = new int[]{7,N,N,N,8,N,N};
        matrix[3] = new int[]{N,9,N,N,N,4,N};
        matrix[4] = new int[]{N,N,8,N,N,5,4};
        matrix[5] = new int[]{N,N,N,4,5,N,6};
        matrix[6] = new int[]{2,3,N,N,4,6,N};
        //创建Graph对象
        Graph graph = new Graph(vertex, matrix);
        //打印矩阵
        graph.print();
        //测试迪杰斯特拉算法
        graph.dsj(6);
        graph.showDjs();
    }
}
//已访问顶点集合
class VisitedVertex{
    //记录各顶点是否访问过 1表示已访问，0表示未访问
    private int[] alreadArr;
    //每给下标对应的值未前一个顶点下标，会动态更新
    private int[] preVisited;
    //记录出发顶点到其它所有顶点的距离，如G为出发顶点，就会记录G到其它顶点的距离
    private int[] dis;

    public VisitedVertex(int len, int index) {
        this.alreadArr = new int[len];
        this.preVisited = new int[len];
        this.dis = new int[len];
        //初始化dis数组
        Arrays.fill(dis, 65535);
        this.alreadArr[index] = 1;//将出发顶点设置为已被访问过
        this.dis[index] = 0; //设置出发顶点都是0
    }

    //判断index顶点是否被访问过
    public boolean in(int index){
        //如果当前顶点下标1表示已访问
        return alreadArr[index] == 1;
    }

    //更新出发顶点到index顶点的距离
    public void updateDis(int index,int len){
        dis[index] = len;
    }

    //更新顶点的前驱为index节点
    public void updatePre(int pre,int index){
        preVisited[pre] = index;
    }

    //返回出发顶点到index顶点的距离
    public int getDis(int index){
        return dis[index];
    }

    //继续选择并返回新的访问顶点
    public int updateArr(){
        int min = 65535,index = 0;
        for (int i = 0; i < alreadArr.length; i++) {
            if (alreadArr[i] == 0 && dis[i] < min){
                min = dis[i];
                index = i;
            }
        }
        //设置该顶点被访问过
        alreadArr[index] = 1;
        return index;
    }

    //打印最终结果
    public void show(){
        System.out.println("============================");
        System.out.println(Arrays.toString(alreadArr));
        System.out.println(Arrays.toString(preVisited));
        System.out.println(Arrays.toString(dis));
    }
}
@Data
class Graph{
    private char[] vertex; //顶点数组
    private int[][] matrix; //邻接矩阵
    private VisitedVertex vv; //已访问顶点集合

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    //打印邻接矩阵
    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf("%10d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //迪杰斯特拉算法
    public void dsj(int index){
        this.vv = new VisitedVertex(vertex.length, index);
        update(index); //更新index下标顶点到周围顶点的距离和前驱顶点
        for (int i = 1; i < vertex.length; i++) {
            index = vv.updateArr();
            update(index);
        }
    }

    //更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index) {
        int len = 0;
        for (int j = 0; j < matrix[index].length; j++) {
            //出发顶点到index顶点的距离+从index顶点到j顶点的距离的和
            len = vv.getDis(index) + matrix[index][j];
            //如果j顶点没有被访问过，并且len小于出发顶点到j顶点的距离，需要更新
            if (!vv.in(j) && len < vv.getDis(j)){
                vv.updatePre(j,index); //更新j顶点的前驱为index顶点
                vv.updateDis(j,len); //更新出发顶点到j顶点的距离
            }
        }
    }

    //打印最终结果
    public void showDjs(){
        vv.show();
    }
}