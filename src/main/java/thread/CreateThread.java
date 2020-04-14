package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread thread0 = new CreateByThread();
        thread0.start();

        CreateByRunnable instance = new CreateByRunnable();
        Thread thread1 = new Thread(instance);
        thread1.start();

        CreateByCallable mc = new CreateByCallable();
        FutureTask<String> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();

        System.out.println(ft.get());
    }
}

class CreateByThread extends Thread{
    public void run() {
        System.out.println("CreateByThread.run()");
    }
}

class CreateByRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("CreateByRunnable.run()"+Thread.currentThread().getName());
    }
}

class CreateByCallable  implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("CreateByCallable.run()");
        return "OK";
    }
}