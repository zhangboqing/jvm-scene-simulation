package com.zbq.controller;

import com.zbq.service.JvmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangboqing
 * @date 2018/5/2
 */
@RestController
@RequestMapping("/jss")
public class JvmSimulateSceneController {


    /**
     * http://localhost:8190/jvm/one/jss/startOrEndFlagChange?startOrEndFlag=2
     * @param startOrEndFlag
     * @throws Exception
     */
    @RequestMapping(value = "/startOrEndFlagChange")
    public String startOrEndFlagChange(int startOrEndFlag) throws Exception {

        JvmService.startOrEndFlagChange(startOrEndFlag);

        return new StringBuilder("startOrEndFlagChange change to : "+ startOrEndFlag).toString();
    }


    /**
     * 场景一：堆内存溢出   ==》 java.lang.OutOfMemoryError: Java heap space
     * VM参数： -Xms100M -Xmx100M -Xmn50M -XX:SurvivorRatio=8  -verbose:gc    -XX:+HeapDumpOnOutOfMemoryError   -XX:HeapDumpPath=/Users/zhangboqing/logs/log_hprof/gc.hprof  -XX:+PrintGCDetails   -XX:+PrintGCTimeStamps   -XX:+PrintGCDateStamps    -Xloggc:/Users/zhangboqing/logs/gclog/gc.log
     *
     * http://localhost:8190/jvm/one/jss/jvmMemoryOverflow/heap
     */
    @RequestMapping(value = "/jvmMemoryOverflow/heap")
    public String jvmMemoryOverflowForHeap() throws Exception {

        JvmService.jvmMemoryOverflowForHeap();

        return new StringBuilder("请求成功").toString();
    }


    /**
     * 场景二：直接内存溢出 ==》java.lang.OutOfMemoryError: Direct buffer memory
     * VM参数： -Xms100M -Xmx100M -Xmn50M -XX:SurvivorRatio=8  -verbose:gc    -XX:+HeapDumpOnOutOfMemoryError   -XX:HeapDumpPath=/Users/zhangboqing/logs/log_hprof/gc.hprof  -XX:+PrintGCDetails   -XX:+PrintGCTimeStamps   -XX:+PrintGCDateStamps    -Xloggc:/Users/zhangboqing/logs/gclog/gc.log
     * 堆外内存
     * 设定一个系统实际可达的-XX: MaxDirectMemorySize值（默认情况下等于-Xmx的设置）。
     *
     * http://localhost:8190/jvm/one/jss/jvmMemoryOverflow/direct
     */
    @RequestMapping(value = "/jvmMemoryOverflow/direct")
    public String jvmMemoryOverflowForDirectBuffer() throws Exception {

        JvmService.jvmMemoryOverflowForDirectBuffer();

        return new StringBuilder("请求成功").toString();
    }


    /**
     * 场景三：过多线程内存溢出 ==》java.lang.OutOfMemoryError: unable to create new native thread
     * VM参数： -Xms100M -Xmx100M -Xmn50M -XX:SurvivorRatio=8  -verbose:gc    -XX:+HeapDumpOnOutOfMemoryError   -XX:HeapDumpPath=/Users/zhangboqing/logs/log_hprof/gc.hprof  -XX:+PrintGCDetails   -XX:+PrintGCTimeStamps   -XX:+PrintGCDateStamps    -Xloggc:/Users/zhangboqing/logs/gclog/gc.log
     * 堆外内存
     *
     * 由于每一个线程的开启都要占用系统内存，因此当线程数量太多时，也有可能导致 OOM。由于线程的栈空间也是在堆外分配的，因此和直接内存非常相似，如果想让系统支持更多的线程，那么应该使用一个较小的堆空间。
     * （1）一个方法是可以尝试减少堆空间，如使用以下参数运行程序：
     *      -Xmx512m
     *   使用 512MB堆空间后，操作系统就可以预留更多内存用于线程创建，因此程序可以正常执行。
     * （2）另一个方法是减少每一个线程所占的内存空间，使用-Xss参数可以指定线程的栈空间。尝试以下参数：
     *      -Xmx1g -Xss128k
     *    这里依然使用 1GB的堆空间，但是将线程的栈空间减少到 128KB，剩余可用的内存理应可以容纳更多的线程，因此程序也可以正常执行。
     *
     *   注意：如果减少了线程的栈空间大小，那么栈溢出的风险会相应地上升。
     *   因此，处理这类 OOM的思路，除了合理的减少线程总数外，减少最大堆空间、减少线程的栈空间也是可行的。
     *
     * http://localhost:8190/jvm/one/jss/jvmMemoryOverflow/multiThread
     */
    @RequestMapping(value = "/jvmMemoryOverflow/multiThread")
    public String jvmMemoryOverflowForMultiThread() throws Exception {

        JvmService.jvmMemoryOverflowForMultiThread();

        return new StringBuilder("请求成功").toString();
    }


    /**
     * 场景四：永久区内存溢出 ==》java.lang.OutOfMemoryError: Metaspace
     * VM参数： -Xms100M -Xmx100M -Xmn50M -XX:SurvivorRatio=8  -verbose:gc    -XX:+HeapDumpOnOutOfMemoryError   -XX:HeapDumpPath=/Users/zhangboqing/logs/log_hprof/gc.hprof  -XX:+PrintGCDetails   -XX:+PrintGCTimeStamps   -XX:+PrintGCDateStamps    -Xloggc:/Users/zhangboqing/logs/gclog/gc.log
     * 堆外内存
     *
     * http://localhost:8190/jvm/one/jss/jvmMemoryOverflow/perm
     */
    @RequestMapping(value = "/jvmMemoryOverflow/perm")
    public String jvmMemoryOverflowForPerm() throws Exception {

        JvmService.jvmMemoryOverflowForPerm();

        return new StringBuilder("请求成功").toString();
    }


    /**
     * 场景五：栈内存溢出  ==> java.lang.StackOverflowError: null
     * VM参数： -Xms100M -Xmx100M -Xmn50M -XX:SurvivorRatio=8  -verbose:gc    -XX:+HeapDumpOnOutOfMemoryError   -XX:HeapDumpPath=/Users/zhangboqing/logs/log_hprof/gc.hprof  -XX:+PrintGCDetails   -XX:+PrintGCTimeStamps   -XX:+PrintGCDateStamps    -Xloggc:/Users/zhangboqing/logs/gclog/gc.log
     * 堆外内存
     *
     * http://localhost:8190/jvm/one/jss/jvmMemoryOverflow/stack
     */
    @RequestMapping(value = "/jvmMemoryOverflow/stack")
    public String jvmMemoryOverflowForStack() throws Exception {

        JvmService.jvmMemoryOverflowForStack();

        return new StringBuilder("请求成功").toString();
    }

}
