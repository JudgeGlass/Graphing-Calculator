import UI.CalculatorWindow;

import javax.swing.*;
import Math.ExpRegression;


public class Main {
    public static void main(String args[]){
        double x[] = {0, 1, 2, 3, 4, 5};
        double y[] = {3, 7, 10, 24, 50, 95};

        System.out.println(new ExpRegression(x, y).getFunction());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CalculatorWindow();
    }
}
