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

/**
 * Holds the variables for function evaluation. Should be the exact number that
 * the function is expecting
 */
public class FunctionArguments {
    private double[] args;

    public FunctionArguments(double[] args) {
        this.args = args;
    }

    public int getArgCount() {
        if(args == null)
            return 0;
        return args.length;
    }

    public double getArg(int index) {
        return args[index];
    }
}
