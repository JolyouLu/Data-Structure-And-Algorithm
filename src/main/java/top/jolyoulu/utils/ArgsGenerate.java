package top.jolyoulu.utils;

import java.util.ArrayList;

/**
 * @Author: JolyouLu
 * @Date: 2021/9/15 11:56
 * @Version 1.0
 */
public abstract class ArgsGenerate<T> implements Generate<T>{
    @Override
    abstract public T next();

    abstract public ArrayList testArg();

}
