package functions.builtin;

// BY HUNTER WILCOX

import functions.Function;
import functions.FunctionArguments;

public class PiFunction implements Function {
    public double evaluate(FunctionArguments args) {
        return 3.141592;
    }

    public int getArgCount() {
        return 0;
    }

    public String toString() {
        return "π";
    }
}
