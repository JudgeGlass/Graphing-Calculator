import FileIO.Utils;
import Program.CorrectFunction;
import UI.CalculatorWindow;
import functions.Function;
import functions.FunctionStore;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.util.ArrayList;


public class Main {
    public static void main(String args[]){
        //System.out.println(CorrectFunction.addPer("sqrt(2+2^2)"));
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CalculatorWindow();
    }
}
