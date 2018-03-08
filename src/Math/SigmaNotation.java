package Math;

import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import java.util.ArrayList;

public class SigmaNotation {
    private String expression;

    public double solve(String expression, double start, double end){
        this.expression = expression;
        ArrayList<String> vars = new ArrayList<>();
        vars.add("y");
        vars.add("n");
        Function f = null;

        try {
            f = TokenizedFunctionFactory.createFunction(expression, vars); // Initializes the function
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        double[] arg = new double[2]; // Sets the first arguments
        arg[0] = 0.0;

        double value = 0;
        for(double counter = start; counter <= end;counter++){
            arg[1] = counter;
            value += f.evaluate(new FunctionArguments(arg));
        }
        return value;
    }

    public String toString(){
        return expression;
    }
}
