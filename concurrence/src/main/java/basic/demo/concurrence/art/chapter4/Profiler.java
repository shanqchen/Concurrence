package basic.demo.concurrence.art.chapter4;

/**
 * ThreadLocal
 *
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue(){
            return System.currentTimeMillis();
        }
    };
    
    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    
    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
    
    public static void main(String[] args) {
        Profiler.begin();
        SleepUtils.second(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }
    
}

//ThreadLocal 不是用来解决共享对象的多线程访问问题的
//拓展：http://www.iteye.com/topic/103804
