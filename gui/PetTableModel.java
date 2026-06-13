package gui;

import main.Main;
import model.Pet;

//to list all pet list
//used in admin window and user window
public class PetTableModel {
    public String getAllDataWithSeparator() {
        StringBuilder sb = new StringBuilder();

        //header
        sb.append("PetID|Type|Name|Age|Size|Health|Status\n");
        sb.append("---------------------------------------------\n");
        //loop through every pet in the data
        //PetID | Type | Name | Age | Size | Health | Status
        for (Pet p : Main.center.getPets()) {
            sb.append(p.getPetId()).append("|")
              .append(p.getType()).append("|")
              .append(p.getName()).append("|")
              .append(p.getAge()).append("|")
              .append(p.getSize()).append("|")
              .append(p.getHealth()).append("|")
              .append(p.getStatus()).append("\n");
        }
        //return the formated text to be displayed
        return sb.toString();
    }
    //FILTERED PET LIST BY TYPE
    public String getFilteredPets(Pet.Type filterType) {
        StringBuilder sb = new StringBuilder();
        sb.append("PetID|Type|Name|Age|Size|Health|Status\n");
        sb.append("---------------------------------------------\n");

        for (Pet p : Main.center.getPets()) {
            //if filter is null = show ALL
            if (filterType == null || p.getType() == filterType) {
                sb.append(p.getPetId()).append("|")
                  .append(p.getType()).append("|")
                  .append(p.getName()).append("|")
                  .append(p.getAge()).append("|")
                  .append(p.getSize()).append("|")
                  .append(p.getHealth()).append("|")
                  .append(p.getStatus()).append("\n");
            }
        }
        return sb.toString();
    }
}