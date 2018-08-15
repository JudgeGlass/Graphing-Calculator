package functions.builtin;

import functions.Function;
import functions.FunctionArguments;

public class Log10Function implements Function {
    public double evaluate(FunctionArguments args) {
        return Math.log10(args.getArg(0));
    }

    public int getArgCount() {
        return 1;
    }

    public String toString() {
        return "log";
    }
}
