package model;
//parent class for staff and adopter
public abstract class User {
	//protected. Only accessible by this class and subclass
    protected String username;
    protected String password;
    //constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //method overloading
    public User(String username) {
        this.username = username;
        this.password = "";
    }
    //get username
    public String getUsername() {
        return username;
    }
    //change/update username
    public void setUsername(String username) {
        this.username = username;
    }
    //get password
    public String getPassword() {
        return password;
    }
    //update/change password
    public void setPassword(String password) {
        this.password = password;
    }
    public abstract String getUserRole();
}