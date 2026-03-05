/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Frame.java to edit this template
 */
package apumedicalcentre;

import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Tan Qi MIng
 */
public class Appointment extends java.awt.Frame {

    private Customer customer;
    
    public Appointment(Customer customer) {
        this.customer = customer;
        initComponents();
        loadAppointments();
        setVisible(true);
    }
    
    private void updateAppointmentStatus(String appointmentId, String newStatus) {
        try{
            File inputFile = new File("src/Data/appointment.txt");
            File tempFile = new File("src/Data/appointment_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    writer.write(line);
                    writer.newLine();
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 8 && data[0].equals(appointmentId)) {
                    data[7] = newStatus;
                    line = String.join(",", data);
                }
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Error updating appointment status!");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void loadAppointments(){
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"AppointmentID","CustomerID","CustomerName","DoctorID","Date","StartTime","VisitReason"},0
        );
        jTable1.setModel(model);
        
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/appointment.txt"))){
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                if(data.length >= 6){
                    String appointmentId = data[0].trim();
                    String fileUserId = data[1].trim();
                    String customerName = data[2].trim();
                    String doctorId = data[3].trim();
                    String dateStr = data[4].trim();
                    String timeStr = data[5].trim();
                    String visitReason = data[6].trim();
                    String status = data[7].trim();
                    
                    if(fileUserId.equals(customer.getUserId())){
                        LocalDate date = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
                        LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);
                        
                        LocalDateTime now = LocalDateTime.now();
                        
                        if (appointmentDateTime.isBefore(now) && status.equalsIgnoreCase("UPCOMING")) {
                            status = "COMPLETED";
                            updateAppointmentStatus(appointmentId, "COMPLETED");
                        }
                        
                        if(appointmentDateTime.isAfter(now)&& !status.equalsIgnoreCase("CANCELLED")){
                            model.addRow(new Object[]{appointmentId,fileUserId,customerName,doctorId,dateStr,timeStr,visitReason});
                        }
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading appointments file!");
        }
    }
    
    
    
    private String generateAppointmentId(){
        String lastId = "A000";
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/appointment.txt"))){
            String line = br.readLine();
            while((line = br.readLine()) !=  null){
                if(line.trim().isEmpty())continue;
                String[] data = line.split(",");
                if(data.length > 0 && data[0].startsWith("A")){
                    lastId = data[0].trim();
                }
            }
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this,"Starting new list");
        }
        int num = Integer.parseInt(lastId.substring(1))+1;
        return String.format("A%03d", num);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        bookAppointment = new javax.swing.JButton();
        cancelAppointment = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jCalendar2 = new com.toedter.calendar.JCalendar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setText("jLabel1");

        jLabel4.setText("jLabel4");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(234, 246, 251));

        jPanel2.setBackground(new java.awt.Color(234, 246, 251));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Appointment Booking", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Variable", 0, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI Variable", 0, 12)); // NOI18N
        jLabel2.setText("Date :");

        jLabel3.setText("Time :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "08:00", "09:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00" }));

        jLabel6.setText("Visit Reason :");

        bookAppointment.setBackground(new java.awt.Color(66, 133, 244));
        bookAppointment.setForeground(new java.awt.Color(255, 255, 255));
        bookAppointment.setText("Book");
        bookAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookAppointmentActionPerformed(evt);
            }
        });

        cancelAppointment.setBackground(new java.awt.Color(66, 133, 244));
        cancelAppointment.setForeground(new java.awt.Color(255, 255, 255));
        cancelAppointment.setText("Cancel");
        cancelAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAppointmentActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(bookAppointment)
                        .addGap(33, 33, 33)
                        .addComponent(cancelAppointment)
                        .addGap(50, 50, 50)
                        .addComponent(backButton)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookAppointment)
                    .addComponent(cancelAppointment)
                    .addComponent(backButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void bookAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookAppointmentActionPerformed
        try{
            String appointmentId = generateAppointmentId();
            String customerId = customer.getUserId();
            String customerName = customer.getUsername();
            String doctorId = "Pending";
            Date date = jCalendar2.getDate();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = localDate.format(formatter);
            String startTime = (String)jComboBox1.getSelectedItem();
            String visitReason = jTextField2.getText().trim();
            String status = "UPCOMING";
            
            if (visitReason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a visit reason before booking!");
                return;
            }
            
            if(localDate.isBefore(LocalDate.now())){
                JOptionPane.showMessageDialog(this,"You cannot book an appointment in the past!");
                return;
            }
            FileWriter fw = new FileWriter("src/Data/appointment.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(appointmentId + "," + customerId + "," + customerName + "," + doctorId + "," + formattedDate + "," + startTime + "," + visitReason + "," + status);
            bw.newLine();
            bw.close();
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.addRow(new Object[]{appointmentId,customerId,customerName,doctorId,formattedDate,startTime,visitReason,status});
            JOptionPane.showMessageDialog(this, "Appointment booked successfully!");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error booking appointment!");
        }
    }//GEN-LAST:event_bookAppointmentActionPerformed

    private void cancelAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAppointmentActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
            return;
        }
        
        String appointmentId = jTable1.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure want to cancel this appointment?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm == 1) {
            return;
        }
        
        if (customer.cancelAppointment(appointmentId)){
            ((DefaultTableModel) jTable1.getModel()).removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Appointment canceled successfully!");
        }else{
            JOptionPane.showMessageDialog(this, "Error updating appointments file!");            }
        
    }//GEN-LAST:event_cancelAppointmentActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        customerMP cmp = new customerMP(customer);
        cmp.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_backButtonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton bookAppointment;
    private javax.swing.JButton cancelAppointment;
    private com.toedter.calendar.JCalendar jCalendar2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
