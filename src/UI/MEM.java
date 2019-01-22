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
import functions.FunctionStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MEM {
    private JFrame frame;
    private JList listVars;
    private DefaultListModel modelList;

    private JComboBox cmbVars;
    private JButton btnDel;
    private SaveSettings save;

    public MEM(final SaveSettings save){
        this.save = save;
        frame = new JFrame("Variable Manager");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        init();
        addItems();
        frame.setVisible(true);
    }

    private void init(){
        modelList = new DefaultListModel();

        listVars = new JList(modelList);
        frame.getContentPane().add(listVars, BorderLayout.CENTER);

        btnDel = new JButton("Delete");
        frame.getContentPane().add(btnDel, BorderLayout.PAGE_END);

        btnDel.addActionListener((ActionEvent) -> {
            if(listVars.getSelectedIndex() < 0) return;
            remove();
            modelList.remove(listVars.getSelectedIndex());
        });
    }

    private void remove(){
        int index = listVars.getSelectedIndex();
        int cIndex = cmbVars.getSelectedIndex();
        if(cIndex == 1){
            ShapeDrawer.circleInfo.remove(index);
        }else if(cIndex == 2){
            ShapeDrawer.segmentInfo.remove(index);
        }else if(cIndex == 3){
            ShapeDrawer.labelInfo.remove(index);
        }else if(cIndex == 0){
            FunctionStore.getStore().removeFunction(modelList.get(index).toString());
        }

        save.update();
    }

    private void addItems(){
        cmbVars = new JComboBox();
        cmbVars.addItemListener(new ListUpdater());
        frame.getContentPane().add(cmbVars, BorderLayout.PAGE_START);
        cmbVars.addItem("Functions");
        cmbVars.addItem("Circle Data");
        cmbVars.addItem("Segments");
        cmbVars.addItem("Label");
    }

    private void addCircleInfo(){
        if(ShapeDrawer.circleInfo != null){
            for(final CircleInfo info: ShapeDrawer.circleInfo){
                modelList.addElement(info.getName() + " - " + info.getCenter().toString() + " R:" + info.getRadius() + " C:" + info.getColorString());
            }
        }
    }

    private void addSegmentData(){
        if(ShapeDrawer.segmentInfo != null){
            for(final SegmentInfo info: ShapeDrawer.segmentInfo){
                modelList.addElement(info.getPos1().toString() + " - " + info.getPos2().toString());
            }
        }
    }

    private void addLabelData(){
        if(ShapeDrawer.labelInfo != null){
            for(final LabelInfo info: ShapeDrawer.labelInfo){
                modelList.addElement(info.getText() + " - " + new PointD(info.getX(), info.getY()).toString());
            }
        }
    }

    private void addFunctions(){
        if(FunctionStore.getStore().getRawFunctions() != null){
            for(final String f: FunctionStore.getStore().getRawFunctions()){
                if(f.equals("y1") || f.equals("y2") || f.equals("y3") || f.equals("y4") || f.equals("y5")) {
                    continue;
                }
                modelList.addElement(f + "(x) = " + FunctionStore.getStore().getHashFunctions().get(f));
            }
        }
    }

    private class ListUpdater implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            int index = cmbVars.getSelectedIndex();

            modelList.clear();
            switch (index){
                case 1:
                    addCircleInfo();
                    break;
                case 2:
                    addSegmentData();
                    break;
                case 3:
                    addLabelData();
                    break;
                case 0:
                    addFunctions();
                    break;
            }
        }
    }
}
