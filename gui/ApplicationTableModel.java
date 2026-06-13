package gui;

import main.Main;
import model.AdoptionApplication;

//I named this class as table because i want to display a table, but i havent learn table. So, I just list all the data with | as the seperator
//List all the adoption application
//shown/used for admin window because show all the application data
public class ApplicationTableModel {
    public String getAllDataWithSeparator() {
        StringBuilder sb = new StringBuilder();
        //loop to show all data
        // App ID | Adopter Username | Pet ID | Application Status
        for (AdoptionApplication app : Main.center.getApplications()) {
            sb.append(app.getAppId()).append("|")
              .append(app.getAdopter().getUsername()).append("|")
              .append(app.getPet().getPetId()).append("|")
              .append(app.getStatus()).append("\n");
        }
        //return the formated text to the gui text area
        return sb.toString();
    }
}