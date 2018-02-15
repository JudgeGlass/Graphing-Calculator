package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScatterPlot {
    private JFrame frame;
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

    private ArrayList<PointD> points;

    public ScatterPlot(ArrayList<PointD> points){
        this.points = points;
        frame = new JFrame();
        frame.setTitle("Scatter Plot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(225, 420);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

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
                ArrayList<PointD> xy = new ArrayList<>();

                for(int i = 0; i<xModel.size();i++){
                    points.add(new PointD(Double.parseDouble(xModel.get(i).toString()), Double.parseDouble(yModel.get(i).toString())));
                }

                frame.dispose();
            }
        });
        frame.getContentPane().add(btnOK);
    }

    private void addToList(PointD point, DefaultListModel xModel, DefaultListModel yModel){
        xModel.addElement(point.x);
        yModel.addElement(point.y);
    }
}
