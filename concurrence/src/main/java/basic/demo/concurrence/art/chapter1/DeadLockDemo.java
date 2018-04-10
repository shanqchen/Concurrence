package basic.demo.concurrence.art.chapter1;

public class DeadLockDemo {

    private static String A = "A";
    private static String B = "B";
    
    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }
    
    @SuppressWarnings("static-access")
    private void deadLock(){
        new Thread(() -> {
            synchronized(A){
                try{
                    Thread.currentThread().sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                
                synchronized(B){
                    System.out.println("1");
                }
            }
        }).start();
        
        new Thread(() -> {
            synchronized(B){
                synchronized(A){
                    System.out.println("2");
                }
            }
        }).start();
    }
}
