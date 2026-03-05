/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apumedicalcentre;

import java.io.*;

/**
 *
 * @author Tan Qi MIng
 */
public class Customer extends User{
    public Customer(User customer){
        super(customer.getUserId(),customer.getUsername(),customer.getPassword(),customer.getRole());
    }
    
    public Customer(String userId,String username,String password,String role){
        super(userId,username,password,role);
    }
    public static class AppointmentDetails{
        public String appointmentId;
        public String doctorId;
        public String date;
        public String visitReason;
        public String paymentAmount;
        public String doctorComment;
    }
    
    public AppointmentDetails getAppointmentDetails(String appointmentId){
        AppointmentDetails details = new AppointmentDetails();
        details.appointmentId = appointmentId;
        details.doctorId = "";
        details.date = "";
        details.visitReason = "";
        details.paymentAmount = "N/A";
        details.doctorComment = "No feedback";
        
        try (BufferedReader br = new BufferedReader(new FileReader("src/Data/appointment.txt"))){
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 8 && parts[0].trim().equals(appointmentId)){
                    details.doctorId = parts[3].trim();
                    details.date = parts[4].trim();
                    details.visitReason = parts[6].trim();
                    break;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("src/Data/feedback.txt"))){
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[1].trim().equals(appointmentId)) {
                    details.doctorComment = parts[4].trim();
                    break;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("src/Data/payment.txt"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].trim().equals(appointmentId)){
                    details.paymentAmount = parts[2].trim();
                    break;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return details;
    }
    
    public boolean cancelAppointment(String appointmentId){
    File inputFile = new File("src/Data/appointment.txt");
    File tempFile = new File("src/Data/appointment_temp.txt");
    
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    writer.write(line);  // write header
                    writer.newLine();
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 8 && data[0].trim().equals(appointmentId)) {
                
                    data[7] = "CANCELLED";
                    writer.write(String.join(",", data));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            return false;
        }
        return true;
    }
}
