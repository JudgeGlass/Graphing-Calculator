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
import FileIO.Utils;
import Program.*;
import functions.Function;
import functions.FunctionArguments;
import functions.FunctionStore;
import functions.TokenizedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import Math.SigmaNotation;
import Math.RootSimp;

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
    private JButton btnQuadratic;
    private JButton btnStoreFunction;
    private JButton btnSolveFun;

    private JTextArea output;

    private GraphWindow window;
    private Graph graph;
    private ShapeDrawer shapeDrawer;
    private final Variable variable;

    private Font fontWide;

    private JTextField expression;
    private JTextField txtVar;
    private JLabel lblSigmaEnd;
    private JTextField txtSigmaEnd;
    private JLabel lblVar;
    private JLabel lblExpression;
    private JLabel lblMode;

    private JComboBox type;
    private JComboBox shape;
    private SaveSettings save;
    private TriangleWIndow triWindow;

    private boolean saveUsed = false;
    private double ANS = 0.0;

    public CalculatorWindow(){
        frame = new JFrame("Calculator [" + ApplicationInfo.VERSION + "]");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon(getClass().getResource("/Program/icons8-square-root-50.png"));
        frame.setIconImage(image.getImage());

        fontWide = new Font(Font.SANS_SERIF, Font.PLAIN, 11);

        preInit();

        graph.repaint2();
        frame.setVisible(true);

        variable = new Variable();

        writeText("Graphing Calculator v" + ApplicationInfo.VERSION);

        writeText("For help, type \"help\".");
        save = new SaveSettings("data.dat", window, graph);
        ApplicationInfo.STATIC_SAVE = save;
        triWindow = new TriangleWIndow(false, graph);

        if(ApplicationInfo.UNSTABLE_BUILD){
            writeText("\n***************** Warning! *****************");
            writeText("This is a debug version: " + ApplicationInfo.VERSION);
            writeText("This may contain errors.");
            writeText("***********************************************");
        }
    }

    private void preInit(){
        output = new JTextArea();
        output.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        output.setEditable(false);

        window = new GraphWindow(-10, 10, -10, 10, 770, 450);
        graph = new Graph(window, output);
        boolean contin = true;

        if(new File("data.dat").exists()){
            System.out.println("Save found! Using...");
            final String filename = "data.dat";

            if(!Utils.indexOf(Utils.readLine(filename, 0), '=').equals(ApplicationInfo.VERSION)){
                new File(filename).delete();
                JOptionPane.showMessageDialog(null, "Old save was deleted due to being an older version.",
                        "Old Save", JOptionPane.WARNING_MESSAGE);
                contin = false;
            }

            if(contin) {
                double xMin = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 2), '='));
                double xMax = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 3), '='));
                double yMin = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 4), '='));
                double yMax = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 5), '='));
                String deg = (Utils.indexOf(Utils.readLine(filename, 12), '='));

                if (deg.equals("true")) ApplicationInfo.useDegrees = true;

                window.xMax = xMax;
                window.xMin = xMin;
                window.yMax = yMax;
                window.yMin = yMin;

                window.resolution = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 1), '='));


                if (Utils.indexOf(Utils.readLine(filename, 6), '=').equals("true")) {
                    String y1 = Utils.indexOf(Utils.readLine(filename, 7), '=');
                    String y2 = Utils.indexOf(Utils.readLine(filename, 8), '=');
                    String y3 = Utils.indexOf(Utils.readLine(filename, 9), '=');
                    String y4 = Utils.indexOf(Utils.readLine(filename, 10), '=');
                    String y5 = Utils.indexOf(Utils.readLine(filename, 11), '=');


                    String[] values = {y1, y2, y3, y4, y5};
                    window.setFunction(values);
                    window.fh.store();

                }

                if (contin) {
                    GetScatterPlotSave gs = new GetScatterPlotSave("data.dat");
                    if (gs.getPoints().size() != 0 && gs.getPoints() != null)
                        graph.points = gs.getPoints();

                    GetDefinedFunctions.store("data.dat", gs.getLastLineIndex());
                    GetShapeSave.store("data.dat", GetDefinedFunctions.getLastIndex());


                    saveUsed = true;
                }
            }
        }

        graphPanel = graph;

        graphPanel.addMouseWheelListener(e -> {
            if(e.getWheelRotation() < 0){
                if(window.xMax - 1 < 1) {
                    return;
                }

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
        });

        tabs = new JTabbedPane();
        tabs.setBounds(5, 110, frame.getWidth() - 20, 650);

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
        expression.setActionCommand("ENTER");
        expression.addActionListener(new Listener());

        btnSolve = new JButton("Enter");
        btnSolve.setBounds(670, 85, 100, 25);
        btnSolve.setFont(fontWide);
        btnSolve.setActionCommand("ENTER");
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

        btnQuadratic = new JButton("Quadratic");
        btnQuadratic.setBounds(245, 85, 100, 25);
        btnQuadratic.setFont(fontWide);
        btnQuadratic.setActionCommand("QU");
        btnQuadratic.addActionListener(new Listener());

        btnStoreFunction = new JButton("Def. Function");
        btnStoreFunction.setBounds(140, 85, 100, 25);
        btnStoreFunction.setFont(fontWide);
        btnStoreFunction.setActionCommand("DEF");
        btnStoreFunction.addActionListener(new Listener());

        btnSolveFun = new JButton("Solve");
        btnSolveFun.setBounds(35, 85, 100, 25);
        btnSolveFun.setFont(fontWide);
        btnSolveFun.setActionCommand("SOLVE");
        btnSolveFun.addActionListener(new Listener());

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
        type.addItem("Sigma \u03A3");

        shape = new JComboBox();
        shape.setBounds(5, 53, 150, 25);
        shape.addItemListener(new ShapeType());
        shape.addItem("Triangle");
        shape.addItem("Circle");
        shape.addItem("Square");
        shape.addItem("Segment");
        shape.addItem("Label");

        shapeDrawer = new ShapeDrawer(new GraphWindow(-10, 10, -10, 10,770, 620), shape);

        tabs.addTab("Shape Drawer", shapeDrawer);

        addToFrame();
    }

    private void addToFrame(){
        frame.getContentPane().add(tabs);
        frame.getContentPane().add(lblMode);
        frame.getContentPane().add(type);
        frame.getContentPane().add(shape);
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
        frame.getContentPane().add(btnQuadratic);
        frame.getContentPane().add(btnStoreFunction);
        frame.getContentPane().add(btnSolveFun);
    }

    private void calculate(){
        writeText("> "+expression.getText());
        try {
            String eq = expression.getText();
            if(eq.contains("ANS")){
                eq = eq.replaceAll("ANS", Double.toString(ANS));
            }
            for(String x: Variable.getVars()){
                if(eq.contains(x)){
                    eq = eq.replaceAll(x, Double.toString(Variable.getValue(x)));
                }
            }
            if(eq.contains("->")){
                if(Variable.addVarFromString(eq)){
                    writeText("sto success!");
                }else{
                    writeText("sto failed!");
                }
                expression.setText("");
                return;
            }
            Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(eq), null);
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
                expression.setText("");
                return;
            }else if(expression.getText().equals("TW")){
                triWindow.show();
                expression.setText("");
                return;
            }else if(expression.getText().equals("MGR")){
                new VariableManager();
                expression.setText("");
                return;
            }else if(expression.getText().equals("DEL")){
                DeleteFunction.showDeleteWindow(save);
                expression.setText("");
                return;
            }else if(expression.getText().equals("SIMP")){
                int value = Integer.parseInt(JOptionPane.showInputDialog("Enter number to simplify:"));
                writeText(RootSimp.simplify(value));
                return;
            }else if(expression.getText().equals("LIST")){
                writeText("Functions:");
                for(String x: FunctionStore.getStore().getRawFunctions()){
                    writeText("> " + x);
                }
                expression.setText("");
                return;
            }else if(expression.getText().equals("DISABLE")){
                CorrectFunction.setEnabled(false);
                writeText("Correcting features disabled");
                expression.setText("");
                return;
            }else if(expression.getText().equals("ENABLE")){
                CorrectFunction.setEnabled(true);
                writeText("Correcting features enabled");
                expression.setText("");
                return;
            }
            if(command.equals("ENTER")){
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
                        graph.triLines.clear();
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
                new YWindow(window, save, output);
            }else if(command.equals("QU")){
                QuadraticFormulaDialog qf = new QuadraticFormulaDialog();
                writeText(qf.getZeros());
                writeText(qf.getLineOfSem());
                writeText(qf.getMinMax());
            }else if(command.equals("DEF")){
                String functionName = JOptionPane.showInputDialog(null, "Function name(eg. f1, f2, f3)", "Function name", JOptionPane.QUESTION_MESSAGE);
                if(functionName == null) return;
                if(FunctionStore.getStore().hasFunction(functionName)){
                    writeText("Name taken!");
                    return;
                }

                String function = JOptionPane.showInputDialog(null, "Function(eg. x^2+3x+2)", "Function", JOptionPane.QUESTION_MESSAGE);
                if(function == null) return;

                FunctionStore.getStore().storeFunction(functionName, function);
                save.update();

                writeText(function + " was saved to " + functionName);
            }else if(command.equals("SOLVE")){
                new SolveWindow();
            }
        }
    }

    private void help(){
        String[] actions = {
                "DEL - Delete function",
                "LIST - Get function list",
                "var->value - Store number in variable(eg. x->2.0)",
                "SIMP - Square root simplifier",
                "'(' and ')' are required",
                "sqrt(x) - Square root",
                "cbrt(x) - Cube root",
                "nthrt(num,root) - Nth root",
                "sin(x) - Sine",
                "cos(x) - Cosine",
                "tan(x) - Tangent",
                "asin(x) - Inverse Sine",
                "acos(x) - Inverse Cosine",
                "atan(x) - Inverse Tangent",
                "!(x) - Factorial",
                "ln(x) - Log (base 8)",
                "log(x) - Log (base 10)",
                "exp(x)",
                "abs(x) - Absolute value",
                "rand(min,max) - Random number",
                "ANS - Previous answer",
                "(π) - Pi"
        };
        writeText("<~~~~~~~~>");
        writeText("Help");
        for(final String x: actions){
            writeText(x);
        }
        writeText("<~~~~~~~~>");
    }

    private class ModeChange implements ItemListener{
        public void itemStateChanged(ItemEvent e) {
            if (type.getSelectedIndex() != 1) {
                lblExpression.setText("Expression");
                lblExpression.setFont(fontWide);
                lblVar.setVisible(false);
                txtVar.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }

            if (type.getSelectedIndex() != 2) {
                lblVar.setVisible(false);
                txtVar.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }

            if (type.getSelectedIndex() == 3) {
                lblVar.setVisible(true);
                lblVar.setText("n = ");
                lblSigmaEnd.setVisible(true);
                txtVar.setVisible(true);
                txtVar.setText("");
                txtSigmaEnd.setVisible(true);
            } else if (type.getSelectedIndex() != 3) {
                lblVar.setVisible(false);
                lblSigmaEnd.setVisible(false);
                txtVar.setVisible(false);
                txtSigmaEnd.setVisible(false);
            }
        }
    }

    private class ShapeType implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            int currentIndex = shape.getSelectedIndex();

            switch (currentIndex){
                case 0: //T //C // S
                    ApplicationInfo.currentShape = ApplicationInfo.Shape.TRIANGLE;
                    break;
                case 1:
                    ApplicationInfo.currentShape = ApplicationInfo.Shape.CIRCLE;
                    break;
                case 2:
                    ApplicationInfo.currentShape = ApplicationInfo.Shape.SQUARE;
                    break;
                case 3:
                    ApplicationInfo.currentShape = ApplicationInfo.Shape.SEGMENT;
                    break;
                case 4:
                    ApplicationInfo.currentShape = ApplicationInfo.Shape.LABEL;

            }
        }
    }
}
