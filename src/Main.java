import UI.CalculatorWindow;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Main {
    public static void main(String args[]){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new CalculatorWindow();
    }
}
