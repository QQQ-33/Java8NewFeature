package com.tom.yang;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流式 API
 * 集合用于保存数据，流用于进行计算
 * 流是数据渠道，可以从集合，数组等数据源生成
 * Stream 不自己存储元素
 * Stream 不改变源对象，它会返回一个持有结果的新Stream
 * Stream 操作是延迟执行的，等到需要结果的时候才执行
 *
 * 1. 创建一个 Stream
 * 2. 中间操作（延迟执行）
 * 3. 终端操作（触发执行）
 */
public class StreamDemo {
    @Test
    public void test1(){
        // 1. Collection 一系列集合提供的 stream() parallelStream() 方法
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        // 2. Arrays 的静态方法 stream()
        Integer[] arr = {1, 2, 3, 4, 5};
        Stream<Integer> stream2 = Arrays.stream(arr);

        // 3. Stream 的静态方法 Stream.of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        // 4. 创建无限流 seed 为初始值， 第二个参数为生成数据的方式
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x * x);
    }
    List<Employee> employees = Arrays.asList(
            new Employee("AA", 18, 1234.1, Employee.Status.BUSY),
            new Employee("BB", 23, 2345.2, Employee.Status.FREE),
            new Employee("CC", 27, 3456.3, Employee.Status.BUSY),
            new Employee("DD", 16, 4567.4, Employee.Status.FREE),
            new Employee("DD", 16, 4567.4, Employee.Status.BUSY),
            new Employee("DD", 16, 4567.4, Employee.Status.DEAD),
            new Employee("EE", 19, 5678.5, Employee.Status.BUSY)
    );
    @Test
    public void test2(){
        /*
            筛选与切片
            filter 过滤需要的元素
            limit 截断 stream
            skip 跳过前 n 个元素， 如果 stream 中的元素不足 n 个，则返回空的流，与 limit(n) 互补
            distinct 通过 hashCode 和 equals 对流中的元素进行去重
        */
        // filter 需要一个 Predicate 接口
        System.out.println("filter==========");
        employees.stream().filter(e -> e.getAge() > 18).forEach(System.out::println);

        // limit 短路操作，只要找到满足数量的元素，那么后续的操作不会进行
        System.out.println("limit==========");
        employees.stream().filter(e -> {
            System.out.println("过滤"); // 观察打印，发现有两个满足条件的元素后，后续的操作没有执行
            return e.getAge() > 18;
        }).limit(2).forEach(System.out::println);

        // skip 跳过操作
        System.out.println("skip==========");
        employees.stream().filter(e -> {
            System.out.println("过滤"); // 满足条件的元素会跳过前 2 个，保留剩余所有满足条件的元素
            return e.getAge() > 18;
        }).skip(2).forEach(System.out::println);

        // distinct 去重操作，注意：需要重写 hashCode 和 equals 让 stream 能确定相同元素
        System.out.println("distinct==========");
        employees.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void test3(){
        /*
            映射
            map 对每个元素应用 lambda 函数，并映射成新的集合， 该 lambda 接受一个参数，返回一个参数
            flatMap 接受一个 lambda 将每个元素转换为另一个流，并将所有的流连接为一个流
        */
        // map 接受 Function 接口
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream().map(String::toUpperCase).forEach(System.out::println);
        System.out.println("==============================================");
        employees.stream().map(Employee::getName).forEach(System.out::println);

        // flatMap 需要一个函数，接受一个参数，返回一个流
        System.out.println("==============================================");
        Stream<Character> stream = list.stream().flatMap((this::getCharacter));
        stream.forEach(System.out::print);
    }
    private Stream<Character> getCharacter(String s){
        List<Character> list = new ArrayList<>();
        for(Character c: s.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }

    @Test
    public void test4(){
        /*
            排序
            sorted() 自然排序， 使用 Comparable.compareTo()
            sorted(Comparator com) 自定义排序 Comparator 实现的 compare() 方法
        */
        List<String> list = Arrays.asList("bbb", "aaa", "eee", "ccc", "ddd");
        list.stream().sorted().forEach(System.out::println);
        employees.stream().sorted((e1, e2) -> {
            if(e1.getAge().equals(e2.getAge())){
                return e1.getName().compareTo(e2.getName());
            } else {
                return e1.getAge() - e2.getAge();
            }
        }).forEach(System.out::println);
    }

    @Test
    public void test5(){
        /*
            终止操作
        */
        // allMatch 判断是否所有元素都满足条件
        boolean isAllBusy = employees.stream().allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println("isAllBusy: " + isAllBusy);

        // anyMatch 判断至少一个元素满足条件
        boolean isAnyBusy = employees.stream().anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println("isAnyBusy: " + isAnyBusy);

        // noneMatch 判断所有元素都不满足条件
        boolean isNoneMatch = employees.stream().noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println("isNoneMatch: " + isNoneMatch);

        // findFirst 返回第一个元素，由于有可能为空，所以返回的是 Optional
        Optional<Employee> first = employees.stream().sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())).findFirst();
        System.out.println("first: " + first.get());

        // findAny 返回任意一个元素，由于有可能为空，所以返回的是 Optional
        Optional<Employee> any = employees.stream().findAny();
        System.out.println("any: " + any.get());

        // count 返回元素的个数
        long count = employees.stream().count();
        System.out.println("count: " + count);

        // max 需要给出如何比较元素大小
        Optional<Employee> max = employees.stream().max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println("max: " + max);

        // min 需要给出如何比较元素大小
        Optional<Employee> min = employees.stream().min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println("min: " + min);
    }

    @Test
    public void test6(){
        /*
            规约操作
        */
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        // 带有初始值，结果必定不为空
        // T reduce(T identity, BinaryOperator<T> accumulator);
        Integer sum = list.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        // 没有初始值，所以结果是 Optional
        // Optional<T> reduce(BinaryOperator<T> accumulator);
        Optional<Integer> sum2 = list.stream().reduce((a, b) -> a + b);
        System.out.println(sum.intValue());

    }
    @Test
    public void test7(){
         /*
            收集操作
        */
         // collect 可以将流中的数据收集起来返回新的集合
        // collect() 接受一个 Collector， 可以利用 Collectors 提供的工具方法快速将流收集为集合
        List<String> list = employees.stream().map(Employee::getName).collect(Collectors.toList());
        list.stream().forEach(System.out::println);

        // Collectors 也可以用于统计
        Double avgAge = employees.stream().collect(Collectors.averagingInt(Employee::getAge));
        Integer sumAge = employees.stream().collect(Collectors.summingInt(Employee::getAge));

        // 分组
        Map<Employee.Status, List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getStatus));
        // 多级分组, groupingBy, 可以自定义分组的层级
        employees.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
            // 自定义分组函数
            if(e.getAge() < 18){
                return "青年";
            } else {
                return "老年";
            }
        })));

        // 分区
        Map<Boolean, List<Employee>> map2 = employees.stream().collect(Collectors.partitioningBy( e -> e.getSalary() > 8000));
    }
}
