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
import Program.Log;

public class ScatterPlot {
    private JFrame frame;
    private JPanel panel;

    private JButton btnListWindow;
    private JButton btnOK;
    private JButton btnRegression;

    private JLabel xList;
    private JLabel yList;
    private JComboBox xComb;
    private JComboBox yComb;

    private ArrayList<Double> x = new ArrayList<>();
    private ArrayList<Double> y = new ArrayList<>();

    private Graph graph;

    private SaveSettings save;
    private FunctionHolder holder;
    private ListManager listManager;
    private ArrayList<PointD> points;

    public ScatterPlot(Graph graph, FunctionHolder holder, SaveSettings save, ListManager listManager, ArrayList<PointD> points){
        this.graph = graph;
        this.save = save;
        this.holder = holder;
        this.listManager = listManager;
        this.points = points;

        frame = new JFrame();
        frame.setTitle("Scatter Plot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 140);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        panel = new JPanel();
        frame.add(panel);

        init();
        frame.setVisible(true);
    }

    private void init(){
        xList = new JLabel("X List");
        xList.setBounds(5, 5, 150, 15);
        frame.getContentPane().add(xList);

        yList = new JLabel("Y List");
        yList.setBounds(160, 5, 150, 15);
        frame.getContentPane().add(yList);

        xComb = new JComboBox();
        xComb.setBounds(5, 25, 100, 25);
        xComb.addItem("L1");
        xComb.addItem("L2");
        xComb.addItem("L3");
        frame.getContentPane().add(xComb);

        yComb = new JComboBox();
        yComb.setBounds(115, 25, 100, 25);
        yComb.addItem("L1");
        yComb.addItem("L2");
        yComb.addItem("L3");
        yComb.setSelectedIndex(1);
        frame.getContentPane().add(yComb);

        switch (listManager.xList){
            case L1:
                xComb.setSelectedIndex(0);
                break;
            case L2:
                xComb.setSelectedIndex(1);
                break;
            case L3:
                xComb.setSelectedIndex(2);
                break;
        }

        switch (listManager.yList){
            case L1:
                yComb.setSelectedIndex(0);
                break;
            case L2:
                yComb.setSelectedIndex(1);
                break;
            case L3:
                yComb.setSelectedIndex(2);
                break;
        }

        btnRegression = new JButton("Regression");
        btnRegression.setBounds(5, 60, 100, 25);
        btnRegression.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ans = JOptionPane.showInputDialog(null, "1. Linear  y=ax+b" +
                                                                                      "\n2. Quadratic  y=ax^2+b+c" +
                                                                                      "\n3. Cubic     y=ax^3+bx^2+cx+d" +
                                                                                      "\n4. Exp y=a*(b)^x");

                x = listManager.getXList();
                y = listManager.getYList();

                double[] xx = new double[x.size()];
                double[] yy = new double[y.size()];

                for(int i = 0; i<x.size();i++){
                    xx[i] = x.get(i);
                    yy[i] = y.get(i);
                }

                if(ans.equals("1")){
                    LinearRegression lr = new LinearRegression(xx, yy);
                    Log.error("INTER: " + lr.intercept());
                    String formula = String.format("y= %.04fx%s%.04f", lr.slope(), (lr.intercept() >= 0) ? "+" : "", lr.intercept());
                    String rs = String.format("r^2= %.04f", lr.R2());
                    String r = String.format("r= %.04f", Math.sqrt(lr.R2()));
                    JOptionPane.showMessageDialog(null, formula + "\n" + rs + "\n" + r);

                    String function = String.format("%.04fx%s%.04f", lr.slope(), (lr.intercept() >= 0) ? "+" : "", lr.intercept());
                    graphRegression(function);
                }else if(ans.equals("2")){
                    PolynomialRegression pr = new PolynomialRegression(xx, yy, 2);
                    String formula = String.format("y=%.06fx^2%s%.06fx%s%.06f", pr.beta(2), (pr.beta(1) >= 0) ? "+" : "", pr.beta(1), (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    String rs = String.format("r^2= %.06f", pr.R2());
                    JOptionPane.showMessageDialog(null, formula + "\n" + rs, "Quadratic Regression", JOptionPane.INFORMATION_MESSAGE);

                    String function = String.format("%.06fx^2%s%.06fx%s%.06f", pr.beta(2), (pr.beta(1) >= 0) ? "+" : "", pr.beta(1), (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    graphRegression(function);
                }else if(ans.equals("3")){
                    PolynomialRegression pr = new PolynomialRegression(xx, yy, 3);
                    String formula = String.format("y=%.06fx^3%s%.06fx^2%s%.06fx%s%.06f", pr.beta(3), (pr.beta(2) >= 0) ? "+" : "", pr.beta(2), (pr.beta(1) > 0) ? "+" : "", pr.beta(1),
                            (pr.beta(0) > 0) ? "+" : "", pr.beta(0));
                    String rs = String.format("r^2= %.06f", pr.R2());

                    JOptionPane.showMessageDialog(null, formula + "\n" + rs, "Cubic Regression", JOptionPane.INFORMATION_MESSAGE);

                    String function = String.format("%.06fx^3%s%.06fx^2%s%.06fx%s%.06f", pr.beta(3), (pr.beta(2) >= 0) ? "+" : "", pr.beta(2), (pr.beta(1) > 0) ? "+" : "", pr.beta(1),
                            (pr.beta(0) >= 0) ? "+" : "", pr.beta(0));
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

        btnListWindow = new JButton("List Mgr.");
        btnListWindow.setBounds(230, 25, 100, 25);
        btnListWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListWindow(listManager, save);
            }
        });
        frame.getContentPane().add(btnListWindow);

        btnOK = new JButton("OK");
        btnOK.setBounds(230, 60, 100, 25);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.clear();
                if(xComb.getSelectedIndex() == 0){
                    listManager.xList = ListManager.Lists.L1;
                }else if(xComb.getSelectedIndex() == 1){
                    listManager.xList = ListManager.Lists.L2;
                }else{
                    listManager.xList = ListManager.Lists.L3;
                }

                if(yComb.getSelectedIndex() == 0){
                    listManager.yList = ListManager.Lists.L1;
                }else if(yComb.getSelectedIndex() == 1){
                    listManager.yList = ListManager.Lists.L2;
                }else{
                    listManager.yList = ListManager.Lists.L3;
                }

                for(int i = 0; i < listManager.getLargestDataList(); i++){
                    if(i > listManager.getXList().size() || i > listManager.getYList().size()){break;}
                    if(listManager.getXList().get(i) != null && listManager.getYList().get(i) != null){
                        if(listManager.getXList().get(i) == 0 && listManager.getYList().get(i) == 0){continue;}
                        points.add(new PointD(listManager.getXList().get(i), listManager.getYList().get(i)));
                    }
                }

                save.update();

                frame.dispose();
            }
        });
        frame.getContentPane().add(btnOK);

    }

    private void graphRegression(String function){
        if(holder.y1Empty() || holder.y2Empty() || holder.y3Empty() || holder.y4Empty() || holder.y5Empty()){
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
}
