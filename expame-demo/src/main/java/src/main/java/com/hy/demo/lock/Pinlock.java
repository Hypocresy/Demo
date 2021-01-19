package src.main.java.com.hy.demo.lock;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/8/9 13:05
 * @since 0.0.1
 */
public class Pinlock {
  private    AtomicReference<Thread> enty =    new AtomicReference();

  public  void lock(){

      while (!enty.compareAndSet(null,Thread.currentThread())){

      }
//      System.out.println(Thread.currentThread().getName()+" 获取锁");
  }
  public  void unlock(){
      enty.compareAndSet(Thread.currentThread(),null);
  }

    public static void main(String[] args) {
        Pinlock pinlock = new Pinlock();
        new Thread(()->{
            try {
                pinlock.lock();
                TimeUnit.SECONDS.sleep(2);
                System.out.println("当前线程： "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                pinlock.unlock();
            }
        },"A").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                pinlock.lock();
                TimeUnit.SECONDS.sleep(2);
                System.out.println("当前线程： "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                pinlock.unlock();
            }
        },"B").start();
    }

}
