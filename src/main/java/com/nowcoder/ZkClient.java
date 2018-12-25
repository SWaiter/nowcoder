package com.nowcoder;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by uwangshan@163.com on 2018/12/24.
 */
public class ZkClient {
    private static final int SESSION_TIMEOUT = 30000;
    public static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            logger.info("process:" + event.getType());
        }
    };

    private ZooKeeper zooKeeper;

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void connect() throws IOException {
        zooKeeper = new ZooKeeper("10.6.25.160:2181", SESSION_TIMEOUT, watcher);
    }

    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            logger.error("关闭发生异常", e);
        }
    }

    public static ZkClient zkClient;

    public static void main(String[] args) throws Exception {

        zkClient = new ZkClient();
        //连接  
        zkClient.connect();
        logger.info("连接成功");
        //判断是否存在

        List<String> url = zkClient.getUrl("/consumers/pcu-sdk-group/partition_offsets/OLD_SDK_REAL_PCU/ser/");

//        rmr("/consumers/test1010/partition_offsets/NEW_SDK_wangshan/log_serpcu");

        for (String u : url) {
            Stat stat = zkClient.getZooKeeper().exists(u, false);
            if (stat != null) {
                logger.info("exists:", stat.getCzxid());
                rmr(u);
            }
        }


    }

    /**
     * 递归删除 因为zookeeper只允许删除叶子节点，如果要删除非叶子节点，只能使用递归
     *
     * @param path
     * @throws IOException
     */
    public static void rmr(String path) throws Exception {
        //获取路径下的节点
        List<String> children = zkClient.getZooKeeper().getChildren(path, false);
        for (String pathCd : children) {
            //获取父节点下面的子节点路径
            String newPath = "";
            //递归调用,判断是否是根节点
            if (path.equals("/")) {
                newPath = "/" + pathCd;
            } else {
                newPath = path + "/" + pathCd;
            }
            rmr(newPath);
        }
        //删除节点,并过滤zookeeper节点和 /节点
        if (path != null && path.trim().startsWith("/consumers") && !path.trim().equals("/")) {
            zkClient.getZooKeeper().delete(path, -1);
            //打印删除的节点路径
            System.out.println("被删除的节点为：" + path);
        }
    }


    public List<String> getUrl(String u) throws ParseException {
        List<String> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_00-00-00");//可以方便地修改日期格式
        Calendar cal = Calendar.getInstance();
        Date n = dateFormat.parse("2018-12-17_00-00-00");

        cal.setTime(n);
        for(int ii = 0; ii< 60 ;ii++){
            if (ii > 0) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            Date now = cal.getTime();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//可以方便地修改日期格式
            String s = dateFormat.format(now);
            Date date = dateFormat1.parse(s);
            long time = date.getTime();
            for (int i = 0; i < 288; i++) {
                if (i > 0) {
                    time = time + 5 * 60 * 1000;
                }
                String ss = dateFormat1.format(new Date(time));
                list.add(u + ss);
            }
        }

//        list.add(u + "2018-10-10_19-45-00");
        return list;
    }


}
