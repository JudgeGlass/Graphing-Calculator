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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YWindow {
    private JDialog frame;

    private JTextField txtY1;
    private JTextField txtY2;
    private JTextField txtY3;

    private JLabel lblY1;
    private JLabel lblY2;
    private JLabel lblY3;

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
        frame.setSize(240, 240);
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

        if(graphWindow.fh != null){
            if(graphWindow.fh.y1 != null){
                txtY1.setText(graphWindow.fh.y1);
            }
            if(graphWindow.fh.y2 != null){
                txtY2.setText(graphWindow.fh.y2);
            }if(graphWindow.fh.y3 != null){
                txtY3.setText(graphWindow.fh.y3);
            }
        }

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(15, 175, 100, 25);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnCancel);

        btnOK = new JButton("OK");
        btnOK.setBounds(120, 175, 100, 25);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isOrder()){
                    JOptionPane.showMessageDialog(null, "Put functions in order!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FunctionStore.getStore().removeFunction("y1");
                FunctionStore.getStore().removeFunction("y2");
                FunctionStore.getStore().removeFunction("y3");
                String[] fs = new String[]{txtY1.getText(), txtY2.getText(), txtY3.getText()};
                graphWindow.setFunction(fs);

                boolean y1Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y1));
                boolean y2Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y2));
                boolean y3Good = CheckFunction.isGood(CorrectFunction.addMul(graphWindow.fh.y3));

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

                graphWindow.fh.store();

                frame.dispose();
                save.update();
            }
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






