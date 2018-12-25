package com.nowcoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_00:00:00");//可以方便地修改日期格式
        Calendar cal = Calendar.getInstance();
        Date n = dateFormat.parse("2018-09-01_00:00:00");
        cal.setTime(n);
        for (int jj = 0; jj < 7; jj++) {
            if (jj > 0) {
                cal.add(Calendar.DAY_OF_MONTH, -1);

            }

            Date now = cal.getTime();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");//可以方便地修改日期格式
            String s = dateFormat.format(now);
            Date date = dateFormat1.parse(s);
            long time = date.getTime();
            for (int i = 0; i < 288; i++) {
                if (i > 0) {
                    time = time + 5 * 60 * 1000;
                }
                String ss = dateFormat1.format(new Date(time));
                System.out.println(ss);
            }
        }
    }
}
