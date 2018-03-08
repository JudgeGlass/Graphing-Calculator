package UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Program.ApplicationInfo;

import javax.swing.*;

public class About {

    private JDialog frame;
    private JPanel panel;
    private JLabel lblAuthor;
    private JLabel lblApName;
    private JLabel lblVersion;
    private JLabel lblCopyright;
    private JLabel lblImage;
    private JTextPane txtPane;
    private JLabel lblDec;
    private JLabel lblJava;
    private JButton btnOk;

    public About() {
        frame = new JDialog();
        frame.setTitle("About");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(325, 290);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        panel = new JPanel();
        frame.add(panel);

        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        ImageIcon image = new ImageIcon(getClass().getResource("/Program/MainIcon.png"));
        lblImage = new JLabel();
        lblImage.setIcon(image);
        lblImage.setBounds(5, 5, 64, 64);
        frame.getContentPane().add(lblImage);

        lblApName = new JLabel("Graphing Calculator");
        lblApName.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblApName.setBounds(77, 10, 170, 15);

        lblApName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(lblApName);

        lblAuthor = new JLabel("Author: Hunter Wilcox");
        lblAuthor.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblAuthor.setBounds(77, 35, 300, 15);
        frame.getContentPane().add(lblAuthor);

        lblVersion = new JLabel("Version: " + ApplicationInfo.VERSION + " [Build Date: " + ApplicationInfo.BUILD_DATE + "]");
        lblVersion.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblVersion.setBounds(77, 60, 300, 15);
        frame.getContentPane().add(lblVersion);

        lblCopyright = new JLabel("Copyright \u00a9 2018 Hunter Wilcox");
        lblCopyright.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblCopyright.setBounds(77, 85, 300, 15);
        frame.getContentPane().add(lblCopyright);

        lblJava = new JLabel("Java Version: " + System.getProperty("java.version"));
        lblJava.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblJava.setBounds(77, 108, 300, 15);
        frame.getContentPane().add(lblJava);

        lblDec = new JLabel("Description:");
        lblDec.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblDec.setBounds(5, 115, 200, 15);
        frame.getContentPane().add(lblDec);

        txtPane = new JTextPane();
        txtPane.setFont(new Font(Font.SANS_SERIF, 0, 12));
        txtPane.setEditable(false);
        txtPane.setBounds(5, 130, 295, 90);
        txtPane.setText("This is a basic calculator with graphing functionality.");
        frame.getContentPane().add(txtPane);

        btnOk = new JButton("OK");
        btnOk.setFont(new Font(Font.SANS_SERIF, 0, 12));
        btnOk.setBounds(210, 225, 95, 20);
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnOk);
    }
}
