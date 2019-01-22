/*
 * Copyright 2018 Hunter Wilcox
 *
 * This file is part of GraphingCalculator.
 *
 * GraphingCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GraphingCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GraphingCalculator.  If not, see <http://www.gnu.org/licenses/>.
 */

package Math;

import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import java.util.ArrayList;

public class SigmaNotation {
    private String expression;


    /**
     * @param expression the function(eg. n^2)
     * @param start count start(eg. 0)
     * @param  end end amount(eg. 5)
     * @return
     */
    public double solve(final String expression, final double start, final double end){
        this.expression = expression;
        ArrayList<String> vars = new ArrayList<>();
        vars.add("y");
        vars.add("n");
        Function f = null;

        try {
            f = TokenizedFunctionFactory.createFunction(expression, vars); // Initializes the function
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        double[] arg = new double[2]; // Sets the first arguments
        arg[0] = 0.0;

        double value = 0;
        for(double counter = start; counter <= end;counter++){
            arg[1] = counter;
            value += f.evaluate(new FunctionArguments(arg));
        }
        return value;
    }

    public String toString(){
        return expression;
    }
}
