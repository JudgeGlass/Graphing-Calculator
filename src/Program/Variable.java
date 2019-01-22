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
package Program;

import functions.FunctionStore;

import java.util.ArrayList;
import java.util.HashMap;

public class Variable {
    private static HashMap<String, Double> values;
    private static ArrayList<String> vars;

    public static void initVar(){
        values = new HashMap<>();
        vars = new ArrayList<>();
    }

    /***
     * Stores a numeric value into a letter variable
     * e.g. x->9; x=9
     *
     * @param function The string data
     * */

    public static boolean addVarFromString(final String function){
        if(function.contains("->")){
            String[] split = function.split("->");
            String var = split[0];
            double value = Double.parseDouble(split[1]);
            if(FunctionStore.getStore().getRawFunctions().contains(var)){
                System.out.println("Cannot use variable name");
                return false;
            }
            addVar(var, value);
            System.out.println("Added: " + var + "=" + value);
        }
        return true;
    }

    /***
     * Stores the variable.
     *
     * @param var The variable name
     * @param value The variable value
     * */

    private static void addVar(final String var, final double value){
        vars.add(var);
        values.put(var, value);
    }

    public static double getValue(String var){
        return values.get(var);
    }

    public static ArrayList<String> getVars(){
        return vars;
    }
}
