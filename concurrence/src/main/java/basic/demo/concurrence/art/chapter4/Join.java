package basic.demo.concurrence.art.chapter4;

public class Join {

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for(int i = 0; i < 10; i++){
            //每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        SleepUtils.second(5);
        System.out.println(Thread.currentThread().getName() + " terminate. "); //main
    }
    
    static class Domino implements Runnable {
        private Thread thread;
        public Domino(Thread thread){
            this.thread = thread;
        }
        
        @Override
        public void run(){
            try{
                thread.join();
            }catch(InterruptedException e){
                
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}/*output
main terminate. 
0 terminate.
1 terminate.
2 terminate.
3 terminate.
4 terminate.
5 terminate.
6 terminate.
7 terminate.
8 terminate.
9 terminate.
*/

/*
从上述输出可以看到，每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程
终止后，才从join()方法返回，这里涉及了等待/通知机制（等待前驱线程结束，接收
前驱线程结束通知）。

下面的代码是JDK中Thread.join()方法的源码（进行了部分调整）
//加锁当前线程对象
public final synchronized void join() throws InterruptedException {
    //条件不满足，继续等待
    while(isAlive()){
        wait(0);
    }
    //条件符合，方法返回
}

当线程终止时，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线程。
可以看到join()方法的逻辑结构与WaitNotify.java中描述的等待/通知经典范式一致，即加锁、循环和处理逻辑3个步骤
*/
//ref: https://www.cnblogs.com/huangzejun/p/7908898.html


