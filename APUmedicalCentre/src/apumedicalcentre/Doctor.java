/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apumedicalcentre;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Tan Qi MIng
 */
public class Doctor extends User{
    public Doctor (User doctor){
        super(doctor.getUserId(),doctor.getUsername(),doctor.getPassword(),doctor.getRole());
    }
    
    public Doctor(String userId,String username,String password,String role){
        super(userId,username,password,role);
    }
    private String specialist;
    
    private String generateNextId(String fileName, String prefix) {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] data = line.split(",");
                if (data[0].startsWith(prefix)) {
                    int idNum = Integer.parseInt(data[0].substring(1));
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                }
            }
        } catch (IOException e) {
            // file may not exist yet, ignore
        }
        return prefix + String.format("%03d", maxId + 1);
    }
    
    public void completeAppointment(String appointmentId, String customerId, String charges, String feedback) {
        String paymentId = generateNextId("src/Data/payment.txt", "P");
        String feedbackId = generateNextId("src/Data/feedback.txt", "F");

        // Save payment
        try (FileWriter fw = new FileWriter("src/Data/payment.txt", true)) {
            fw.write(paymentId + "," + appointmentId + "," + charges + "," +
                    LocalDate.now() + "," +
                    LocalTime.now().withSecond(0).withNano(0) + "," +
                    customerId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save feedback (customer -> doctor OR doctor -> customer depending on UI)
        try (FileWriter fw = new FileWriter("src/Data/feedback.txt", true)) {
            fw.write(feedbackId + "," + appointmentId + "," +
                    customerId + "," +       // From (Customer)
                    this.getUserId() + "," + // To (Doctor)
                    feedback + "," +
                    LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update appointment status
        File inputFile = new File("src/Data/appointment.txt");
        File tempFile = new File("src/Data/appointment_temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
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
                if (data[0].equals(appointmentId)) {
                    data[data.length - 1] = "COMPLETED"; // overwrite status
                    writer.write(String.join(",", data));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.err.println("Failed to update appointment status.");
        }
    }

}
