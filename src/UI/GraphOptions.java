package UI;

import javax.swing.*;
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
    private JLabel lblResolution;
    private JLabel setResolution;

    private JTextField txtXMax;
    private JTextField txtXMin;
    private JTextField txtYMax;
    private JTextField txtYMin;
    private JSlider slider;

    private JButton btnSave;
    private JButton btnCancel;

    public GraphOptions(GraphWindow window){
        this.window = window;
        frame = new JFrame();
        frame.setTitle("Options");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 380);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        panel = new JPanel();
        frame.add(panel);

        frame.setVisible(true);

        init();
    }

    private void init(){
        lblXMax = new JLabel("X Max:");
        lblXMax.setBounds(5, 5, 50, 15);

        frame.getContentPane().add(lblXMax);

        txtXMax = new JTextField();
        txtXMax.setBounds(60, 5, 180, 25);
        txtXMax.setText(Double.toString(window.xMax));

        frame.getContentPane().add(txtXMax);

        lblXMin = new JLabel("X Min:");
        lblXMin.setBounds(5, 70, 50, 15);

        frame.getContentPane().add(lblXMin);

        txtXMin = new JTextField();
        txtXMin.setBounds(60, 65, 180, 25);
        txtXMin.setText(Double.toString(window.xMin));

        frame.getContentPane().add(txtXMin);

        lblYMax = new JLabel("Y Max:");
        lblYMax.setBounds(5, 130, 50, 15);

        frame.getContentPane().add(lblYMax);

        txtYMax = new JTextField();
        txtYMax.setBounds(60, 130, 180, 25);
        txtYMax.setText(Double.toString(window.yMax));

        frame.getContentPane().add(txtYMax);

        lblYMin = new JLabel("Y Min");
        lblYMin.setBounds(5, 185, 50, 15);

        frame.getContentPane().add(lblYMin);

        txtYMin = new JTextField();
        txtYMin.setBounds(60, 185, 180, 25);
        txtYMin.setText(Double.toString(window.yMin));

        frame.getContentPane().add(txtYMin);

        lblResolution = new JLabel("Resolution:");
        lblResolution.setBounds(5, 220, 70, 15);

        frame.getContentPane().add(lblResolution);

        slider = new JSlider();
        slider.setMaximum(10);
        slider.setMinimum(1);
        slider.setBounds(5, 240, 246, 25);
        slider.setValue((int)window.resolution * 1000);
        slider.setEnabled(false);

        frame.getContentPane().add(slider);

        setResolution = new JLabel("High Quality                                 Low Quality");
        setResolution.setBounds(5, 255, 240, 15);


        frame.getContentPane().add(setResolution);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(40, 310, 90, 25);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.getContentPane().add(btnCancel);

        btnSave = new JButton("Save");
        btnSave.setBounds(145, 310, 90, 25);
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
                    System.out.println(slider.getValue());
                    //window.resolution = slider.getValue() / 1000;

                    frame.dispose();
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.getContentPane().add(btnSave);
    }
}