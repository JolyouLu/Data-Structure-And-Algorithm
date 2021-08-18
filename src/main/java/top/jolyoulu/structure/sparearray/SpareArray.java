package top.jolyoulu.structure.sparearray;


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author: JolyouLu
 * @Date: 2021/8/17 22:47
 * @Version 1.0
 * 数据结构-稀疏数组
 */
public class SpareArray {

    public static void main(String[] args) {
        //初始化一个11*11的棋盘
        //0:没有棋子，1:黑棋子，2:蓝棋子
        int[][] chessArr = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        //打印棋盘内容
        System.out.println("打印棋盘原始二维数组内容");
        println(chessArr);
        //将二维棋盘 转 稀疏数组
        System.out.println("二维数组棋盘转稀疏数组");
        //获取二维数组长宽，以及不为零的总数
        int row = chessArr.length;
        int column = chessArr[0].length;
        int sum = 0;
        //合计非零数目
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (chessArr[i][j] != 0){
                    sum++;
                }
            }
        }
        System.out.println("行："+row+" 列："+column+" 非零数："+sum);
        //创建稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //第一行存放，棋盘长宽、非零总数
        sparseArr[0][0] = row;
        sparseArr[0][1] = column;
        sparseArr[0][2] = sum;
        //遍历棋盘，将来非零数据的位置保存到稀疏数组中
        int index = 0;//记录当前插入非零数据的下班
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (chessArr[i][j] != 0){
                    index++;
                    sparseArr[index][0] = i; //行下标
                    sparseArr[index][1] = j; //列下标
                    sparseArr[index][2] = chessArr[i][j]; //数据本身
                }
            }
        }
        //打印棋盘内容
        System.out.println("打印稀疏数组内容");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n",sparseArr[i][0],sparseArr[i][1],sparseArr[i][2]);
        }
        outputFile(sparseArr);
        //稀疏数组恢复为二维数组
        System.out.println("稀疏数组恢复为二维数组");
        //获取二维数组行列
        int[][] sparseArr2chessArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        //填充非零位置
        int len = sparseArr[0][2];
        for (int i = 1; i <= len; i++) {
            sparseArr2chessArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        System.out.println("稀疏数组恢复为二维数组后内容");
        println(sparseArr2chessArr);
    }

    private static void println(int[][] array){
        for (int[] row : array) {
            for (int data : row) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }

    private static void outputFile(int[][] array){
        File file = new File("src/main/resources/sparseArr.txt");
        try(FileOutputStream os = new FileOutputStream(file)) {
            if (!file.exists()){
                file.createNewFile();
            }
            for (int[] row : array) {
                for (int data : row) {
                    os.write((data+",").getBytes(StandardCharsets.UTF_8));
                }
                os.write(new byte[]{'\n'});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@TODO
    private static int[][] InputFile(int[][] array){
//        int[][] res = new int[][];
        File file = new File("src/main/resources/sparseArr.txt");
        try(FileInputStream is = new FileInputStream(file)) {
            int read = is.read();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
