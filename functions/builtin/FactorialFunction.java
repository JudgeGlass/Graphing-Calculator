package functions.builtin;

// BY HUNTER WILCOX

import functions.Function;
import functions.FunctionArguments;

public class FactorialFunction implements Function {
    public double evaluate(FunctionArguments args) {
        long result = 1;

        for (int factor = 2; factor <= args.getArg(0); factor++) {
            result *= factor;
        }

        return (double) result;
    }

    public int getArgCount() {
        return 1;
    }
}
