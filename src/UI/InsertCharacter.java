package UI;

import javax.swing.*;

public class InsertCharacter {
    private JTextField txt;

    public InsertCharacter(JTextField txt){
        this.txt = txt;
    }

    public void insert(){
        String x = JOptionPane.showInputDialog(null, "");
    }
}
