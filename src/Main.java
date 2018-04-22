import Program.CorrectFunction;
import UI.CalculatorWindow;
import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import javax.swing.*;


public class Main {
    public static void main(String args[]){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CalculatorWindow();
    }
}
