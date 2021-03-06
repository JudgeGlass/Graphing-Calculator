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
import Program.ApplicationInfo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphOptions {
    private GraphWindow window;

    private JFrame frame;
    private JPanel panel;

    private JLabel lblXMax;
    private JLabel lblXMin;
    private JLabel lblYMax;
    private JLabel lblYMin;
    private JLabel lblTableInc;
    private JLabel lblResolution;
    private JLabel lblLineType;

    private JTextField txtXMax;
    private JTextField txtXMin;
    private JTextField txtYMax;
    private JTextField txtYMin;
    private JTextField txtTableInc;

    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnDefaults;

    private JCheckBox chkDrawLines;
    private JCheckBox chkDegrees;
    private JSlider slider;
    private JComboBox lineType;

    private Graph graph;
    private SaveSettings save;

    public GraphOptions(GraphWindow window, Graph graph, SaveSettings save){
        this.window = window;
        this.graph = graph;
        this.save = save;

        frame = new JFrame();
        frame.setTitle("Options");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(270, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        panel = new JPanel();
        frame.add(panel);

        init();

        frame.setVisible(true);
    }

    /**
     * Set everything up
     * */

    private void init(){
        lblXMax = new JLabel("X Max:");
        lblXMax.setBounds(5, 5, 50, 15);

        frame.getContentPane().add(lblXMax);

        txtXMax = new JTextField();
        txtXMax.setBounds(60, 5, 180, 25);
        txtXMax.setText(Double.toString(window.xMax));

        frame.getContentPane().add(txtXMax);

        lblXMin = new JLabel("X Min:");
        lblXMin.setBounds(5, 50, 50, 15);

        frame.getContentPane().add(lblXMin);

        txtXMin = new JTextField();
        txtXMin.setBounds(60, 50, 180, 25);
        txtXMin.setText(Double.toString(window.xMin));

        frame.getContentPane().add(txtXMin);

        lblYMax = new JLabel("Y Max:");
        lblYMax.setBounds(5, 100, 50, 15);

        frame.getContentPane().add(lblYMax);

        txtYMax = new JTextField();
        txtYMax.setBounds(60, 95, 180, 25);
        txtYMax.setText(Double.toString(window.yMax));

        frame.getContentPane().add(txtYMax);

        lblYMin = new JLabel("Y Min:");
        lblYMin.setBounds(5, 145, 50, 15);

        frame.getContentPane().add(lblYMin);

        txtYMin = new JTextField();
        txtYMin.setBounds(60, 140, 180, 25);
        txtYMin.setText(Double.toString(window.yMin));

        lblLineType = new JLabel("Line Type:");
        lblLineType.setBounds(5, 170, 150, 15);
        frame.getContentPane().add(lblLineType);

        lineType = new JComboBox();
        lineType.addItem("Line");
        lineType.addItem("Circle");
        lineType.addItem("Filled (top)");
        lineType.addItem("Filled (bottom)");
        lineType.setBounds(5, 190, 240, 25);
        frame.getContentPane().add(lineType);

        frame.getContentPane().add(txtYMin);

        lblTableInc = new JLabel("Table Delta X");
        lblTableInc.setBounds(5, 270, 200, 15);

        frame.getContentPane().add(lblTableInc);

        txtTableInc = new JTextField();
        txtTableInc.setBounds(4, 290, 240, 25);
        txtTableInc.setText(Double.toString(window.tableInc));
        frame.getContentPane().add(txtTableInc);

        lblResolution = new JLabel("Resolution(Low=better)");
        lblResolution.setBounds(5, 225, 150, 15);
        frame.getContentPane().add(lblResolution);

        slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                window.resolution = (double)slider.getValue();
                graph.repaint2();
            }
        });
        slider.setValue((int)window.resolution);
        slider.setBounds(5, 240, 240, 25);
        slider.setMinimum(1);
        slider.setMaximum(100);
        frame.getContentPane().add(slider);


        chkDrawLines = new JCheckBox("Draw Grid");
        chkDrawLines.setBounds(5, 345, 150, 15);
        chkDrawLines.setSelected(window.drawLines);

        chkDegrees = new JCheckBox("Degrees");
        chkDegrees.setBounds(170, 345, 150, 15);
        chkDegrees.setSelected(ApplicationInfo.useDegrees);

        frame.getContentPane().add(chkDrawLines);
        frame.getContentPane().add(chkDegrees);

        btnDefaults = new JButton("Defaults");
        btnDefaults.setBounds(5, 315, 100, 25);
        btnDefaults.addActionListener(e -> {
            txtXMax.setText("10.0");
            txtXMin.setText("-10.0");
            txtYMax.setText("10.0");
            txtYMin.setText("-10.0");
            txtTableInc.setText("1.0");

            chkDrawLines.setSelected(true);
            slider.setValue(0);
            chkDegrees.setSelected(false);
            lineType.setSelectedIndex(0);
        });
        frame.getContentPane().add(btnDefaults);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(40, 365, 90, 25);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnCancel);

        btnSave = new JButton("Save");
        btnSave.setBounds(145, 365, 90, 25);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double xMax = Double.parseDouble(txtXMax.getText());
                    double xMin = Double.parseDouble(txtXMin.getText());
                    double yMax = Double.parseDouble(txtYMax.getText());
                    double yMin = Double.parseDouble(txtYMin.getText());

                    window.xMin = xMin;
                    window.xMax = xMax;
                    window.yMax = yMax;
                    window.yMin = yMin;
                    window.drawLines = chkDrawLines.isSelected();
                    window.tableInc = Double.parseDouble(txtTableInc.getText());
                    window.resolution = (double)slider.getValue();

                    ApplicationInfo.useDegrees = chkDegrees.isSelected();

                    switch (lineType.getSelectedIndex()){
                        case 0:
                            window.currentDrawType = GraphWindow.DrawType.LINE;
                            break;
                        case 1:
                            window.currentDrawType = GraphWindow.DrawType.CIRCLE;
                            break;
                        case 2:
                            window.currentDrawType = GraphWindow.DrawType.SOLD_TOP;
                            break;
                        case 3:
                            window.currentDrawType = GraphWindow.DrawType.SOLD_BOTTOM;
                            break;
                    }

                    graph.repaint2();

                    frame.dispose();

                    save.update();
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.getContentPane().add(btnSave);
    }
}
