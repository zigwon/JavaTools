package org.utils.elo;

import java.util.StringTokenizer;

import org.utils.elo.KFactor;



/**
 * @author <a href="mailto:ziguang.wang@renren-inc.com">王紫光</a>
 * @version 1.0 2012-6-8 上午11:05:42
 * @since 1.0
 */
public class SimpleEloRating {
    
    public static double DEFAULT_ELO_K_FACTOR = 24.0;
    public static String K_FACTOR_STRING="0-2099=32,2100-2399=24,2490-3000=16";

    public final static double WIN = 1.0;
    public final static double DRAW = 0.5;
    public final static double LOSE = 0.0;
    
    public final static int RESULT_WIN = 1;
    public final static int RESULT_LOSE = 2;
    public final static int RESULT_DRAW = 3;
    
    public KFactor [] kFactors = {};
    
    private static SimpleEloRating instance = new SimpleEloRating();
    
    private SimpleEloRating() {
        kFactorInit();
    }
    
    public static SimpleEloRating getInstance(){
        return instance;
    }
    
    
    private void kFactorInit(){
        if (K_FACTOR_STRING != null) {
            
            StringTokenizer st1 = new StringTokenizer (K_FACTOR_STRING, ",");
            kFactors = new KFactor [st1.countTokens()];
            
            int index = 0;
            while (st1.hasMoreTokens()) {
                String kfr = st1.nextToken();
                                
                StringTokenizer st2 = new StringTokenizer (kfr, "=");
                String range = st2.nextToken();
                                
                double value = Double.parseDouble (st2.nextToken());
                                
                st2 = new StringTokenizer (range, "-");
                int startIndex = Integer.parseInt(st2.nextToken());
                int endIndex   = Integer.parseInt(st2.nextToken());
                                
                kFactors [index++] = new KFactor (startIndex, endIndex, value);
            }
        }
    }
    
    public int getNewRating (int rating, int opponentRating, int resultType) {
        switch (resultType) {
            case RESULT_WIN:
                return getNewRating (rating, opponentRating, WIN);
            case RESULT_LOSE:
                return getNewRating (rating, opponentRating, LOSE);
            case RESULT_DRAW:
                return getNewRating (rating, opponentRating, DRAW);             
        }
        return -1;      
    }
    
    //新实力值 = 旧实力值+ K(胜负值 – 期望胜率)
    //期望胜率 =  1/(1+10^(dr/400)) 
    private int getNewRating(int rating, int opponentRating, double resultValue) {
        double kFactor       = getKFactor(rating);
        double expectedRate = getExpectedRate(rating, opponentRating);
        int    newRating     = calculateNewRating(rating, resultValue, expectedRate, kFactor);
        
        return newRating;
    }
    
    
    
    private double getKFactor (int rating) {
        for (int i = 0; i < kFactors.length; i++) 
            if (rating >= kFactors[i].getStartIndex() && rating <= kFactors[i].getEndIndex()) {
                return kFactors[i].getValue();
            }       
        return DEFAULT_ELO_K_FACTOR;
    }
    
    
    private double getExpectedRate (int rating, int opponentRating) {
        return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentRating - rating) / 400.0)));
    }
    
    private int calculateNewRating(int oldRating, double resultValue, double expectedRate, double kFactor) {
        return oldRating + (int) (kFactor * (resultValue - expectedRate));
    }
    
    
    public static void main(String[] args) {
        int a = 1000;
        for(int i = 0; i < 100; i++){
            a = getInstance().getNewRating(a,a,RESULT_WIN);
            System.out.println(a);
        }
    }
}
