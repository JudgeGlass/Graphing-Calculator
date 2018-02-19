package UI;

import functions.Function;
import functions.FunctionArguments;

import javax.swing.*;
import java.awt.*;

public class Table {
    private JFrame frame;
    private DefaultListModel model;
    private JList list;
    private int orginIndex = 0;

    private Function function;
    private GraphWindow window;

    public Table(GraphWindow window, Function f){
        this.window = window;
        function = f;

        frame = new JFrame();
        frame.setTitle("Table");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        init();
        frame.setVisible(true);
        list.setSelectedIndex(orginIndex);
    }

    private void init(){
        model = new DefaultListModel();

        list = new JList(model);
        list.setFixedCellWidth(frame.getWidth());

        makeLists(model);

        frame.getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);
    }

    private void makeLists(DefaultListModel model){
        double[] arg = new double[2];
        model.addElement("X          Y");
        for(double i = window.xMin; i<=window.xMax;i++){
            arg[1] = i;
            if(i == 0.0){
                orginIndex = (int)i;
            }

            if(Double.isNaN(function.evaluate(new FunctionArguments(arg)))){
                model.addElement(i + "      Error");
                continue;
            }

            model.addElement(i + "     " + function.evaluate(new FunctionArguments(arg)));
        }
    }
}
