package com.tom.yang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java 内置的四大核心函数式接口：
 * Consumer<T>: 消费型接口
 *      void accept(T t);
 * Supplier<T>: 供给型接口
 *      T get();
 * Function<T, R>: 函数型接口
 *      R apply(T t);
 * Predicate<T>: 断言型接口
 *      boolean test(T t);
 */
public class FunctionalInterfaces {
    /**
     * Consumer<T>: 消费型接口
     * 对传入的参数进行操作，没有返回值
     */

    @Test
    public void test1(){
        happy(9999.99, m -> System.out.println("消费了：" + m + " 元"));
    }
    private void happy(double money, Consumer<Double> com){
        com.accept(money);
    }

    /**
     * Supplier<T>: 供给型接口
     * 产生对象，无需传入参数
     */

    @Test
    public void test2(){
        List<Integer> list = getNumList(10, () -> (int)(Math.random() * 100));
        list.forEach(System.out::println);
    }
    private List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++){
            list.add(sup.get());
        }
        return list;
    }

    /**
     * Function<T, R>: 函数型接口
     * 传入一个参数，进行处理，返回一个对象
     */

    @Test
    public void test3(){
        System.out.println(strHandler("2020-07-28T10:11:12.111Z", s -> s.replaceAll("T"," ").replaceAll("Z","")));
    }
    private String strHandler(String str, Function<String, String> fun){
        return fun.apply(str);
    }

    /**
     * Predicate<T>: 断言型接口
     * 传入一个参数，进行条件判断，返回一个boolean
     */
    @Test
    public void test4(){
        List<String> list = Arrays.asList("Hello", "Tom", "Lambda", "Functional");
        List<String> res = filterStr(list, s -> s.length() > 3);
        res.forEach(System.out::println);
    }
    private List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> res = new ArrayList<>();
        for(String s : list){
            if(pre.test(s)){
                res.add(s);
            }
        }
        return res;
    }
}
