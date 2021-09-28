package top.jolyoulu.常用10种算法.贪心算法;

import java.util.*;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/28 16:06
 * @Version 1.0
 * 贪心算法解决最优单台
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //构建测试数据
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        HashSet<String> k1 = new HashSet<>();
        k1.add("北京");
        k1.add("上海");
        k1.add("天津");
        HashSet<String> k2 = new HashSet<>();
        k2.add("广州");
        k2.add("北京");
        k2.add("深圳");
        HashSet<String> k3 = new HashSet<>();
        k3.add("成都");
        k3.add("上海");
        k3.add("杭州");
        HashSet<String> k4 = new HashSet<>();
        k4.add("上海");
        k4.add("天津");
        HashSet<String> k5 = new HashSet<>();
        k5.add("北京");
        k5.add("大连");
        broadcasts.put("k1", k1);
        broadcasts.put("k2", k2);
        broadcasts.put("k3", k3);
        broadcasts.put("k4", k4);
        broadcasts.put("k5", k5);
        //使用贪心算法得出覆盖所有地区的动态最优方案
        //取出所有补充地区保存到list中
        HashSet<String> set = new HashSet<>();
        for (Map.Entry<String, HashSet<String>> entry : broadcasts.entrySet()) {
            HashSet<String> value = entry.getValue();
            set.addAll(value);
        }
        ArrayList<String> allAres = Collections.list(Collections.enumeration(set));
        //最终结果
        List<String> selects = new ArrayList<>();
        //保存在遍历中还为覆盖的地区
        HashSet<String> tempSet = new HashSet<>();
        //保存一次遍历中，能够覆盖最大值
        String maxKey = null;
        while (allAres.size() != 0){
            //将最大key设置null
            maxKey = null;
            //遍历broadcasts，取出key
            for (String key:broadcasts.keySet()){
                //每次循环都需要清空tempSet
                tempSet.clear();
                //获取每个单台覆盖的区域
                HashSet<String> areas = broadcasts.get(key);
                //加入到临时存储temp中
                tempSet.addAll(areas);
                //取交集
                tempSet.retainAll(allAres);
                if (tempSet.size() > 0 && //如果当前比较当前maxkey获取的集合地区，如果比他多那么就需要修改maxkey
                        (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())){
                    maxKey = key;
                }
            }
            //将maxkey加入
            if (maxKey != null){
                selects.add(maxKey);
                //清理maxkey集合中的所有区域
                allAres.removeAll(broadcasts.get(maxKey));
            }
        }
        System.out.println("最优电台覆盖方案："+selects);
    }
}
