package functions.builtin;

// BY HUNTER WILCOX

import functions.Function;
import functions.FunctionArguments;

public class RoundFunction implements Function {
    public double evaluate(FunctionArguments args) {
        return Math.round(args.getArg(0));
    }

    public int getArgCount() {
        return 1;
    }
}