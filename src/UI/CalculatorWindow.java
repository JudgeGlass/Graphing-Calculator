package UI;

import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import Math.SigmaNotation;

public class CalculatorWindow {
    private JFrame frame;
    private JTabbedPane tabs;
    private JPanel graphPanel;

    private JButton btnSolve;
    private JButton btnOptions;
    private JButton btnAbout;
    private JButton btnClear;
    private JButton btnTable;
    private JButton btnScatter;
    private JButton btnZoomIn;
    private JButton btnZoomOut;

    private JTextArea output;

    private GraphWindow window;
    private Graph graph;

    private Font fontWide;

    private JTextField expression;
    private JTextField txtVar;
    private JLabel lblSigmaEnd;
    private JTextField txtSigmaEnd;
    private JLabel lblVar;
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
        writeText("Ready. Type \"help\" for a list of operators.");
    }

    private void preInit(){
        window = new GraphWindow(-10, 10, -10, 10, 770, 450);
        graph = new Graph("", window);

        graphPanel = graph;

        output = new JTextArea();
        output.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        output.setEditable(false);

        tabs = new JTabbedPane();
        tabs.setBounds(5, 110, frame.getWidth() - 20, 450);

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
        expression.setBounds(175, 25, 470, 25);
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

        btnTable = new JButton("Table");
        btnTable.setBounds(465, 85, 100, 25);
        btnTable.setFont(fontWide);
        btnTable.setActionCommand("TABLE");
        btnTable.addActionListener(new Listener());

        lblVar = new JLabel("Var:");
        lblVar.setBounds(175, 60, 50, 15);
        lblVar.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        txtVar = new JTextField();
        txtVar.setBounds(200, 55, 50, 25);
        txtVar.setText("x");
        //txtVar.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));

        lblSigmaEnd = new JLabel("End:");
        lblSigmaEnd.setBounds(250, 60, 30, 15);
        lblSigmaEnd.setVisible(false);

        txtSigmaEnd = new JTextField();
        txtSigmaEnd.setBounds(280, 55, 50, 25);
        txtSigmaEnd.setFont(fontWide);
        txtSigmaEnd.setVisible(false);

        btnZoomIn = new JButton("Zoom +");
        btnZoomIn.setBounds(565, 50, 100, 25);
        btnZoomIn.setFont(fontWide);
        btnZoomIn.setActionCommand("ZOOM_IN");
        btnZoomIn.addActionListener(new Listener());

        btnZoomOut = new JButton("Zoom -");
        btnZoomOut.setBounds(465, 50, 100, 25);
        btnZoomOut.setFont(fontWide);
        btnZoomOut.setActionCommand("ZOOM_OUT");
        btnZoomOut.addActionListener(new Listener());

        type = new JComboBox();
        type.setBounds(5, 23, 150, 25);
        type.addItemListener(new ModeChange());
        type.addItem("Calculator");
        type.addItem("Graph");
        type.addItem("Scatter Plot");
        type.addItem("Sigma");

        addToFrame();
    }

    private void addToFrame(){
        frame.getContentPane().add(tabs);
        frame.getContentPane().add(lblMode);
        frame.getContentPane().add(type);
        frame.getContentPane().add(lblExpression);
        frame.getContentPane().add(expression);
        frame.getContentPane().add(btnSolve);
        frame.getContentPane().add(btnOptions);
        frame.getContentPane().add(btnAbout);
        frame.getContentPane().add(btnClear);
        frame.getContentPane().add(btnTable);
        frame.getContentPane().add(btnScatter);
        frame.getContentPane().add(lblVar);
        frame.getContentPane().add(txtVar);
        frame.getContentPane().add(btnZoomIn);
        frame.getContentPane().add(btnZoomOut);
        frame.getContentPane().add(lblSigmaEnd);
        frame.getContentPane().add(txtSigmaEnd);
    }

    private void calculate(){
        writeText("> "+expression.getText());
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
                    graph.vars = new ArrayList<>();
                    graph.vars.add("y");
                    graph.vars.add(txtVar.getText());
                    graph.function = expression.getText();
                    graph.repaint2();
                }else if(type.getSelectedIndex() == 3){
                    double value = new SigmaNotation().solve(expression.getText(), Double.parseDouble(txtVar.getText()),
                            Double.parseDouble(txtSigmaEnd.getText()));
                    writeText("Sigma ANS:"+Double.toString(value));
                }
            }else if(command.equals("OPTIONS")){
                new GraphOptions(window, graph);
            }else if(command.equals("ABOUT")){
                new About();
            }else if(command.equals("CLEAR")){
                if(type.getSelectedIndex() == 0){
                    int clear = JOptionPane.showConfirmDialog(null, "Do you really want to clear the calculator output?", "Clear?", JOptionPane.YES_NO_OPTION);
                    if(clear == JOptionPane.YES_OPTION)
                        output.setText("");
                }else if(type.getSelectedIndex() == 1 || type.getSelectedIndex() == 2){
                    int clear = JOptionPane.showConfirmDialog(null, "Do you really want to clear the graph?", "Clear?", JOptionPane.YES_NO_OPTION);
                    if(clear == JOptionPane.YES_OPTION) {
                        graph.function = "";
                        graph.points.clear();
                    }
                }
            }else if(command.equals("TABLE")){
                if(graph.getFunction() != null || !graph.function.isEmpty())
                    new Table(window, graph.getFunction());
                else
                    JOptionPane.showMessageDialog(null, "A graph has not been set.", "Error", JOptionPane.ERROR_MESSAGE);
            }else if(command.equals("SCATTER")){
                new ScatterPlot(graph.points, graph);
            }else if(command.equals("ZOOM_IN")){
                if(window.xMax - 1 < 1)
                    return;

                window.xMin++;
                window.xMax--;
                window.yMin++;
                window.yMax--;
                graph.repaint2();
            }else if(command.equals("ZOOM_OUT")){
                window.xMin--;
                window.xMax++;
                window.yMin--;
                window.yMax++;
                graph.repaint2();
            }
        }
    }

    private void help(){
        String[] actions = {
                "'(' and ')' are required",
                "(sqrt(x)) - Square root",
                "(cbrt(x)) - Cube root",
                "(sin(x)) - Sine",
                "(cos(x)) - Cosine",
                "(tan(x)) - Tangent",
                "(asin(x)) - ASine",
                "(acos(x)) - ACosine",
                "(atan(x)) - ATangent",
                "(!(x)) - Factorial",
                "(ln(x)) - log",
                "(exp(x))",
                "(abs(x)) - Absolute value",
                "(π) - Pi"
        };
        writeText("<~~~~~~~~>");
        writeText("Help");
        for(String x: actions){
            writeText(x);
        }
        writeText("<~~~~~~~~>");
    }

    private class ModeChange implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            if(type.getSelectedIndex() == 1){
                lblExpression.setText("f(x)=");
                lblVar.setText("Var:");
                lblExpression.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
                lblVar.setVisible(true);
                txtVar.setVisible(true);
                txtVar.setText("x");
                tabs.setSelectedIndex(1);
                btnScatter.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtSigmaEnd.setVisible(false);
                return;
            }else if(type.getSelectedIndex() != 1){
                lblExpression.setText("Expression");
                lblExpression.setFont(fontWide);
                lblVar.setVisible(false);
                txtVar.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }

            if(type.getSelectedIndex() == 2){
                lblVar.setVisible(true);
                txtVar.setVisible(true);
                txtVar.setText("x");
                btnScatter.setVisible(true);
                lblSigmaEnd.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }else if(type.getSelectedIndex() != 2){
                btnScatter.setVisible(false);
                lblVar.setVisible(false);
                txtVar.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }

            if(type.getSelectedIndex() == 3){
                lblVar.setVisible(true);
                lblVar.setText("n = ");
                lblSigmaEnd.setVisible(true);
                txtVar.setVisible(true);
                txtVar.setText("");
                txtSigmaEnd.setVisible(true);
            }else if(type.getSelectedIndex() != 3){
                lblVar.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtVar.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }
        }
    }
}
