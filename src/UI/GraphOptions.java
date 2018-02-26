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
    private JLabel lblTableInc;

    private JTextField txtXMax;
    private JTextField txtXMin;
    private JTextField txtYMax;
    private JTextField txtYMin;
    private JTextField txtTableInc;

    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnDefaults;

    private JCheckBox chkDrawLines;
    private JSlider slider;

    private Graph graph;

    public GraphOptions(GraphWindow window, Graph graph){
        this.window = window;
        this.graph = graph;

        frame = new JFrame();
        frame.setTitle("Options");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(270, 415);
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

        lblTableInc = new JLabel("Table X increment");
        lblTableInc.setBounds(5, 220, 200, 15);

        frame.getContentPane().add(lblTableInc);

        txtTableInc = new JTextField();
        txtTableInc.setBounds(4, 240, 240, 25);
        txtTableInc.setText(Double.toString(window.tableInc));

        frame.getContentPane().add(txtTableInc);

        chkDrawLines = new JCheckBox("Draw Grid");
        chkDrawLines.setBounds(5, 275, 150, 15);
        chkDrawLines.setSelected(window.drawLines);

        frame.getContentPane().add(chkDrawLines);

        btnDefaults = new JButton("Defaults");
        btnDefaults.setBounds(5, 295, 100, 25);
        btnDefaults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtXMax.setText("10");
                txtXMin.setText("-10");
                txtYMax.setText("10");
                txtYMin.setText("-10");
                txtTableInc.setText("1");

                chkDrawLines.setSelected(true);
            }
        });
        frame.getContentPane().add(btnDefaults);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(40, 345, 90, 25);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnCancel);

        btnSave = new JButton("Save");
        btnSave.setBounds(145, 345, 90, 25);
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
                    //window.resolution = (double) slider.getValue() / 15;

                    graph.repaint2();

                    frame.dispose();
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.getContentPane().add(btnSave);
    }
}
