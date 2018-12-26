package com.nowcoder.sync;

/**
 * Created by uwangshan@163.com on 2018/12/25.
 */
public class Demo implements Runnable {
    static int num = 100;
    boolean flag = true;

    Object obj = new Object();

    public void run() {
        if (flag)  //使用判断进行线程执行任务的代码切换
        {
            while (true) {    //thread-0
                synchronized (Demo.class) {
                    if (num > 0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        System.out.println(Thread.currentThread().getName() + "......" + num);
                        num--;
                    }
                }
            }
        } else {
            while (true) {
                //thread-1
                show();
            }
        }
    }

    /*
34         show方法中的所有代码全部是需要被同步的代码。这时就可以把这个同步加载show方法
35         在方法上加同步的格式:直接在方法上书写同步关键字即可
36
37
38         非静态的方法在执行的时候需要被对象调用。
39         这个show方法是被当前的Demo对象调用的，这时在show方法上加的同步使用的锁就是当前的
40         那个Demo对象。就是this
41
42
43         静态方法上使用的锁不是this，静态方法执行时不需要对象。而静态方法是被类名直接调用。
44         静态方法上使用的锁是当前的class文件
45     */
    public static synchronized void show() {
        if (num > 0) {
            System.out.println(Thread.currentThread().getName() + "=================" + num);
            num--;
        }
    }
}
