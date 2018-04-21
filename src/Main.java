import UI.CalculatorWindow;

import javax.swing.*;
import java.util.ArrayList;

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
