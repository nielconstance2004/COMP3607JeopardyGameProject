
package com.Scoring;
public class ScoringContext {
    private Scoring method;

    public ScoringContext (Scoring method){
        this.method=method;
    }

    public void setScoring(Scoring method){
        this.method = method;
    }

    public int preformCal(Question question , String userAns){
        return (method.calScore( question , userAns));
    }
}