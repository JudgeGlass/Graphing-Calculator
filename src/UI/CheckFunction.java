package UI;

import Program.CorrectFunction;
import functions.Function;
import functions.FunctionArguments;
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
            Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(function), vars);
        }catch (MalformedFunctionException e){
            return false;
        }
        return true;
    }
}
