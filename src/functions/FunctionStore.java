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
import java.util.HashMap;

import Program.CorrectFunction;
import functions.builtin.*;

/**
 * Storage mechanism for all builtin and defined functions
 */
public class FunctionStore {
    // Singleton
    private static FunctionStore inst = new FunctionStore();

    /** Maps string ID's to the function objects */
    private HashMap<String, Function> functions;
    private HashMap<String, String> rawFunctions;

    private ArrayList<String> idNames = new ArrayList<>();

    private FunctionStore() {
        rebuildStore();
    }

    /**
     * Allows for the function store to be reset to it's default state
     */
    public void rebuildStore() {
        functions = new HashMap<String, Function>();
        rawFunctions = new HashMap<>();

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
        storeFunction("nthrt", new NthRootFunction()); // Added by Hunter Wilcox
        storeFunction("!", new FactorialFunction()); // Added by Hunter Wilcox
        storeFunction("abs", new AbsFunction()); // Added by Hunter Wilcox
        storeFunction("π", new PiFunction()); // Added by Hunter Wilcox
        storeFunction("log", new Log10Function()); // Added by Hunter Wilcox
        storeFunction("rand", new RandomFunction()); // Added by Hunter Wilcox
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
     * By Hunter Wilcox
     * **/
    public void storeFunction(String id, String function){
        if(hasFunction(id)){
            return;
        }

        ArrayList<String> vars = new ArrayList<>();
        vars.add("x");
        Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(function), vars);
        functions.put(id, f);
        idNames.add(id);
        rawFunctions.put(id, function);
    }

    /**
     * By Hunter Wilcox
     * **/
    public ArrayList<String> getOperators() {
        ArrayList<String> op = new ArrayList<>();
        op.add("sqrt");
        op.add("cbrt");
        op.add("sin");
        op.add("tan");
        op.add("cos");
        op.add("ln");
        op.add("asin");
        op.add("atan");
        op.add("acos");
        op.add("exp");
        op.add("abs");
        op.add("!");
        op.add("log");
        op.add("rand");
        op.add("nthrt");

        return op;
    }

    /**
     * By Hunter Wilcox
     * **/
    public void removeFunction(String id){
        if(idNames.contains(id) && functions.containsKey(id) && rawFunctions.containsKey(id)) {
            idNames.remove(id);
            functions.remove(id);
            rawFunctions.remove(id);
        }
    }

    /**
     * Returns true if the given id corresponds to a stored function
     */
    public boolean hasFunction(String id) {
        return functions.containsKey(id);
    }


    /**
     * By Hunter Wilcox
     * */
    public ArrayList<String> getRawFunctions(){
        return idNames;
    }

    public HashMap<String, String> getHashFunctions(){return rawFunctions;}

    public String getFunctionsIdByIndex(final int index){return idNames.get(index);}

    public ArrayList<String> getIdNames() {
        return idNames;
    }
}
