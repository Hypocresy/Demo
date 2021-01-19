package src.main.java.com.hy.demo;


import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/6/8 10:51
 * @since 0.0.1
 */

public class ThreadPoolTest {
   public volatile   static  int a = 100;
    public static void main(String[] args) throws InterruptedException {
         LinkedBlockingQueue<Runnable> q = new LinkedBlockingQueue<>();
        ThtradPoje poje = new ThtradPoje();
        System.out.println("size: "+q.size());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 30, 60,TimeUnit.SECONDS,q);
        executor.allowCoreThreadTimeOut(true);
        for(int i= 0;i<100;i++){
            executor.execute(()->poje.runk(a));
        }

        while (true){
            if(q.size()==0){
                System.out.println("size: "+q.size());
                System.out.println("队列为空 结束");
                break;
            }else {

                System.out.println("size: "+q.size());
                System.out.println("队列不为空 结束");
                System.out.println("a: "+a);
                Thread.sleep(2000);
                System.out.println("a: "+a);
            }
        }


      System.out.println("休息时间");
      Thread.sleep(6000);
        System.out.println("重新启用线程池");
        for(int i= 0;i<100;i++){
            executor.execute(()-> poje.runk(a));
        }

        while (true){

            if(q.size()==0){
                System.out.println("队列为空 结束");
                break;
            }else {
                System.out.println("队列不为空 结束");
                Thread.sleep(2000);
            }
        }

    }


}
