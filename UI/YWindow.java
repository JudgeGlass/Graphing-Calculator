/*
 * Copyright 2018 Hunter Wilcox
 * 
 * This file is part of GraphingCalculator.
 *
 * GraphingCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GraphingCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GraphingCalculator.  If not, see <http://www.gnu.org/licenses/>.
 */

package UI;

import FileIO.SaveSettings;
import Program.CorrectFunction;
import functions.FunctionStore;

import javax.swing.*;
import java.awt.*;

public class YWindow {
    private JDialog frame;

    private JTextField txtY1;
    private JTextField txtY2;
    private JTextField txtY3;
    private JTextField txtY4;
    private JTextField txtY5;

    private JLabel lblY1;
    private JLabel lblY2;
    private JLabel lblY3;
    private JLabel lblY4;
    private JLabel lblY5;

    private JButton btnOK;
    private JButton btnCancel;

    private GraphWindow graphWindow;
    private SaveSettings save;
    private JTextArea output;

    public YWindow(GraphWindow window, SaveSettings save, JTextArea output){
        this.save = save;
        this.output = output;
        graphWindow = window;
        frame = new JDialog();
        frame.setTitle("Y=");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setModal(true);
        frame.setSize(240, 320);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        init();
        frame.setVisible(true);
    }

    private void init(){
        lblY1 = new JLabel("Y1= ");
        lblY1.setBounds(5, 5, 70, 15);
        frame.getContentPane().add(lblY1);

        txtY1 = new JTextField();
        txtY1.setBounds(5, 25, 215, 25);
        frame.getContentPane().add(txtY1);

        lblY2 = new JLabel("Y2= ");
        lblY2.setBounds(5, 55, 60, 15);
        frame.getContentPane().add(lblY2);

        txtY2 = new JTextField();
        txtY2.setBounds(5, 75, 215, 25);
        frame.getContentPane().add(txtY2);

        lblY3 = new JLabel("Y3= ");
        lblY3.setBounds(5, 105, 60, 15);
        frame.getContentPane().add(lblY3);

        txtY3 = new JTextField();
        txtY3.setBounds(5, 125, 215, 25);
        frame.getContentPane().add(txtY3);

        lblY4 = new JLabel("Y4= ");
        lblY4.setBounds(5, 155, 60, 15);
        frame.getContentPane().add(lblY4);

        txtY4 = new JTextField();
        txtY4.setBounds(5, 175, 215, 25);
        frame.getContentPane().add(txtY4);

        lblY5 = new JLabel("Y5= ");
        lblY5.setBounds(5, 205, 60, 15);
        frame.getContentPane().add(lblY5);

        txtY5 = new JTextField();
        txtY5.setBounds(5, 225, 215, 25);
        frame.getContentPane().add(txtY5);

        if(graphWindow.fh != null){
            if(graphWindow.fh.y1 != null){
                txtY1.setText(graphWindow.fh.y1);
            }
            if(graphWindow.fh.y2 != null){
                txtY2.setText(graphWindow.fh.y2);
            }
            if(graphWindow.fh.y3 != null){
                txtY3.setText(graphWindow.fh.y3);
            }
            if(graphWindow.fh.y4 != null){
                txtY4.setText(graphWindow.fh.y4);
            }
            if(graphWindow.fh.y5 != null){
                txtY5.setText(graphWindow.fh.y5);
            }
        }

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(15, 255, 100, 25);
        btnCancel.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(btnCancel);

        btnOK = new JButton("OK");
        btnOK.setBounds(120, 255, 100, 25);
        btnOK.addActionListener(e -> {
            FunctionStore.getStore().removeFunction("y1");
            FunctionStore.getStore().removeFunction("y2");
            FunctionStore.getStore().removeFunction("y3");
            FunctionStore.getStore().removeFunction("y4");
            FunctionStore.getStore().removeFunction("y5");
            String[] fs = new String[]{txtY1.getText(), txtY2.getText(), txtY3.getText(), txtY4.getText(), txtY5.getText()};
            graphWindow.setFunction(fs);

            boolean y1Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y1));
            boolean y2Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y2));
            boolean y3Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y3));
            boolean y4Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y4));
            boolean y5Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y5));


            if(!y1Good && !graphWindow.fh.y1.isEmpty()){
                graphWindow.fh.y1 = "";
                output.append("Y1: Syntax Error\n");
            }
            if(!y2Good && !graphWindow.fh.y2.isEmpty()) {
                graphWindow.fh.y2 = "";
                output.append("Y2: Syntax Error\n");
            }
            if(!y3Good && !graphWindow.fh.y3.isEmpty()){
                graphWindow.fh.y3 = "";
                output.append("Y3: Syntax Error\n");
            }
            if(!y4Good && !graphWindow.fh.y4.isEmpty()){
                graphWindow.fh.y4 = "";
                output.append("Y4: Syntax Error\n");
            }
            if(!y5Good && !graphWindow.fh.y5.isEmpty()){
                graphWindow.fh.y5 = "";
                output.append("Y5: Syntax Error\n");
            }

            graphWindow.fh.store();

            frame.dispose();
            save.update();
        });
        frame.getContentPane().add(btnOK);
    }

    private boolean isOrder(){
        if(txtY1.getText().isEmpty() && txtY2.getText().isEmpty() && txtY3.getText().isEmpty()){
            return true;
        }

        if(!txtY1.getText().isEmpty() && txtY2.getText().isEmpty() && txtY3.getText().isEmpty()){
            return true;
        }else if(!txtY1.getText().isEmpty() && !txtY2.getText().isEmpty() && txtY3.getText().isEmpty()){
            return true;
        }else if(!txtY1.getText().isEmpty() && !txtY2.getText().isEmpty() && !txtY3.getText().isEmpty()){
            return true;
        }

        return false;
    }
}






