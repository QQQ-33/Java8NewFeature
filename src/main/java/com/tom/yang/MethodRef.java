package com.tom.yang;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 方法引用：
 *      如果lambda 中的内容已经有方法实现了，那么可以使用方法引用，它是lambda的另一种表现形式
 * 主要有三种语法格式：
 *      对象::实例方法名
 *      类::静态方法名
 *      类::实例方法名
 *
 *  构造器引用：
 *      类名::new
 */
public class MethodRef {
    @Test
    public void test1(){
        // lambda 体中的内容，已经被实现，可以直接使用方法引用
        // 何为已经实现，也是就是参数列表与返回值类型完全相同的方法
        // 使用方法引用时，无需按照 lambda 的格式，只需写出方法引用的格式
        Consumer<String> con1 = s -> System.out.println(s);

        PrintStream ps = System.out;
        Consumer<String> con2 = ps::println;
        // 对象::实例方法名
        Consumer<String> con3 = System.out::println;

    }
    @Test
    public void test2(){
        Employee emp = new Employee("Tom", 18, 9999.99);
        Supplier<String> sup1 = () -> emp.getName();
        Supplier<Integer> sup2 = emp::getAge;
    }
    @Test
    public void test3(){
        // 类::静态方法名
        Comparator<Integer> com1 = (a, b) -> Integer.compare(a, b);
        Comparator<Integer> com2 = Integer::compare;

    }
    @Test
    public void test4(){
        // 类::实例方法名
        BiPredicate<String, String> pre1 = (a, b) -> a.equals(b);
        // 特殊情况，第一个参数是实例方法的调用者，第二个参数是实例方法的入参时，可以这样调用
        BiPredicate<String, String> pre2 = String::equals;
    }

    @Test
    public void test5(){
        // 构造器引用
        Supplier<Employee> sup1 = () -> new Employee();
        Supplier<Employee> sup2 = Employee::new;
    }
}
