package functions.builtin;

import functions.Function;
import functions.FunctionArguments;

import java.util.Random;

public class RandomFunction implements Function {
    public double evaluate(FunctionArguments args) {
        return (int)(Math.random() * args.getArg(1) + (args.getArg(0)));
    }

    public int getArgCount() {
        return 2;
    }

    public String toString() {
        return "rand";
    }
}