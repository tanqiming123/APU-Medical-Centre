package apumedicalcentre;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class ManagerFeedback extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerFeedback.class.getName());
    private static final Path FEEDBACK_PATH = Paths.get(
    "src/Data/feedback.txt"
);

    
      
    private DefaultTableModel feedbackTableModel;
    
    public ManagerFeedback() {
        initComponents();
        configureTable();
        loadFeedback();
    }

     private void configureTable() {
        String[] cols = { "Feedback ID", "Appointment ID", "From", "To", "Comment", "Date" }; 
        Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, String.class};

        feedbackTableModel = new DefaultTableModel(cols, 0) {
            @Override public Class<?> getColumnClass(int c) { return types[c]; }
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        feedbackTable.setModel(feedbackTableModel);
        feedbackTable.setAutoCreateRowSorter(true);

        int[] widths = {110, 120, 160, 160, 420, 110};
    for (int i = 0; i < widths.length && i < feedbackTable.getColumnModel().getColumnCount(); i++) {
        feedbackTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        
    }
  }
    
   private void loadFeedback() {
        feedbackTableModel.setRowCount(0);

        if (!Files.exists(FEEDBACK_PATH)) {
            logger.info("feedback file not found: " + FEEDBACK_PATH.toAbsolutePath());
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(FEEDBACK_PATH)) {
            String header = br.readLine(); // skip header row
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                List<String> f = parseCsvLine(line);
                
                if (f.size() < 6) continue;

                String feedbackId   = f.get(0).trim();
                String appointmentId= f.get(1).trim();
                String fromId       = f.get(2).trim();
                String toId         = f.get(3).trim();           
                String comment      = f.get(4).trim();
                String date         = f.get(5).trim();

            String fromRole = roleFromId(fromId);
            String toRole   = roleFromId(toId);

            String fromDisplay = (fromRole.equals("UNKNOWN") ? "" : fromRole + " ") + fromId;
            String toDisplay   = (toRole.equals("UNKNOWN")   ? "" : toRole   + " ") + toId;

                
                feedbackTableModel.addRow(new Object[] 
                {
                    feedbackId, appointmentId, fromDisplay, toDisplay, comment, date
                });
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to read feedback file", ex);
        }
    }

    private static List<String> parseCsvLine(String line) {
        List<String> out = new ArrayList<String>();
        if (line == null) return out;
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') { // escaped quote
                    sb.append('"'); i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        out.add(sb.toString());
        return out;
    }
        private static String roleFromType(String type, boolean isFrom) {
    if (type == null || !type.contains("_to_")) return "UNKNOWN";
    String[] parts = type.split("_to_");
    if (parts.length != 2) return "UNKNOWN";
    String raw = isFrom ? parts[0] : parts[1];
    if ("customer".equals(raw)) return "CUSTOMER";
    if ("doctor".equals(raw))   return "DOCTOR";
    return "UNKNOWN";
}

    private static String roleFromId(String id) {
    if (id == null || id.isEmpty()) return "UNKNOWN";
    char c = Character.toUpperCase(id.charAt(0));
    if (c == 'C') return "CUSTOMER";
    if (c == 'D') return "DOCTOR";
    return "UNKNOWN";
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        feedbackTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Feedback");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 16, 0, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(234, 246, 251));

        feedbackTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Feedback ID", "AppointmentID", "From", "To", "Comment", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        feedbackTable.setRowHeight(30);
        jScrollPane1.setViewportView(feedbackTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 1049;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel3.add(jPanel2, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable feedbackTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
