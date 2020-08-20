package com.tom.yang;

public class DefaultMethodDemo {
}

/**
 * 接口可以有默认的方法实现
 * 类优先原则：
 *      如果一个接口定义了一个默认方法，且父类中也定义了同名的方法（有方法实现），此时选择使用父类中的方法
 *      如果实现的多个接口具有相同的默认方法，此方法必须重写，以此避免冲突
 */
interface MyFun {
    default String sayHello(){
        return "Hello World";
    }
}