package com.iuin.common.utils.thread;//package com.ssy.lingxi.common.util.thread;
//
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 通用线程池工厂
// *
// * @author fa
// */
//public class CommonThreadFactory implements ThreadFactory {
//
//    private final ThreadGroup group;
//    private final AtomicInteger threadNumber = new AtomicInteger(1);
//    private final String namePrefix;
//
//    public CommonThreadFactory(String type) {
//        SecurityManager s = System.getSecurityManager();
//        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
//        namePrefix = "common-pool-" + type + "-thread-";
//    }
//
//    @Override
//    public Thread newThread(Runnable runnable) {
//        Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
//        if (t.isDaemon()) {
//            t.setDaemon(false);
//        }
//        if (t.getPriority() != Thread.NORM_PRIORITY) {
//            t.setPriority(Thread.NORM_PRIORITY);
//        }
//        return t;
//    }
//}
