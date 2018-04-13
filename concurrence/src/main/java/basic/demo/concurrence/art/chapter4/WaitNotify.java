package basic.demo.concurrence.art.chapter4;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 等待/通知机制
 * @author chens
 *
 */


public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();
    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }
 
    static class Wait implements Runnable {
        
        @Override
        public void run(){
            //加锁，拥有lock的Monitor
            synchronized(lock){
                //当条件不满足时，继续wait，同时释放了lock的锁
                while(flag){
                    try{
                        System.out.println(Thread.currentThread() + " flag is true. wait @ "
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    }catch(InterruptedException e){
                        
                    }
                }
                
                //条件满足时，完成工作
                System.out.println(Thread.currentThread() + "flag is false. running @ " 
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
    
    static class Notify implements Runnable {
        
        @Override
        public void run(){
            //加锁，拥有lock的Monitor
            synchronized(lock){
                //获取lock的锁，然后进行通知，通知时不会释放lock的锁，
                //直到当前线程释放了lock后，WaitThread才能从wait()方法中返回
                System.out.println(Thread.currentThread() + " hold lock. notify@ " 
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            
            //再次加锁
            synchronized(lock){
                System.out.println(Thread.currentThread() + " hold lock again. sleep@" 
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }
}

/* ref: 《Java并发编程的艺术》 4.3.2 等待/通知机制
 * output:(第三行和第四行输出的顺序可能会互换)
Thread[WaitThread,5,main] flag is true. wait @ 14:53:30
Thread[NotifyThread,5,main] hold lock. notify@ 14:53:30
Thread[NotifyThread,5,main] hold lock again. sleep@14:53:35
Thread[WaitThread,5,main]flag is false. running @ 14:53:40
*/

/*
等待/通知的经典范式
该范式分为两部分，分别针对等待方（消费者）和通知方（生产者）

等待方遵循如下原则：
1）获取对象的锁
2）如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件
3）条件满足则执行对应的逻辑
对应的伪代码如下：
synchronized(对象){
    while(条件不满足){
                  对象.wait();
    }
          对应的处理逻辑
}

通知方遵循如下原则：
1）获得对象的锁
2）改变条件
3）通知所有等待在对象上的线程。
对应的伪代码如下：
synchronized(对象){
        改变条件
        对象.notifyAll();
}
*/
