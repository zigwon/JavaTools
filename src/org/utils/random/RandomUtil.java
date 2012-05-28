package org.utils.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 随机生成工具类
 * 
 * @author <a href="mailto:ziguang.wang@renren-inc.com">王紫光</a>
 * @version 1.0 2012-5-25 下午6:15:09
 * @since 1.0
 */
public class RandomUtil {
    
    private static final Random rand = new Random();
    
    private RandomUtil() {}
    
    /**
     * 50%的几率返回true
     * 
     * @return boolean
     */
    public static boolean nextBoolean() {
        return RandomUtils.nextBoolean();
    }
    
    /**
     * 根据传入的概率参数(0-100)返回true.<br>
     * 当传入100时，表示会100%返回true.<br>
     * 
     * @param retTrueRate   返回true的概率
     * @return  根据传入的概率返回true或false.
     */
    public static boolean nextBoolean(int retTrueRate) {
    	if(retTrueRate>=100) return true;
        if (retTrueRate < 0) return false;
        
        return retTrueRate >= (RandomUtils.nextInt(100) + 1);
    }
    
    /**
     * 从指定集合随机获取N个元素
     * 
     * @param <T>
     * @param des
     * @param num
     * @return
     */
    public static <T> List<T> randomList(List<T> des, int num) {
        if (des == null) {
            throw new NullPointerException("param must not be null.");
        }
        
        if (num <= 0 || des.size() < num) {
            throw new IllegalArgumentException("param num must less than the size of list.");
        }
        
        Collections.shuffle(des);
        List<T> ret = new ArrayList<T>();
        for (int i = 0; i < num; i++) {
            ret.add(des.get(i));
        }
        return ret;
    }
    
    
    /**
     * List 根据概率随机
     * 
     * @param list
     * @param rate
     * @return <T>
     */
    public static <T> T randomRate(List<T> list, List<Integer> rate) {
        if (list.size() != rate.size())
            throw new IllegalArgumentException("list's size must equal rate's size");
        
        int total = 0;
        for (int i = 0; i < rate.size(); i++) {
            total += rate.get(i);
        }
        int t = rand.nextInt(total);
        for (int i = 0; i < rate.size(); i++) {
            t = t - rate.get(i);
            if (t < 0) {
                return list.get(i);
            }
        }
        return null;
    }
    
    /**
     * 随机函数 rate
     * @param array
     * @param rate
     * @return Object
     */
    public Object randomRate(Object[] array , int[] rate) {
        if (array.length != rate.length)
            throw new IllegalArgumentException("array's length must equal rate's length");
        
        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return array[i];
            }
        }
        return 0;
    }
    
    /**
     * 根据map的概率取value
     * @param map <value,rate>
     * @return Object
     */
    public Object randomRate(Map<Object,Integer> map){
        int[] rate = new int[map.size()];
        Object[] value = new Integer[map.size()];
        int x = 0;
        for(Map.Entry<Object,Integer> entry :map.entrySet()){
            rate[x] = entry.getValue();
            value[x] = entry.getKey();
            x++;
        }
        return randomRate(value, rate);    
    }
    
    
    /**
     * 随机函数,随机概率相同
     * @param list
     * @return T
     */
    public static <T> T randomAverageRate(List<T> list) {
        List<Integer> rate = new ArrayList<Integer>();
        for(int i =0; i < list.size(); i++){
            rate.add(1);
        }
        return randomRate(list, rate);
    }
    
    
    /**
     * 随机函数,随机概率相同
     */
    public Object randomAverageRate(Object[] array) {
        int rate[] = new int[array.length];
        Arrays.fill(rate, 1);

        int total = 0;
        for (int i = 0; i < rate.length; i++) {
            total += rate[i];
        }
        int t = rand.nextInt(total);
        for (int i = 0; i < rate.length; i++) {
            t = t - rate[i];
            if (t < 0) {
                return array[i];
            }
        }
        return new Object();
    }
    
    
    /**
     * 产生某个范围内的随机数
     * @param min
     * @param max
     * @return
     */
    public static int randomRange(int min,int max){
        return rand.nextInt(max-min+1)+min;
    }
    
    
    /**
     * 随机取子list，去重
     * @param list 原始list
     * @param count 
     * @return List<T>
     */
    public <T> List<T> randomListNoRepeat(List<T> list, int count) {
        List<T> result = new ArrayList<T>();
        
        if(list.size() < count){
            return result;
        }else if(list.size() == count){
            return list;
        }
        
        for(int i = 0; i < count; i++){
            T value = randomAverageRate(list);
            result.add(value);
            list.remove(value);
        }
        
        return result;
    }
    
    
    /**
     * 随机取子list，去重
     * @param list 原始list
     * @param rate 
     * @param count 
     * @return List<T>
     */
    public <T> List<T> randomListNoRepeat(List<T> list, List<Integer> rate, int count) {
        List<T> result = new ArrayList<T>();
        
        if(list.size() < count){
            return result;
        }else if(list.size() == count){
            return list;
        }
        
        for(int i = 0; i < count; i++){
            T value = randomRate(list, rate);
            result.add(value);
            int index = list.indexOf(value);
            list.remove(index);
            rate.remove(index);
        }
        
        return result;
    }
    
    
    
    
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        
        for (int j = 0; j < 10; j++) {
            es.submit(new Runnable() {
                
                @Override
                public void run() {
                    int n = 0, count = 1000;
                    for (int i = 0; i < count; i++) {
                        if (nextBoolean(80)) {
                            n++;
                        }
                    }
                    System.out.println(n + "/" + count);
                }
            });
        }
        
    }
    
}
