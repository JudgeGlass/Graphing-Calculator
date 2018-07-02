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
    private JFrame frame;
    private JPanel panel;

    private JLabel lblValue;
    private JLabel lblAns;
    private JTextField txtValue;
    private JTextField txtAns;
    private JComboBox functionList;
    private JButton btnOK;

    public SolveWindow(){
        frame = new JFrame("Solve");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300, 220);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

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

        btnOK = new JButton("OK");
        btnOK.setBounds(100, 145, 100, 30);
        btnOK.addActionListener((ActionEvent) ->{
            try {
                final String selectedFunction = FunctionStore.getStore().getFunctionsIdByIndex(functionList.getSelectedIndex());
                ArrayList<String> vars = new ArrayList<>();
                vars.add("y");
                vars.add("x");
                Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(FunctionStore.getStore().getHashFunctions()
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
