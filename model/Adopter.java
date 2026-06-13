package model;

import java.util.ArrayList;
import java.util.List;

//adopter
//child class
public class Adopter extends User {
	//list to store IDs in the favorite
    private List<String> favoritePetIds;
    //constructor
    public Adopter(String username, String password) {
        super(username, password); //call parent class
        favoritePetIds = new ArrayList<>();//initialize empty favorite list
    }
    //Add favorite. Prevent duplicate
    public void addFavorite(String petId) {
    	//add only when petId is not already in the list
        if(!favoritePetIds.contains(petId)){
            favoritePetIds.add(petId);
        }
    }

    //Remove pet from favorite
    public void removeFavorite(String petId) {
        favoritePetIds.remove(petId);
    }

    //Get the list of all favorite pet IDs (for display in GUI)
    public List<String> getFavoritePetIds() {
        return favoritePetIds;
    }
    //method overriding
    @Override
    public String getUserRole() {
    	return "ADOPTER";
    }
}