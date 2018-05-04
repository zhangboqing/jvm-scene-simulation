package com.zbq.service;

import com.zbq.constant.StartOrEndFlagEnum;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author zhangboqing
 * @date 2018/5/2
 */
public class JvmService {

    private volatile static int WHOLE_START_OR_END_FLAG = 1;
    //==========================================

    private static final int _1MB = 1024 * 1024;

    /**
     * 改变启停参数
     *
     * @param startOrEndFlag startOrEndFlag 1 启动；2 停止
     */
    public static void startOrEndFlagChange(int startOrEndFlag) {
        WHOLE_START_OR_END_FLAG = startOrEndFlag;
    }

    /**
     * 堆内存溢出
     */
    public static void jvmMemoryOverflowForHeap() {
        ArrayList<byte[]> bytes = new ArrayList<>();

        while (WHOLE_START_OR_END_FLAG == StartOrEndFlagEnum.START.getId()) {

            byte[] allocation;
            allocation = new byte[2 * _1MB];
            bytes.add(allocation);
        }
    }


    public static void jvmMemoryOverflowForDirectBuffer() {

        ArrayList<ByteBuffer> byteBuffers = new ArrayList<>();

        int i = 0;
        while (WHOLE_START_OR_END_FLAG == StartOrEndFlagEnum.START.getId()) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024);
            byteBuffers.add(byteBuffer);
            System.out.println(++i);

        }

    }

    public static void jvmMemoryOverflowForMultiThread() {
        int i = 0;

        while (WHOLE_START_OR_END_FLAG == StartOrEndFlagEnum.START.getId()) {
            ++i;
            new Thread(new SleepThread(), "Threaad" + i).start();
            System.out.println("Threaad" + i + " created");
        }

    }

    public static void jvmMemoryOverflowForPerm() throws ClassNotFoundException {


        int i = 0;

        while (WHOLE_START_OR_END_FLAG == StartOrEndFlagEnum.START.getId()) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
            i++;
            System.out.println("运行次数："+i);
        }
    }





    public static void sofMethod(int depth){
        System.out.println("递归次数：" + ++depth);
        sofMethod(depth);
    }

    public static void jvmMemoryOverflowForStack() {
            int depth = 0;
            sofMethod(depth);
    }


    public static class SleepThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(10_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class OOMObject {

    }
}
