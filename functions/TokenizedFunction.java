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
import java.util.Iterator;

/**
 * Stores a complex (as in complicated) function as a list of Tokens, evaluated
 * in RPN fasion
 */
public class TokenizedFunction implements Function {
    private int variables;

    /** Must be stored in RPN format */
    private ArrayList<Token> tokens;

    /**
     * Don't use directly. Use TokenizedFunctionFactory. tokens must be in RPN
     * format
     */
    TokenizedFunction(ArrayList<Token> tokens, int vars) {
        this.tokens = tokens;
        variables = vars;
    }

    /**
     * Evaluates the function using an RPN stack-based approach.
     * 
     * Throws an IllegalArgumentException if the number of arguments does not
     * match the number in the function. Throws MalformedFunctionException if
     * the function is not properly formed for evaluation.
     */
    public double evaluate(FunctionArguments args)
            throws IllegalArgumentException, MalformedFunctionException {
        if ((args == null && variables != 0)
                || (args != null && args.getArgCount() != variables)) {
            throw new IllegalArgumentException("Wrong argument count");
        }

        ArrayList<Double> stack = new ArrayList<Double>();
        Iterator<Token> iter = tokens.iterator();
        do {
            Token next = iter.next();

            if (next instanceof ConstantToken) {
                stack.add(((ConstantToken) next).getValue());
            } else if (next instanceof VariableToken) {
                stack.add(args.getArg(((VariableToken) next).getID()));
            } else if (next instanceof FunctionToken) {
                Function f = ((FunctionToken) next).getFunction();
                if (stack.size() < f.getArgCount()) {
                    throw new MalformedFunctionException(
                            "Improper argument count");
                }

                double[] d = new double[f.getArgCount()];

                for (int i = 0; i < f.getArgCount(); i++) {
                    d[d.length - i - 1] = stack.remove(stack.size() - 1);
                }

                stack.add(f.evaluate(new FunctionArguments(d)));
            } else {
                throw new MalformedFunctionException("Unkown token type");
            }
        } while (iter.hasNext());

        if (stack.size() != 1) {
            throw new MalformedFunctionException(
                    "Function was not properly formed");
        }
        return stack.get(0);
    }

    public int getArgCount() {
        return variables;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Token> i = tokens.iterator();
        while (i.hasNext()) {
            Token t = i.next();
            if (t instanceof FunctionToken) {
                sb.append(((FunctionToken) t).getFunction().toString());
            } else if (t instanceof VariableToken) {
                sb.append("x" + ((VariableToken) t).getID());
            } else if (t instanceof ConstantToken) {
                sb.append(((ConstantToken) t).getValue());
            }
            sb.append("  ");
        }

        return sb.toString();
    }
}
