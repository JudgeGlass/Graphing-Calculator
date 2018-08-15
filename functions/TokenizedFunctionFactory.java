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
import java.util.StringTokenizer;

public class TokenizedFunctionFactory {
    /**
     * Creates a new TokenizedFunction using the shunting-yard algorithm.
     * 
     * @param expression
     *            An infix-style expression to parse
     * @param vars
     *            A list of variables, in order of appearance for evaluation.
     */
    public static Function createFunction(String expression,
            ArrayList<String> vars) {
        ArrayList<String> parts = breakupExpression(expression);
        ArrayList<Token> tokens = shuntingYard(parts, vars);

        return new TokenizedFunction(tokens, (vars != null) ? vars.size() : 0);
    }

    /**
     * Performs Dijkstra's Shunting-Yard algorithm to convert a tokenized, infix
     * ordered expression into an RPN style list, for use in evaluation of a
     * TokenizedFunction. See:
     * http://en.wikipedia.org/wiki/Shunting_yard_algorithm
     */
    private static ArrayList<Token> shuntingYard(ArrayList<String> parts,
            ArrayList<String> vars) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        ArrayList<String> stack = new ArrayList<String>();

        replaceUnaryMinus(parts);

        for (int i = 0; i < parts.size(); i++) {
            String current = parts.get(i).trim();

            if (isNumber(current)) {
                try {
                tokens.add(new ConstantToken(Double.parseDouble(current)));
                } catch (NumberFormatException e) {
                    throw new MalformedFunctionException("Unknown token " + current + "\n");
                }
            } else if (!isOperator(current)
                    && FunctionStore.getStore().hasFunction(current)) {
                stack.add(current);
            } else if (current.equals(",")) {
                while (!stack.get(stack.size() - 1).equals("(")) {
                    tokens.add(new FunctionToken(FunctionStore.getStore()
                            .getFunction(stack.remove(stack.size() - 1))));
                }
            } else if (isOperator(current)) {
                while (true) {
                    if (stack.size() == 0) {
                        break;
                    }

                    String o2 = stack.get(stack.size() - 1);
                    if ((getAssociativity(current) == -1 && getPrecedence(current) <= getPrecedence(o2))
                            || (getAssociativity(current) == 1 && getPrecedence(current) < getPrecedence(o2))) {

                        tokens.add(new FunctionToken(FunctionStore.getStore()
                                .getFunction(o2)));
                        stack.remove(stack.size() - 1);
                    } else {
                        break;
                    }

                }

                stack.add(current);
            } else if (current.equals("(")) {
                stack.add(current);
            } else if (current.equals(")")) {
                try {
                    while (!stack.get(stack.size() - 1).equals("(")) {
                        tokens.add(new FunctionToken(FunctionStore.getStore()
                                .getFunction(stack.get(stack.size() - 1))));
                        stack.remove(stack.size() - 1);
                    }
                    stack.remove(stack.size() - 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new MalformedFunctionException(
                            "Mismatched parenthesis");
                }
            } else { // We had better be a variable

                boolean found = false;

                if (vars == null) {
                    throw new MalformedFunctionException("Unresolved Symbol '"
                            + current + "'");
                }

                int j = 0;
                for (j = 0; j < vars.size(); j++) {
                    if (vars.get(j).equals(current)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    throw new MalformedFunctionException("Unresolved Symbol '"
                            + current + "'");
                }

                tokens.add(new VariableToken(j));
            }
        }

        while (stack.size() != 0) {
            tokens.add(new FunctionToken(FunctionStore.getStore().getFunction(
                    stack.get(stack.size() - 1))));
            stack.remove(stack.size() - 1);
        }

        return tokens;
    }

    /**
     * Replaces unary '-' with '_' to disambiguate it from the binary '-' used
     * for subtraction
     */
    private static void replaceUnaryMinus(ArrayList<String> parts) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).equals("-")) {
                if (i == 0) { // Leading - of an expression is unary
                    parts.set(i, "_");
                } else {
                    if (isOperator(parts.get(i - 1)) // Unary if the preceeding
                                                     // char is an operator
                            || parts.get(i - 1).equals("(") // or a paren
                            || parts.get(i - 1).equals(",")) { // or a comma
                        parts.set(i, "_");
                    }
                }
            }
        }
    }

    private static boolean isOperator(String s) {
        return (s.equals("+") || s.equals("-") || s.equals("_")
                || s.equals("^") || s.equals("*") || s.equals("/"));
    }

    private static int getPrecedence(String s) {
        switch (s.charAt(0)) {
        case '(':
        case ')':
            return 1;
        case '_': // Unary -
            return 4;
        case '*':
        case '/':
            return 3;
        case '+':
        case '-':
            return 2;
        case '^':
            return 5;
        default:
            return 0;
        }
    }

    /**
     * The associativity of an operator affects order of operations, and how
     * terms are grouped
     * 
     * @return -1 for left, +1 for right
     */
    private static int getAssociativity(String s) {
        if (s.equals("_") || s.equals("^")) {
            return 1;
        } else {
            return -1;
        }
    }

    private static boolean isNumber(String num) {
        char ch = num.charAt(0);
        if (Character.isDigit(ch))
            return true;
        return false;
    }

    /**
     * Tokenizes an infix expression into a list of Strings
     */
    private static ArrayList<String> breakupExpression(String expression) {
        ArrayList<String> parts = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(expression, "+-/*^(),", true);
        while (st.hasMoreTokens()) {
            parts.add(st.nextToken());
        }

        return parts;
    }
}
