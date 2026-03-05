package apumedicalcentre;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


public class ManageDoctor extends javax.swing.JFrame {
    private static final String DOCTOR_HEADER = "DoctorID,Name,Password,Specialist";

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageDoctor.class.getName());
     private static final java.nio.file.Path DOCTOR_PATH = java.nio.file.Paths.get("src/Data/doctor.txt");

     private final Manager manager;
     private DefaultTableModel tableModel;

    
     
    public ManageDoctor(Manager manager) {
        this.manager = manager;
        initComponents();
        configureTable();   
        
        manager.loadDoctorsInto(tableModel); 
    }


    private void configureTable() {
        // Hide Password in table; keep in memory only
        String[] cols = { "DoctorID", "Name", "Specialist"};
        Class<?>[] types = { String.class, String.class, String.class};

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public Class<?> getColumnClass(int c) { return types[c]; }
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        doctorTable.setModel(tableModel);
        doctorTable.setAutoCreateRowSorter(true);

       TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        doctorTable.setRowSorter(sorter);

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

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        doctorTable = new javax.swing.JTable();
        addDoctorButton = new javax.swing.JButton();
        editDoctorButton = new javax.swing.JButton();
        deleteDoctorButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(234, 246, 251));

        doctorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "DoctorID", "Name", "Password", "Specialist"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        doctorTable.setRowHeight(30);
        doctorTable.setShowGrid(true);
        jScrollPane1.setViewportView(doctorTable);

        addDoctorButton.setBackground(new java.awt.Color(66, 133, 244));
        addDoctorButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addDoctorButton.setForeground(new java.awt.Color(255, 255, 255));
        addDoctorButton.setText("Add Doctor");
        addDoctorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDoctorButtonActionPerformed(evt);
            }
        });

        editDoctorButton.setBackground(new java.awt.Color(66, 133, 244));
        editDoctorButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editDoctorButton.setForeground(new java.awt.Color(255, 255, 255));
        editDoctorButton.setText("Edit Doctor");
        editDoctorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDoctorButtonActionPerformed(evt);
            }
        });

        deleteDoctorButton.setBackground(new java.awt.Color(66, 133, 244));
        deleteDoctorButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteDoctorButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteDoctorButton.setText("Delete Doctor");
        deleteDoctorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDoctorButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Manage Doctor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1818, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(102, 102, 102));
        searchLabel.setText("Search");

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(searchTextField)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(addDoctorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213)
                .addComponent(editDoctorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteDoctorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addDoctorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editDoctorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteDoctorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addDoctorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDoctorButtonActionPerformed
        JTextField nameField = new JTextField();
        JTextField pwdField  = new JTextField();
        JTextField specField = new JTextField();
        JPanel p = new JPanel(new GridLayout(3,2,6,6));
        p.add(new JLabel("Full Name:"));  p.add(nameField);
        p.add(new JLabel("Password:"));   p.add(pwdField);
        p.add(new JLabel("Specialist:")); p.add(specField);
        if (JOptionPane.showConfirmDialog(this, p, "Add Doctor", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String pwd  = pwdField.getText().trim();
        String spec = specField.getText().trim();
        if (name.isEmpty() || pwd.isEmpty() || spec.isEmpty()) { JOptionPane.showMessageDialog(this, "All fields required."); return; }

        String id = manager.addDoctor(name, pwd, spec);
        manager.loadDoctorsInto(tableModel);
        JOptionPane.showMessageDialog(this, "Added: "+id);
    }//GEN-LAST:event_addDoctorButtonActionPerformed

    private void editDoctorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDoctorButtonActionPerformed
          int vRow = doctorTable.getSelectedRow();
    if (vRow < 0) { JOptionPane.showMessageDialog(this, "Select a doctor to edit."); return; }
    int row = doctorTable.convertRowIndexToModel(vRow);

    String id   = String.valueOf(tableModel.getValueAt(row, 0));
    String name = String.valueOf(tableModel.getValueAt(row, 1));
    String spec = String.valueOf(tableModel.getValueAt(row, 2));

    JTextField nameField = new JTextField(name);
    JTextField pwdField  = new JTextField(); // optional
    JTextField specField = new JTextField(spec);

    JPanel p = new JPanel(new GridLayout(3,2,6,6));
    p.add(new JLabel("Name:"));                        p.add(nameField);
    p.add(new JLabel("New Password (optional):"));     p.add(pwdField);
    p.add(new JLabel("Specialist:"));                  p.add(specField);

    if (JOptionPane.showConfirmDialog(this, p, "Edit Doctor " + id,
            JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

    String newName = nameField.getText().trim();
    String newPwd  = pwdField.getText().trim();
    String newSpec = specField.getText().trim();

    if (newName.isEmpty() || newSpec.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Name and Specialist are required.");
        return;
    }

    boolean ok = manager.editDoctor(id, newName, newPwd.isEmpty() ? null : newPwd, newSpec);
    if (ok) {
        manager.loadDoctorsInto(tableModel);
        JOptionPane.showMessageDialog(this, "Doctor updated: " + id);
    } else {
        JOptionPane.showMessageDialog(this, "Update failed for " + id);
    }
    }//GEN-LAST:event_editDoctorButtonActionPerformed

    private void deleteDoctorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDoctorButtonActionPerformed
    int vRow = doctorTable.getSelectedRow();
    if (vRow < 0) { JOptionPane.showMessageDialog(this, "Select a doctor to delete."); return; }
    int row = doctorTable.convertRowIndexToModel(vRow);
    String id = String.valueOf(tableModel.getValueAt(row, 0));

if (JOptionPane.showConfirmDialog(
        this,
        "Delete doctor " + id + " ?",
        "Confirm",
        JOptionPane.YES_NO_OPTION
    ) != JOptionPane.YES_OPTION) {
    return;
}

    boolean ok = manager.deleteDoctor(id);
    if (ok) {
        manager.loadDoctorsInto(tableModel);
        JOptionPane.showMessageDialog(this, "Doctor deleted: " + id);
    } else {
        JOptionPane.showMessageDialog(this, "Delete failed for " + id);
    }
    }//GEN-LAST:event_deleteDoctorButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDoctorButton;
    private javax.swing.JButton deleteDoctorButton;
    private javax.swing.JTable doctorTable;
    private javax.swing.JButton editDoctorButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables
}
