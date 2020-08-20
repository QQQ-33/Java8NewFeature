package com.tom.yang;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 全新的 时间日期 API
 * 旧的 API 不好用，且都是线程不安全的
 * java.time 包下提供了一组新的 API，他们都是不可变的，也就是线程安全的
 * java.time.chrono 特殊的时间格式处理
 * java.time.format 时间日期格式化
 * java.time.temporal 时间矫正器，对时间日期做特殊运算
 * java.time.zone 时区
 */
public class DateTimeDemo {
    @Test
    public void test() throws Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Callable<Date> call = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return sf.parse("2020-08-17");
            }
        };
        List<Future<Date>> results = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            results.add(pool.submit(call));
        }
        // 有时候会出现并发异常
        for(Future<Date> f: results){
            System.out.println(f.get());
        }
        pool.shutdown();
    }
    @Test
    public void test1() throws Exception{
        /*
          使用新API改写
         */
        // DateTimeFormatter 日期格式化，自带各种标准日期格式
        // 也可以自定义格式

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Callable<LocalDate> call = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("2020-08-17", dtf);
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> results = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            results.add(pool.submit(call));
        }
        // 此时不会再出现并发的问题
        for(Future<LocalDate> f: results){
            System.out.println(f.get());
        }
        pool.shutdown();
    }
    @Test
    public void test2(){
        /**
         * 为人读取的时间日期
         * LocalTime, LocalDate, LocalDateTime 类的实例是不可变的对象
         * 分别表示ISO-8601 日历系统的时间，日期，日期和时间
         * 他们只提供简单的日期或时间，不包含当前的时间信息，也不包含时区相关的信息
         */
        // 这三个类的使用方式几乎一样
        // 获取当前系统时间
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = LocalDateTime.of(2020, 8, 20, 20, 16, 33);
        System.out.println(ldt2);

        // 时间计算
        LocalDateTime ldt3 = ldt.plusYears(2);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ldt.minusMonths(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }
    @Test
    public void test3(){
        /**
         * 计算机读取的日期时间
         * Unix时间戳： 1970-01-01 00:00:00至现在的毫秒值
         */
        // 默认获取的 UTC 时间
        Instant ins = Instant.now();
        System.out.println(ins);
        // 时区偏移量
        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);
        // 获取毫秒值
        System.out.println(ins.toEpochMilli());

        // 以毫秒数构建时间戳
        Instant ins2 = Instant.ofEpochMilli(1000);
//        Instant ins2 = Instant.ofEpochSecond(1);
        System.out.println(ins2);
    }
    @Test
    public void test4() throws Exception{
        /**
         * Duration 计算时间间隔
         * Period 计算日期间隔
         */
        Instant ins1 = Instant.now();
        Thread.sleep(1000);
        Instant ins2 = Instant.now();

        Duration d1 = Duration.between(ins1, ins2);
        // 直接输出的话不是标准时间
        System.out.println(d1);
        System.out.println(d1.toMillis());
        System.out.println(d1.getSeconds());

        LocalTime l1 = LocalTime.now();
        Thread.sleep(1000);
        LocalTime l2 = LocalTime.now();
        Duration d2 = Duration.between(l1, l2);
        System.out.println(d2.toMillis());

        // 日期的计算
        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2019, 8,20);
        Period p = Period.between(ld1, ld2);
        System.out.println(p);
        System.out.println(p.getYears());
        System.out.println(p.getMonths());
        System.out.println(p.getDays());
    }
    @Test
    public void test5(){
        /**
         * 时间校正器
         */
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        // 利用基础的API设定时间
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);

        //利用 TemporalAdjuster(TemporalAdjusters 是一个工具类，提供了很多常用日期校正方式)
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        // 自定义 TemporalAdjuster， 比如查找下一个工作日
        ldt.with(l -> {
            LocalDateTime ldt4 = (LocalDateTime)l;
            DayOfWeek d = ldt4.getDayOfWeek();
            if(d.equals(DayOfWeek.FRIDAY)){
                return ldt4.plusDays(3);
            } else if(d.equals(DayOfWeek.SATURDAY)){
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });
    }
    @Test
    public void test6(){
        /**
         * 格式化时间日期
         * DateTimeFormatter
         */
        //DateTimeFormatter 内置了很多标准时间格式
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime ldt = LocalDateTime.now();
        String str = ldt.format(dtf);
        System.out.println(str);
        // 自定义时间格式
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String str2 = ldt.format(dtf2);
        System.out.println(str2);

        // 将字串格式化为时间
        LocalDateTime dtf3 = ldt.parse(str2, dtf2);
        System.out.println(dtf3);
    }
    @Test
    public void test7(){
        /**
         * 时区
         * ZonedDate
         * ZonedTime
         * ZonedDateTime
         */
        // 所有可用时区
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
//        availableZoneIds.forEach(System.out::println);
        // 利用时区构建时间
        LocalDateTime ldt = LocalDateTime.now();
//        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/Warsaw"));
        System.out.println(zdt);
    }
}
