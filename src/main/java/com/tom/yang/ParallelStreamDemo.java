package com.tom.yang;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ParallelStreamDemo {

    @Test
    public void test1(){
        Instant stime = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalcuate(0, 10000000000L);

        Long result = pool.invoke(pool.submit(task));
        Instant eTime = Instant.now();
        System.out.println(result);
        System.out.println("Time: " + Duration.between(stime, eTime).toMillis());
    }
    /**
     * 由于Fork/join 的操作略显复杂，所以 Stream提供了并行流来简化多线程运算
     */
    @Test
    public void test2(){
        Instant stime = Instant.now();
        // 数值类型可以直接生成数值区间的流
        // 使用并行流，只需要 .parallel()
        // .parallel() 的底层也是 ForkJoin, 可以指定 ForkJoinPool ,不指定的话默认使用公共的 ForkJoinPool
        LongStream.rangeClosed(0, 10000000000L).parallel().reduce(Long::sum);
        Instant eTime = Instant.now();
        System.out.println("Time: " + Duration.between(stime, eTime).toMillis());

    }

}
/**
 * Fork/Join框架， 将一个大任务，拆分成若干小任务，再将一个个的小任务运算的结果进行汇总
 * 使用工作窃取模式，当当前线程队列没有任务时，从其他队列的末尾窃取任务执行，充分利用CPU资源
 *
 * RecursiveAction  没有返回值的任务
 * RecursiveTask    有返回值的任务
 */
class ForkJoinCalcuate extends RecursiveTask<Long>{

    private long start;
    private long end;
    private static final long THRESHOLD = 10000;

    public ForkJoinCalcuate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 这个方法决定如何 fork/join 以及计算结果
     * @return
     */
    @Override
    protected Long compute() {
        long length = end - start;
        if(length < THRESHOLD){
            long sum = 0;
            for(long i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinCalcuate left = new ForkJoinCalcuate(start, mid);
            left.fork();
            ForkJoinCalcuate right = new ForkJoinCalcuate(mid + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}