//package model;

//public class Staff extends User{
//	public Staff(String username, String password) {
//		super(username, password);
//	}
//
//	@Override
//	public String getRole() {
//		return "STAFF";
//	}
//}
package model;
//child class
public class Staff extends User {
    public Staff(String username, String password) {
        super(username, password); //call parent class
    }
    //method overriding
    @Override
    public String getUserRole() {
    	return "STAFF";
    }
}