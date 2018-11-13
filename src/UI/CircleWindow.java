package UI;

import Program.ApplicationInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CircleWindow {
    private JFrame frame;
    private JPanel panel;

    private JLabel lblCenter;
    private JLabel lblRadius;
    private JLabel lblName;
    private JLabel lblColor;
    private JTextField txtX;
    private JTextField txtY;

    private JTextField txtRadius;
    private JTextField txtName;

    private JComboBox colorPicker;
    private JButton btnOK;
    private JButton btnCancel;

    private double x;
    private double y;

    public CircleWindow(final double x, final double y){
        frame = new JFrame("Plot Circle");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 170);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        this.x = x;
        this.y = y;

        init();
        frame.setVisible(true);
    }

    private void init(){
        lblCenter = new JLabel("Center(X,Y):");
        lblCenter.setBounds(5, 5, 150, 15);
        frame.getContentPane().add(lblCenter);

        lblColor = new JLabel("Color:");
        lblColor.setBounds(215, 5, 150, 15);
        frame.getContentPane().add(lblColor);

        txtX = new JTextField();
        txtX.setText(String.format("%.3f", x));
        txtX.setBounds(5, 25, 100, 25);
        frame.getContentPane().add(txtX);

        txtY = new JTextField();
        txtY.setText(String.format("%.3f", y));
        txtY.setBounds(110, 25, 100, 25);
        frame.getContentPane().add(txtY);

        colorPicker = new JComboBox();
        colorPicker.addItem("Blue");
        colorPicker.addItem("Red");
        colorPicker.addItem("Green");
        colorPicker.addItem("Black");
        colorPicker.setBounds(215, 25, 175, 25);
        frame.getContentPane().add(colorPicker);

        lblRadius = new JLabel("Radius:");
        lblRadius.setBounds(5, 55, 150, 15);
        frame.getContentPane().add(lblRadius);

        txtRadius = new JTextField();
        txtRadius.setBounds(5, 75, 100, 25);
        frame.getContentPane().add(txtRadius);

        lblName = new JLabel("Name:");
        lblName.setBounds(110, 55, 150, 15);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(110, 75, 100, 25);
        frame.getContentPane().add(txtName);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(60, 105, 120, 25);
        btnCancel.addActionListener((ActionEvent) -> {
            frame.dispose();
        });
        frame.getContentPane().add(btnCancel);

        btnOK = new JButton("OK");
        btnOK.setBounds(185, 105, 120, 25);
        btnOK.addActionListener((ActionEvent) -> {
            try{
                if(txtName.getText().length() > 1){
                    JOptionPane.showMessageDialog(null, "The circle name can only be ONE character!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(txtName.getText().length() <= 0){
                    JOptionPane.showMessageDialog(null, "The circle name can not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double x = Double.parseDouble(txtX.getText());
                double y = Double.parseDouble(txtY.getText());
                double r = Double.parseDouble(txtRadius.getText());

                Color c = null;

                switch (colorPicker.getSelectedIndex()){
                    case 0:
                        c = Color.BLUE;
                        break;
                    case 1:
                        c = Color.RED;
                        break;
                    case 2:
                        c = Color.GREEN;
                        break;
                    case 3:
                        c = Color.BLACK;
                }

                ShapeDrawer.circleInfo.add(new CircleInfo(txtName.getText(), new PointD(x, y), r, c));
                frame.dispose();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.getContentPane().add(btnOK);
    }
}
