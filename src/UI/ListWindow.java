package UI;

import FileIO.SaveSettings;
import Program.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListWindow {
    private JFrame frame;
    private JTable table;

    private JPanel panelBottom;
    private JButton btnAddRow;
    private JButton btnDelete;
    private JButton btnOK;

    private ListManager listManager;
    private SaveSettings saveSettings;

    public ListWindow(final ListManager listManager, final SaveSettings saveSettings){
        this.listManager = listManager;
        this.saveSettings = saveSettings;

        frame = new JFrame("Lists");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        panelBottom = new JPanel();
        frame.getContentPane().add(panelBottom, BorderLayout.PAGE_END);


        init();
        frame.setVisible(true);
    }

    private void init(){
        String[] columNames = {"L1", "L2", "L3"};

        DefaultTableModel tableModel = new DefaultTableModel(null, columNames);

        for(int i = 0; i < listManager.getLargestListCount(); i++){
            double data1 = 0;
            double data2 = 0;
            double data3 = 0;

            if(i < listManager.getL1().size()){
                data1 = listManager.getL1().get(i);
            }

            if(i < listManager.getL2().size()){
                data2 = listManager.getL2().get(i);
            }

            if(i < listManager.getL3().size()){
                data3 = listManager.getL3().get(i);
            }


            tableModel.addRow(new Object[]{Double.toString(data1), Double.toString(data2), Double.toString(data3)});
        }

        table = new JTable(tableModel);

        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        btnAddRow = new JButton("Add");
        btnAddRow.addActionListener((ActionEvent) -> {
            tableModel.addRow(new Object[]{null, null, null});
        });
        panelBottom.add(btnAddRow, BorderLayout.LINE_START);
        btnDelete = new JButton("Delete");
        btnDelete.addActionListener((ActionEvent) -> {
            if(table.getSelectedRow() != -1) {
                int index = table.getSelectedRow();
                tableModel.removeRow(index);
                listManager.removeFromList(ListManager.Lists.ALL, index);
            }
        });
        panelBottom.add(btnDelete, BorderLayout.CENTER);
        btnOK = new JButton("OK");
        btnOK.addActionListener((ActionEvent) -> {
            listManager.clearList(ListManager.Lists.ALL);

            for(int z = 0; z < 3; z++) {
                for (int i = 0; i < table.getRowCount(); i++) {
                    if(tableModel.getValueAt(i, z) == null){
                        continue;
                    }

                    double d = Double.parseDouble((String) tableModel.getValueAt(i, z));

                    switch (z){
                        case 0:
                            listManager.addToL1(d);
                            break;
                        case 1:
                            listManager.addToL2(d);
                            break;
                        case 2:
                            listManager.addToL3(d);
                            break;
                    }
                }
            }

            saveSettings.update();

            Log.info("L1={" + listManager.toString(ListManager.Lists.L1) + "} S: " + listManager.getL1().size());
            Log.info("L2={" + listManager.toString(ListManager.Lists.L2) + "} S: " + listManager.getL2().size());
            Log.info("L3={" + listManager.toString(ListManager.Lists.L3) + "} S: " + listManager.getL3().size());

            frame.dispose();
        });
        panelBottom.add(btnOK, BorderLayout.LINE_END);
    }
}
