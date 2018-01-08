package com.palm.mats.math;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mats on 2018-01-06.
 */

public class Game {

    float numberOfQuestions;
    float numberOfCorrectQuestions;
    int level;
    GameMode gameMode;
    ExpressionFactory  expressionFactory;
    private Expression expression;
    ArrayList<Operator> operatorList;

    public Game (int numberOfQuestions, int level, ArrayList<Operator> operatorList)
    {
        this.operatorList = operatorList;
        this.numberOfQuestions = numberOfQuestions;
        expressionFactory = new ExpressionFactory();
    }

    public String getOperand() {
        return expression.getOperatorSymbol();
    }

    public void newExpression() {

        Random random = new Random();
        int listSize = operatorList.size();
        int randomIndex = random.nextInt(listSize);
        expression = expressionFactory.getExpression(operatorList.get(randomIndex));


    }

    public void increaseCorrect() {
        numberOfCorrectQuestions++;
    }


    public float getRate() {
        return (numberOfCorrectQuestions/numberOfQuestions)*5;
    }

    @Override
    public String toString() {
        return(expression.toString());
    }




    public GameMode getGameMode() {return expression.getGameMode();}
    public int getX() {
        return expression.getX();
    }
    public int getY() {
        return expression.getY();
    }
    public int getZ() {
        return expression.getZ();
    }





}
