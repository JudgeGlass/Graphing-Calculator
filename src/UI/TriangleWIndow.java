package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TriangleWIndow {
    private JFrame frame;
    private JTextField txtA;
    private JTextField txtB;
    private JTextField txtC;

    private JLabel lblA;
    private JLabel lblB;
    private JLabel lblC;

    private JButton btnGraph;
    private JButton btnInfo;

    private Graph graph;

    public TriangleWIndow(final boolean show, Graph graph){
        this.graph = graph;
        frame = new JFrame("Triangle");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(230, 250);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        init();
        frame.setVisible(show);
    }

    public void show(){
        frame.setVisible(true);
    }

    private void init(){
        lblA = new JLabel("Point A:");
        lblA.setBounds(5, 5, 150, 15);
        frame.add(lblA);

        txtA = new JTextField();
        txtA.setBounds(5, 25, 200, 30);
        frame.add(txtA);

        lblB = new JLabel("Point B");
        lblB.setBounds(5, 60, 150, 15);
        frame.add(lblB);

        txtB = new JTextField();
        txtB.setBounds(5, 80, 200, 30);
        frame.add(txtB);

        lblC = new JLabel("Point C:");
        lblC.setBounds(5, 115, 150, 15);
        frame.add(lblC);

        txtC = new JTextField();
        txtC.setBounds(5, 135, 200, 30);
        frame.add(txtC);

        btnGraph = new JButton("Graph");
        btnGraph.setBounds(5, 170, 100, 20);

        btnGraph.addActionListener((ActionEvent) -> {
            PointD point1;
            PointD point2;
            PointD point3;
            try{
                String[] p1 = txtA.getText().split(",");
                String[] p2 = txtB.getText().split(",");
                String[] p3 = txtC.getText().split(",");

                point1 = new PointD(Double.parseDouble(p1[0]), Double.parseDouble(p1[1]));
                point2 = new PointD(Double.parseDouble(p2[0]), Double.parseDouble(p2[1]));
                point3 = new PointD(Double.parseDouble(p3[0]), Double.parseDouble(p3[1]));
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


        });

        frame.add(btnGraph);

        btnInfo = new JButton("Info");
        btnInfo.setBounds(110, 170, 100, 20);
        frame.add(btnInfo);
    }
}
