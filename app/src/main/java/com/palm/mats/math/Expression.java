package com.palm.mats.math;

import android.util.Log;

import java.util.Random;

/**
 * Created by mats on 2018-01-06.
 */

public abstract class Expression {

    protected int xval = -1;
    protected int xmax = 10;
    protected int yval = -1;
    protected int ymax = 10;
    protected int zval = -1;
    protected int zmax = 10;

    protected GameMode mode;

    protected static Random random = new Random();
    protected String operatorSymbol;
    protected int OPERAND_LEVEL_STEP = 5;
    protected Operator op = Operator.PLUS;
    protected int level;


    public Expression() {
        mode = GameMode.values()[random.nextInt(GameMode.values().length)];
        Log.d("mapa", "Mode " + mode.toString());
    }

    public void setLevel(int lev) {
        level = lev;
        xmax = level * OPERAND_LEVEL_STEP;
        ymax = level * OPERAND_LEVEL_STEP;
    }


    public int genRand(int max) {
        int randomNum = random.nextInt((max) + 1) + 1;
        return randomNum;
    }

    public int getX() {
        return xval;
    }

    public int getY() {
        return yval;
    }

    public int getZ() {
        return zval;
    }

    public GameMode getGameMode() {
        return mode;
    }

    public String getOperatorSymbol() {
        return operatorSymbol;
    }

    public abstract void setMode();


}

