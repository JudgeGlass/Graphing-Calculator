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
package functions.builtin;

import Program.ApplicationInfo;
import functions.Function;
import functions.FunctionArguments;

public class TangentFunction implements Function {
    public double evaluate(FunctionArguments args) {
        if(ApplicationInfo.useDegrees){
            return Math.tan(Math.toRadians(args.getArg(0)));
        }

        return Math.tan(args.getArg(0));
    }

    public int getArgCount() {
        return 1;
    }

    public String toString() {
        return "tan";
    }
}
