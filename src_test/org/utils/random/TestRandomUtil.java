package org.utils.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.base.BaseTestCase;



public class TestRandomUtil extends BaseTestCase{
    
    public void testRandomAverageRate() throws Exception{
        int threadNumber = 10;  
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        
        final List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 100; i++){
            list.add(i);
        }
        final int[] arr = new int[100];
        Arrays.fill(arr, 0);
        
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int j = 0; j < threadNumber; j++) {
            final int threadID = j;
            
            es.submit(new Runnable() {
                @Override
                public void run() {
                    int  count = 10000;
                    for (int i = 0; i < count; i++) {
                        int res = RandomUtil.randomAverageRate(list);
                        arr[res] += 1;
                        //System.out.print(res + ",");
                    }
                    System.out.println(String.format("threadID:[%s] finished!!", threadID));
                    countDownLatch.countDown();
                }
            });
        }
        
        countDownLatch.await();
        for(int a : arr){
            System.out.print(a + ",");
        }
    }
}
