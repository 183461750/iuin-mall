package com.iuin.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskMigrationExample {
    public static void main(String[] args) {
        // 创建源线程池
        ExecutorService sourceThreadPool = Executors.newFixedThreadPool(2);

        // 创建目标线程池
        ExecutorService targetThreadPool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        // 提交任务到源线程池
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            sourceThreadPool.execute(() -> {
                System.out.println("Task " + taskId + " is running in source thread pool");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " is completed in source thread pool");

                // 迁移任务到目标线程池
                targetThreadPool.execute(() -> {
                    System.out.println("Task " + taskId + " is running in target thread pool");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Task " + taskId + " is completed in target thread pool");
                });
            });
        }

        // 关闭源线程池
        sourceThreadPool.shutdown();

        // 等待源线程池中的任务执行完成
        try {
            sourceThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭目标线程池
        targetThreadPool.shutdown();
    }
}