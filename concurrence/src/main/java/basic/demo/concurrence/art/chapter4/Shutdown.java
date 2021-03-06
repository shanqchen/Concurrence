package basic.demo.concurrence.art.chapter4;

/**
 * 安全地终止线程
 * 1.中断
 * 2. boolean变量控制
 *
 */
public class Shutdown {

    /*
     * 示例在执行过程中，main线程通过中断操作和cancel()方法均可以使CountThread得以终止。
     * 这种通过标识位或者中断操作的方式能够使线程在终止时有机会去清理资源，而不是武断地将线程停止，
     * 这种终止线程的做法显得更加安全和优雅。
     */
    public static void main(String[] args) {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
        
        //睡眠1s，main线程对CountThread进行中断，使CountThread能够感知中断而结束
        SleepUtils.second(1);
        countThread.interrupt();
        
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        
        //睡眠1s，main线程对Runner two进行取消，使CountThread能够感知on为false而结束
        SleepUtils.second(1);
        two.cancel();
    }
    
    
    static class Runner implements Runnable {

        private long i;
        private volatile boolean on = true;
        
        @Override
        public void run() {
            while(on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("Count i = " + i);
        }
        
        public void cancel(){
            on = false;
        }
        
    }
}
