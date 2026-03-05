package apumedicalcentre;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import javax.swing.table.TableRowSorter;

public class ManageManager extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageManager.class.getName());
  private static final java.nio.file.Path MANAGER_PATH = java.nio.file.Paths.get("src/Data/manager.txt");

    private final Manager manager;


    
    private DefaultTableModel tableModel;
    
    public ManageManager(Manager manager) {
        this.manager = manager;
        initComponents();       
        configureTable();
    }
    private void configureTable() {
        String[] cols = { "ManagerID", "Name", "Password" };
        Class<?>[] types = { String.class, String.class, String.class };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public Class<?> getColumnClass(int c) { return types[c]; }
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        managerTable.setModel(tableModel);
        managerTable.setAutoCreateRowSorter(true);
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        managerTable.setRowSorter(sorter);

        // Add search box listener
        searchTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        private void filter() {
        String text = searchTextField.getText();
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
    @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
    @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
    @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
});
        manager.loadManagersInto(tableModel);
    }
    
 
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        managerTable = new javax.swing.JTable();
        addManagerButton = new javax.swing.JButton();
        editManagerButton = new javax.swing.JButton();
        deleteManagerButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(234, 246, 251));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        managerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        managerTable.setRowHeight(30);
        managerTable.setShowGrid(true);
        jScrollPane1.setViewportView(managerTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 928;
        gridBagConstraints.ipady = 331;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(33, 15, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        addManagerButton.setBackground(new java.awt.Color(66, 133, 244));
        addManagerButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addManagerButton.setForeground(new java.awt.Color(255, 255, 255));
        addManagerButton.setText("Add Manager");
        addManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addManagerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 57;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 37, 6, 0);
        jPanel1.add(addManagerButton, gridBagConstraints);

        editManagerButton.setBackground(new java.awt.Color(66, 133, 244));
        editManagerButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editManagerButton.setForeground(new java.awt.Color(255, 255, 255));
        editManagerButton.setText("Edit Manager");
        editManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editManagerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 59;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 179, 6, 0);
        jPanel1.add(editManagerButton, gridBagConstraints);

        deleteManagerButton.setBackground(new java.awt.Color(66, 133, 244));
        deleteManagerButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteManagerButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteManagerButton.setText("Delete Manager");
        deleteManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteManagerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 164, 6, 0);
        jPanel1.add(deleteManagerButton, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Manage Manager");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(792, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipadx = 786;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        jPanel1.add(jPanel2, gridBagConstraints);

        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(102, 102, 102));
        searchLabel.setText("Search");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 15, 0, 0);
        jPanel1.add(searchLabel, gridBagConstraints);

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 907;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 0, 0);
        jPanel1.add(searchTextField, gridBagConstraints);

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteManagerButtonActionPerformed
        int vRow = managerTable.getSelectedRow();
    if (vRow < 0) { JOptionPane.showMessageDialog(this, "Select a manager to delete."); return; }
    int row = managerTable.convertRowIndexToModel(vRow);
    String id = String.valueOf(tableModel.getValueAt(row, 0));

    if (JOptionPane.showConfirmDialog(this, "Delete manager " + id + " ?", "Confirm",
            JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

    boolean ok = manager.deleteManager(id);
    if (ok) {
        manager.loadManagersInto(tableModel);
        JOptionPane.showMessageDialog(this, "Manager deleted: " + id);
    } else {
        JOptionPane.showMessageDialog(this, "Delete failed for " + id);
    }

    }//GEN-LAST:event_deleteManagerButtonActionPerformed

    private void editManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editManagerButtonActionPerformed
         int vRow = managerTable.getSelectedRow();
        if (vRow < 0) { JOptionPane.showMessageDialog(this, "Select a manager to edit."); return; }
        int row = managerTable.convertRowIndexToModel(vRow);

        String id   = String.valueOf(tableModel.getValueAt(row, 0));
        String name = String.valueOf(tableModel.getValueAt(row, 1));
        String pass = String.valueOf(tableModel.getValueAt(row, 2));

        JTextField nameField = new JTextField(name);
        JTextField pwdField  = new JTextField(); // optional

        JPanel panel = new JPanel(new GridLayout(2,2,6,6));
        panel.add(new JLabel("Full Name:")); panel.add(nameField);
        panel.add(new JLabel("New Password:")); panel.add(pwdField);

        if (JOptionPane.showConfirmDialog(this, panel, "Edit Manager " + id, JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        String newName = nameField.getText().trim();
        String newPwd  = pwdField.getText().trim();

        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }

        boolean ok = manager.editManager(id, newName, newPwd.isEmpty() ? null : newPwd);
        if (ok) {
            manager.loadManagersInto(tableModel);
            JOptionPane.showMessageDialog(this, "Manager updated: " + id);
        } else {
            JOptionPane.showMessageDialog(this, "Update failed for " + id);
        }
    

    }//GEN-LAST:event_editManagerButtonActionPerformed

    private void addManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addManagerButtonActionPerformed
        JTextField nameField = new JTextField();
        JTextField pwdField  = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2,2,6,6));
        panel.add(new JLabel("Full Name:")); panel.add(nameField);
        panel.add(new JLabel("Password:"));  panel.add(pwdField);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Manager", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String pwd  = pwdField.getText().trim();
        if (name.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }
        String id = manager.addManager(name, pwd);
        manager.loadManagersInto(tableModel);
        JOptionPane.showMessageDialog(this, "Manager added: " + id);
    
    }//GEN-LAST:event_addManagerButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addManagerButton;
    private javax.swing.JButton deleteManagerButton;
    private javax.swing.JButton editManagerButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable managerTable;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables
}

