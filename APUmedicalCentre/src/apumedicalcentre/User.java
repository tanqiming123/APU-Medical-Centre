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
public class User {
    private String userId;
    private String username;
    private String password;
    private String role;
    
    public User(String userId, String username, String password,String role){
        this.userId=userId;
        this.password=password;
        this.role=role;
        this.username=username;
    }
    public String getUserId() {
        return userId;
    }
    public void setId(String userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public static User checkLogin(String role, String username, String password){
        String fileName = "";
        switch(role){
            case "Manager":
                fileName = "src/Data/manager.txt";
                break;
            case "Staff":
                fileName = "src/Data/staff.txt";
                break;
            case "Customer":
                fileName = "src/Data/customer.txt";
                break;
            case "Doctor":
                fileName = "src/Data/doctor.txt";
                break;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null){
                if (line.trim().isEmpty()) continue;
                if (isFirstLine && role.equals("Customer")){
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String id = data[0].trim();
                String pass = data[2].trim();
                String name = data[1].trim();
                
                if (name.equals(username.trim()) && pass.equals(password.trim())){
                    if (role.equals("Customer")){
                        String registerStatus = data[6].trim();
                        if (!registerStatus.equals("Accepted")){
                            return new User("STATUS_" + registerStatus.toUpperCase(), name, pass, role);
                        }
                        return new Customer(id, name, pass, role);
                    }else if(role.equals("Staff")){
                        return new Staff(id, name, pass, role);
                    }else if(role.equals("Manager")){
                        return new Manager(id,name,pass,role);
                    }else if(role.equals("Doctor")){
                        return new Doctor(id, name, pass, role);
                    }
                    else{
                        return new User(id, name, pass, role);
                    }
                    
                }
                
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
