/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Frame.java to edit this template
 */
package apumedicalcentre;

import java.io.*;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tan Qi MIng
 */
public class CheckAppointment extends java.awt.Frame {
    private Doctor doctor;
    /**
     * Creates new form CheckAppointment
     */
    public CheckAppointment(Doctor doctor) {
        this.doctor = doctor;
        initComponents();
        loadUpcomingAppointments("UPCOMING", jTable1);
        loadHistoryAppointments();
    }
    
    
    
    private void loadUpcomingAppointments(String statusFilter, javax.swing.JTable table) {
        String fileName = "src/Data/appointment.txt";
        String[] columns = {"AppointmentID", "CustomerID", "CustomerName", "Date", "StartTime", "VisitReason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 8) {
                    String doctorId = data[3].trim();
                    String status = data[7].trim();
                    if (doctor != null && doctorId.equals(doctor.getUserId())) {
                        
                            LocalDate appointmentDate = LocalDate.parse(data[4].trim());

                            if (!appointmentDate.isBefore(LocalDate.now())&& !isStatusCompleted(status)) {
                                model.addRow(new Object[]{
                                    data[0].trim(), // AppointmentID
                                    data[1].trim(), // CustomerID
                                    data[2].trim(), // CustomerName
                                    data[4].trim(), // Date
                                    data[5].trim(), // StartTime
                                    data[6].trim(), // VisitReason
                                    status  // Status
                                });
                            }
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading appointments file.");
        }
        table.setModel(model);
    }
    
    private void loadHistoryAppointments() {
        String fileName = "src/Data/appointment.txt";
        String[] columns = {"AppointmentID", "CustomerID", "Amount", "C_fromCustomer", "C_toCustomer", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] data = line.split(",", -1);
                if (data.length >= 8) {
                    String appointmentId = data[0].trim();
                    String customerId = data[1].trim();
                    String doctorId = data[3].trim();
                    String status = data[7].trim();

                    if (doctor != null && doctorId.equals(doctor.getUserId()) && isStatusCompleted(status)) {
                        // find payment amount (first matching payment record)
                        String amount = findPaymentAmountForAppointment(appointmentId);

                        // find feedback(s). We want:
                        // - commentFromCustomer (FromUserID startsWith "C")
                        // - commentFromDoctor   (FromUserID startsWith "D")
                        String[] feedbackPair = findFeedbacksForAppointment(appointmentId);
                        String commentFromCustomer = feedbackPair[0];
                        String commentFromDoctor = feedbackPair[1];

                        model.addRow(new Object[]{
                            appointmentId,
                            customerId,
                            amount != null ? amount : "",
                            commentFromCustomer != null ? commentFromCustomer : "",
                            commentFromDoctor != null ? commentFromDoctor : "",
                            status
                        });
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading history appointments file.");
        }

        jTable2.setModel(model);
    }

    private String findPaymentAmountForAppointment(String appointmentId) {
        File paymentFile = new File("src/Data/payment.txt");
        if (!paymentFile.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(paymentFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    String apptId = parts[1].trim();
                    if (apptId.equals(appointmentId)) {
                        return parts[2].trim(); // Amount
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String[] findFeedbacksForAppointment(String appointmentId) {
        String fromCustomer = null;
        String fromDoctor = null;
        File feedbackFile = new File("src/Data/feedback.txt");
        if (!feedbackFile.exists()) return new String[]{null, null};

        try (BufferedReader br = new BufferedReader(new FileReader(feedbackFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String apptId = parts[1].trim();
                    if (!apptId.equals(appointmentId)) continue;

                    String fromUser = parts[2].trim();
                    String commentText = parts[4].trim();
                    // If FromUser starts with 'C' -> from customer
                    if (fromUser.length() > 0 && Character.toUpperCase(fromUser.charAt(0)) == 'C') {
                        // if multiple customer comments, keep first (or you could append)
                        if (fromCustomer == null || fromCustomer.isEmpty()) fromCustomer = commentText;
                    } else if (fromUser.length() > 0 && Character.toUpperCase(fromUser.charAt(0)) == 'D') {
                        if (fromDoctor == null || fromDoctor.isEmpty()) fromDoctor = commentText;
                    } else {
                        // Unknown prefix: decide to store as fromCustomer if not set
                        if (fromCustomer == null) fromCustomer = commentText;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{fromCustomer, fromDoctor};
    }
    private boolean isStatusCompleted(String status) {
        if (status == null) return false;
        String s = status.trim().toUpperCase();
        return s.equals("COMPLETED") || s.equals("COMPLETE") || s.equals("CO");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(234, 246, 251));

        jPanel1.setBackground(new java.awt.Color(234, 246, 251));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Charges :");

        jLabel2.setText("Feedback :");

        jButton1.setText("Enter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit Profile");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(212, 212, 212)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Appointment", jPanel1);

        jPanel2.setBackground(new java.awt.Color(234, 246, 251));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("History", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Appointment");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment.");
            return;
        }

        String appointmentId = jTable1.getValueAt(row, 0).toString();
        String customerId    = jTable1.getValueAt(row, 1).toString();
        String dateStr       = jTable1.getValueAt(row, 3).toString();
        String charges       = jTextField1.getText().trim();
        String feedback      = jTextField2.getText().trim();

        if (charges.isEmpty() || feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter charges and feedback.");
            return;
        }

        LocalDate appointmentDate = LocalDate.parse(dateStr);
        if (!appointmentDate.equals(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "You can only complete appointments scheduled for today.");
            return;
        }

        doctor.completeAppointment(appointmentId, customerId, charges, feedback);
        loadUpcomingAppointments("UPCOMING", jTable1);
        loadHistoryAppointments();
        jTextField1.setText("");
        jTextField2.setText("");
        JOptionPane.showMessageDialog(this, "Payment and feedback saved.");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DoctorEditProfile dep = new DoctorEditProfile(doctor);
        dep.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         int choice = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to logout?",
        "Confirm Logout",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (choice == JOptionPane.YES_OPTION) {
        // Close this Edit Profile window
        this.setVisible(false);
        this.dispose();

        // Redirect to login page (replace Login with your login frame class)
        new Login().setVisible(true);
    }// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
