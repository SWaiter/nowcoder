package com.nowcoder.sync;

/**
 * Created by uwangshan@163.com on 2018/12/25.
 */
public class ThreadDemo3 {
     public static void main(String[] args)
     {
                //创建任务
                Demo d = new Demo();

                //创建线程
                Thread t = new Thread(d);
                Thread t2 = new Thread(d);

                t.start();
                //程序是从主线程开始运行，在运行时，虽然开启了thread-0线程，
                //但是cpu可能不会立刻切换到thread-0线程上。
                //为了保证thread-0一定能够进入到if中执行，thread-1进入else执行
                //让主线程在开启thread-0线程之后，让主线程休眠
                try{Thread.sleep(100);}catch(InterruptedException e){}
                //把标记改为false，让下一个线程进入的else中执行
                d.flag = false;
                t2.start();
            }
}