package UI;

import functions.Function;
import functions.FunctionArguments;

import javax.swing.*;
import java.awt.*;

public class Table {
    private JFrame frame;
    private DefaultListModel model;
    private JScrollPane scroll;
    private JList list;
    private double orginIndex;

    private Function function;
    private GraphWindow window;

    public Table(final GraphWindow window, final Function f){
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
        list.ensureIndexIsVisible((int)orginIndex);
    }

    private void init(){
        model = new DefaultListModel();

        list = new JList(model);
        list.setFixedCellWidth(frame.getWidth());

        makeLists(model);
        scroll = new JScrollPane(list);
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void makeLists(DefaultListModel model){
        double[] arg = new double[2];
        model.addElement("X          Y");
        for(double i = -1000; i<=1000;i+=window.tableInc){
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
    }
}
