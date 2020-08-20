package com.tom.yang;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestAnnotation {

    @Test
    public void test() throws Exception{
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method m = clazz.getMethod("show");
        MyAnnotation[] a = m.getAnnotationsByType(MyAnnotation.class);
        for(MyAnnotation ma: a){
            System.out.println(ma.value());
        }
    }

    @MyAnnotation("TTT")
    @MyAnnotation("Hello")
    public void show(){

    }
}
