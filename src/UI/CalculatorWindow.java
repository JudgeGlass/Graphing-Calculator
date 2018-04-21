package UI;

import FileIO.SaveSettings;
import FileIO.Utils;
import Program.ApplicationInfo;
import Program.GetScatterPlotSave;
import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
    private JButton btnYE;

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
    private SaveSettings save;

    private boolean saveUsed = false;
    private double ANS = 0.0;

    public CalculatorWindow(){
        frame = new JFrame("Calculator [" + ApplicationInfo.VERSION + "]");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        fontWide = new Font(Font.SANS_SERIF, Font.PLAIN, 11);

        preInit();
        frame.setVisible(true);
        if(saveUsed)
            writeText("Save found! Using...");
        writeText("Ready. Type \"help\" for a list of operators.");
        save = new SaveSettings("data.dat", window, graph);
    }

    private void preInit(){
        window = new GraphWindow(-10, 10, -10, 10, 770, 450);
        graph = new Graph(window);

        if(new File("data.dat").exists()){
            System.out.println("Save found! Using...");
            double xMin = Double.parseDouble(Utils.indexOf(Utils.readLine("data.dat", 0), '='));
            double xMax = Double.parseDouble(Utils.indexOf(Utils.readLine("data.dat", 1), '='));
            double yMin = Double.parseDouble(Utils.indexOf(Utils.readLine("data.dat", 2), '='));
            double yMax = Double.parseDouble(Utils.indexOf(Utils.readLine("data.dat", 3), '='));

            window.xMax = xMax;
            window.xMin = xMin;
            window.yMax = yMax;
            window.yMin = yMin;

            if(Utils.indexOf(Utils.readLine("data.dat", 4), '=').equals("true")){
                String y1 = Utils.indexOf(Utils.readLine("data.dat", 5), '=');
                String y2 = Utils.indexOf(Utils.readLine("data.dat", 6), '=');
                String y3 = Utils.indexOf(Utils.readLine("data.dat", 7), '=');

                String[] values = {y1, y2, y3};
                window.setFunction(values);
            }
            GetScatterPlotSave gs = new GetScatterPlotSave("data.dat");
            if(gs.getPoints().size() != 0 && gs.getPoints() != null)
                graph.points = gs.getPoints();

            saveUsed = true;
        }

        graphPanel = graph;

        graphPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation() < 0){
                    if(window.xMax - 1 < 1)
                        return;

                    window.xMin++;
                    window.xMax--;
                    window.yMin++;
                    window.yMax--;
                    graph.repaint2();
                }else{
                    window.xMin--;
                    window.xMax++;
                    window.yMin--;
                    window.yMax++;
                    graph.repaint2();
                }
            }
        });

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
        btnScatter.setBounds(350, 85, 100, 25);
        btnScatter.setActionCommand("SCATTER");
        btnScatter.setFont(fontWide);
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
        btnTable.setBounds(460, 85, 100, 25);
        btnTable.setFont(fontWide);
        btnTable.setActionCommand("TABLE");
        btnTable.addActionListener(new Listener());

        btnYE = new JButton("Y=");
        btnYE.setBounds(350, 50, 100, 25);
        btnYE.setFont(fontWide);
        btnYE.setActionCommand("Y");
        btnYE.addActionListener(new Listener());

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
        btnZoomOut.setBounds(460, 50, 100, 25);
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
        frame.getContentPane().add(btnYE);
    }

    private void calculate(){
        writeText("> "+expression.getText());
        try {
            String eq = expression.getText();
            if(eq.contains("ANS")){
                eq = eq.replaceAll("ANS", "(" + Double.toString(ANS) + ")");
            }
            Function f = TokenizedFunctionFactory.createFunction(eq, null);
            double answer = f.evaluate(new FunctionArguments(null));
            writeText("ANS: " + Double.toString(answer));
            ANS = answer;

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
                if(type.getSelectedIndex() == 3){
                    double value = new SigmaNotation().solve(expression.getText(), Double.parseDouble(txtVar.getText()),
                            Double.parseDouble(txtSigmaEnd.getText()));
                    writeText("Sigma ANS:"+Double.toString(value));
                }else{
                   calculate();
                }
            }else if(command.equals("OPTIONS")){
                new GraphOptions(window, graph, save);
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
                        window.fh.clearFunctionHolder();
                    }
                }
            }else if(command.equals("TABLE")){
                new Table(window);
            }else if(command.equals("SCATTER")){
                new ScatterPlot(graph.points, graph, window.fh, save);
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
            }else if(command.equals("Y")){
                new YWindow(window, save);
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
                "ANS - Previous answer",
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
            if(type.getSelectedIndex() != 1){
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
