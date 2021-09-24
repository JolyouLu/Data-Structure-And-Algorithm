package top.jolyoulu.数据结构.图.邻接矩阵;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/24 14:54
 * @Version 1.0
 * 邻接矩阵，即将图转为二维数组方式保存
 *                             A B C D E
 *      [   B  ]             -------------
 *     /   | | \           A | 0 1 1 0 0 |
 *    /   |  |  \          B | 1 0 1 1 1 |
 * [C]   |   \   [E]  ==>  C | 1 1 0 0 0 |
 *  \   |    [D]           D | 0 1 0 0 0 |
 *   \  |                  E | 0 1 0 0 0 |
 *   [ A ]                   -------------
 *
 * 横坐标与纵坐标分别表示顶点 0表示不相通 1表示相通
 * [A]与[B]相通 => 那么标记[0][1]=1
 * [A]与[C]相通 => 那么标记[0][2]=1
 * 以此类推。。。
 */
public class GraphDemo{
    public static void main(String[] args) {
        //定义节点
        String[] vertexs = {"A","B","C","D","E"};
        //创建图对象
        Graph graph = new Graph(vertexs.length);
        //循环添加节点到图中
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }

        //添加边
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);
        System.out.println("================打印邻接矩阵================");
        graph.print();
        System.out.println("================深度遍历================");
        graph.dfs();
    }
}
class Graph {

    private ArrayList<String> vertexList; //存储顶点集合
    private int[][] edges; // 存储图对应的邻接矩阵
    private int numOfEdges; //表示边的数目
    //定义boolean[]，记录某个节点是否被范围
    private boolean[] isVisited;

    //构造器
    public Graph(int n) {
        //初始化矩阵和vertexList
        this.edges = new int[n][n];
        this.vertexList = new ArrayList<>();
        this.numOfEdges = 0;
        this.isVisited = new boolean[n];
    }

    //新增顶点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 传入2个顶点，设置是否2顶点是否向连
     * @param v1 第1个顶点
     * @param v2 第2个顶点
     * @param weight 0表示不相连(默认)，1表示相连
     */
    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    //返回节点的个数
    public int getNumOfVertex(){
        return vertexList.size();
    }

    //得到边的数目
    public int getNumOfEdges(){
        return numOfEdges;
    }

    //返回下标对应的顶点值
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    //返回v1与v2的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    //打印图
    public void print(){
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }

    //遍历所有节点，都走一次dfs
    public void dfs(){
        for (int i = 0; i < getNumOfVertex(); i++) {
            //如果该节点未被访问过
            if (!isVisited[i]){
                dfs(isVisited,i);
                System.out.println();
            }
        }
    }

    //深度优先遍历算法
    public void dfs(boolean[] isVisited,int i){
        //访问该节点
        System.out.print(getValueByIndex(i) + "->");
        //将该节点设置已访问
        isVisited[i] = true;
        //查找当前节点的第一个邻接节点
        int w = getFirstNeighbor(i);
        //如果存在
        while (w != -1){
            //如果邻接节点未被访问，将当前邻接点作为主节点寻找下一个邻接点
            if (!isVisited[w]){
                dfs(isVisited,w);
            }
            //如果邻接节点已被访问过，去查找邻接节点的下一个节点
            w = getNextNeighbor(i,w);
        }
    }
    
    //得到第一个邻接节点的下标
    public int getFirstNeighbor(int index){
        //遍历所有节点
        for (int i = 0; i < vertexList.size(); i++) {
            //查看当前节点与其它节点是否相邻，如果相邻返回
            if (edges[index][i] > 0){
                return i;
            }
        }
        return -1;
    }

    //根据前一个邻接节点下标获取下一个邻接节点
    public int getNextNeighbor(int v1,int v2){
        //遍历所有节点
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            //查看当前节点与其它节点是否相邻，如果相邻返回
            if (edges[v1][i] > 0){
                return i;
            }
        }
        return -1;
    }

}
