/*
 * Copyright 2012 Justin Wilcox
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
package functions;

import java.util.ArrayList;

/** A TokenizedFunctionFactory test driver */
public class TokenParseTest {
    public static void main(String[] args) {
        String expr = "-8.5*x-3*-(-4.5^(1+ln(4*y))-8.0*(1--5))";
        ArrayList<String> vars = new ArrayList<String>();
        vars.add("y");
        vars.add("x");

        double[] arg = new double[2];
        arg[0] = 1.0;
        arg[1] = 2.0;

        System.out.println(TokenizedFunctionFactory.createFunction(expr, vars)
                .evaluate(new FunctionArguments(arg)));
    }
}
