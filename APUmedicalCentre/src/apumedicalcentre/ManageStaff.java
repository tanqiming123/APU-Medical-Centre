package apumedicalcentre;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.io.*;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.EventQueue;
import java.util.logging.Level; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



public class ManageStaff extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageStaff.class.getName());
    private static final java.nio.file.Path STAFF_PATH = java.nio.file.Paths.get("src/Data/staff.txt");

private final Manager manager;    
private DefaultTableModel tableModel;
    
    public ManageStaff(Manager manager) {
        this.manager = manager;
        initComponents();
        configureTable();
        manager.loadStaffInto(tableModel);
    }
    
    private void configureTable() {
        // Only show StaffID and Name (hide Password in UI but keep it in memory/file)
        String[] cols = { "StaffID", "Name" };
        Class<?>[] types = { String.class, String.class };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public Class<?> getColumnClass(int columnIndex) { return types[columnIndex]; }
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        staffTable.setModel(tableModel);
        staffTable.setAutoCreateRowSorter(true);
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        staffTable.setRowSorter(sorter);

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
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        staffTable = new javax.swing.JTable();
        deleteStaffButton = new javax.swing.JButton();
        editStaffButton = new javax.swing.JButton();
        addStaffButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(234, 246, 251));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        staffTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "StaffID", "Name", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        staffTable.setRowHeight(30);
        staffTable.setShowGrid(true);
        jScrollPane1.setViewportView(staffTable);

        deleteStaffButton.setBackground(new java.awt.Color(66, 133, 244));
        deleteStaffButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteStaffButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteStaffButton.setText("Delete Staff");
        deleteStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStaffButtonActionPerformed(evt);
            }
        });

        editStaffButton.setBackground(new java.awt.Color(66, 133, 244));
        editStaffButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editStaffButton.setForeground(new java.awt.Color(255, 255, 255));
        editStaffButton.setText("Edit Staff");
        editStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editStaffButtonActionPerformed(evt);
            }
        });

        addStaffButton.setBackground(new java.awt.Color(66, 133, 244));
        addStaffButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addStaffButton.setForeground(new java.awt.Color(255, 255, 255));
        addStaffButton.setText("Add Staff");
        addStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStaffButtonActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Manage Staff");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(743, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(102, 102, 102));
        searchLabel.setText("Search");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(addStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addComponent(deleteStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTextField)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(searchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(270, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipady = 264;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        getContentPane().add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 304, 0, 0);
        getContentPane().add(jPanel1, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStaffButtonActionPerformed
       int vRow = staffTable.getSelectedRow();
        if (vRow < 0) { JOptionPane.showMessageDialog(this, "Select a staff to delete."); return; }
        int row = staffTable.convertRowIndexToModel(vRow);
        String id = String.valueOf(tableModel.getValueAt(row, 0));

        if (JOptionPane.showConfirmDialog(this, "Delete staff " + id + " ?", "Confirm",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        boolean ok = manager.deleteStaff(id);
        if (ok) {
            manager.loadStaffInto(tableModel);
            JOptionPane.showMessageDialog(this, "Staff deleted: " + id);
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed for " + id);
        }
    
    }//GEN-LAST:event_deleteStaffButtonActionPerformed

    private void editStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editStaffButtonActionPerformed
         int vRow = staffTable.getSelectedRow();
        if (vRow < 0) { JOptionPane.showMessageDialog(this, "Select a staff to edit."); return; }
        int row = staffTable.convertRowIndexToModel(vRow);

        String id   = String.valueOf(tableModel.getValueAt(row, 0));
        String name = String.valueOf(tableModel.getValueAt(row, 1));

        JTextField nameField = new JTextField(name);
        JTextField pwdField  = new JTextField(); // optional

        JPanel p = new JPanel(new GridLayout(2,2,6,6));
        p.add(new JLabel("Full Name:"));           p.add(nameField);
        p.add(new JLabel("New Password (optional):")); p.add(pwdField);

        if (JOptionPane.showConfirmDialog(this, p, "Edit Staff " + id, JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        String newName = nameField.getText().trim();
        String newPwd  = pwdField.getText().trim();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }

        boolean ok = manager.editStaff(id, newName, newPwd.isEmpty() ? null : newPwd);
        if (ok) {
            manager.loadStaffInto(tableModel);
            JOptionPane.showMessageDialog(this, "Staff updated: " + id);
        } else {
            JOptionPane.showMessageDialog(this, "Update failed for " + id);
        }
    }//GEN-LAST:event_editStaffButtonActionPerformed

    private void addStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStaffButtonActionPerformed
        JTextField nameField = new JTextField();
        JTextField pwdField  = new JTextField();

        JPanel p = new JPanel(new GridLayout(2,2,6,6));
        p.add(new JLabel("Full Name:")); p.add(nameField);
        p.add(new JLabel("Password:"));  p.add(pwdField);

        if (JOptionPane.showConfirmDialog(this, p, "Add Staff", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String pwd  = pwdField.getText().trim();
        if (name.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        String id = manager.addStaff(name, pwd);
        manager.loadStaffInto(tableModel);
        JOptionPane.showMessageDialog(this, "Staff added: " + id);
    
    }//GEN-LAST:event_addStaffButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStaffButton;
    private javax.swing.JButton deleteStaffButton;
    private javax.swing.JButton editStaffButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable staffTable;
    // End of variables declaration//GEN-END:variables
}