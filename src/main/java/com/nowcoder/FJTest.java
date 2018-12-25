package com.nowcoder;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class FJTest {
    private List<String> list;

    public static void main(String[] args) {
        new FJTest().init();
    }

    private void init() {
//模拟初始化1W条数据
        list = new ArrayList<String>();
/*MMap map = new MMap();
 VData mVDate = new VData();*/
        for (int i = 0; i < 10000; i++) {
            list.add(i + "");
/*  System.out.println(" shuzi:"+list.get(i));
 map.put(" insert into testName (id,name) values('"+list.get(i)+"','"+list.get(i)+"')", "INSERT");
 */
//直接插入不用线程用2分半
        }

/*  mVDate.clear();
 mVDate.add(map);
 PubSubmit tSubmit = new PubSubmit();
 tSubmit.submitData(mVDate, "");*/


        int borf = new Date().getMinutes();
        System.out.println(new Date().getMinutes());
        int count = 300;
        int listSize = list.size();
        int RunSize = (listSize / count) + 1;
        CountDownLatch countDownLatch = new CountDownLatch(RunSize);
        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(RunSize);
        List<String> newList = null;
        for (int i = 0; i < RunSize; i++) {
            if ((i + 1) == RunSize) {
                int startIndex = (i * count);
                int endIndex = list.size();
                newList = list.subList(startIndex, endIndex);
            } else {
                int startIndex = i * count;
                int endIndex = (i + 1) * count;
                newList = list.subList(startIndex, endIndex);
            }
            ExecupteHp hpRunnable = new ExecupteHp(newList);
            executor.execute(hpRunnable);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println(new Date().getMinutes());
        System.out.println(new Date().getMinutes() - borf);
    }

    //累不了
    class ExecupteHp implements Runnable {
        private List<String> list;
        public ExecupteHp(List<String> list) {
            this.list=list;
        }
        Map map = new HashMap();

//        VData mVDate = new VData();

        @Override
        public void run() {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(" shuzi:" + list.get(i));
                    map.put(" insert into testName (id,name) values('" + list.get(i) + "','" + list.get(i) + "')", "INSERT");
                }
            }
//            mVDate.clear();
//            mVDate.add(map);
//            PubSubmit tSubmit = new PubSubmit();
//            tSubmit.submitData(mVDate, "");
        }
    }
}