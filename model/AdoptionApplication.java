package model;

import java.time.LocalDate;

//for adoption request from adopter
public class AdoptionApplication {
	//unique appId
    private String appId;
    //the adopter who submit the app
    private Adopter adopter;
    //the pet that the adopter wants
    private Pet pet;
    //status of the application(pending, approve, reject)
    private Pet.Status status;
    //date when the application submitted
    private LocalDate applyDate;
    //constructor
    public AdoptionApplication(String appId, Adopter adopter, Pet pet, Pet.Status status, LocalDate applyDate) {
        this.appId = appId;
        this.adopter = adopter;
        this.pet = pet;
        this.status = status;
        this.applyDate = applyDate;
    }
    //get unique id
    public String getAppId(){ 
    	return appId; 
    }
    //get the adopter that submit the app
    public Adopter getAdopter(){
    	return adopter; 
    }
    //get the pet associated with the application
    public Pet getPet(){
    	return pet;
    }
    //get the current status of the application
    public Pet.Status getStatus(){
    	return status;
    }
    //get the date when application submitted
    public LocalDate getApplyDate(){
    	return applyDate;
    }
    //change/update the status of the apllication
    public void setStatus(Pet.Status status){ 
    	this.status = status;
    }
}