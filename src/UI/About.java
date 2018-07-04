/*
 * Copyright 2018 Hunter Wilcox
 *
 * This file is part of GraphingCalculator.
 *
 * GraphingCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GraphingCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GraphingCalculator.  If not, see <http://www.gnu.org/licenses/>.
 */

package UI;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

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
        ImageIcon image = new ImageIcon(getClass().getResource("/Program/icons8-square-root-50.png"));
        lblImage = new JLabel();
        lblImage.setIcon(image);
        lblImage.setBounds(5, 5, 64, 64);
        frame.getContentPane().add(lblImage);

        lblApName = new JLabel("<html><font color=\"blue\">Graphing Calculator</font></html>");
        lblApName.setFont(new Font(Font.SANS_SERIF, 0, 12));
        lblApName.setBounds(77, 10, 170, 15);

        lblApName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblApName.setToolTipText("Open: " + ApplicationInfo.SOURCE_URL);
        lblApName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(ApplicationInfo.SOURCE_URL));
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(lblApName);

        lblAuthor = new JLabel("Author: " + ApplicationInfo.AUTHOR);
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
        txtPane.setText("This is a basic calculator with graphing functionality.\n\nLicense: " + ApplicationInfo.LICENSE);
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
