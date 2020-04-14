package thread;

import java.util.concurrent.*;

public class CreateThreadPool {
    public static void main(String[] args){
        int corePoolSize = 3;// 核心线程数，即确定有多少个核心线程。
        int maximumPoolSize = 5; // 最大线程数，即限定线程池中的最大线程数量。
        long keepAliveTime  = 5;//非核心线程的存活时间，配合下面的TimeUnit参数确定时间。
        TimeUnit unit =  TimeUnit.SECONDS ; //一个时间类型的枚举类。有从纳秒到天的时间量度，配合上面的keepAliveTime确定非核心线程的存活时间。
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>(10); //  装载Runnable的阻塞队列，具体类型可以自己确定。
        //拒绝服务处理
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        //存放任务的阻塞队列
        BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(10);
        //线程工厂，这是一个函数式接口，里面只定义了一个newThread（Runnable task）方法，需要自己实现工厂的方法，在这里我们可以对线程进行自定义的初始化，例如给线程设定名字，这样方便后期的调试。
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("由工厂创建的线程");
                return t;
            }
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, queue, threadFactory,handler);

        threadPool.execute(new CreateByRunnable());
        threadPool.shutdown();
    }
}