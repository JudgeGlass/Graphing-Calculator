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

package UI;

import Program.CorrectFunction;
import Program.ErrorCodes;
import functions.Function;
import functions.MalformedFunctionException;
import functions.TokenizedFunctionFactory;

import java.util.ArrayList;

public class CheckFunction {
    public static boolean isGood(final String function){
        if(function == null || function.isEmpty()) return false;
        try{
            ArrayList<String> vars = new ArrayList<>();
            vars.add("y");
            vars.add("x");
            Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.fix(function), vars);
        }catch (MalformedFunctionException e){
            ErrorCodes.printErrorCode(ErrorCodes.FUNCTION_ERROR);
            return false;
        }
        return true;
    }
}
