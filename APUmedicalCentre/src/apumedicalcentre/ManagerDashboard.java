package apumedicalcentre;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ManagerDashboard extends javax.swing.JFrame {
    private Manager manager;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerDashboard.class.getName());

    private static final Path APPOINTMENT_PATH = Paths.get(
    "src/Data/appointment.txt");
    private javax.swing.table.DefaultTableModel apptModel;
    
    public ManagerDashboard(Manager manager) {
        this.manager = manager;
        initComponents();
        configureAppointmentTable();
        loadAppointmentsIntoTable();

    }

private void configureAppointmentTable() {
    String[] cols = {
        "Appointment ID", "Customer ID", "Customer",
        "Doctor ID", "Date", "Start Time", "Visit Reason","Status"
    };
    Class<?>[] types = {
        String.class, String.class, String.class,
        String.class, String.class, String.class, String.class, String.class
    };

    apptModel = new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public Class<?> getColumnClass(int c) { return types[c]; }
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    // Important: replace the design-time model so the dummy rows disappear
    appointmentTable.setModel(apptModel);
    appointmentTable.setAutoCreateRowSorter(true);
    appointmentTable.setRowHeight(28);

}

private void loadAppointmentsIntoTable() {
    apptModel.setRowCount(0);

    if (!java.nio.file.Files.exists(APPOINTMENT_PATH)) {
        javax.swing.JOptionPane.showMessageDialog(
            this, "appointment.txt not found:\n" + APPOINTMENT_PATH.toAbsolutePath()
        );
        return;
    }

    try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(APPOINTMENT_PATH)) {
        String header = br.readLine(); // skip header line
        String line;
        int lineNo = 1;
        while ((line = br.readLine()) != null) {
            lineNo++;
            if (line.trim().isEmpty()) continue;

            java.util.List<String> f = parseCsv(line);

            // Expected CSV columns (exactly 7):
            // 0 AppointmentID, 1 CustomerID, 2 CustomerName,
            // 3 DoctorID,     4 DoctorName,  5 Date, 6 StartTime
            if (f.size() != 8) {
                java.util.logging.Logger.getLogger(getClass().getName())
                        .warning("Skipping malformed line " + lineNo + " (fields=" + f.size() + "): " + line);
                continue;
            }

            // Add in the SAME order as the headers above
            apptModel.addRow(new Object[] {
                f.get(0).trim(), 
                f.get(1).trim(), 
                f.get(2).trim(), 
                f.get(3).trim(), 
                f.get(4).trim(), 
                f.get(5).trim(),
                f.get(6).trim(),
                f.get(7).trim()     
            });
        }
    } catch (java.io.IOException ex) {
        java.util.logging.Logger.getLogger(getClass().getName())
            .log(java.util.logging.Level.SEVERE, "Failed to read appointment file", ex);
        javax.swing.JOptionPane.showMessageDialog(this, "Error reading appointment.txt:\n" + ex.getMessage());
    }
}

private static java.util.List<String> parseCsv(String line) {
    java.util.List<String> out = new java.util.ArrayList<>();
    if (line == null) return out;
    StringBuilder sb = new StringBuilder();
    boolean inQ = false;
    for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (c == '"') {
            if (inQ && i + 1 < line.length() && line.charAt(i + 1) == '"') { sb.append('"'); i++; }
            else inQ = !inQ;
        } else if (c == ',' && !inQ) {
            out.add(sb.toString());
            sb.setLength(0);
        } else sb.append(c);
    }
    out.add(sb.toString());
    return out;
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        ManagerDashboard = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        reportButton = new javax.swing.JButton();
        doctorManagementButton = new javax.swing.JButton();
        feedbackButton = new javax.swing.JButton();
        staffManagemenButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        appointmentTable = new javax.swing.JTable();
        manageManagerButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        ManagementDashboard = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        ManagerDashboard.setBackground(new java.awt.Color(234, 246, 251));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Appointment");

        reportButton.setBackground(new java.awt.Color(66, 133, 244));
        reportButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reportButton.setForeground(new java.awt.Color(255, 255, 255));
        reportButton.setText("Report Overview");
        reportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportButtonActionPerformed(evt);
            }
        });

        doctorManagementButton.setBackground(new java.awt.Color(66, 133, 244));
        doctorManagementButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        doctorManagementButton.setForeground(new java.awt.Color(255, 255, 255));
        doctorManagementButton.setText("Doctor Management");
        doctorManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorManagementButtonActionPerformed(evt);
            }
        });

        feedbackButton.setBackground(new java.awt.Color(66, 133, 244));
        feedbackButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        feedbackButton.setForeground(new java.awt.Color(255, 255, 255));
        feedbackButton.setText("Feedback");
        feedbackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedbackButtonActionPerformed(evt);
            }
        });

        staffManagemenButton.setBackground(new java.awt.Color(66, 133, 244));
        staffManagemenButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        staffManagemenButton.setForeground(new java.awt.Color(255, 255, 255));
        staffManagemenButton.setText("Staff Management");
        staffManagemenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staffManagemenButtonActionPerformed(evt);
            }
        });

        appointmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Appointment ID", "Customer ID", "Customer", "Doctor ID", "Date", "Start Time", "Visit Reason", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        appointmentTable.setRowHeight(30);
        appointmentTable.setShowGrid(true);
        jScrollPane1.setViewportView(appointmentTable);

        manageManagerButton.setBackground(new java.awt.Color(66, 133, 244));
        manageManagerButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        manageManagerButton.setForeground(new java.awt.Color(255, 255, 255));
        manageManagerButton.setText("Manager Management");
        manageManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageManagerButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ManagementDashboard.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ManagementDashboard.setText("Management Dashboard");

        logoutButton.setBackground(new java.awt.Color(66, 133, 244));
        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Log Out");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ManagementDashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ManagementDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ManagerDashboardLayout = new javax.swing.GroupLayout(ManagerDashboard);
        ManagerDashboard.setLayout(ManagerDashboardLayout);
        ManagerDashboardLayout.setHorizontalGroup(
            ManagerDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagerDashboardLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 987, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManagerDashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ManagerDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ManagerDashboardLayout.createSequentialGroup()
                        .addComponent(reportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(manageManagerButton)
                        .addGap(18, 18, 18)
                        .addComponent(doctorManagementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(staffManagemenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(feedbackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ManagerDashboardLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)))
                .addGap(16, 16, 16))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ManagerDashboardLayout.setVerticalGroup(
            ManagerDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagerDashboardLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(ManagerDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageManagerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doctorManagementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(staffManagemenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feedbackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 6, 6);
        getContentPane().add(ManagerDashboard, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportButtonActionPerformed
        new ManagerReport().setVisible(true);
    }//GEN-LAST:event_reportButtonActionPerformed

    private void doctorManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorManagementButtonActionPerformed
        new ManageDoctor(manager).setVisible(true);
    }//GEN-LAST:event_doctorManagementButtonActionPerformed

    private void feedbackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedbackButtonActionPerformed
        new ManagerFeedback().setVisible(true);
    }//GEN-LAST:event_feedbackButtonActionPerformed

    private void staffManagemenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffManagemenButtonActionPerformed
        new ManageStaff(manager).setVisible(true);
    }//GEN-LAST:event_staffManagemenButtonActionPerformed

    private void manageManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageManagerButtonActionPerformed
        new ManageManager(manager).setVisible(true);
    }//GEN-LAST:event_manageManagerButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
         int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are You Sure You Want To Log Out?",
        "Confirm Logout",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        // Close the dashboard
        this.dispose();

        // Redirect to Login Frame (replace LoginFrame with your actual login class name)
        new Login().setVisible(true);
    }
  
    }//GEN-LAST:event_logoutButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ManagementDashboard;
    private javax.swing.JPanel ManagerDashboard;
    private javax.swing.JTable appointmentTable;
    private javax.swing.JButton doctorManagementButton;
    private javax.swing.JButton feedbackButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton manageManagerButton;
    private javax.swing.JButton reportButton;
    private javax.swing.JButton staffManagemenButton;
    // End of variables declaration//GEN-END:variables
}
