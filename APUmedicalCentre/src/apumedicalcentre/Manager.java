package apumedicalcentre;
    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.Component;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Manager extends User {
    
     private static final java.nio.file.Path DOCTOR_PATH = java.nio.file.Paths.get("src/Data/doctor.txt");
     private static final java.nio.file.Path MANAGER_PATH = java.nio.file.Paths.get("src/Data/manager.txt");
     private static final java.nio.file.Path STAFF_PATH = java.nio.file.Paths.get("src/Data/staff.txt");

    private static void JOption(String toString, IOException ex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

     
     
   public Manager(String userId, String username, String password, String role) {
        super(userId, username, password, role);
    }

   
    public void openDashboard(Manager manager) {
    new ManagerDashboard(manager).setVisible(true);
}

    private static final String DOCTOR_HEADER = "DoctorID,Name,Password,Specialist";
    private static final String STAFF_HEADER = "StaffID,Name,Password";

  public void loadDoctorsInto(DefaultTableModel tableModel) {
    tableModel.setRowCount(0);
    ensureHeader(DOCTOR_PATH, DOCTOR_HEADER);

    try (BufferedReader br = Files.newBufferedReader(DOCTOR_PATH)) {
        br.readLine(); // skip header
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) continue;
            var c = parseCsv(line);
            if (c.size() < 4) continue;

            String id   = c.get(0).trim();
            String name = c.get(1).trim();
            String spec = c.get(3).trim();  // hide password in UI

            // Skip a stray header accidentally saved as a row
            if ("DoctorID".equalsIgnoreCase(id)) continue;

            tableModel.addRow(new Object[]{ id, name, spec });
        }
    } catch (IOException ex) {
        showIoError("doctor.txt", ex);
    }
}

public String addDoctor(String name, String password, String specialist) {
    ensureHeader(DOCTOR_PATH, DOCTOR_HEADER);
    String nextId = nextIdFromFile(DOCTOR_PATH, 'D');
    String row = csv(nextId)+","+csv(name)+","+csv(password)+","+csv(specialist);
    appendLine(DOCTOR_PATH, row);
    return nextId;
}

public boolean editDoctor(String doctorId, String newName, String newPasswordOrNull, String newSpecialist) {
    return updateRowById(
        DOCTOR_PATH,
        "DoctorID",
        DOCTOR_HEADER,
        doctorId,                              // <-- use the parameter!
        cols -> {
            if (cols.length < 4) return cols;
            cols[1] = newName;                 // Name
            if (newPasswordOrNull != null) cols[2] = newPasswordOrNull; // Password (optional)
            cols[3] = newSpecialist;           // Specialist
            return cols;
        }
    );
}

public boolean deleteDoctor(String doctorId) {
    return updateRowById(
        DOCTOR_PATH,
        "DoctorID",
        DOCTOR_HEADER,
        doctorId,                              
        cols -> null                           
    );
}

    

     public void loadStaffInto(DefaultTableModel tableModel) {
          tableModel.setRowCount(0);
    ensureHeader(STAFF_PATH, STAFF_HEADER);

    try (BufferedReader br = Files.newBufferedReader(STAFF_PATH)) {
        br.readLine(); // skip header
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) continue;
            var c = parseCsv(line);
            if (c.size() < 3) continue;

            String id   = c.get(0).trim();
            String name = c.get(1).trim();
            String pass = c.get(2).trim();  // hide password in UI

            // Skip a stray header accidentally saved as a row
            if ("StaffID".equalsIgnoreCase(id)) continue;

            tableModel.addRow(new Object[]{ id, name, pass });
        }
    } catch (IOException ex) {
        showIoError("staff.txt", ex);
    }
     }

   public String addStaff(String name, String password) {
        ensureHeader(STAFF_PATH, STAFF_HEADER);
    String nextId = nextIdFromFile(STAFF_PATH, 'S');
    String row = csv(nextId)+","+csv(name)+","+csv(password);
    appendLine(STAFF_PATH, row);
    return nextId;
    }

public boolean editStaff(String staffId, String newName, String newPasswordOrNull) {
    return updateRowById(
        STAFF_PATH,
        "StaffID",
        STAFF_HEADER,
        staffId,
        cols -> {
            if (cols.length < 3) return cols;
            cols[1] = newName; // Name
            if (newPasswordOrNull != null) cols[2] = newPasswordOrNull; // Password (optional)
            return cols;
        }
    );
    }

    public boolean deleteStaff(String staffId) {
    return updateRowById(
        STAFF_PATH,
        "StaffID",
        STAFF_HEADER,
        staffId,
        cols -> null // returning null deletes the matched row
    );
}
    
      public void loadManagersInto(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        ensureHeader(MANAGER_PATH, "ManagerID,Name,Password");
        try (BufferedReader br = Files.newBufferedReader(MANAGER_PATH)) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                List<String> f = parseCsv(line);
                if (f.size() < 3) continue;
                String id   = f.get(0).trim();
                String name = f.get(1).trim();
                String pass = f.get(2).trim();
                tableModel.addRow(new Object[]{ id, name, pass });
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Read failed: " + MANAGER_PATH + "\n" + ex.getMessage());
        }
    }

      public String addManager(String name, String password) {
        ensureHeader(MANAGER_PATH, "ManagerID,Name,Password");
        String nextId = nextIdFromFile(MANAGER_PATH, 'M');
        String row = csv(nextId)+","+csv(name)+","+csv(password);
        appendLine(MANAGER_PATH, row);
        return nextId;
    }
    
      public boolean editManager(String managerId, String newName, String newPasswordOrNull) {
        ensureHeader(MANAGER_PATH, "ManagerID,Name,Password");
        try {
            List<String> all = Files.readAllLines(MANAGER_PATH);
            if (all.isEmpty()) all.add("ManagerID,Name,Password");
            for (int i = 1; i < all.size(); i++) {
                List<String> f = parseCsv(all.get(i));
                if (f.size() < 3) continue;
                if (f.get(0).trim().equals(managerId)) {
                    String oldPwd = f.get(2).trim();
                    String pass = (newPasswordOrNull == null || newPasswordOrNull.isEmpty()) ? oldPwd : newPasswordOrNull;
                    all.set(i, csv(managerId)+","+csv(newName)+","+csv(pass));
                    return rewrite(MANAGER_PATH, all);
                }
            }
            return false;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Update failed: " + MANAGER_PATH + "\n" + ex.getMessage());
            return false;
        }
    }


        public boolean deleteManager(String managerId) {
        ensureHeader(MANAGER_PATH, "ManagerID,Name,Password");
        try {
            List<String> all = Files.readAllLines(MANAGER_PATH);
            if (all.isEmpty()) return false;
            List<String> out = new ArrayList<>();
            out.add(all.get(0)); // header
            boolean removed = false;
            for (int i = 1; i < all.size(); i++) {
                List<String> f = parseCsv(all.get(i));
                if (!f.isEmpty() && f.get(0).trim().equals(managerId)) { removed = true; continue; }
                out.add(all.get(i));
            }
            return removed && rewrite(MANAGER_PATH, out);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Delete failed: " + MANAGER_PATH + "\n" + ex.getMessage());
            return false;
        }
    }
       
    

   protected static void ensureHeader(Path p, String header) {
    try {
        if (!Files.exists(p)) {
            Files.createDirectories(p.getParent());
            Files.write(p, java.util.List.of(header));
            return;
        }
        var all = Files.readAllLines(p);
        if (all.isEmpty()) {
            Files.write(p, java.util.List.of(header));
            return;
        }
        if (!all.get(0).trim().equalsIgnoreCase(header.trim())) {
            all.set(0, header);                // replace old/incorrect header
            Files.write(p, all);
        }
    } catch (IOException ignored) {}
}


    protected static String nextIdFromFile(Path p, char prefix) {
        int max = 0;
        if (!Files.exists(p)) {
            return String.format("%c%03d", Character.toUpperCase(prefix), 1);
        }
        try (BufferedReader br = Files.newBufferedReader(p)) {
            br.readLine(); // header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                List<String> cols = parseCsv(line);
                if (cols.isEmpty()) continue;
                String id = cols.get(0).trim();
                if (id.length() > 1 && Character.toUpperCase(id.charAt(0)) == Character.toUpperCase(prefix)) {
                    try {
                        int n = Integer.parseInt(id.substring(1));
                        if (n > max) max = n;
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException ignored) {}
        return String.format("%c%03d", Character.toUpperCase(prefix), max + 1);
    }

    protected static void appendLine(Path p, String row) {
        try (BufferedWriter bw = Files.newBufferedWriter(p, StandardOpenOption.APPEND)) {
            bw.write(row);
            bw.newLine();
        } catch (IOException ignored) {}
    }

    /** Update a row by ID. If mapper returns null -> delete that row. */
    protected static boolean updateRowById(
            Path p,
            String idHeaderName,
            String header,
            String idToMatch,
            java.util.function.Function<String[], String[]> mapper
    ) {
        ensureHeader(p, header);
        List<String> out = new ArrayList<>();
        boolean changed = false;

        try (BufferedReader br = Files.newBufferedReader(p)) {
            String head = br.readLine();
            if (head == null) head = header;
            out.add(head);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                List<String> colsList = parseCsv(line);
                String[] cols = colsList.toArray(new String[0]);

                // find index of ID column from header
                String[] heads = head.split(",");
                int idIdx = 0;
                for (int i = 0; i < heads.length; i++) {
                    if (heads[i].trim().equalsIgnoreCase(idHeaderName)) { idIdx = i; break; }
                }

                if (cols.length > idIdx && cols[idIdx].trim().equals(idToMatch)) {
                    String[] res = mapper.apply(cols);
                    changed = true;
                    if (res != null) out.add(String.join(",", mapCsv(res)));
                } else {
                    out.add(String.join(",", mapCsv(cols)));
                }
            }
        } catch (IOException ignored) {}

        if (changed) {
            try {
                Files.write(p, out);
            } catch (IOException ignored) {}
        }
        return changed;
    }

    private static List<String> mapCsv(String[] arr) {
        List<String> out = new ArrayList<>(arr.length);
        for (String s : arr) out.add(csv(s));
        return out;
    }

    /** Very small CSV (quotes + commas) */
    protected static List<String> parseCsv(String line) {
        List<String> out = new ArrayList<>();
        if (line == null) return out;
        boolean inQ = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQ && i + 1 < line.length() && line.charAt(i + 1) == '"') { sb.append('"'); i++; }
                else inQ = !inQ;
            } else if (c == ',' && !inQ) {
                out.add(sb.toString()); sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        out.add(sb.toString());
        return out;
    }

    protected static String csv(String s) {
        if (s == null) return "";
        String t = s.replace("\"", "\"\"");
        boolean needQ = t.contains(",") || t.contains("\"") || t.contains("\n");
        return needQ ? ("\"" + t + "\"") : t;
    }

    protected static void showIoError(String file, Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "I/O error with " + file + ":\n" + ex.getMessage());
    }

    private static boolean rewrite(Path p, java.util.List<String> lines) {
    try {
        Files.write(p, lines);
        return true;
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Write failed: " + p + "\n" + ex.getMessage());
        return false;
    }
}
}  

