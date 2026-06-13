package gui;

import exception.InvalidInputException;
import exception.ApplicationProcessingException;
import main.Main;
import model.Pet;
import model.AdoptionApplication;
import javax.swing.*;
import java.awt.*;

//this admin window is shown when login with admin username and password
//can manage pets (CRUD)
//can manage adoption application (reject/approve)
//logout to go back to the login window
public class AdminWindow extends JFrame {
	//list pet and adoption application
    private JTextArea dataDisplayArea;
    //input field for update/create pet
    private JTextField petIdTxt, nameTxt, ageTxt, appIdTxt;
    //dropdown menu for pet
    private JComboBox<Pet.Type> typeBox;
    private JComboBox<Pet.Size> sizeBox;
    private JComboBox<Pet.Health> healthBox;
    private JComboBox<Pet.Status> statusBox;
    //buttons
    private JButton showPetsBtn, showAppBtn, createBtn, updateBtn, deleteBtn, approveBtn, denyBtn, logoutBtn;
    //filter
    private JComboBox<Pet.Type> petTypeFilterBox;
    private JButton applyFilterBtn;
    //window constructor
    //screen layout and everything that can be seen
    public AdminWindow() {
        setTitle("Staff Panel");
        setSize(900, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        
        //panel at the left
        //to create/update/delete pet info
        JPanel inputPanel = new JPanel(new GridLayout(7,2,5,5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Pet Info"));
        inputPanel.add(new JLabel("Pet ID:")); 
        petIdTxt = new JTextField(); 
        inputPanel.add(petIdTxt);
        inputPanel.add(new JLabel("Name:")); 
        nameTxt = new JTextField(); 
        inputPanel.add(nameTxt);
        inputPanel.add(new JLabel("Age:")); 
        ageTxt = new JTextField(); 
        inputPanel.add(ageTxt);
        inputPanel.add(new JLabel("Type:")); 
        typeBox = new JComboBox<>(Pet.Type.values()); 
        inputPanel.add(typeBox);
        inputPanel.add(new JLabel("Size:")); 
        sizeBox = new JComboBox<>(Pet.Size.values()); 
        inputPanel.add(sizeBox);
        inputPanel.add(new JLabel("Health:")); 
        healthBox = new JComboBox<>(Pet.Health.values()); 
        inputPanel.add(healthBox);
        inputPanel.add(new JLabel("Status:")); 
        statusBox = new JComboBox<>(Pet.Status.values()); 
        inputPanel.add(statusBox);
        add(inputPanel, BorderLayout.WEST);

        //panel at the bottom
        //approve/reject app by input appID
        JPanel appPanel = new JPanel(new FlowLayout());
        appPanel.setBorder(BorderFactory.createTitledBorder("Process Application"));
        appPanel.add(new JLabel("App ID:")); appIdTxt = new JTextField(10);
        appPanel.add(appIdTxt);
        approveBtn = new JButton("Approve");
        denyBtn = new JButton("Reject");
        appPanel.add(approveBtn);
        appPanel.add(denyBtn);
        add(appPanel, BorderLayout.SOUTH);

        //panel at the top
        JPanel northAllPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        //buttons
        //row 1
        showPetsBtn = new JButton("Show Pets");
        showAppBtn = new JButton("Show Applications");
        createBtn = new JButton("Add Pet");
        //row2
        updateBtn = new JButton("Update Pet");
        deleteBtn = new JButton("Delete Pet");
        logoutBtn = new JButton("LOGOUT");
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        //row 3
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Filter Pets:"));
        petTypeFilterBox = new JComboBox<>(Pet.Type.values());
        petTypeFilterBox.insertItemAt(null, 0);
        petTypeFilterBox.setSelectedIndex(0);
        filterPanel.add(petTypeFilterBox);
        applyFilterBtn = new JButton("Apply Filter");
        filterPanel.add(applyFilterBtn);
        
        northAllPanel.add(showPetsBtn);
        northAllPanel.add(showAppBtn);
        northAllPanel.add(createBtn);
        northAllPanel.add(updateBtn);
        northAllPanel.add(deleteBtn);
        northAllPanel.add(logoutBtn);
        northAllPanel.add(filterPanel);
        add(northAllPanel, BorderLayout.NORTH);

        //area at the right
        //area to display all data
        dataDisplayArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(dataDisplayArea);
        add(scroll, BorderLayout.CENTER);

        //connect all buttons functions
        bindEvents();
    }

    //FOR ALL BUTTON ACTIONS
    private void bindEvents(){
    	//show all pet list
        showPetsBtn.addActionListener(e -> {
            PetTableModel model = new PetTableModel();
            dataDisplayArea.setText(model.getAllDataWithSeparator());
        });
        //filter
        applyFilterBtn.addActionListener(e -> {
            Pet.Type selectedType = (Pet.Type) petTypeFilterBox.getSelectedItem();
            PetTableModel model = new PetTableModel();
            dataDisplayArea.setText(model.getFilteredPets(selectedType));
        });

        //show list all adopt application from all adotpter
        showAppBtn.addActionListener(e -> {
        	//from the ApplicatioinTableModel.java
            ApplicationTableModel model = new ApplicationTableModel();
            dataDisplayArea.setText(model.getAllDataWithSeparator());
        });

        // APPROVE APPLICATION button
        approveBtn.addActionListener(e -> {
        	try {
	            String appId = appIdTxt.getText().trim();
	            //error if appId is empty
	            if (appId.isEmpty())
	                throw new InvalidInputException("Enter Application ID");
	            
	            AdoptionApplication targetApp = null;
	            //search the appId in the data
	            for (AdoptionApplication app : Main.center.getApplications()) {
//	                if (app.getAppId().equals(appId) && app.getStatus() == Pet.Status.PENDING) {
//	                    app.setStatus(Pet.Status.ADOPTED);
//	                    app.getPet().setStatus(Pet.Status.ADOPTED);
//	                    JOptionPane.showMessageDialog(this,"Approved! Pet adopted.");
//	                    appIdTxt.setText("");
//	                    return;
//	                }
	            	if (app.getAppId().equals(appId)) {
                        targetApp = app;
                        break;
                    }
	            }
	            //App not found
	            if (targetApp == null) {
                    throw new ApplicationProcessingException("Application not found!");
                }
                // app is Not pending status
                if (targetApp.getStatus() != Pet.Status.PENDING) {
                    throw new ApplicationProcessingException("Only PENDING applications can be approved!");
                }
                //update statuses
                targetApp.setStatus(Pet.Status.ADOPTED);
                targetApp.getPet().setStatus(Pet.Status.ADOPTED);
                JOptionPane.showMessageDialog(this, "Approved! Pet marked as adopted.");
                appIdTxt.setText("");
//	            JOptionPane.showMessageDialog(this,"Application not found or not pending.");
        	}catch (InvalidInputException ex) {
                JOptionPane.showMessageDialog(this, "Input Error: " + ex.getMessage());
            } catch (ApplicationProcessingException ex) {
                JOptionPane.showMessageDialog(this, "Process Error: " + ex.getMessage());
            }
        });

        // REJECT APPLICATION button
        denyBtn.addActionListener(e -> {
        	try {
                String appId = appIdTxt.getText().trim();
                //error if appId empty
                if (appId.isEmpty()) {
                    throw new InvalidInputException("Application ID cannot be empty!");
                }
                AdoptionApplication targetApp = null;
                //search the appId in the data file
                for (AdoptionApplication app : Main.center.getApplications()) {
                    if (app.getAppId().equals(appId)) {
                        targetApp = app;
                        break;
                    }
                }
                //error if not found
                if (targetApp == null) {
                    throw new ApplicationProcessingException("Application not found!");
                }
                //error if the status is not pending
                if (targetApp.getStatus() != Pet.Status.PENDING) {
                    throw new ApplicationProcessingException("Only PENDING applications can be rejected!");
                }
                //update statuses
                targetApp.setStatus(Pet.Status.REJECTED);
                targetApp.getPet().setStatus(Pet.Status.AVAILABLE);
                JOptionPane.showMessageDialog(this, "Rejected! Pet available again.");
                appIdTxt.setText("");

            } catch (InvalidInputException ex) {
                JOptionPane.showMessageDialog(this, "Input Error: " + ex.getMessage());
            } catch (ApplicationProcessingException ex) {
                JOptionPane.showMessageDialog(this, "Process Error: " + ex.getMessage());
            }
//            JOptionPane.showMessageDialog(this,"Application not found or not pending.");
        });
        //ADD NEW PET button
        createBtn.addActionListener(e -> {
            try {
                //Get input from text fields
                String pid = petIdTxt.getText().trim();
                String pname = nameTxt.getText().trim();
                String ageText = ageTxt.getText().trim();
                //if Any field empty then throw InvalidInputException
                if (pid.isEmpty() || pname.isEmpty() || ageText.isEmpty()) {
                    throw new InvalidInputException("All fields (Pet ID, Name, Age) cannot be empty!");
                }
                //Age must be number
                int age;
                try {
                    age = Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    throw new InvalidInputException("Age must be a valid number!");
                }
                //error if pet ID already exists (duplicate)
                boolean idExists = Main.center.getPets().stream().anyMatch(pet -> pet.getPetId().equals(pid));
                if (idExists) {
                    throw new ApplicationProcessingException("Pet ID already exists! Use another ID.");
                }
                //create new pet object
                Pet p = new Pet(pid, (Pet.Type) typeBox.getSelectedItem(),pname,age,(Pet.Size) sizeBox.getSelectedItem(),(Pet.Health) healthBox.getSelectedItem(), (Pet.Status) statusBox.getSelectedItem()
                );
                //add pet to system
                Main.center.addPet(p);
                JOptionPane.showMessageDialog(this, "Pet added successfully!");
                //clear input after success
                petIdTxt.setText("");
                nameTxt.setText("");
                ageTxt.setText("");
            } catch (InvalidInputException ex) {
                //show error for empty/wrong format input
                JOptionPane.showMessageDialog(this, "Input Error: " + ex.getMessage());
            } catch (ApplicationProcessingException ex) {
                //show error for duplicate ID / business logic
                JOptionPane.showMessageDialog(this, "Process Error: " + ex.getMessage());
            }
        });
        //update information of existing pet
        updateBtn.addActionListener(e -> {
            String pid = petIdTxt.getText().trim();
            String name = nameTxt.getText().trim();
            String ageText = ageTxt.getText().trim();
            //validation because update pet cannot missing properties
            //error if id empty
            if (pid.isEmpty()){
                JOptionPane.showMessageDialog(this, "Error: Pet ID cannot be empty!");
                return;
            }
            //error if name empty
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Error: Pet Name cannot be empty!");
                return;
            }
            //error if age empty
            if (ageText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Error: Pet Age cannot be empty!");
                return;
            }
            //check if the age input is a number
            try {
                Integer.parseInt(ageText);
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Error: Age must be a valid number!");
                return;
            }
            //check if pet exist or not
            Pet targetPet = null;
            for (Pet p : Main.center.getPets()) {
                if (p.getPetId().equals(pid)){
                    targetPet = p;
                    break;
                }
            }
            //error if pet not exist
            if (targetPet == null) {
                JOptionPane.showMessageDialog(this, "Error: Pet ID not found!");
                return;
            }
            //then if all is validated, update pet
            for (Pet p : Main.center.getPets()) {
                if (p.getPetId().equals(pid)) {
                    p.setName(nameTxt.getText().trim());
                    p.setAge(Integer.parseInt(ageTxt.getText().trim()));
                    p.setType((Pet.Type) typeBox.getSelectedItem());
                    p.setSize((Pet.Size) sizeBox.getSelectedItem());
                    p.setHealth((Pet.Health) healthBox.getSelectedItem());
                    p.setStatus((Pet.Status) statusBox.getSelectedItem());
                    JOptionPane.showMessageDialog(this,"Updated");
                    return;
                }
            }
//            JOptionPane.showMessageDialog(this,"Pet not found");
        });
        //delete pet button
        deleteBtn.addActionListener(e -> {
            String pid = petIdTxt.getText().trim();
            //error if pid empty
            if (pid.isEmpty()){
                JOptionPane.showMessageDialog(this, "Error: Pet ID cannot be empty!");
                return;
            }
            //check if pet exist
            boolean petExists = Main.center.getPets().stream().anyMatch(p -> p.getPetId().equals(pid));
            //error if pet not exist
            if (!petExists) {
                JOptionPane.showMessageDialog(this, "Error: Pet ID not found!");
                return;
            }
            Main.center.getPets().removeIf(p->p.getPetId().equals(pid));
            JOptionPane.showMessageDialog(this,"Deleted");
        });
        //logout button
        //close admin window then return to the login window
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow().setVisible(true);
        });
    }
}