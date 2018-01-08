package com.palm.mats.math;

import android.util.Log;

/**
 * Created by mats on 2018-01-06.
 */

public class ExpressionFactory {

        public  Expression getExpression(Operator op)
        {
            switch (op)
            {
                case PLUS:
                    return new ExpressionAdd();
                case MINUS:
                    return new ExpressionMinus();

                case MULT:
                    return null;
                case DIV:
                    return null;
                default:
                    return new ExpressionAdd();

            }


        }
}
