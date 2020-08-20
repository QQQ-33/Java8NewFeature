package com.tom.yang;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Tom
 * Java 8 新特性
 * lambda 表达式基础语法：
 *  一、新的操作符 -> 箭头操作符，需要函数式接口的支持（@FunctionalInterface 接口中只有一个抽象方法）
 *  -> 将 lambda 表达式拆分成两部分：
 *      左侧：参数列表
 *      右侧：lambda 的函数体
 *  lambda 中使用局部变量时，局部变量被视为 final，句柄无法改变，内容可以改变
 *  语法格式：
 *      1. 无参数，无返回值 () -> ()
 *      2. 一个参数，无返回值 num -> () 或者 (num) -> ()
 *      3. 多个参数，多条语句 (a, b, c) -> {}
 *      4. 多个参数，一条语句 (a, b, c) ->   {} 和 return可以省略
 *      5. 参数列表的数据类型可以省略，编译器可以通过上下文推断数据类型
 *  二、函数式接口
 *  接口中只有一个抽象方法时，被称作函数式接口，需要用 @FunctionalInterface 修饰
 *  这个注解可以对接口进行检查，是否满足函数式接口
 */
public class LambdaDemo {

    @Test
    public void test1(){
        Runnable run = () -> System.out.println("Hello Lambda!");
        run.run();
    }
    @Test
    public void test2(){
        Consumer cos = a -> System.out.println(a);
        cos.accept("Hello Lambda!");
    }

    @Test
    public void test3(){
        Comparator<Integer> com = (a, b) -> {
          return Integer.compare(a, b);
        };
    }

    @Test
    public void test4(){
        MyFunction fun = a -> a * a;
        System.out.println(fun.calculate(3));
    }

    @Test
    public void test5(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Tom", 18,9999.9));
        list.add(new Employee("Bob", 28, 8888.8));
        list.add(new Employee("AAA",28, 7777.7));

        list.sort((e1, e2) -> {
            if (e1.getAge().equals(e2.getAge())) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        list.forEach(System.out::println);
    }

}

/**
 * 函数式接口
 *
 */
@FunctionalInterface
interface MyFunction {
    /**
     * 对一个数进行运算
     * @param a
     * @return
     */
    public Integer calculate(Integer a);
}

class Employee {
    public enum Status{
        FREE, BUSY, DEAD;
    }
    private Status status;
    private String name;
    private Integer age;
    private Double salary;
    Employee(){}
    Employee(String name, Integer age, Double salary, Status status){
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.status = status;
    }
    Employee(String name, Integer age, Double salary){
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "status=" + status +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
