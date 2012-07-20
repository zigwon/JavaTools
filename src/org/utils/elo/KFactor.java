package org.utils.elo;


public class KFactor {
    private int startIndex, endIndex;
    private double value;
    
    public KFactor (int startIndex, int endIndex, double value) {
        this.startIndex = startIndex;
        this.endIndex   = endIndex;
        this.value      = value;
    }
    public int getStartIndex () { return startIndex; }
    public int getEndIndex ()   { return endIndex; }
    public double getValue ()      { return value; }
    
    public String toString () { 
        return "kfactor: " + startIndex + " " + endIndex + " " + value;
    }
}
