package top.jolyoulu.线性结构.栈.逆波兰计算器;

import java.util.*;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/13 10:41
 * @Version 1.0
 * 逆波兰计算器
 * 栈、后缀表达式转换
 */
public class PolishNotationDemo {
    public static void main(String[] args) {
        String str = "10+((2+3)*4)-5";
        List<String> exp = new Expression(str).toPostfixExpr();
        System.out.println("=================中缀表达式转后缀表达式=================");
        System.out.println(str+" => "+exp);
        System.out.println("=================逆波兰计算器=================");
        Integer cal = new PolishNotation(exp).cal();
        System.out.println("计算结果："+cal);

    }
}
/**
 * 中缀表达式转后缀表达式
 * 转化规则
 * 1、初始化2个栈，运算符栈s1和存储中间结果栈s2
 * 2、从左到又扫描中缀表达式
 * 3、遇到操作数时，压入s2
 * 4、遇到运算符时，需比较与s1栈顶运算符的优先级
 *      1)、 如果s1为空，或s1栈顶为"("，直接压入s1
 *      2)、 否则如果当前运算符优先级比s1栈顶运算符的高，也将运算符压入s1
 *      3)、 否则，将s1栈顶的运算符弹出，并且压入到s2中，再次回到4-1中，重新与s1中栈顶运算符做比较
 * 5、遇到括号时
 *      1)、 如果是左括号"("，直接压入s1栈
 *      2)、 如果是右括号")"，依次弹出s1栈顶的运算符，并且压入到s2，直到遇到左括号为止，此时一对括号就丢弃了
 * 6、 重复2至5步骤，直到表达式最右边
 * 7、 将s1中剩余的运算符依次弹出并压入s2
 * 8、 将s2中所有的元素，逆序输出得到后缀表达式
 */
class Expression{
    private String nifixExpression;

    public Expression(String nifixExpression) {
        this.nifixExpression = nifixExpression;
    }

    //获取中缀表达式
    public List<String> toPostfixExpr(){
        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        String[] split = nifixExpression.split("");
        String temp = "";
        for (int i = 0; i < split.length; i++) {
            //如果不是数字直接覆盖temp
            if (!split[i].matches("[0-9]")){
                temp = split[i];
            //数字，持续追加
            }else {
                temp += split[i];
                //如果下一个还是数字结束当前循环继续下一个循环
                if ((i+1 != split.length) && split[i+1].matches("[0-9]")){
                    continue;
                }
            }
            //如果是数字直接压入s2
            if (temp.matches("\\d+")){
                s2.push(temp);
            //否则如果是操作数
            }else if (temp.matches("[\\+\\-\\*\\/]")){
                while (true){
                    //如果s1为空，或s1栈顶为"("，直接压入s1
                    if (s1.empty() || temp.matches("\\(")){
                        s1.push(temp);
                        break;
                    //否则如果当前运算符优先级比s1栈顶运算符的高，也将运算符压入s1
                    }else if (priority(temp) >= priority(s1.peek())){
                        s1.push(temp);
                        break;
                    //否则，将s1栈顶的运算符弹出，并且压入到s2中，再次回到4-1中，重新与s1中栈顶运算符做比较
                    }else {
                        s2.push(s1.pop());
                    }
                }
            //否则是括号
            }else {
                //如果是左括号"("，直接压入s1栈
                if (temp.matches("\\(")){
                    s1.push(temp);
                //如果是右括号")"，依次弹出s1栈顶的运算符，并且压入到s2，直到遇到左括号为止，此时一对括号就丢弃了
                }else {
                    while (true){
                        String pop = s1.pop();
                        if (pop.equals("(")){
                            break;
                        }
                        s2.push(pop);
                    }
                }
            }
            //初始化
            temp = "";
        }
        //将s1中剩余的运算符依次弹出并压入s2
        while (!s1.empty()){
            s2.push(s1.pop());
        }
        return Collections.list(s2.elements());
    }

    //获取运算符优先级别
    private int priority(String oper){
        if (oper.equals("*") || oper.equals("/")){
            return 1;
        }else if (oper.equals("+") || oper.equals("-")){
            return 0;
        }else {
            return -1;
        }
    }
}
/**
 * 逆波兰表达式(后缀表达式)计算过程
 * 将中缀表达式转成逆波兰表达式 (3+4)*5-6 ==> 3,4,+,5,*,6,-
 * 1、 3,4 直接入栈
 * 2、 + 取出栈顶元素(4),次栈顶元素(3),使用+计算(3+4)得出7,入栈
 * 3、 5 直接入栈
 * 4、 * 取出栈顶元素(5),次栈顶元素(7),使用*计算(7*5)得出35,入栈
 * 5、 6 直接入栈
 * 6、 - 取出栈顶元素(6),次栈顶元素(35),使用-计算(35-6)得出29,入栈
 * 7、 下一位无元素返回最终结果29
 */
class PolishNotation{
    private List<String> suffixExpress;

    public PolishNotation(String suffixExpress) {
        this.suffixExpress = Arrays.asList(suffixExpress.split(","));
    }

    public PolishNotation(List<String> suffixExpress) {
        this.suffixExpress = suffixExpress;
    }

    public Integer cal(){
        Stack<Integer> stack = new Stack<>();
        //将逆波兰表达式转化为数组，使用栈计算
        for (String s : suffixExpress) {
            //正则表达式匹配，如果是是数字直接入栈
            if (s.matches("(\\d+)|(-\\d+)")){
                stack.push(Integer.valueOf(s));
                //否则出站计算
            }else {
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                int cal = cal(num1, num2, s);
                stack.push(cal);
            }
        }
        return stack.pop();
    }

    //计算方法，后一个数在最前面
    private int cal(int num1,int num2,String oper){
        int res = 0;
        switch (oper){
            case "+":
                res = num2 + num1;
                break;
            case "-":
                res = num2 - num1;
                break;
            case "*":
                res = num2 * num1;
                break;
            case "/":
                res = num2 / num1;
                break;
        }
        return res;
    }
}

