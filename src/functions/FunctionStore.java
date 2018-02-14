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

import java.util.HashMap;

import functions.builtin.*;

/**
 * Storage mechanism for all builtin and defined functions
 */
public class FunctionStore {
    // Singleton
    private static FunctionStore inst = new FunctionStore();

    /** Maps string ID's to the function objects */
    private HashMap<String, Function> functions;

    private FunctionStore() {
        rebuildStore();
    }

    /**
     * Allows for the function store to be reset to it's default state
     */
    public void rebuildStore() {
        functions = new HashMap<String, Function>();

        // All the basic functions a calculator needs
        storeFunction("+", new AddFunction());
        storeFunction("-", new SubtractFunction());
        storeFunction("*", new MultiplyFunction());
        storeFunction("/", new DivideFunction());
        storeFunction("^", new PowFunction());
        storeFunction("_", new NegateFunction()); // Unary -
        storeFunction("sqrt", new SquareRootFunction());
        storeFunction("exp", new ExpFunction());
        storeFunction("ln", new LogFunction());
        storeFunction("sin", new SineFunction());
        storeFunction("cos", new CosineFunction());
        storeFunction("tan", new TangentFunction());
        storeFunction("asin", new ASineFunction());
        storeFunction("acos", new ACosineFunction());
        storeFunction("atan", new ATangentFunction());
        storeFunction("atan2", new ATangent2Function());
        storeFunction("cbrt", new CubeRootFunction()); // Added by Hunter Wilcox
        storeFunction("!", new FactorialFunction()); // Added by Hunter Wilcox
        storeFunction("abs", new AbsFunction()); // Added by Hunter Wilcox
    }

    public static FunctionStore getStore() {
        return inst;
    }

    /**
     * Returns null if the function wasn't found
     */
    public Function getFunction(String id) {
        return functions.get(id);
    }

    /**
     * This will overwrite any function with 'id' that is already present,
     * including built-in functions
     */
    public void storeFunction(String id, Function f) {
        functions.put(id, f);
    }

    /**
     * Returns true if the given id corresponds to a stored function
     */
    public boolean hasFunction(String id) {
        return functions.containsKey(id);
    }
}
