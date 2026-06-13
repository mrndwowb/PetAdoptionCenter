package model;

//for pet in the shelter
public class Pet {
    //enums
    public enum Type { CAT, DOG, RABBIT }
    public enum Size { SMALL, MEDIUM, LARGE }
    public enum Health { HEALTHY, SICK, DISABILITY }
    public enum Status { AVAILABLE, PENDING, ADOPTED, REJECTED }
    //attributes
    private String petId; //unique
    private Type type;
    private String name;
    private int age;
    private Size size;
    private Health health;
    private Status status;

    //constructor
    public Pet(String petId, Type type, String name, int age, Size size, Health health, Status status) {
        this.petId = petId;
        this.type = type;
        this.name = name;
        this.age = age;
        this.size = size;
        this.health = health;
        this.status = status;
    }

    //getters
    public String getPetId(){ 
    	return petId; 
    }
    public Type getType(){ 
    	return type; 
    }
    public String getName() { 
    	return name; 
    }
    public int getAge(){ 
    	return age;
    }
    public Size getSize(){ 
    	return size; 
    }
    public Health getHealth() { 
    	return health; 
    }
    public Status getStatus() { 
    	return status; 
    }

    //setters
    public void setPetId(String petId){ 
    	this.petId = petId; 
    }
    public void setType(Type type){ 
    	this.type = type; 
    }
    public void setName(String name){ 
    	this.name = name; 
    }
    public void setAge(int age) { 
    	this.age = age; 
    }
    public void setSize(Size size){ 
    	this.size = size; 
    }
    public void setHealth(Health health) { 
    	this.health = health; 
    }
    public void setStatus(Status status){ 
    	this.status = status; 
    }
}