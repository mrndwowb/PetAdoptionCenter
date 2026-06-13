package main;

import gui.LoginWindow;
import model.AdoptionCenter;

public class Main {
    public static AdoptionCenter center = new AdoptionCenter(); // GLOBAL DATA

    public static void main(String[] args) {
        //AUTO LOAD DATA WHEN PROGRAM STARTS
        center.loadFromFile(AdoptionCenter.FILE_NAME);

        LoginWindow login = new LoginWindow();
        login.setVisible(true);

        //AUTO SAVE DATA WHEN PROGRAM CLOSES
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                center.saveToFile(AdoptionCenter.FILE_NAME)
        ));
    }
}