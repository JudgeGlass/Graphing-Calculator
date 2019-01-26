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

import Program.CorrectFunction;
import functions.Function;
import functions.FunctionArguments;
import functions.FunctionStore;
import functions.TokenizedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SolveWindow {
    private JDialog frame;
    private JPanel panel;

    private JLabel lblValue;
    private JLabel lblAns;
    private JTextField txtValue;
    private JTextField txtAns;
    private JComboBox functionList;
    private JButton btnOK;

    public SolveWindow(){
        frame = new JDialog();
        frame.setTitle("Solve");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300, 220);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        panel = new JPanel();
        frame.add(panel);

        init();

        frame.setVisible(true);
    }

    private void init(){
        functionList = new JComboBox();
        functionList.setBounds(0, 0, 295, 30);
        frame.getContentPane().add(functionList);

        for(final String function: FunctionStore.getStore().getRawFunctions()){
            functionList.addItem(function + "= " + FunctionStore.getStore().getHashFunctions().get(function));
        }

        lblValue = new JLabel("Value:");
        lblValue.setBounds(0, 35, 200, 15);
        frame.getContentPane().add(lblValue);

        txtValue = new JTextField();
        txtValue.setBounds(0, 55, 295, 30);
        frame.getContentPane().add(txtValue);

        lblAns = new JLabel("Answer:");
        lblAns.setBounds(0, 90, 200, 15);
        frame.getContentPane().add(lblAns);

        txtAns = new JTextField();
        txtAns.setEditable(false);
        txtAns.setBounds(0, 110, 295, 30);
        frame.getContentPane().add(txtAns);

        btnOK = new JButton("Enter");
        btnOK.setBounds(100, 145, 100, 30);
        btnOK.addActionListener((ActionEvent) ->{
            try {
                final String selectedFunction = FunctionStore.getStore().getFunctionsIdByIndex(functionList.getSelectedIndex());
                ArrayList<String> vars = new ArrayList<>();
                vars.add("y");
                vars.add("x");
                Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.fix(FunctionStore.getStore().getHashFunctions()
                        .get(selectedFunction)), vars);

                double[] args = new double[2];
                args[0] = 0.0;
                args[1] = Double.parseDouble(txtValue.getText());

                txtAns.setText(Double.toString(f.evaluate(new FunctionArguments(args))));
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.getContentPane().add(btnOK);
    }
}
