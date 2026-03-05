/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apumedicalcentre;

import apumedicalcentre.User;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author User
 */
public class Staff extends User {
    
    
    private static final String DOCTOR_FILE = "src/Data/doctor.txt";
    private static final String APPOINTMENT_FILE = "src/Data/appointment.txt";
    
    public Staff(User staff){
        super(staff.getUserId(),staff.getUsername(),staff.getPassword(),staff.getRole());
    }
    
    public Staff(String userId,String username,String password,String role){
        super(userId,username,password,role);
       
    }
    
    public void logOperation(String action) {
        System.out.println( "Action=" + action +", StaffID=" + getUserId() + ", Name=" + getUsername());
    }


    //to find value from another file used in AllTranssaction
    public static String getValueById(File file, String id, String idColumn, String targetColumn) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine == null) return null;

            String[] headers = headerLine.split(",");
            int idIndex = Arrays.asList(headers).indexOf(idColumn);
            int targetIndex = Arrays.asList(headers).indexOf(targetColumn);

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[idIndex].equals(id)) {
                    return values[targetIndex];
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }
    
    public void updateDateTime(File file, String id, String idColumn, String dateColumn, String timeColumn) {
        try {
            List<String[]> records = new ArrayList<>();
            String headerLine;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                headerLine = br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    records.add(line.split(",", -1));
                }
            }

            String[] headers = headerLine.split(",");
            int idIndex = Arrays.asList(headers).indexOf(idColumn);
            int dateIndex = Arrays.asList(headers).indexOf(dateColumn);
            int timeIndex = Arrays.asList(headers).indexOf(timeColumn);

            // get current date and time
            String currentDate = java.time.LocalDate.now().toString();  //format:2025-09-17
            String currentTime = java.time.LocalTime.now().withSecond(0).withNano(0).toString(); //10:15

            for (String[] record : records) {
                if (record[idIndex].equals(id)) {
                    record[dateIndex] = currentDate;
                    record[timeIndex] = currentTime;
                }
            }

            // update
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(headerLine);
                bw.newLine();
                for (String[] record : records) {
                    bw.write(String.join(",", record));
                    bw.newLine();
                }
            logOperation("updateDateTime");
            }
        } catch (IOException e) {
            System.out.println("Error updating date/time: " + e.getMessage());
        }
    }
    
    public static void loadColumns(JComboBox<String> comboBox, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] columns = headerLine.split(",");
                for (String col : columns) {
                    comboBox.addItem(col.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public boolean deleteRecordById(File file, String id, String idColumn) {
        File tempFile = new File(file.getParent(), "temp_" + file.getName());
        boolean deleted = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile))
        ) {
            String header = br.readLine();
            if (header == null) return false;
            pw.println(header);

            String[] headers = header.split(",");
            int idIndex = -1;
            int nameIndex = -1;

            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(idColumn)) idIndex = i;
                if (headers[i].equalsIgnoreCase("Name")) nameIndex = i;
            }

            if (idIndex == -1 || nameIndex == -1) {
                JOptionPane.showMessageDialog(null, "Invalid column configuration.");
                return false;
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > idIndex && parts[idIndex].equals(id)) {
                    String customerName = (nameIndex < parts.length) ? parts[nameIndex] : "(unknown)";
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "Delete this record?\n\nID: " + id + "\nName: " + customerName,
                            "Confirm Deletion",
                            JOptionPane.OK_CANCEL_OPTION
                    );
                    if (result == JOptionPane.OK_OPTION) {
                        JOptionPane.showMessageDialog(null, "Record deleted successfully.");
                        deleted = true;
                        continue; // skip this record (effectively delete)
                    }
                }
                pw.println(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while deleting record: " + e.getMessage());
            return false;
        }

        // Replace original file with temp file if deleted
        if (deleted) {
            if (!file.delete() || !tempFile.renameTo(file)) {
                JOptionPane.showMessageDialog(null, "Error replacing original file.");
                return false;
            }
            logOperation("deleteRecordById");
        } else {
            tempFile.delete();
        }

        return deleted;
    }
    
    public boolean updateRecordById(File file, String id, String idColumn, String column, String newValue) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String headerLine = br.readLine();
            if (headerLine == null) {
                JOptionPane.showMessageDialog(null, "File is empty.");
                br.close();
                return false;
            }

            String[] headers = headerLine.split(",");
            int idIndex = Arrays.asList(headers).indexOf(idColumn);
            int colIndex = Arrays.asList(headers).indexOf(column);

            if (idIndex == -1 || colIndex == -1) {
                JOptionPane.showMessageDialog(null, "Invalid column name.");
                br.close();
                return false;
            }

            if (colIndex == 0) {
                JOptionPane.showMessageDialog(null, "ID column cannot be edited.");
                br.close();
                return false;
            }

            List<String[]> records = new ArrayList<>();
            String line;
            boolean updated = false;
            String oldValue = "";

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > idIndex && values[idIndex].equals(id)) {
                    oldValue = values[colIndex];
                    int result = JOptionPane.showConfirmDialog(null,
                            "ID: " + id + "\nColumn: " + column + "\nOld value: " + oldValue + "\nNew value: " + newValue +
                                    "\n\nDo you want to update?",
                            "Confirm Update",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        values[colIndex] = newValue;
                        updated = true;
                    }
                }
                records.add(values);
            }
            br.close();

            if (updated) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(headerLine);
                bw.newLine();
                for (String[] record : records) {
                    bw.write(String.join(",", record));
                    bw.newLine();
                }
                bw.close();
                JOptionPane.showMessageDialog(null, "Record updated successfully.");
                logOperation("updateRecordById");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Record not found.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating file: " + e.getMessage());
        }
        return false;
    }
    
    // Load specific column into a ComboBox
    public static void loadColumnIntoComboBox(File file, String columnName, JComboBox<String> comboBox) {
        comboBox.removeAllItems(); // clear existing items first

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine == null) return;

            String[] headers = headerLine.split(",");
            int targetIndex = -1;

            // find the column index
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnName)) {
                    targetIndex = i;
                    break;
                }
            }

            if (targetIndex == -1) {
                System.out.println("Column not found: " + columnName);
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > targetIndex) {
                    comboBox.addItem(parts[targetIndex]); // add Name into comboBox
                }
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "An error occurred while loading the file.");
        }
    }


    public boolean assignDoctorToAppointment(String appointmentID, String doctorName) throws IOException {
        String doctorID = getDoctorIDByName(doctorName);
        if (doctorID == null) return false;

        List<String> lines = new ArrayList<>();
        String[] targetRecord = null;

        // Read appointment.txt
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                String[] parts = line.split(",", -1);
                if (parts[0].equals(appointmentID)) {
                    targetRecord = parts;
                }
            }
        }

        if (targetRecord == null) return false;

        String targetDate = targetRecord[4];
        String targetTime = targetRecord[5];

        // Check double booking
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (!parts[0].equals(appointmentID) && parts[3].equals(doctorID)) {
                if (parts[4].equals(targetDate) && parts[5].equals(targetTime)) {
                    return false; // double booking detected
                }
            }
        }

        // Update appointment
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (parts[0].equals(appointmentID)) {
                parts[3] = doctorID; // assign DoctorID
                updatedLines.add(String.join(",", parts));
            } else {
                updatedLines.add(line);
            }
        }

        // Write back to file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
                //this.logOperation("assignDoctorToAppointment");
            }
        }
        logOperation("assignDoctorToAppointment");
        return true;
    }

    private String getDoctorIDByName(String doctorName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts[1].equals(doctorName)) {
                    return parts[0]; // DoctorID
                }
            }
        }
        return null;
    }
}
