package basic.demo.concurrence.art.chapter4;

/**
 * 理解中断：中断可以理解为线程的一个标识位属性，它表示一个运行中的线程是否被其他线程进行了中断操作。
 * 中断好比其他线程对该线程打了个招呼，其他线程通过调用该线程的interrupt()方法对其进行中断操作。
 * 注意：如果某线程已经处于终结状态，即使该线程被中断过，在调用该线程对象的isInterrupted()时依旧会返回false
 * @author chens
 *
 */
public class Interrupted {

    public static void main(String[] args) throws Exception {
        
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        
        sleepThread.start();
        busyThread.start();
        
        //休眠5s,让sleepThread和busyThread充分运行
        SleepUtils.second(5);
        
        sleepThread.interrupt();
        busyThread.interrupt();
        
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        
        //防止sleepThread和busyThread立刻退出
        SleepUtils.second(2);
    }
    /*output
     *  java.lang.InterruptedException: sleep interrupted
        SleepThread interrupted is false
        BusyThread interrupted is true
     */
    //从结果可以看出，抛出InterruptedException的线程SleepThread，其中断标识位被清除了，
    //而一直忙碌运作的线程BusyThread，中断标识位没有被清除
    
    //不停地睡眠
    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            while(true){
                SleepUtils.second(10);
            }
        }
    }
    
    //不停地运行
    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while(true){
                
            }
        }
    }
}
