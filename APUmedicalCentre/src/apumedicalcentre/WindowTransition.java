/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apumedicalcentre;
/**
 *
 * @author User
 */
public class WindowTransition {

    private static Staff loggedInStaff;

    public static void setLoggedInStaff(Staff staff) {
        loggedInStaff = staff;
    }

    public static Staff getLoggedInStaff() {
        return loggedInStaff;
    }

    public static void clearLoggedInStaff() {
        loggedInStaff = null;
    }

    public static void transitionOpen(javax.swing.JFrame currentWindow, 
                                    Class<? extends javax.swing.JFrame> targetWindowClass,Staff staff) {
        try {
            javax.swing.JFrame targetWindow = targetWindowClass.getDeclaredConstructor().newInstance();
            targetWindow.setLocationRelativeTo(currentWindow);
            targetWindow.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace(); 
            javax.swing.JOptionPane.showMessageDialog(currentWindow, 
                "Failed to open the target window", 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void transitionClose(javax.swing.JFrame currentWindow, 
                                     Class<? extends javax.swing.JFrame> targetWindowClass,Staff staff) {
        try {
            javax.swing.JFrame targetWindow = targetWindowClass.getDeclaredConstructor(Staff.class).newInstance(staff);
            targetWindow.setLocationRelativeTo(currentWindow);
            targetWindow.setVisible(true);

            currentWindow.dispose();

        } catch (Exception e) {
            e.printStackTrace(); 
            javax.swing.JOptionPane.showMessageDialog(currentWindow, 
                "Failed to open the target window", 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
