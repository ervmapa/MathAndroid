package com.palm.mats.math;

import static com.palm.mats.math.Operator.MINUS;

/**
 * Created by mats on 2018-01-08.
 */

public class ExpressionMinus extends Expression {

    public ExpressionMinus() {
        op = MINUS;
        operatorSymbol = "-";
        setMode();
    }

    @Override
    public String toString() {

        return( String.format("%d - %d = %d", xval, yval, zval) );

    }

    public void setMode() {
        xval = genRand(xmax);
        yval = genRand(ymax);
        zval = xval + yval;

        // Swap to avoid negative numbers
        if (xval - yval <0)
        {
            int tmp = xval;
            yval = xval;
            xval = yval;
        }

        zval = xval - yval;

    }

}
