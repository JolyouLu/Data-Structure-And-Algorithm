package top.jolyoulu.常用10种算法.马踏棋盘算法;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @Author: JolyouLu
 * @Date: 2021/10/8 13:46
 * @Version 1.0
 * 马踏棋盘算法，在一个国际象棋中，有一只马(m)，每次移动一步并且只能以日字方式行走，如何走才能走完整盘棋
 * # # # # # # # #
 * # # # # # # # #
 * # # # 6 # 7 # #
 * # # 5 # # # 0 #
 * # # # # m # # #
 * # # 4 # # # 1 #
 * # # # 3 # 2 # #
 * # # # # # # # #
 */
public class HorseChessBoard {

    private static int x; //棋盘列数
    private static int y; //棋盘行数
    //创建一个数组标记棋盘位置是否被访问
    private static boolean visited[];
    //使用一个属性标记，棋盘所有的位置是否已被访问过了
    private static boolean finished; //如果true表示成功

    public static void main(String[] args) {
        x = 8;
        y = 8;
        int row = 1; //马的起始行
        int column = 1; //马的起始列
        //创建棋盘
        int[][] chessboard = new int[x][y];
        visited = new boolean[x * y]; //标记所有位置都为false
        //测试耗时
        long start = System.currentTimeMillis();
        traversalChessBoard(chessboard, row - 1, column - 1, 1);
        long end = System.currentTimeMillis();
        System.out.println("执行耗时：" + (end - start));
        //输出棋盘情况
        for (int[] rows : chessboard) {
            for (int step : rows) {
                System.out.print(step+"\t");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归完成骑士周游问题
     *
     * @param chessboard 棋盘
     * @param row        马当前在第几行
     * @param column     马当前在第几列
     * @param step       马当前走到第几步，初始位置1
     */
    public static void traversalChessBoard(int[][] chessboard, int row, int column, int step) {
        chessboard[row][column] = step;
        visited[row * x + column] = true; //标记该位置已经访问
        //获取当前位置可以走的下一个位置的集合
        ArrayList<Point> ps = next(new Point(column, row));
        //对ps排序，对ps所有元素的下一步可移动的数目，进行非递减排序
        sort(ps);
        //遍历list查看那些位置是可以走
        while (!ps.isEmpty()) {
            Point p = ps.remove(0); //获取下一个可走的点
            //判断该点是否已经访问过
            if (!visited[p.y * x + p.x]) {
                traversalChessBoard(chessboard, p.y, p.x, step + 1);
            }
        }
        //step < x * y 可能是2种情况
        //1、棋盘未走完
        //2、棋盘走完了走到了死路，需要回溯
        if (step < x * y && !finished) {
            chessboard[row][column] = 0;
            visited[row * x + column] = false;
        } else {
            finished = true;
        }
    }

    //根据当前位置，计算出马儿还在走那些位置，最多8个位置
    public static ArrayList<Point> next(Point curPoint) {
        //创建一个ArrayList
        ArrayList<Point> ps = new ArrayList<>();
        //创建一个Point
        Point p1 = new Point();
        //判断5位置是否可走
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断6位置是否可走
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //判断7位置是否可走
        if ((p1.x = curPoint.x + 1) < x && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //判断0位置是否可走
        if ((p1.x = curPoint.x + 2) < x && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断1位置是否可走
        if ((p1.x = curPoint.x + 2) < x && (p1.y = curPoint.y + 1) < y) {
            ps.add(new Point(p1));
        }
        //判断2位置是否可走
        if ((p1.x = curPoint.x + 1) < x && (p1.y = curPoint.y + 2) < y) {
            ps.add(new Point(p1));
        }
        //判断3位置是否可走
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < y) {
            ps.add(new Point(p1));
        }
        //判断4位置是否可走
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < y) {
            ps.add(new Point(p1));
        }
        return ps;
    }

    //根据当前步骤，的所有下一步选择位置，进行非递减排序
    public static void sort(ArrayList<Point> ps){
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                //获取o1点的下一步的所有位置的数量
                int count1 = next(o1).size();
                //获取o2点的下一步的所有位置的数量
                int count2 = next(o2).size();
                if (count1 < count2){
                    return -1;
                }else if(count1 == count2){
                    return 0;
                }else {
                    return 1;
                }
            }
        });
    }
}
