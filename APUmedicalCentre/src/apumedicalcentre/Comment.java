/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Frame.java to edit this template
 */
package apumedicalcentre;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tan Qi MIng
 */
public class Comment extends java.awt.Frame {
    private Customer customer;
    /**
     * Creates new form Comment
     */
    public Comment(Customer customer) {
        initComponents();
        this.customer = customer;
        
        String[] columnNames = {"UserID","Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        jTable2.setModel(model);
        loadUser();
    }
    
    private String findAppointmentId(String customerId,String doctorId){
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/appointment.txt"))){
            String line;
            boolean isFirstLine = true;
            while((line = br.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false; 
                    continue;
                }
                String[] parts = line.split(",");
                if(parts.length >= 4){
                    String appointmentId = parts[0].trim();
                    String custId = parts[1].trim();
                    String docId = parts[3].trim();
                    String status = parts[7].trim();
                    if (custId.equals(customerId) && docId.equals(doctorId) && status.equalsIgnoreCase("COMPLETED")) {
                        return appointmentId;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private void loadUser(){
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        Set<String> doctorIds = new HashSet<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/appointment.txt"))){
            String line;
            boolean isFirstLine = true;
            
            while((line = br.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                if(line.trim().isEmpty()){
                    continue;
                }
            
                String[] parts = line.split(",");
                if(parts.length >= 4){
                    String custId = parts[1].trim();
                    String docId = parts[3].trim();
                    String status = parts[7].trim();
                    if(custId.equals(customer.getUserId())&& status.equalsIgnoreCase("COMPLETED")){
                        doctorIds.add(docId);
                    }
                }
            }
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/doctor.txt"))){
            String dline;
            boolean isFirstLine = true;
            while((dline = br.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] parts = dline.split(",");
                if(parts.length >= 3){
                    String doctorId = parts[0].trim();
                    String doctorName = parts[1].trim();
                    if(doctorIds.contains(doctorId)){
                      model.addRow(new Object[]{doctorId,doctorName});  
                    }
                    
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/staff.txt"))){
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if (isFirstLine) {
                    isFirstLine = false; 
                    continue;
                }
                String[] parts = line.split(",");
                if(parts.length >= 3){
                    String staffId = parts[0].trim();
                    String staffName = parts[1].trim();
                    model.addRow(new Object[]{staffId, staffName});
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private String generateFeedbacktId(){
        String lastId = "F000";
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/feedback.txt"))){
            String line = br.readLine();
            while((line = br.readLine()) !=  null){
                if(line.trim().isEmpty())continue;
                String[] data = line.split(",");
                if(data.length > 0 && data[0].startsWith("F")){
                    lastId = data[0].trim();
                }
            }
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this,"Starting new list");
        }
        int num = Integer.parseInt(lastId.substring(1))+1;
        return String.format("F%03d", num);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        giveFeedback = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(234, 246, 251));

        jLabel2.setText("Feedback :");

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

        giveFeedback.setBackground(new java.awt.Color(66, 133, 244));
        giveFeedback.setForeground(new java.awt.Color(255, 255, 255));
        giveFeedback.setText("Reply");
        giveFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveFeedbackActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(66, 133, 244));
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Comment");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(giveFeedback)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(backButton)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(giveFeedback))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void giveFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveFeedbackActionPerformed
        int selectedRow = jTable2.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Please select a user to give feedback.");
            return;
        }
        
        String feedbackId = generateFeedbacktId();
        String fromId = customer.getUserId();
        String toId = jTable2.getValueAt(selectedRow,0).toString();
        String commentText = jTextField1.getText().trim();
        String date = java.time.LocalDate.now().toString();
        if(commentText.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter your feedback.");
            return;
        }
        
        String appointmentId = "N/A";
        
        if(fromId.startsWith("C") && toId.startsWith("D")){
            appointmentId = findAppointmentId(fromId,toId);
        }else if(fromId.startsWith("D") && toId.startsWith("C")){
            appointmentId = findAppointmentId(toId, fromId);
        }
        
        if (appointmentId == null) {
            JOptionPane.showMessageDialog(this, "No appointment found between these users.");
            return;
        }//deletable
        
        try(FileWriter fw = new FileWriter("src/Data/feedback.txt",true);
            BufferedWriter bw = new BufferedWriter(fw)){
            bw.write(feedbackId + "," + appointmentId + "," + fromId + "," + toId + "," + commentText + "," + date);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");
            jTextField1.setText("");
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error saving feedback!");
        }
    }//GEN-LAST:event_giveFeedbackActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        customerMP cmp = new customerMP(customer);
        cmp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton giveFeedback;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
