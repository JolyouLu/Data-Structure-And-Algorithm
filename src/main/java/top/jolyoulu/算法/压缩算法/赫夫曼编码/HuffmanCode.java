package top.jolyoulu.算法.压缩算法.赫夫曼编码;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
import lombok.Data;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/22 17:29
 * @Version 1.0
 * 赫夫曼编码(无损压缩算法)
 * 赫夫曼编码常用于通讯领域的字节流压缩传输，压缩率可达到20%-90%
 * <p>
 * 原始字符串 => i like like like java do you like a java
 * 统计字符出现频率 => d:1,y:1,u:1,j:2,v:2,o:2,l:4,k:4,e:4,i:5,a:5,空格:9
 * ↓ 根据以上字符出现频率(频率作为权值)构建赫夫曼树 ↓
 * 注：每次构建结构不一定与如下一致，但只要遵循赫夫曼wpl不管怎么构建最终的wpl都是一致的
 * <p>
 * -----[40]----
 * /             \  1
 * 0  /              ---[23]--
 * /          0  /         \  1
 * [17]           [10]        [13]
 * 0  /  \  1      0  / \  1    0  / \  1
 * [8] [空格:9]    [5] [i:5]   [a:5] [8]
 * 0  /  \  1      0 /  \  1        0  /  \  1
 * [4] [l:4]     [o:2] [3]        [k:4] [e:4]
 * 0  /  \  1          0  /  \  1
 * [j:2] [v:2]         [u:1] [2]
 * 0  / \  1
 * [d:1] [y:1]
 * <p>
 * 指定编码规则，在每一个分叉左为1右边为0，得出如下二进制规则编码
 * 空格:01      i:101     a:110
 * l:001     o:1000    k:1100   e:1111
 * j:0000    v:0001    u:10010
 * d:100110  y:100111
 * 可以发现，得出的编码都是前缀编码，不会与其它编码冲突
 */
public class HuffmanCode {

    public static void main(String[] args) {
        System.out.println("=======================原文转字节数组=======================");
        byte[] contentBytes = "i like like like java do you like a java".getBytes();
        System.out.println(Arrays.toString(contentBytes));
        System.out.println("压缩前长度 = " + contentBytes.length);
        System.out.println("=======================权值计算=======================");
        List<Node> nodes = getNodes(contentBytes);
        System.out.println(nodes);
        System.out.println("=======================赫夫曼树构建=======================");
        Node node = creatHuffmanTree(nodes);
        preOrder(node);
        System.out.println("=======================获取哈夫曼编码表=======================");
        Map<Byte, String> huffmanCodes = getCodes(node);
        System.out.println(huffmanCodes);
        System.out.println("=======================使用赫夫曼压缩数据=======================");
        byte[] zip = zip(contentBytes, huffmanCodes);
        System.out.println(Arrays.toString(zip));
        System.out.println("压缩后长度 = " + zip.length);
        System.out.println("=======================解压赫夫曼数据=======================");
        byte[] decode = decode(huffmanCodes, zip);
        System.out.println(Arrays.toString(decode));
        System.out.println(new String(decode));

        System.out.println("=======================提升练习，文件压缩=======================");
        zipFile("src/main/resources/1.jpg","src/main/resources/1.HFZip");
        unZipFile("src/main/resources/1.HFZip","src/main/resources/unzip.jpg");

    }

    //数据解压
    //1.将内容转2进制字符串
    //2.对照赫夫曼编码表，转成的字符串
    private static byte[] decode(Map<Byte, String> huffmanCodes,byte[] huffmanZip){
        //得到赫夫曼byte对应的二进制字符串
        StringBuffer bitStr = new StringBuffer();
        for (int i = 0; i < huffmanZip.length; i++) {
            //最后一个字节不用补高位
            boolean flag = (i == huffmanZip.length -1);
            bitStr.append(byteToBitStr(!flag,huffmanZip[i]));
        }
        //把字符串按照赫夫曼反转成<String,Byte>
        Map<String,Byte> map = new HashMap<>();
        for (Map.Entry<Byte,String> entry: huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }
        List<Byte> list = new ArrayList<>();
        //遍历每一个二进制字符串与反转后的赫夫曼编码比较
        for (int i = 0; i < bitStr.length();) {
            //定义一个指针
            int index = 1;
            //标记是否匹配成功
            boolean flag = true;
            Byte b = null;

            //循环匹配
            while (flag) {
                //截取i到指针位置字符串
                String key = bitStr.substring(i, i + index);
                b = map.get(key);
                if (b == null) { //未匹配到，指针后移动一位继续匹配
                    index++;
                } else { //匹配到结束循环
                    flag = false;
                }
            }
            list.add(b);
            //将i一定到指针位置进行下一轮匹配
            i += index;
        }
        //将list中数据加入到byte数组返回
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    /**
     * 将byte转成二进制字符串
     * @param flag 是否需要补高位
     * @param b 转换的byte
     * @return 二进制字符串（补码）
     */
    private static String byteToBitStr(boolean flag,byte b){
        int temp = b;
        if (flag){
            //按位与上 1 0000 0000 解决正数
            temp |= 256;
            //得到二进制补码
            String str = Integer.toBinaryString(temp);
            //截取后8位
            return str.substring(str.length() - 8);
        }else {
            return Integer.toBinaryString(temp);
        }
    }

    //封装压缩过程
    public static byte[] huffmanZip(byte[] contentBytes){
//        System.out.println("=======================原文转字节数组=======================");
//        System.out.println(Arrays.toString(contentBytes));
//        System.out.println("压缩前长度 = " + contentBytes.length);
//        System.out.println("=======================权值计算=======================");
        List<Node> nodes = getNodes(contentBytes);
//        System.out.println(nodes);
//        System.out.println("=======================赫夫曼树构建=======================");
        Node node = creatHuffmanTree(nodes);
//        preOrder(node);
//        System.out.println("=======================获取哈夫曼编码表=======================");
        Map<Byte, String> huffmanCodes = getCodes(node);
//        System.out.println(huffmanCodes);
//        System.out.println("=======================使用赫夫曼压缩数据=======================");
        byte[] zip = zip(contentBytes, huffmanCodes);
//        System.out.println(Arrays.toString(zip));
//        System.out.println("压缩后长度 = " + zip.length);
        return zip;
    }

    /**
     * 根据赫夫曼编码表，压缩数据
     *
     * @param bytes        原始字符串byte数组
     * @param huffmanCodes 赫夫曼编码表
     * @return 压缩后byte数组
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //保存压缩后的二进制字符串内容
        StringBuffer bitStr = new StringBuffer();
        //遍历bytes将，将所有byte转赫夫曼
        for (byte aByte : bytes) {
            bitStr.append(huffmanCodes.get(aByte));
        }
        //将二进制字符串转byte数组，将二进制字符字符串按每八位拆分转成byte保存到byte[]中
        //创建一个合适的byte[]，保存binaryStr内容
        int len;
        if (bitStr.length() % 8 == 0) { //如果是8的整数倍，着直接除8得出需创建的byte[]长度
            len = bitStr.length() / 8;
        } else { //如果不是8的整数倍，除8后+1得出需创建的byte[]长度
            len = bitStr.length() / 8 + 1;
        }
        byte[] huffmanBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < bitStr.length(); i += 8) { //每次遍历，截取8位
            String strByte;
            if (i + 8 > bitStr.length()) { //如果剩余不够8位，取剩余的所有
                strByte = bitStr.substring(i);
            } else {
                strByte = bitStr.substring(i, i + 8);
            }
            //将strByte转成byte加入到
            huffmanBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        return huffmanBytes;
    }

    //根据list构建相应的赫夫曼树
    private static Node creatHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            //排序
            Collections.sort(nodes);
            //获取最小节点与次节点
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //构建一个新树组合
            Node parent = new Node(null, leftNode.getWeight() + rightNode.getWeight());
            parent.setLeft(leftNode);
            parent.setRight(rightNode);
            //移除已合成节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新节点加入到list中
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    //用于存储哈夫曼编码表
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    //拼接路径时所需要用到的
    static StringBuffer stringBuffer = new StringBuffer();

    //重载getCodes使用更加方便
    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        //处理左子树
        getCodes(root.getLeft(), "0", stringBuffer);
        //处理右子树
        getCodes(root.getRight(), "1", stringBuffer);
        return huffmanCodes;
    }

    /**
     * 从传入的节点开始递归哈夫曼树，将所有叶子节点编码得到，存入到集合中
     *
     * @param node   存入节点
     * @param code   路径 左子节点0，右边子节点1
     * @param buffer 递归拼接的对象
     * @return
     */
    private static void getCodes(Node node, String code, StringBuffer buffer) {
        //根据原来的缓冲区构建一个新的缓冲区
        StringBuffer stringBuffer = new StringBuffer(buffer);
        //将code加入到stringBuffer中
        stringBuffer.append(code);
        //表示当前树未处理完
        if (node != null) {
            //当前是非叶子节点，继续向左右递归
            if (node.getData() == null) {
                getCodes(node.getLeft(), "0", stringBuffer);
                getCodes(node.getRight(), "1", stringBuffer);
                //达到了叶子节点
            } else {
                //将记录到stringBuffer路径保存到map中
                huffmanCodes.put(node.getData(), stringBuffer.toString());
            }
        }
    }

    //接收一个字节数组，返回各字节出现频率list
    private static List<Node> getNodes(byte[] bytes) {
        //利用map统计每个byte出现的次数
        HashMap<Byte, Integer> map = new HashMap<>();
        for (byte aByte : bytes) {
            if (map.containsKey(aByte)) {
                Integer integer = map.get(aByte);
                map.put(aByte, ++integer);
            } else {
                map.put(aByte, 1);
            }
        }
        //获取map中的元素放入到list中
        List<Node> nodes = new ArrayList<>();
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    //前序遍历
    private static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        }
    }

    //提升练习文件压缩
    public static void zipFile(String srcFile,String dstFile){
        FileInputStream in = null;
        FileOutputStream out = null;
        ObjectOutputStream oos = null;
        try {
            in = new FileInputStream(srcFile);
            byte[] b = new byte[in.available()];
            in.read(b);
            //压缩
            byte[] huffmanZip = huffmanZip(b);
            //构建输出流
            out = new FileOutputStream(dstFile);
            oos = new ObjectOutputStream(out);
            //将数据与赫夫曼编码表写入到对象流中
            oos.writeObject(huffmanZip);
            oos.writeObject(huffmanCodes);
            oos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //解压压缩文件
    public static void unZipFile(String srcFile,String dstFile){
        FileInputStream in = null;
        ObjectInputStream ois = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            ois = new ObjectInputStream(in);
            byte[] huffmanZip = (byte[]) ois.readObject();
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            byte[] bytes = decode(huffmanCodes, huffmanZip);
            out = new FileOutputStream(dstFile);
            out.write(bytes);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

//节点
@Data
class Node implements Comparable<Node> {
    private Byte data; //存放数据本身
    private Integer weight; //权值，字符的出现次数
    private Node left;
    private Node right;

    public Node(Byte data, Integer weight) {
        this.data = data;
        this.weight = weight;
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        //从小到大
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Node{");
        sb.append("data=").append(data);
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}
