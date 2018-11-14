package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

public class VariableManager {
    private JFrame frame;
    private JList listVars;
    private DefaultListModel modelList;

    private JComboBox cmbVars;
    private JButton btnOK;
    private JButton btnDel;

    public VariableManager(){
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
        if(cIndex == 0){
            ShapeDrawer.circleInfo.remove(index);
        }else if(cIndex == 1){
            ShapeDrawer.segmentInfo.remove(index);
        }else if(cIndex == 2){
            ShapeDrawer.labelInfo.remove(index);
        }
    }

    private void addItems(){
        cmbVars = new JComboBox();
        cmbVars.addItemListener(new ListUpdater());
        frame.getContentPane().add(cmbVars, BorderLayout.PAGE_START);
        cmbVars.addItem("Circle Data");
        cmbVars.addItem("Segments");
        cmbVars.addItem("Label");
    }

    private void addCircleInfo(){
        if(ShapeDrawer.circleInfo != null){
            for(final CircleInfo info: ShapeDrawer.circleInfo){
                modelList.addElement(info.getName() + " - " + info.getCenter().toString());
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

    private class ListUpdater implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            int index = cmbVars.getSelectedIndex();

            modelList.clear();
            switch (index){
                case 0:
                    addCircleInfo();
                    break;
                case 1:
                    addSegmentData();
                    break;
                case 2:
                    addLabelData();
                    break;
            }
        }
    }
}
