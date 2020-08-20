package com.tom.yang;

import org.junit.Test;

import java.util.Optional;

/**
 * 避免空指针异常
 */
public class OptionalDemo {
    @Test
    public void test1() {
        // 创建一个不为空的 Optional
        Optional<Employee> op = Optional.of(new Employee());
        // 如果构建 Optional 时传入的是 null，那么会抛出空指针异常
//        Optional<Employee> op = Optional.of(null);
        // 获取值
        Employee emp = op.get();
        System.out.println(emp);

        // 创建一个可以为空 Optional,
//        Optional<Employee> op2 = Optional.ofNullable(new Employee());
//        Optional<Employee> op2 = Optional.ofNullable(null);
//        System.out.println(op2.get());

        // 创建一个空的 Optional
//        Optional<Employee> op3 = Optional.empty();
//        System.out.println(op3.get());


    }

    @Test
    public void test2() {
        Optional<Employee> op = Optional.empty();
        // 对Optional进行空值判断
        if(op.isPresent()){
            System.out.println(op.get());
        } else {
            System.out.println("null");
        }
        // 有值就用 Optional 的值, 否则就用传入的默认值
        Employee emp = op.orElse(new Employee("Tom", 18, 8888.88));
        System.out.println(emp);

        // 需要传入 Supplier 型接口指定如果创建默认值
        Employee emp2 = op.orElseGet(() -> new Employee("Tom", 18, 8888.88));
        System.out.println(emp2);

    }

    @Test
    public void test3() {
        Optional<Employee> op = Optional.ofNullable(new Employee("Tom", 18, 8888.88));
        // 对容器中的对象应用 function, 并返回 Optional
        Optional<String> str = op.map((e) -> e.getName());
        System.out.println(str);

        // 对容器中的对象应用 function, 但是要求返回的必须是 Optional
        Optional<String> str2 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(str2);
    }
}
