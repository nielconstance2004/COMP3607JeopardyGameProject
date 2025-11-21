package com.Scoring;
public class BooleanScore implements Scoring{

    @Override
    public int calScore(Question question , String userAns){
        if ( question.getAnswer() == userAns){
            return question.getValue;
        }
        return 0;

    }
}