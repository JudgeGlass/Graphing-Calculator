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

/** ExpRegression class for TokenizedFunctions */
public class TokenizedFunctionTestDrive {
    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new ConstantToken(1.0));
        tokens.add(new ConstantToken(2.0));
        tokens.add(new VariableToken(0));
        tokens.add(new FunctionToken(FunctionStore.getStore().getFunction("^")));
        tokens.add(new FunctionToken(FunctionStore.getStore().getFunction("-")));

        Function f = new TokenizedFunction(tokens, 1);
        double[] arg1 = new double[1];
        arg1[0] = 1.0;

        System.out.println("Eval: " + f.evaluate(new FunctionArguments(arg1)));

        System.out.println("F: " + f);
    }

}
