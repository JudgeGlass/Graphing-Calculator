package UI;

import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunction;
import functions.TokenizedFunctionFactory;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CalculatorWindow {
    private JFrame frame;
    private JTabbedPane tabs;
    private JPanel graphPanel;
    private JButton btnSolve;
    private JButton btnOptions;
    private JButton btnAbout;
    private JButton btnClear;
    private JButton btnTabel;
    private JButton btnScatter;
    private JTextArea output;
    private GraphWindow window;
    private Graph graph;

    private Font fontWide;

    private JTextField expression;
    private JLabel lblExpression;
    private JLabel lblMode;
    private JComboBox type;

    public CalculatorWindow(){
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        fontWide = new Font(Font.SANS_SERIF, Font.PLAIN, 11);

        preInit();
        frame.setVisible(true);
        writeText("Ready");
    }

    private void preInit(){
        window = new GraphWindow(-10, 10, -10, 10, 770, 450);
        graph = new Graph("", window);

        graphPanel = graph;

        output = new JTextArea();
        output.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        output.setEditable(false);

        tabs = new JTabbedPane();
        tabs.setBounds(5, 110, 770, 450);

        tabs.addTab("Calculator", new JScrollPane(output));
        tabs.addTab("Graph", null,graphPanel,null);

        lblMode = new JLabel("Mode:");
        lblMode.setBounds(5, 5, 60, 15);

        btnScatter = new JButton("Scatter Plot");
        btnScatter.setBounds(335, 85, 120, 25);
        btnScatter.setActionCommand("SCATTER");
        btnScatter.addActionListener(new Listener());

        lblExpression = new JLabel("Expression:");
        lblExpression.setBounds(175, 5, 150, 15);
        lblExpression.setFont(fontWide);

        expression = new JTextField();
        expression.setBounds(175, 27, 470, 25);
        expression.setFont(fontWide);
        expression.setActionCommand("SOLVE");
        expression.addActionListener(new Listener());

        btnSolve = new JButton("Solve");
        btnSolve.setBounds(670, 85, 100, 25);
        btnSolve.setFont(fontWide);
        btnSolve.setActionCommand("SOLVE");
        btnSolve.addActionListener(new Listener());

        btnOptions = new JButton("Options");
        btnOptions.setBounds(670, 50, 100, 25);
        btnOptions.setFont(fontWide);
        btnOptions.setActionCommand("OPTIONS");
        btnOptions.addActionListener(new Listener());

        btnAbout = new JButton("About");
        btnAbout.setBounds(670, 15, 100, 25);
        btnAbout.setFont(fontWide);
        btnAbout.setActionCommand("ABOUT");
        btnAbout.addActionListener(new Listener());

        btnClear = new JButton("Clear");
        btnClear.setBounds(565, 85, 100, 25);
        btnClear.setFont(fontWide);
        btnClear.setActionCommand("CLEAR");
        btnClear.addActionListener(new Listener());

        btnTabel = new JButton("Table");
        btnTabel.setBounds(465, 85, 100, 25);
        btnTabel.setFont(fontWide);
        btnTabel.setActionCommand("TABLE");
        btnTabel.addActionListener(new Listener());

        type = new JComboBox();
        type.setBounds(5, 23, 150, 25);
        type.addItemListener(new ModeChange());
        type.addItem("Calculator");
        type.addItem("Graph");
        type.addItem("Scatter Plot");

        frame.getContentPane().add(tabs);
        frame.getContentPane().add(lblMode);
        frame.getContentPane().add(type);
        frame.getContentPane().add(lblExpression);
        frame.getContentPane().add(expression);
        frame.getContentPane().add(btnSolve);
        frame.getContentPane().add(btnOptions);
        frame.getContentPane().add(btnAbout);
        frame.getContentPane().add(btnClear);
        frame.getContentPane().add(btnTabel);
        frame.getContentPane().add(btnScatter);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabs.repaint();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.repaint();
            }
        });
    }

    private void calculate(){
        writeText(expression.getText());
        try {
            Function f = TokenizedFunctionFactory.createFunction(expression.getText(), null);
            writeText("ANS: " + Double.toString(f.evaluate(new FunctionArguments(null))));
        }catch (RuntimeException e){
            writeText("Syntax Error: " + e.getMessage());
        }
        expression.setText("");
    }

    private void writeText(final String text){
        output.append(text + "\n");
    }

    private class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            final String command = e.getActionCommand();
            if(expression.getText().equals("help")){
                help();
                return;
            }

            if(command.equals("SOLVE")){
                if(type.getSelectedIndex() == 0){
                    calculate();
                }else if(type.getSelectedIndex() == 1 || type.getSelectedIndex() == 2){
                    graph.function = expression.getText();
                }
            }else if(command.equals("OPTIONS")){
                new GraphOptions(window);
            }else if(command.equals("ABOUT")){
                JOptionPane.showMessageDialog(frame, "Copyright (c) 2018 Hunter Wilcox\nLibraries:\n- Java AWT API\n- (Modified)Functions (https://github.com/Nitori-/GraphingCalculator)", "About", JOptionPane.INFORMATION_MESSAGE);
            }else if(command.equals("CLEAR")){
                if(type.getSelectedIndex() == 0){
                    int clear = JOptionPane.showConfirmDialog(null, "Do you really want to clear the calculator output?", "Clear?", JOptionPane.YES_NO_OPTION);
                    if(clear == JOptionPane.YES_OPTION)
                        output.setText("");
                }else if(type.getSelectedIndex() == 1){
                    int clear = JOptionPane.showConfirmDialog(null, "Do you really want to clear the graph?", "Clear?", JOptionPane.YES_NO_OPTION);
                    if(clear == JOptionPane.YES_OPTION)
                        graph.function = "";
                }
            }else if(command.equals("TABLE")){
                if(graph.getFunction() != null || !graph.function.isEmpty())
                    new Table(window, graph.getFunction());
                else
                    JOptionPane.showMessageDialog(null, "A graph has not been set.", "Error", JOptionPane.ERROR_MESSAGE);
            }else if(command.equals("SCATTER")){
                new ScatterPlot(graph.points);
            }
        }
    }

    private void help(){
        String[] actions = {
                "(sqrt(x))",
                "(cbrt(x))",
                "(sin(x))",
                "(cos(x))",
                "(tan(x))",
                "(asin(x))",
                "(acos(x))",
                "(atan(x))",
                "(!(x))",
                "(ln(x))",
                "(exp(x))"
        };
        writeText("########");
        writeText("Help");
        for(int i = 0; i<actions.length;i++){
            writeText(actions[i]);
        }
        writeText("########");
    }

    private class ModeChange implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            if(type.getSelectedIndex() == 2){
                btnScatter.setVisible(true);
            }else{
                btnScatter.setVisible(false);
            }

            if(type.getSelectedIndex() == 1){
                lblExpression.setText("f(x)=");
                tabs.setSelectedIndex(1);
            }else{
                lblExpression.setText("Expression");
            }
        }
    }
}
