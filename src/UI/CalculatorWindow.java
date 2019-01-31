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
import functions.*;

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
    private ListManager listManager;

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

    private JPanel menuPanel;

    private boolean saveUsed = false;
    private double ANS = 0.0;

    private int windowWidth = 800;
    private int windowHeight = 800;

    public CalculatorWindow(){
        frame = new JFrame("Calculator [" + ApplicationInfo.VERSION + "]");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                windowWidth = frame.getWidth();
                windowHeight = frame.getHeight();

                if(window != null){
                    window.pixelWidth = windowWidth - 30;
                    window.pixelHeight = windowHeight - 360;
                }

                if(shapeDrawer != null){
                    shapeDrawer.window.pixelWidth = windowWidth - 30;
                    shapeDrawer.window.pixelHeight = windowHeight - 180;
                    shapeDrawer.window.rescale();
                    shapeDrawer.redraw2();
                }
            }
        });


        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        //frame.setLayout(null);
        frame.setResizable(true);
        ImageIcon image = new ImageIcon(getClass().getResource("/Program/icons8-square-root-50.png"));
        frame.setIconImage(image.getImage());

        fontWide = new Font(Font.SANS_SERIF, Font.PLAIN, 11);

        Variable.initVar();

        preInit();

        graph.repaint2();
        frame.setVisible(true);

        //variable = new Variable();

        writeText("Graphing Calculator v" + ApplicationInfo.VERSION);

        writeText("For help, type \"help\".");
        save = new SaveSettings("data.dat", window, graph, listManager);
        ApplicationInfo.STATIC_SAVE = save;

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
        listManager = new ListManager();
        boolean contin = true;

        if(new File("data.dat").exists()){
            Log.info("Save found! Using...");
            final String filename = "data.dat";

            if(!Utils.indexOf(Utils.readLine(filename, 0), '=').equals(ApplicationInfo.VERSION)){
                Log.warn("Old save found. This will be deleted");
                new File(filename).delete();
                JOptionPane.showMessageDialog(null, "Old save was deleted due to being an older version.",
                        "Old Save", JOptionPane.WARNING_MESSAGE);
                contin = false;
            }

            if(contin) {
                Log.info("Getting program settings...");
                double xMin = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 2), '='));
                double xMax = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 3), '='));
                double yMin = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 4), '='));
                double yMax = Double.parseDouble(Utils.indexOf(Utils.readLine(filename, 5), '='));
                String deg = (Utils.indexOf(Utils.readLine(filename, 12), '='));

                if (deg.equals("true")) ApplicationInfo.useDegrees = true;

                listManager.stringToList(Utils.indexOf(Utils.readLine(filename, 13), '='), ListManager.Lists.L1);
                listManager.stringToList(Utils.indexOf(Utils.readLine(filename, 14), '='), ListManager.Lists.L2);
                listManager.stringToList(Utils.indexOf(Utils.readLine(filename, 15), '='), ListManager.Lists.L3);

                String xList = Utils.indexOf(Utils.readLine(filename, 16), '=');
                String yList = Utils.indexOf(Utils.readLine(filename, 17), '=');

                if(xList.equals("L1")){
                    listManager.xList = ListManager.Lists.L1;
                }else if(xList.equals("L2")){
                    listManager.xList = ListManager.Lists.L2;
                }else{
                    listManager.xList = ListManager.Lists.L3;
                }

                if(yList.equals("L1")){
                    listManager.yList = ListManager.Lists.L1;
                }else if(yList.equals("L2")){
                    listManager.yList = ListManager.Lists.L2;
                }else{
                    listManager.yList = ListManager.Lists.L3;
                }



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

                    try {
                        window.fh.store();
                    }catch (MalformedFunctionException e){
                        Log.error("Failed to write functions.");
                        ErrorCodes.errorDialog(ErrorCodes.READ_ERROR, "Please delete the 'data.dat' file!");
                        System.exit(-1);
                    }

                }

                if (contin) {
                    Log.info("Checking / getting scatter plot data");

                    Log.info("Getting user defined functions");
                    GetDefinedFunctions.store("data.dat", 19);
                    Log.info("Getting shape data");
                    GetShapeSave.store("data.dat", GetDefinedFunctions.getLastIndex());


                    for(int i = 0; i < listManager.getLargestDataList(); i++){
                        if(i > listManager.getXList().size() || i > listManager.getYList().size()){break;}
                        if(listManager.getXList().get(i) != null && listManager.getYList().get(i) != null){
                            graph.points.add(new PointD(listManager.getXList().get(i), listManager.getYList().get(i)));
                        }
                    }


                    saveUsed = true;
                }
            }
        }

        Log.info("Setting up panel items...");

        graphPanel = graph;

        graphPanel.addMouseWheelListener(e -> {
            if(e.getWheelRotation() < 0){
                zoomIn();
            }else{
                zoomOut();
            }
        });

        tabs = new JTabbedPane();
        tabs.setBounds(5, 110, frame.getWidth() - 20, 650);

        tabs.addTab("Calculator", new JScrollPane(output));
        tabs.addTab("Graph", null, graphPanel,null);


        menuPanel = new JPanel();



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

        shapeDrawer = new ShapeDrawer(new GraphWindow(-10, 10, -10, 10,770, 450), shape);

        tabs.addTab("Shape Drawer", shapeDrawer);

        addToFrame();
    }

    private void addToFrame(){
        Log.info("Adding panel items to frame");
        menuPanel.setLayout(null);
        menuPanel.setPreferredSize(new Dimension(700, 120));
        //menuPanel.setBounds(0, 0, 750, 300);

        frame.add(tabs, BorderLayout.CENTER);
        menuPanel.add(lblMode);
        menuPanel.add(type);
        menuPanel.add(shape);
        menuPanel.add(lblExpression);
        menuPanel.add(expression);
        menuPanel.add(btnSolve);
        menuPanel.add(btnOptions);
        menuPanel.add(btnAbout);
        menuPanel.add(btnClear);
        menuPanel.add(btnTable);
        menuPanel.add(btnScatter);
        menuPanel.add(lblVar);
        menuPanel.add(txtVar);
        menuPanel.add(btnZoomIn);
        menuPanel.add(btnZoomOut);
        menuPanel.add(lblSigmaEnd);
        menuPanel.add(txtSigmaEnd);
        menuPanel.add(btnYE);
        menuPanel.add(btnQuadratic);
        menuPanel.add(btnStoreFunction);
        menuPanel.add(btnSolveFun);

        frame.getContentPane().add(menuPanel, BorderLayout.PAGE_START);
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
            Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.fix(eq), null);
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
        output.setCaretPosition(output.getDocument().getLength());
    }

    private class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            final String command = e.getActionCommand();
            if(expression.getText().equals("help")){
                help();
                expression.setText("");
                return;
            }else if(expression.getText().equals("MEM")){
                new MEM(save);
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
                    }
                }
            }else if(command.equals("TABLE")){
                new Table(window);
            }else if(command.equals("SCATTER")){
                new ScatterPlot(graph, window.fh, save, listManager, graph.points);
            }else if(command.equals("ZOOM_IN")){
                zoomIn();
            }else if(command.equals("ZOOM_OUT")){
                zoomOut();
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
                "MEM - Memory Manager",
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
                "round(x) - Rounds a number up or down",
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

    private void zoomOut(){
        window.xMin--;
        window.xMax++;
        window.yMin--;
        window.yMax++;
        graph.repaint2();
    }

    private void zoomIn(){
        if(window.xMax - 1 < 1)
            return;
        window.xMin++;
        window.xMax--;
        window.yMin++;
        window.yMax--;
        graph.repaint2();
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

    private class ShapeType implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            int currentIndex = shape.getSelectedIndex();

            switch (currentIndex) {
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
