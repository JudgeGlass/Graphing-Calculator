package UI;

import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunction;
import functions.TokenizedFunctionFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculatorWindow {
    private JFrame frame;
    private JTabbedPane tabs;
    private JPanel calculaorPanel;
    private JPanel graphPanel;
    private JPanel graphControlPanel;
    private JButton btnSolve;
    private JButton btnOptions;
    private GraphWindow window;
    private Graph graph;

    private Font fontWide;

    private JTextField expression;
    private JLabel lblExpression;
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
    }

    private void preInit(){
        calculaorPanel = new JPanel();

        window = new GraphWindow(-10, 10, -10, 10, 770, 450);
        graph = new Graph("", window);

        graphPanel = graph;

        tabs = new JTabbedPane();
        tabs.setBounds(5, 100, 770, 450);

        tabs.addTab("Calculator", calculaorPanel);
        tabs.addTab("Graph", null,graphPanel,null);

        type = new JComboBox();
        type.setBounds(5, 5, 150, 25);
        type.addItem("Calculator");
        type.addItem("Graph");

        lblExpression = new JLabel("Expression:");
        lblExpression.setBounds(175, 5, 150, 15);
        lblExpression.setFont(fontWide);

        expression = new JTextField();
        expression.setBounds(175, 27, 300, 25);

        btnSolve = new JButton("Solve");
        btnSolve.setBounds(670, 90, 100, 20);
        btnSolve.setFont(fontWide);
        btnSolve.setActionCommand("SOLVE");
        btnSolve.addActionListener(new Listener());

        btnOptions = new JButton("Graph Options");
        btnOptions.setBounds(670, 60, 100, 20);
        btnOptions.setFont(fontWide);

        frame.getContentPane().add(tabs);
        frame.getContentPane().add(type);
        frame.add(lblExpression);
        frame.add(expression);
        frame.add(btnSolve);
        frame.add(btnOptions);

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
        ArrayList<String> vars = new ArrayList<>();
        vars.add("y");

        Function f = TokenizedFunctionFactory.createFunction(expression.getText(), null);
        double[] a = new double[2];
        

        System.out.println(f.evaluate(new FunctionArguments(null)));
    }

    class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            final String command = e.getActionCommand();

            if(command.equals("SOLVE")){
                if(type.getSelectedIndex() == 0){
                    calculate();
                }else if(type.getSelectedIndex() == 1){
                    graph.function = expression.getText();
                }
            }
        }
    }
}
