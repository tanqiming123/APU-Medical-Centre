package apumedicalcentre;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ManagerReport extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerReport.class.getName());
    private static final java.nio.file.Path PAYMENT_PATH = java.nio.file.Paths.get("src/Data/payment.txt"); 
     private static final java.nio.file.Path APPOINTMENT_PATH = java.nio.file.Paths.get("src/Data/appointment.txt");
    
      private static final int COL_PAY_ID   = 0;
    private static final int COL_APPT_ID  = 1;
    private static final int COL_AMOUNT   = 2;
    private static final int COL_DATE     = 3;
    private static final int COL_TIME     = 4;
    private static final int COL_CUSTOMER = 5;
    
     private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int WEEKLY_REVENUE_TARGET = 3000; 
    private static final int WEEKLY_APPT_TARGET    = 30; 

    private javax.swing.table.DefaultTableModel tableModel;
    
    public ManagerReport() {
        initComponents();
        configureTable();
        loadWeekReport();
    }

   private void configureTable() {
    String[] cols = { "PaymentID","AppointmentID", "Amount", "Date", "Time", "Customer" };
    Class<?>[] types = { String.class, String.class, String.class,String.class, String.class, String.class };

    tableModel = new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public Class<?> getColumnClass(int c) { return types[c]; }
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    reportTable.setModel(tableModel);
    reportTable.setAutoCreateRowSorter(true);
    reportTable.setRowHeight(28);
    
    revenueProgress.setStringPainted(true);
    appointmentProgress.setStringPainted(true);


    revenueProgress.setMaximum(WEEKLY_REVENUE_TARGET);
    appointmentProgress.setMaximum(WEEKLY_APPT_TARGET);

    revenueProgress.setValue(0);
    appointmentProgress.setValue(0);

}

    private void loadWeekReport() {
    tableModel.setRowCount(0);

    // 1) Decide the weekly window (anchor to the latest PAYMENT date; if none, fall back to appointments)
    LocalDate latestPayment = findLatestDateInFile(PAYMENT_PATH, COL_DATE);
        LocalDate anchor = !LocalDate.MIN.equals(latestPayment)
                ? latestPayment
                : findLatestDateInFile(APPOINTMENT_PATH, 4);               // appointment date = column 5

    if (LocalDate.MIN.equals(anchor)) {
        JOptionPane.showMessageDialog(this, "No dates found in payment/appointment files.");
        totalRevenue.setText("RM 0.00");
        totalAppointment.setText("0");
        return;
    }
    LocalDate weekStart = anchor.minusDays(6); // inclusive range [weekStart .. anchor]

    double weeklyRevenue = 0.0;

    if (Files.exists(PAYMENT_PATH)) {
            try (BufferedReader br = Files.newBufferedReader(PAYMENT_PATH)) {
                br.readLine(); // header
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    var f = parseCsv(line);
                    if (f.size() < 6) continue; // need all 6 columns

                    // Parse amount (index 2)
                    double amt = safeDouble(f.get(COL_AMOUNT));

                    // Always show in table
                    tableModel.addRow(new Object[] {
                        f.get(COL_PAY_ID).trim(),
                        f.get(COL_APPT_ID).trim(),
                        amt,
                        f.get(COL_DATE).trim(),
                        f.get(COL_TIME).trim(),
                        f.get(COL_CUSTOMER).trim()
                    });

                    // Sum within the weekly window
                    try {
                        LocalDate d = LocalDate.parse(f.get(COL_DATE).trim(), DATE_FMT);
                        if (!d.isBefore(weekStart) && !d.isAfter(anchor)) {
                            weeklyRevenue += amt;
                        }
                    } catch (Exception ignore) {}
                }
            } catch (IOException ex) {
                logger.log(java.util.logging.Level.SEVERE, "Failed to read payments", ex);
                JOptionPane.showMessageDialog(this, "Error reading payment.txt:\n" + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "payment.txt not found:\n" + PAYMENT_PATH.toAbsolutePath());
        }

        // ---- Weekly appointments count (keep your existing appointment.txt layout) ----
        int weeklyAppt = 0;
        if (Files.exists(APPOINTMENT_PATH)) {
            try (BufferedReader br = Files.newBufferedReader(APPOINTMENT_PATH)) {
                br.readLine(); // header
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    var f = parseCsv(line);
                    if (f.size() < 6) continue; // need date at index 4
                    try {
                        LocalDate d = LocalDate.parse(f.get(4).trim(), DATE_FMT);
                        if (!d.isBefore(weekStart) && !d.isAfter(anchor)) {
                            weeklyAppt++;
                        }
                    } catch (Exception ignore) {}
                }
            } catch (IOException ex) {
                logger.log(java.util.logging.Level.SEVERE, "Failed to read appointments", ex);
                JOptionPane.showMessageDialog(this, "Error reading appointment.txt:\n" + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "appointment.txt not found:\n" + APPOINTMENT_PATH.toAbsolutePath());
        }

    // 4) Update KPI tiles
    totalRevenue.setText("RM " + new java.text.DecimalFormat("#,##0.00").format(weeklyRevenue));
    totalAppointment.setText(String.valueOf(weeklyAppt));

    int revenueInt = (int)Math.round(weeklyRevenue);
int revMax = Math.max(WEEKLY_REVENUE_TARGET, revenueInt);
revenueProgress.setMaximum(revMax);
revenueProgress.setValue(Math.min(revenueInt, revMax));
revenueProgress.setString(String.format("RM %,d / RM %,d (%.0f%%)",
        revenueInt, WEEKLY_REVENUE_TARGET,
        (WEEKLY_REVENUE_TARGET == 0 ? 0.0 : (weeklyRevenue / WEEKLY_REVENUE_TARGET) * 100)));

// Appointments
int apptMax = Math.max(WEEKLY_APPT_TARGET, weeklyAppt);
appointmentProgress.setMaximum(apptMax);
appointmentProgress.setValue(Math.min(weeklyAppt, apptMax));
appointmentProgress.setString(String.format("%d / %d (%.0f%%)",
        weeklyAppt, WEEKLY_APPT_TARGET,
        (WEEKLY_APPT_TARGET == 0 ? 0.0 : (weeklyAppt * 100.0 / WEEKLY_APPT_TARGET))));
    reportTable.setAutoCreateRowSorter(true);
    
}
    
    private static LocalDate findLatestDateInFile(Path path, int dateIndex) {
        if (!Files.exists(path)) return LocalDate.MIN;
        LocalDate latest = LocalDate.MIN;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.readLine(); // skip
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                java.util.List<String> f = parseCsv(line);
                if (f.size() <= dateIndex) continue;
                try {
                    LocalDate d = LocalDate.parse(f.get(dateIndex).trim(),
                            DateTimeFormatter.ISO_LOCAL_DATE);
                    if (d.isAfter(latest)) latest = d;
                } catch (Exception ignore) {}
            }
        } catch (IOException ignore) {}
        return latest;
    }

    private static double safeDouble(String s) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0.0; }
    }

    // Minimal CSV parser: supports quoted fields and commas.
    private static java.util.List<String> parseCsv(String line) {
        java.util.List<String> out = new ArrayList<>();
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

    private static int indexOf(String header, String wanted) {
    if (header == null) return -1;
    String[] cols = header.split(",", -1);
    for (int i = 0; i < cols.length; i++) {
        String h = cols[i].trim().replace("\"", "");
        // accept some common variants
        if (h.equalsIgnoreCase(wanted)) return i;
        if (wanted.equalsIgnoreCase("Customer") && (h.equalsIgnoreCase("CustomerID") || h.equalsIgnoreCase("CustomerName"))) return i;
    }
    return -1;
}

    private static boolean hasAll(java.util.List<String> f, int... idxs) {
    for (int i : idxs) if (i < 0 || i >= f.size()) return false;
    return true;
}

    private static java.time.LocalDate parseDateSafe(String s) {
    try { return java.time.LocalDate.parse(s, DATE_FMT); }
    catch (Exception e) { return null; }
}


private static String formatCurrency(double v) {
    // no locale currency symbol here; you already prefix with RM
    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
    return df.format(v);
}

private static String stripTrailingZeros(double v) {
    if (Math.floor(v) == v) return String.valueOf((long)v);
    return new java.text.DecimalFormat("#,##0.##").format(v);
}

private static java.time.LocalDate[] weekRange(java.time.LocalDate anchor) {
    java.time.DayOfWeek dow = anchor.getDayOfWeek();
    java.time.LocalDate start = anchor.minusDays(dow.getValue() - java.time.DayOfWeek.MONDAY.getValue());
    java.time.LocalDate end   = start.plusDays(6);
    return new java.time.LocalDate[]{ start, end };
}


private static java.util.Optional<java.time.LocalDate> latestDateAcrossFiles(java.nio.file.Path... paths) {
    java.time.LocalDate latest = null;
    for (java.nio.file.Path p : paths) {
        if (!java.nio.file.Files.exists(p)) continue;
        try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(p)) {
            String header = br.readLine();
            int dateIdx = indexOf(header, "Date");
            if (dateIdx < 0) continue;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                java.util.List<String> f = parseCsv(line);
                if (f.size() <= dateIdx) continue;
                java.time.LocalDate d = parseDateSafe(f.get(dateIdx));
                if (d != null && (latest == null || d.isAfter(latest))) latest = d;
            }
        } catch (java.io.IOException ignore) {}
    }
    return java.util.Optional.ofNullable(latest);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        totalRevenue = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        totalAppointment = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        revenueProgress = new javax.swing.JProgressBar();
        appointmentProgress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(234, 246, 251));

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "PaymentID", "AppointmentID", "Amount", "Date", "Time", "Customer"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        reportTable.setRowHeight(30);
        reportTable.setShowGrid(true);
        jScrollPane1.setViewportView(reportTable);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Weekly Revenue");

        totalRevenue.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalRevenue.setText("RM0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(totalRevenue)))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(totalRevenue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Weekly Appointment");

        totalAppointment.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalAppointment.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(totalAppointment)))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(12, 12, 12)
                .addComponent(totalAppointment)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Report Overview");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 22, 866);
        jPanel4.add(jLabel1, gridBagConstraints);

        revenueProgress.setBackground(new java.awt.Color(204, 204, 204));
        revenueProgress.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        revenueProgress.setForeground(new java.awt.Color(51, 255, 0));
        revenueProgress.setToolTipText("");

        appointmentProgress.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        appointmentProgress.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(revenueProgress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(appointmentProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(appointmentProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(revenueProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar appointmentProgress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable reportTable;
    private javax.swing.JProgressBar revenueProgress;
    private javax.swing.JLabel totalAppointment;
    private javax.swing.JLabel totalRevenue;
    // End of variables declaration//GEN-END:variables

   
}
