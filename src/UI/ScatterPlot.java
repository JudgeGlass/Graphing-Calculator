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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import FileIO.SaveSettings;
import Math.LinearRegression;
import Math.FunctionHolder;
import Math.PolynomialRegression;
import Math.ExpRegression;

public class ScatterPlot {
    private JDialog frame;
    private JPanel panel;

    private JList xList;
    private JList yList;

    private DefaultListModel xModel;
    private DefaultListModel yModel;

    private JTextField txtX;
    private JTextField txtY;

    private JLabel lblX;
    private JLabel lblY;
    private JLabel lblTX;
    private JLabel lblTY;

    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnOK;
    private JButton btnRegression;

    private ArrayList<Double> x = new ArrayList<>();
    private ArrayList<Double> y = new ArrayList<>();

    private ArrayList<PointD> points;
    private Graph graph;

    private SaveSettings save;
    private FunctionHolder holder;

    public ScatterPlot(ArrayList<PointD> points, Graph graph, FunctionHolder holder, SaveSettings save){
        this.points = points;
        this.graph = graph;
        this.save = save;
        this.holder = holder;

        frame = new JDialog();
        frame.setTitle("Scatter Plot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(225, 420);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        panel = new JPanel();
        frame.add(panel);

        init();
        frame.setVisible(true);
    }

    private void init(){
        xModel = new DefaultListModel();
        yModel = new DefaultListModel();

        xList = new JList(xModel);
        yList = new JList(yModel);

        lblX = new JLabel("X");
        lblX.setBounds(5, 5, 50, 15);
        frame.getContentPane().add(lblX);

        xList.setBounds(5, 25, 100, 200);
        frame.getContentPane().add(xList);

        lblY = new JLabel("Y");
        lblY.setBounds(110, 5, 50, 15);
        frame.getContentPane().add(lblY);

        yList.setBounds(110, 25, 100, 200);
        yList.setEnabled(false);
        frame.getContentPane().add(yList);

        lblTX = new JLabel("X");
        lblTX.setBounds(5, 250, 50, 15);
        frame.getContentPane().add(lblTX);

        txtX = new JTextField();
        txtX.setBounds(5, 270, 50, 25);
        frame.getContentPane().add(txtX);

        lblTY = new JLabel("Y");
        lblTY.setBounds(60, 250, 50, 15);
        frame.getContentPane().add(lblTY);

        txtY = new JTextField();
        txtY.setBounds(60, 270, 50, 25);
        frame.getContentPane().add(txtY);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(110, 270, 100, 25);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(txtX.getText());
                    double y = Double.parseDouble(txtY.getText());
                    addX(x);
                    addY(y);

                    addToList(new PointD(x, y), xModel, yModel);
                    txtX.setText("");
                    txtY.setText("");
                }catch (Exception e1){
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error:" + e1, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.getContentPane().add(btnAdd);

        btnRegression = new JButton("Regression");
        btnRegression.setBounds(5, 300, 100, 25);
        btnRegression.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ans = JOptionPane.showInputDialog(null, "1. Linear  y=ax+b" +
                                                                                      "\n2. Quadratic  y=ax^2+b+c" +
                                                                                      "\n3. Cubic     y=ax^3+bx^2+cx+d" +
                                                                                      "\n4. Exp y=a*(b)^x");

                double[] xx = new double[x.size()];
                double[] yy = new double[y.size()];

                for(int i = 0; i<x.size();i++){
                    xx[i] = x.get(i);
                    yy[i] = y.get(i);
                }

                if(ans.equals("1")){
                    LinearRegression lr = new LinearRegression(xx, yy);
                    String formula = String.format("y= %.04fx%s%.04f", lr.slope(), (lr.intercept() > 0) ? "+" : "", lr.intercept());
                    String rs = String.format("r^2= %.04f", lr.R2());
                    String r = String.format("r= %.04f", Math.sqrt(lr.R2()));
                    JOptionPane.showMessageDialog(null, formula + "\n" + rs + "\n" + r);

                    String function = String.format("%.04fx%s%.04f", lr.slope(), (lr.intercept() > 0) ? "+" : "", lr.intercept());
                    graphRegression(function);
                }else if(ans.equals("2")){
                    PolynomialRegression pr = new PolynomialRegression(xx, yy, 2);
                    String formula = String.format("y=%.06fx^2%s%.06fx%s%.06f", pr.beta(2), (pr.beta(1) > 0) ? "+" : "", pr.beta(1), (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    String rs = String.format("r^2= %.06f", pr.R2());
                    JOptionPane.showMessageDialog(null, formula + "\n" + rs, "Quadratic Regression", JOptionPane.INFORMATION_MESSAGE);

                    String function = String.format("%.06fx^2%s%.06fx%s%.06f", pr.beta(2), (pr.beta(1) > 0) ? "+" : "", pr.beta(1), (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    graphRegression(function);
                }else if(ans.equals("3")){
                    PolynomialRegression pr = new PolynomialRegression(xx, yy, 3);
                    String formula = String.format("y=%.06fx^3%s%.06fx^2%s%.06fx%s%.06f", pr.beta(3), (pr.beta(2) > 0) ? "+" : "", pr.beta(2), (pr.beta(1) > 0) ? "+" : "", pr.beta(1),
                            (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    String rs = String.format("r^2= %.06f", pr.R2());

                    JOptionPane.showMessageDialog(null, formula + "\n" + rs, "Cubic Regression", JOptionPane.INFORMATION_MESSAGE);

                    String function = String.format("%.06fx^3%s%.06fx^2%s%.06fx%s%.06f", pr.beta(3), (pr.beta(2) > 0) ? "+" : "", pr.beta(2), (pr.beta(1) > 0) ? "+" : "", pr.beta(1),
                            (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    graphRegression(function);
                }else if(ans.equals("4")){
                    ExpRegression ep = new ExpRegression(xx, yy);
                    String formula = "y=" + ep.getFunction();
                    String rs = String.format("r^2= %.06f", ep.getR());
                    JOptionPane.showMessageDialog(null, formula + "\n" + rs, "Exp Regression", JOptionPane.INFORMATION_MESSAGE);

                    graphRegression(ep.getFunction());
                }
            }
        });
        frame.getContentPane().add(btnRegression);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(110, 300, 100, 25);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!xList.isSelectionEmpty()){
                    int index = xList.getSelectedIndex();
                    xModel.remove(index);
                    yModel.remove(index);
                }

                if(!xList.isSelectionEmpty()){
                    int index = yList.getSelectedIndex();
                    xModel.remove(index);
                    yModel.remove(index);
                }
            }
        });
        frame.getContentPane().add(btnDelete);

        btnOK = new JButton("OK");
        btnOK.setBounds(60, 360, 100, 25);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.clear();
                for(int i = 0; i<xModel.size();i++){
                    points.add(new PointD(Double.parseDouble(xModel.get(i).toString()), Double.parseDouble(yModel.get(i).toString())));
                }

                graph.repaint2();
                save.update();
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnOK);

        if(graph.points.size() != 0 || graph.points != null)
            for(PointD d: graph.points){
                addToList(d, xModel, yModel);
                x.add(d.x);
                y.add(d.y);
            }
    }

    private void graphRegression(String function){
        if(holder.y1Empty() || holder.y2Empty() || holder.y3Empty()){
            int ans = JOptionPane.showConfirmDialog(null, "Would you like to graph?", "Graph", JOptionPane.YES_NO_OPTION);
            if(ans == JOptionPane.YES_OPTION){
                if(holder.y1Empty()){
                    holder.y1 = function;
                }else if(holder.y2Empty()){
                    holder.y2 = function;
                }else if(holder.y3Empty()){
                    holder.y3 = function;
                }
            }
        }
        graph.repaint2();
    }

    private void addToList(PointD point, DefaultListModel xModel, DefaultListModel yModel){
        xModel.addElement(point.x);
        yModel.addElement(point.y);
    }

    private void addX(final double x){
        this.x.add(x);
    }

    private void addY(final double y){
        this.y.add(y);
    }
}
