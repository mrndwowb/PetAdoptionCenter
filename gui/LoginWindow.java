package gui;

import exception.InvalidInputException;
//import exception.ApplicationProcessingException;
import main.Main;
//import model.Adopter;
//import model.Staff;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//the first window to show when run the system
//login using existed username and password
//according to the username and password, either admin window will show up or user window will show up
public class LoginWindow extends JFrame {
	//text input field for username
    private JTextField usernameField;
    //pasword input field for password
    private JPasswordField passwordField;
    //login button
    private JButton loginBtn;

    //constructor
    public LoginWindow() {
        setTitle("Login");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        //username label
        add(new JLabel("Username:"));
        //text input field
        usernameField = new JTextField(20);
        add(usernameField);
        //password label
        add(new JLabel("Password:"));
        //password input field
        passwordField = new JPasswordField(20);
        add(passwordField);
        loginBtn = new JButton("Login");
        add(loginBtn);
        
        //login button action function
        loginBtn.addActionListener((ActionEvent e) -> {
            try {
            	//get input
                String user = usernameField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();
                //check username or password empty or not
                if (user.isEmpty() || pass.isEmpty())
                    throw new InvalidInputException("Empty username/password");

                //Check if Staff (Admin)
                boolean isStaff = Main.center.getStaffList().stream()
                        .anyMatch(s -> s.getUsername().equals(user) && s.getPassword().equals(pass));

                // Check if Adopter
                boolean isAdopter = Main.center.getAdopters().stream()
                        .anyMatch(a -> a.getUsername().equals(user) && a.getPassword().equals(pass));
                //login result
                //if staff account, open admin window
                if (isStaff) {
                    dispose();
                    new AdminWindow().setVisible(true);
                //if adopter account, open user window
                } else if (isAdopter) {
                    dispose();
                    new UserWindow(user).setVisible(true);
                //if account not found
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials");
                }
            } catch (InvalidInputException ex) {
            	//error if username/password empty
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}