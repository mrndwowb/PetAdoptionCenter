package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

//adoption ceenter
//main data manager for the entire system
//store data: adopter, staff, pets, adopt application
//save when system closed
//load when system opened
//generate default test data if the txt file not exists
public class AdoptionCenter implements DataPersistence {
	//list all registered adopters
    private List<Adopter> adopters;
  //list all registered admin/staff
    private List<Staff> staffList;
  //list all registered pets
    private List<Pet> pets;
  //list all adoption application
    private List<AdoptionApplication> applications;
    //name of the data file whre all data is stored
    public static final String FILE_NAME = "adoptioncenterData.txt";
    //constructor. Initialized all empty lists when the system starts
    public AdoptionCenter() {
        adopters = new ArrayList<>();
        staffList = new ArrayList<>();
        pets = new ArrayList<>();
        applications = new ArrayList<>();
    }

    //DATA METHODS
    public void addAdopter(Adopter a){
    	adopters.add(a); 
    }
    public void addStaff(Staff s){ 
    	staffList.add(s); 
    }
    public void addPet(Pet p){ 
    	pets.add(p); 
    }
    public void addApplication(AdoptionApplication app){ 
    	applications.add(app); 
    }
    //getters
    public List<Adopter> getAdopters(){ 
    	return adopters; 
    }
    public List<Staff> getStaffList(){ 
    	return staffList; 
    }
    public List<Pet> getPets() { 
    	return pets; 
    }
    public List<AdoptionApplication> getApplications() { 
    	return applications; 
    }

    //SAVE TO FILE
    //save all data to the .txt file with format
    //seperated by sections
    //each record is seperated by |
    @Override
    public void saveToFile(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            //save Adopters and their favorite pets
        	pw.println("ADOPTERS");
        	for (Adopter a : adopters) {
        	    String favStr = String.join(",", a.getFavoritePetIds());
        	    pw.println(a.getUsername() + "|" + a.getPassword() + "|" + favStr);
        	}

            //Save Staff
            pw.println("STAFF");
            for (Staff s : staffList) pw.println(s.getUsername() + "|" + s.getPassword());

            //save Pets
            pw.println("PETS");
            for (Pet p : pets) {
                pw.println(p.getPetId() + "|" + p.getType() + "|" + p.getName() + "|" + p.getAge() + "|"
                        + p.getSize() + "|" + p.getHealth() + "|" + p.getStatus());
            }

            //save Applications
            pw.println("APPLICATIONS");
            for (AdoptionApplication app : applications) {
                pw.println(app.getAppId() + "|" + app.getAdopter().getUsername() + "|"
                        + app.getPet().getPetId() + "|" + app.getStatus() + "|" + app.getApplyDate());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    //LOAD FROM FILE
    @Override
    public void loadFromFile(String filePath) {
        clearAllData();
        File file = new File(filePath);

        if (!file.exists()) {
            generateDefaultData(); // Create default data if file missing
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            String section = "";
            //read file line by line
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isBlank()) continue;
                //identify which section
                if (line.equals("ADOPTERS") || line.equals("STAFF") || line.equals("PETS") || line.equals("APPLICATIONS")) {
                    section = line;
                    continue;
                }
                //split each line by | to get individual data
                String[] parts = line.split("\\|");
                //load data to the correct list based on section
                switch (section) {
	                case "ADOPTERS" -> {
			                    String un = parts[0];
			                    String pw = parts[1];
			                    Adopter adp = new Adopter(un, pw);
			                    //load favorite pets if available
			                    if(parts.length >=3 && !parts[2].isBlank()){
			                        String[] favs = parts[2].split(",");
			                        for(String fid : favs){
			                            adp.addFavorite(fid);
			                        }
			                    }
			                    adopters.add(adp);
			                }
                    case "STAFF" -> staffList.add(new Staff(parts[0], parts[1]));
                    case "PETS" -> pets.add(new Pet(
                            parts[0],
                            Pet.Type.valueOf(parts[1]),
                            parts[2],
                            Integer.parseInt(parts[3]),
                            Pet.Size.valueOf(parts[4]),
                            Pet.Health.valueOf(parts[5]),
                            Pet.Status.valueOf(parts[6])
                    ));
                 //when loading applications
                    case "APPLICATIONS" -> {
                    	//find mathcing adopter and peet for the application
	                        Adopter adopter = findAdopter(parts[1]);
	                        Pet pet = findPet(parts[2]);
	                        //only add application if both user and pet exists
	                        if (adopter != null && pet != null) {
	                            applications.add(new AdoptionApplication(
	                                parts[0], adopter, pet,
	                                Pet.Status.valueOf(parts[3]),
	                                LocalDate.parse(parts[4])
	                            ));
	                        }
                    }
                    }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    //DEFAULT DATA
    public void generateDefaultData() {
        clearAllData();

        //4 adopters
        adopters.add(new Adopter("alex", "1111"));
        adopters.add(new Adopter("dave", "2222"));
        adopters.add(new Adopter("ella", "3333"));
        adopters.add(new Adopter("steve", "4444"));

        //1 staff
        staffList.add(new Staff("admin", "0000"));

        //5 pets
        pets.add(new Pet("P001", Pet.Type.DOG, "Buddy", 3, Pet.Size.MEDIUM, Pet.Health.HEALTHY, Pet.Status.AVAILABLE));
        pets.add(new Pet("P002", Pet.Type.CAT, "Whiskers", 2, Pet.Size.SMALL, Pet.Health.HEALTHY, Pet.Status.AVAILABLE));
        pets.add(new Pet("P003", Pet.Type.RABBIT, "Fluffy", 1, Pet.Size.SMALL, Pet.Health.HEALTHY, Pet.Status.AVAILABLE));
        pets.add(new Pet("P004", Pet.Type.DOG, "Max", 4, Pet.Size.LARGE, Pet.Health.HEALTHY, Pet.Status.AVAILABLE));
        pets.add(new Pet("P005", Pet.Type.CAT, "Luna", 1, Pet.Size.SMALL, Pet.Health.SICK, Pet.Status.AVAILABLE));
    }
    //polymorphism 
    public void printAllUserRoles() {
        // Treat both Adopter and Staff as User objects
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(adopters);
        allUsers.addAll(staffList);

        for (User user : allUsers) {
            System.out.println(user.getUsername() + " is a " + user.getUserRole());
        }
    }
    //HELPER
    //clear all data from the lists
    private void clearAllData() {
        adopters.clear(); 
        staffList.clear(); 
        pets.clear(); 
        applications.clear();
    }
    //find adopter by their username
    private Adopter findAdopter(String username) {
        for (Adopter a : adopters)
        	if (a.getUsername().equals(username)) 
        		return a;
        return null;
    }
    //find pet by pet id
    private Pet findPet(String petId) {
        for (Pet p : pets)
        	if (p.getPetId().equals(petId))
        		return p;
        return null;
    }
}