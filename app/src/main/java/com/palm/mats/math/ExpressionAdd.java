package com.palm.mats.math;

import android.util.Log;

import static com.palm.mats.math.Operator.*;

/**
 * Created by mats on 2018-01-06.
 */

public class ExpressionAdd extends Expression {

    public ExpressionAdd() {
        op = PLUS;
        operatorSymbol = "+";
        setMode();
    }

    @Override
    public String toString() {

        return( String.format("%d + %d = %d", xval, yval, zval) );

    }

    public void setMode() {
        xval = genRand(xmax);
        yval = genRand(ymax);
        zval = xval + yval;
    }

}
