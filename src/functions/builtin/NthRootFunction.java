package functions.builtin;

import functions.Function;
import functions.FunctionArguments;

public class NthRootFunction implements Function {
    public double evaluate(FunctionArguments args) {
        return Math.pow(Math.E, Math.log(args.getArg(0))/args.getArg(1));
    }

    public int getArgCount() {
        return 2;
    }
}
