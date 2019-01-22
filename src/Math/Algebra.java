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

import Program.CorrectFunction;
import UI.PointD;
import com.sun.istack.internal.NotNull;
import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import java.util.ArrayList;

public class Algebra {

    // TODO Make a method of finding a line intersection.
    public static ArrayList<PointD> linearIntersection(final String f1, final String f2){
        double y2 = calc(f1, 0); // Y-interc.
        double y1 = calc(f1, 3);
        double slopef1 = -(y2 - y1) / 3;

        double yy2 = calc(f2, 0); // Y-interc.
        double yy1 = calc(f2, 3);
        double slopef2 = -(yy2 - yy1) / 3;

        double top = yy2 - y2;
        double bottom = slopef2 - slopef1;


        ArrayList<PointD> points = new ArrayList<>();
        points.add(new PointD((-top / bottom), calc(f1, (-top/bottom))));

        if(f1.contains("x^2") || f2.contains("x^2")){
            points.add(new PointD((top / bottom), calc(f1, (top/bottom))));
        }

        return points;
    }

    /***
     * Calculate the y-value of a given x-value.
     *
     * @param func The math function
     * @param x The x-value
     * */

    public static double calc(@NotNull final String func, double x){
        ArrayList<String> vars = new ArrayList<>();
        vars.add("y");
        vars.add("x");

        Function function = TokenizedFunctionFactory.createFunction(CorrectFunction.fix(func), vars);

        double[] args = new double[2];
        args[0] = 0.0;
        args[1] = x;

        return function.evaluate(new FunctionArguments(args));
    }


}
