package xhx.pinganapi.pinganapiserver.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {
    private static final int size = 20; // 线程池的大小
    private static ExecutorService servicePool;

    public static void init(int threadSize){
        if(servicePool == null){
            synchronized (ThreadExecutor.class){
                if(servicePool == null){
                    if(threadSize <= 0){
                        threadSize = size;
                    }
                    servicePool = Executors.newFixedThreadPool(threadSize);
                }
            }
        }
    }

    public static void execute(Runnable task){
        init(size);
        servicePool.execute(task);
    }

    /**
     * 等待线程池中的任务完成
     */
    public static void awaitTermination(){
        init(size);
        servicePool.shutdown();
        try {
            servicePool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否有空闲线程
     * @return
     */
    public static boolean isIdle(){
        init(size);
        ThreadPoolExecutor me = ((ThreadPoolExecutor)servicePool);
        return me.getActiveCount()<me.getCorePoolSize();
    }

    /**
     * 获取空闲线程数
     * @return
     */
    public static int getIdleThreadCount(){
        init(size);
        ThreadPoolExecutor me = ((ThreadPoolExecutor)servicePool);
        return me.getCorePoolSize()-me.getActiveCount();
    }
}
