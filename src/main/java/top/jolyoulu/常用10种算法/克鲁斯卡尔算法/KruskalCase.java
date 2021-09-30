package top.jolyoulu.常用10种算法.克鲁斯卡尔算法;

import lombok.Data;

import java.util.Arrays;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/30 13:30
 * @Version 1.0
 * 克鲁斯卡尔算法，计算最小生成树
 * 与普里姆算法相比，克鲁斯卡尔算法特点是构建成是路线不会有回路
 *         10
 *      B------ C
 * 12 /  \7 6/ | \3
 *   / 16 \ /  |  \
 *  A ---  F   |5   D
 *  \   9/ \2  |  /4
 * 14\  /   \  | /
 *    G------- E
 *        8
 *
 * 1)、对所有边进行从小到大排序处理
 * 2)、遍历所有排序后的边，若2边顶点终点不等，那么就记录下这条边，否则跳过
 */
public class KruskalCase {

    private int edgeNum; //边的数量
    private char[] vertexs; //顶点数组
    private int[][] matrix; //邻接矩阵
    private static final int INF = Integer.MAX_VALUE; //该值表示2点未相通

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                //A   B   C   D   E   F   G
                {0, 12, INF, INF, INF, 16, 14}, //A
                {12, 0, 10, INF, INF, 7, INF}, //B
                {INF, 10, 0, 3, 5, 6, INF}, //C
                {INF, INF, 3, 0, 4, INF, INF}, //D
                {INF, INF, 5, INF, 0, 2, 8}, //E
                {16, 7, 6, INF, 2, 0, 9}, //F
                {14, INF, INF, INF, 8, 9, 0}, //G
        };
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        kruskalCase.print();
        kruskalCase.kruskal();
    }

    //构造器
    public KruskalCase(char[] vertexs, int[][] matrix) {
        int vlen = vertexs.length;
        //拷贝生成顶点
        this.vertexs = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }
        //拷贝生成图
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        //统计有效边数量
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal(){
        int index = 0; //最后结果数组的索引
        int[] ends = new int[edgeNum]; //保存最小生成数数，中每一个顶点在树中的重点
        //创建结果数组，保存最后的最小生成树
        EData[] res = new EData[edgeNum];
        //获取图纸所有边的集合，一共有12条边
        EData[] edges = getEdges();
        //按边的权值从小到大排序
        sortEdges(edges);
        //遍历edges，将边添加到最小生成树中，判断准备加入的边是否形成回来
        //如没有就加入，否则跳过当前边
        for (int i = 0; i < edgeNum; i++) {
            //获取到第i条边的第1个点
            int p1 = getPosition(edges[i].getStar());
            //获取到第i条边的第2个点
            int p2 = getPosition(edges[i].getEnd());
            //获取p1顶点在已有最小生成树的终点
            int m = getEnd(ends,p1);
            //获取p2顶点在已有最小生成树的终点
            int n = getEnd(ends,p2);
            //是否构成回路(2个顶点是否指向同一个终点)
            if (m != n){ //不相同，未构成回路
                ends[m] = n; //将当前边的终点
                res[index++] = edges[i]; //有一条边加入到res数组中
            }
        }
        //打印最小生成树
        System.out.println("==============最小生成树==============");
        for (int i = 0; i < index; i++) {
            System.out.println(res[i]);
        }
    }

    //打印邻接矩阵
    public void print() {
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%10d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //对边集合进行排序，冒泡排序
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].getWeight() > edges[j + 1].getWeight()) {
                    EData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    //传入一个顶点，返回对呀的下标
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    //生成图中所有边与权值
    //例如 EData[] ==> [['A','B',12],['B','F',7],....]
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    //获取下标i的顶点的重点
    private int getEnd(int[] ends,int i){
        while (ends[i] != 0){
            i = ends[i];
        }
        return i;
    }
}
//创建一个类EData，它的对象实例就表示一条边
@Data
class EData{
    private char star; //边的一个点
    private char end; //边的另外一个点
    private int weight; //边的权值

    //构造器
    public EData(char star, char end, int weight) {
        this.star = star;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EData{");
        sb.append("<").append(star);
        sb.append(", ").append(end);
        sb.append("> =").append(weight);
        sb.append('}');
        return sb.toString();
    }
}
