package com.nowcoder;

import java.util.Arrays;

/**
 * Created by uwangshan@163.com on 2018/12/24.
 */
public class RepeatedNumbersInArrays {
    /*题目描述

在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。*/
    // 方法一： 使用字符串拼接
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            sb.append(numbers[i]);
        }
        for (int i = 0; i < length; i++) {
            if (sb.indexOf(numbers[i] + "") != sb.lastIndexOf(numbers[i] + "")) {
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }

    public boolean duplicate2(int numbers[], int length, int[] duplication) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }
        Arrays.sort(numbers);
        int flag = 0;//坐标记
        for (int i = 0; i < length - 1; i++) {
            if (numbers[i] == numbers[i + 1]) {
                duplication[0] = numbers[i];
                flag = 1;
                break;
            }
        }
        return flag == 1 ? true : false;
    }

    public static void main(String[] args) {
        RepeatedNumbersInArrays r = new RepeatedNumbersInArrays();
        int[] numbers = {2, 3, 1, 0, 2, 5, 3};
        int[] duplication = new int[1];
        boolean b = r.duplicate(numbers, numbers.length, duplication);
        System.out.println(b + "--" + Arrays.toString(duplication));
    }

}
