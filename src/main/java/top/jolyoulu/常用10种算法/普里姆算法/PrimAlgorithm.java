package top.jolyoulu.常用10种算法.普里姆算法;

import lombok.Data;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/29 10:31
 * @Version 1.0
 * 普里姆算法
 *
 * 如：当前存在ABCDEF，6个村庄，这6个存庄相隔的权值(距离)已标记出来，
 * 现在需要修建一条公路将所有村庄连接起来，任何规划是最短路线
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
 * 从任意1点开始如A开始
 * 1)、找出与A点相连的所有点已经它们之间的权值 => A-C(7),A-B(5),A-G(2)
 * 2)、将最少权值路线向连的点保存下来 => 当前已连接点：{A,G} 当前最短路线：[A-G] 权值：2
 * 3)、找出A,G点相连的所有点已经它们之间的权值 => A-C(7),A-B(5),G-B(3),G-E(4),G-F(6)
 * 4)、将最少权值路线向连的点保存下来 => 当前已连接点：{A,G,B} 当前最短路线：[A-G,G-B] 权值：2+3
 * 5)、找出A,G,B点相连的所有点已经它们之间的权值 => A-C(7),B-D(9),G-E(4),G-F(6)
 * 6)、将最少权值路线向连的点保存下来 => 当前已连接点：{A,G,B,E} 当前最短路线：[A-G,G-B,G-E] 权值：2+3+4
 * 7)、以此类推直到所有点找到即可
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        //创建点
        char[] data = {'A','B','C','D','E','F','G'};
        int verxs = data.length;
        //邻接矩阵关系
        //max表示无法相连
        Integer max = Integer.MAX_VALUE;
        int[][] weight = new int[][]{
            //A   B   C   D   E   F   G
            {max,  5,  7,max,max,max,  2}, //A
            {  5,max,max,  9,max,max,  3}, //B
            {  7,max,max,max,  8,max,max}, //C
            {max,  9,max,max,max,  5,max}, //D
            {max,max,  8,max,max,  5,  4}, //E
            {max,max,max,  4,  5,max,  6}, //F
            {  2,  3,max,max,  4,  6,max}, //G
        };
        //创建mGraph对象
        MGraph graph = new MGraph(verxs);
        MinTree minTree = new MinTree();
        minTree.createGraph(graph,verxs,data,weight);
        minTree.showGraph(graph);
        minTree.prim(graph,0);

    }
}
//创建最小生成树
class MinTree{

    //将给定的顶点，权值，构建到graph对象中
    public void createGraph(MGraph graph,int verxs,char[] data,int[][] weight){
        for (int i = 0; i < verxs; i++) {
            graph.getData()[i] = data[i];
            for (int j = 0; j < verxs; j++) {
                graph.getWeight()[i][j] = weight[i][j];
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph(MGraph graph){
        for (int[] link : graph.getWeight()) {
            System.out.println(Arrays.toString(link));
        }
    }
    /**
     * 使用普里姆算法得到最小生成树
     * @param graph 图
     * @param v 起点
     */
    public void prim(MGraph graph,int v){
        //记录那些点已被访问过，默认0表示所有顶点都未被访问
        int[] visited = new int[graph.getVerxs()];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = 0;
        }
        //标记当前节点已经访问
        visited[v] = 1;
        //使用h1与h2记录2顶点下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = Integer.MAX_VALUE; //初始化最大，后面发现有权值比他小会被替换
        //普里姆算法结束后会，产生(顶点数-1)条边
        for (int k = 0; k <  graph.getVerxs() - 1; k++) {
            //获取每一个节点
            for (int i = 0; i < graph.getVerxs(); i++) {
                //将这个节点与其它所有节点进行比较
                for (int j = 0; j < graph.getVerxs(); j++) {
                    //如果当前i节点是已访问节点
                    //并且他所访问的节点j是未被访问过的
                    //并且i点与j点是否相连，以及权值小于当前记录值
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight){
                        //记录下当前2节点的权值，用于下次循环尝试找到更小的
                        minWeight = graph.weight[i][j];
                        //记录下最短路径开始点
                        h1 = i;
                        //记录下最短路径结束点
                        h2 = j;
                    }
                }
            }
            //找到最小的边
            System.out.println("边<"+graph.getData()[h1]+","+graph.getData()[h2]+">权值："+minWeight);
            visited[h2] = 1;
            //重置最小值
            minWeight = Integer.MAX_VALUE;
        }
    }
}
@Data
class MGraph{
    int verxs; //表示节点图的节点个数
    char[] data;//存放节点数据
    int[][] weight; //存放边的连接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        this.data = new char[verxs];
        this.weight = new int[verxs][verxs];
    }
}