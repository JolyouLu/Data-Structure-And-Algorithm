package top.jolyoulu.递归算法.迷宫;

import lombok.Data;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/14 11:17
 * @Version 1.0
 * 递归回溯算法解决迷宫路线问题
 */
public class MazeDemo {
    public static void main(String[] args) {
        System.out.println("==============构建一个地图==============");
        Maze maze = new Maze();
        setWay(maze.getMap(),1,1);
        System.out.println("==============地图路线==============");
        maze.println();
    }

    /**
     * 使用递归回溯算法找到路径
     * map：表示二维数组地图，地图中0表示可移动位置，1表示不可移动位置
     * x,y：表示二维数组的起始坐标，坐标初始值从左上角为(0,0)
     * 结束位置为：二维数组右下角的坐标(例子中的[6][5]位置)
     * 路线标记：在二维数组中，2标记路径为可到重点路线，3标记路径表示已走过但是无法到达终点
     * 路线策略：这里是路线策略，先向下走=>向右边走=>向上走=>向左走(最后不通回溯找另外路线)
     * @param map 地图
     * @param x 其实位置的横坐标
     * @param y 起始位置的纵坐标
     * @return
     */
    public static boolean setWay(int[][] map,int x,int y){
        //已经找到终点
        if (map[6][5] == 2){
            return true;
        }else {
            //如果当前点未走过
            if (map[x][y] == 0){
                //按照策略 下=>右=>上=>左
                //先假定该点是能走通的
                map[x][y] = 2;
                //递归向下走，直到返回false
                if (setWay(map,x+1,y)){
                    return true;
                //递归向右走，直到返回false
                }else if (setWay(map,x,y+1)){
                    return true;
                //递归向上走，直到返回false
                }else if (setWay(map,x-1,y)){
                    return true;
                //递归向左走，直到返回false
                }else if (setWay(map,x,y-1)){
                    return true;
                //前面都是false，走到死路了
                }else {
                    //标记3说明该点无法走向终点
                    map[x][y] = 3;
                    return false;
                }
            //可能是1、2、3
            }else {
                return false;
            }
        }
    }

    /**
     * 调整原setWay策略
     * 路线策略：这里是路线策略，先向上走=>向右边走=>向下走=>向左走(最后不通回溯找另外路线)
     * @param map 地图
     * @param x 其实位置的横坐标
     * @param y 起始位置的纵坐标
     * @return
     */
    public static boolean setWay2(int[][] map,int x,int y){
        //已经找到终点
        if (map[6][5] == 2){
            return true;
        }else {
            //如果当前点未走过
            if (map[x][y] == 0){
                //按照策略 下=>右=>上=>左
                //先假定该点是能走通的
                map[x][y] = 2;
                //递归向上走，直到返回false
                if (setWay2(map,x-1,y)){
                    return true;
                //递归向右走，直到返回false
                }else if (setWay2(map,x,y+1)){
                    return true;
                //递归向下走，直到返回false
                }else if (setWay2(map,x+1,y)){
                    return true;
                //递归向左走，直到返回false
                }else if (setWay2(map,x,y-1)){
                    return true;
                //前面都是false，走到死路了
                }else {
                    //标记3说明该点无法走向终点
                    map[x][y] = 3;
                    return false;
                }
            //可能是1、2、3
            }else {
                return false;
            }
        }
    }

}
@Data
class Maze{
    private int[][] map = new int[8][7];

    public Maze() {
        //上下设置1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //左右设置1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        System.out.println("地图情况，0可移动，1墙(不可移动)");
        println();
    }

    //打印
    public void println(){
        for (int[] row : map) {
            for (int data : row) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }
}
