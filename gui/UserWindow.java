package gui;

import exception.InvalidInputException;
import exception.ApplicationProcessingException;
import main.Main;
import model.Adopter;
import model.AdoptionApplication;
import model.Pet;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


//shown when login with adopter account
//Read all pet list
//add delete see favorite pet
//submit adopt application
//cannot cancel pet adoption application
//see 2 statistics
//logout, back to login window
public class UserWindow extends JFrame {
	//list pet and adoption application
    private JTextArea dataDisplayArea;
    //all button
    private JButton showPetsBtn, showMyFavBtn, addFavBtn, removeFavBtn, applyBtn, showMyAppsBtn, showMonthlyStatBtn, showPopularTypeBtn, logoutBtn;
    //text input field petId to submit app and favorite pet
	//filter dropdown
    private JComboBox<Pet.Type> petTypeFilterBox;
    private JButton applyFilterBtn;
    //for search, favorite, submit application pet by id
    private JTextField petIdInput;
    private JButton searchPetBtn;

    //show which adopter username logged in
    private String currentAdopterUsername;
    //current logged-in adopter object
    private Adopter loginAdopter;
    
    //CONTRSUCTOR
    public UserWindow(String adopterName) {
        this.currentAdopterUsername = adopterName;
        //find and store the currently logged-in user
        for (Adopter a : Main.center.getAdopters()) {
            if (a.getUsername().equals(adopterName)) {
                loginAdopter = a;
                break;
            }
        }

        setTitle("Adopter Panel");
        setSize(880, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //TOP PANEL
        JPanel topPanel = new JPanel(new GridLayout(3, 3, 5, 5)); 
        //buttons
        //row 1
        showPetsBtn = new JButton("Show All Pets");
        showMyFavBtn = new JButton("View My Favorites");
        showMyAppsBtn = new JButton("View My Applications");
        //row 2
        showMonthlyStatBtn = new JButton("Monthly Adoption Stats");
        showPopularTypeBtn = new JButton("Most Popular Pet Type");
        logoutBtn = new JButton("LOGOUT");
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        //row 3
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Filter:"));
        petTypeFilterBox = new JComboBox<>(Pet.Type.values());
        petTypeFilterBox.insertItemAt(null, 0);
        filterPanel.add(petTypeFilterBox);
        applyFilterBtn = new JButton("Apply");
        filterPanel.add(applyFilterBtn);
        
        topPanel.add(showPetsBtn);
        topPanel.add(showMyFavBtn);
        topPanel.add(showMyAppsBtn);
        topPanel.add(showMonthlyStatBtn);
        topPanel.add(showPopularTypeBtn);
        topPanel.add(logoutBtn);
        topPanel.add(filterPanel);
        topPanel.add(new JLabel(""));
        topPanel.add(new JLabel(""));
        add(topPanel, BorderLayout.NORTH);

        // RIGHT PANEL
        //favorite pet by imput petId
        JPanel opPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        opPanel.add(new JLabel("Pet ID:"));
        petIdInput = new JTextField();
        opPanel.add(petIdInput);
        searchPetBtn = new JButton("Search Pet by ID");
        opPanel.add(searchPetBtn);
        applyBtn = new JButton("Submit Adoption Application");
        opPanel.add(applyBtn);
        addFavBtn = new JButton("Add To Favorite");
        opPanel.add(addFavBtn);
        removeFavBtn = new JButton("Remove Favorite");
        opPanel.add(removeFavBtn);
        add(opPanel, BorderLayout.EAST);


        //DISPLAY AREA
        //list all data
        dataDisplayArea = new JTextArea();
        dataDisplayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(dataDisplayArea);
        add(scroll, BorderLayout.CENTER);
        //connect button function action
        bindAction();
    }
    
    //ALL THE BUTTON ACTION FUNCTION
    private void bindAction() {
        //SHOW ALL PETS
        showPetsBtn.addActionListener(e -> {
        	//use PetTableModel.java
            PetTableModel model = new PetTableModel();
            dataDisplayArea.setText(model.getAllDataWithSeparator());
        });
        //filter pet by type
        applyFilterBtn.addActionListener(e -> {
            Pet.Type selectedType = (Pet.Type) petTypeFilterBox.getSelectedItem();
            PetTableModel model = new PetTableModel();
            dataDisplayArea.setText(model.getFilteredPets(selectedType));
        });
        //search pet by id
        searchPetBtn.addActionListener(e -> {
            String searchId = petIdInput.getText().trim();
            //show error if the search input is empty
            if (searchId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Pet ID!");
                return;
            }
            //check if pet exist
            Pet foundPet = null;
            for (Pet p : Main.center.getPets()) {
                if (p.getPetId().equalsIgnoreCase(searchId)) {
                    foundPet = p;
                    break;
                }
            }
            //if pet found, then show
            if (foundPet != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("=== PET FOUND ===\n");
                sb.append("PetID|Type|Name|Age|Size|Health|Status\n");
                sb.append("---------------------------------------------\n");
                sb.append(foundPet.getPetId()).append("|")
                  .append(foundPet.getType()).append("|")
                  .append(foundPet.getName()).append("|")
                  .append(foundPet.getAge()).append("|")
                  .append(foundPet.getSize()).append("|")
                  .append(foundPet.getHealth()).append("|")
                  .append(foundPet.getStatus()).append("\n");
                dataDisplayArea.setText(sb.toString());
            } else {
            	//if pet not found, send error message
                dataDisplayArea.setText("Pet not found: " + searchId);
            }
        });
        //SHOW MY FAVORITES
        //display only the current user's favorite pets
        //cannot see other user's favorite pets
        showMyFavBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            //heading
            sb.append("=== YOUR FAVORITE PETS ===\n");
            sb.append("PetID|Type|Name|Status\n");
            sb.append("-------------------------\n");
            //loop through favorite petId then match with the pet datas
            for (String fid : loginAdopter.getFavoritePetIds()) {
                for (Pet p : Main.center.getPets()) {
                    if (p.getPetId().equals(fid)) {
                        sb.append(p.getPetId()).append("|")
                          .append(p.getType()).append("|")
                          .append(p.getName()).append("|")
                          .append(p.getStatus()).append("\n");
                    }
                }
            }
            dataDisplayArea.setText(sb.toString());
        });

        //SHOW MY APPLICATIONS
        //cannot see other user's adopt app
        showMyAppsBtn.addActionListener(e -> {
            List<AdoptionApplication> myApps = new ArrayList<>();
            //filter only show the logged user's adopt app
            for (AdoptionApplication app : Main.center.getApplications()) {
                if (app.getAdopter().getUsername().equals(loginAdopter.getUsername())) {
                    myApps.add(app);
                }
            }
            //header
            StringBuilder sb = new StringBuilder();
            sb.append("===== MY ADOPTION APPLICATIONS =====\n");
            sb.append("AppID | PetID | ApplyDate | Status\n");
            sb.append("----------------------------------------\n");
            //display
            for (AdoptionApplication app : myApps) {
                sb.append(app.getAppId()).append(" | ");
                sb.append(app.getPet().getPetId()).append(" | ");
                sb.append(app.getApplyDate()).append(" | ");
                sb.append(app.getStatus()).append("\n");
            }
            dataDisplayArea.setText(sb.toString());
        });

        //SUBMIT ADOPTION APPLICATION
        applyBtn.addActionListener(e -> {
            try {
                String petId = petIdInput.getText().trim();
                //if no input
                if (petId.isEmpty()) 
                	throw new InvalidInputException("Enter Pet ID");
                //find pet by id
                Pet target = null;
                for (Pet p : Main.center.getPets()) {
                    if (p.getPetId().equals(petId) && p.getStatus() == Pet.Status.AVAILABLE) {
                        target = p;
                        break;
                    }
                }
                //if pet not exist
                if (target == null) {
//                    JOptionPane.showMessageDialog(this, "Pet not available");
//                    return;
                	throw new ApplicationProcessingException("Pet ID does not exist");
                }
                //if pet not available for adoption
                if (target.getStatus() != Pet.Status.AVAILABLE)
                    throw new ApplicationProcessingException("Pet is not available for adoption");
                //create appId
                //UUID is Universally Unique Identifier. Unique random code
                //only take first 6 char and convert to uppercase
                String appId = "APP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                AdoptionApplication app = new AdoptionApplication(appId, loginAdopter, target, Pet.Status.PENDING, LocalDate.now());
                Main.center.addApplication(app);
                //set pet status to pending
                //no one can apply when pet status is pending
                target.setStatus(Pet.Status.PENDING);

                JOptionPane.showMessageDialog(this, "Applied!\nApp ID: " + appId);
                petIdInput.setText("");
            }catch (InvalidInputException ex) {
            	//error if empty field
                JOptionPane.showMessageDialog(this, "Input Error: " + ex.getMessage());
            } catch (ApplicationProcessingException ex) {
            	//error if invalid pet/unavailable
                JOptionPane.showMessageDialog(this, "Process Error: " + ex.getMessage());
            } 
//            catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, ex.getMessage());
//            }
        });

        //MONTHLY ADOPTION STATISTICS
        showMonthlyStatBtn.addActionListener(e -> {
            Map<String, Integer> monthlyCount = new HashMap<>();
            List<AdoptionApplication> apps = Main.center.getApplications();
            //group by "YYYY-MM" format
            for (AdoptionApplication app : apps) {
                String yearMonth = app.getApplyDate().getYear() + "-" +
                        String.format("%02d", app.getApplyDate().getMonthValue());
                monthlyCount.put(yearMonth, monthlyCount.getOrDefault(yearMonth, 0) + 1);
            }
            //header
            StringBuilder sb = new StringBuilder();
            sb.append("===== MONTHLY ADOPTION STATISTICS =====\n");
            if (monthlyCount.isEmpty()) {
                sb.append("No applications yet.");
            } else {
                for (Map.Entry<String, Integer> entry : monthlyCount.entrySet()) {
                    sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(" application(s)\n");
                }
            }
            dataDisplayArea.setText(sb.toString());
        });

        //MOST POPULAR PET TYPE
        showPopularTypeBtn.addActionListener(e -> {
            List<AdoptionApplication> apps = Main.center.getApplications();
            if (apps.isEmpty()) {
                dataDisplayArea.setText("No applications yet.");
                return;
            }
            //count application per pet type using stream
            Map<Pet.Type, Long> typeCount = apps.stream()
                    .collect(Collectors.groupingBy(
                            app -> app.getPet().getType(),
                            Collectors.counting()
                    ));
            //find the type with highest count
            Pet.Type mostPopular = null;
            long max = 0;
            for (Map.Entry<Pet.Type, Long> entry : typeCount.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    mostPopular = entry.getKey();
                }
            }
            //header
            StringBuilder sb = new StringBuilder();
            sb.append("===== MOST POPULAR ANIMAL TYPE =====\n");
            sb.append("Most popular: ").append(mostPopular).append(" (").append(max).append(" applications)\n\n");
            sb.append("All types:\n");
            for (Map.Entry<Pet.Type, Long> entry : typeCount.entrySet()) {
                sb.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            dataDisplayArea.setText(sb.toString());
        });

        //ADD FAVORITE button
        addFavBtn.addActionListener(e -> {
            String pid = petIdInput.getText().trim();
            //if the input id empty, send error message
            if (pid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Pet ID");
                return;
            }
            //check if pet exist
            boolean petExists = false;
            for (Pet p : Main.center.getPets()) {
                if (p.getPetId().equals(pid)) {
                    petExists = true;
                    break;
                }
            }
            //pet not exist, then show error
            if (!petExists) {
                JOptionPane.showMessageDialog(this, "Error: Pet ID not found!");
                return;
            }
            loginAdopter.addFavorite(pid);
            JOptionPane.showMessageDialog(this, "Added to favorite");
            petIdInput.setText("");
        });

        //REMOVE FAVORITE button
        removeFavBtn.addActionListener(e -> {
            String pid = petIdInput.getText().trim();
            //check if the input empty or not
            if (pid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Pet ID cannot be empty!");
                return;
            }
            //check if pet exist
            boolean petExists = false;
            for (Pet p : Main.center.getPets()) {
                if (p.getPetId().equals(pid)) {
                    petExists = true;
                    break;
                }
            }
            //if pet not exist, send error message
            if (!petExists) {
                JOptionPane.showMessageDialog(this, "Error: Pet ID not found!");
                return;
            }
            loginAdopter.removeFavorite(pid);
            JOptionPane.showMessageDialog(this, "Removed from favorite");
            petIdInput.setText("");
        });

        //LOGOUT button
        //go back to the login window
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow().setVisible(true);
        });
    }
}