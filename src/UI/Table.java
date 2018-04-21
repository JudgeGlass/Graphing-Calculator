package UI;

import Program.CorrectFunction;
import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Table {
    private JFrame frame;
    private DefaultListModel model;
    private JScrollPane scroll;
    private JList list;
    private JComboBox functionIndex;
    private double orginIndex;

    private Function function;
    private GraphWindow window;

    public Table(final GraphWindow window){
        this.window = window;
        //function = f;

        frame = new JFrame();
        frame.setTitle("Table");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        init();
        frame.setVisible(true);
        list.ensureIndexIsVisible((int)orginIndex);
    }

    private void init(){

        functionIndex = new JComboBox();
        if(window.fh != null){
            if(!window.fh.y1.isEmpty()){
                functionIndex.addItem("Y1= " + window.fh.y1);
            }
            if(!window.fh.y2.isEmpty()){
                functionIndex.addItem("Y2= " + window.fh.y2);
            }
            if(!window.fh.y3.isEmpty()){
                functionIndex.addItem("Y3= " + window.fh.y3);
            }
        }
        functionIndex.addItemListener(new ModeChange());

        frame.getContentPane().add(functionIndex, BorderLayout.PAGE_START);

        model = new DefaultListModel();

        list = new JList(model);
        list.setFixedCellWidth(frame.getWidth());

        scroll = new JScrollPane(list);
        //makeLists(model);

        frame.getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void makeLists(DefaultListModel model){
        double[] arg = new double[2];
        model.addElement("X          Y");
        for(double i = window.xMin; i<=window.xMax;i+=window.tableInc){
            arg[1] = i;
            if(i == 0.0){
                System.out.println("Orgin is at: " + i);
                orginIndex = i;
            }

            double y = function.evaluate(new FunctionArguments(arg));

            if(Double.isNaN(y)){
                model.addElement(i + "      Error");
                continue;
            }

            model.addElement(i + "     " + y);
        }
        list.setSelectedIndex(model.size()/2);
    }

    private class ModeChange implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            ArrayList<String> vars = new ArrayList<>();
            vars.add("y");
            vars.add("x");
            String at = functionIndex.getItemAt(functionIndex.getSelectedIndex()).toString();
            if(at.contains("Y1")){
                model.clear();
                function = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(window.fh.y1), vars);
                makeLists(model);
            }else if(at.contains("Y2")){
                model.clear();
                function = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(window.fh.y2), vars);
                makeLists(model);
            }else if(at.contains("Y3")){
                model.clear();
                function = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(window.fh.y3), vars);
                makeLists(model);
            }
        }
    }
}
