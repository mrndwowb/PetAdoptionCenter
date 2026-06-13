# PetAdoptionCenter

Java programing language A platform connecting animal shelters with potential adopters. Shelter staff can add, update, and remove pet listings with details such as species, age, size, and health status. Adopters can search and filter available pets, submit adoption applications, and track application status. Staff can review pending applications and approve or deny requests, updating pet availability accordingly. Advanced features can include a favorite list where adopters can save pets they are interested in, and adoption statistics that display the number of successful adoptions per month and the most popular species adopted.

OOP concept:
1. interface: DataPersistence.java
2. abstract class: User.java
3. 6 interconnected class in the model package
4. method overriding
5. method overloading
6. polymorphism
7. Exeption and error handling

Data Storage: Use file I/O to store and retrieve data persistently. The system load data from a file when the program starts and save data back to the file when the program exits. In here the file is adoptioncenterData.txt

Features:
1. can add and remove favorite pets
2. can apply for pet adoption
3. can see adoption statistics: number of successful adoptions per month and the most popular species adopted
4. can search and filter pet list


interconnected class means: 
• Inheritance – A subclass extends a superclass. For example, Staff extends User, Adopter extends User. 
• Association – A class has a field of another class type. For example, an Order class has a Customer field. 
• Aggregation / Composition – A class contains a collection of another class. For example, a ShoppingCart contains a list of Product objects. 
• Dependency – A method of one class uses an object of another class as a parameter or local variable. For example, processPayment (Payment p).

PetAdoptionSystem/ ├── main/ │ └── Main.java # Main entry point, holds static AdoptionCenter object │ ├── model/ # ALL DATA MODEL CLASSES │ ├── User.java # Parent Superclass │ ├── Adopter.java # Child subclass 1 (extends User) │ ├── Staff.java # Child subclass 2 (extends User) │ ├── Pet.java # Pet entity class │ └── AdoptionApplication.java # Adoption application class │ ├── exception/ # Custom Exception Classes │ ├── InvalidInputException.java │ └── ApplicationProcessingException.java │ ├── gui/ # ALL SWING GUI WINDOWS │ ├── LoginWindow.java # Login interface │ ├── UserWindow.java # Adopter user panel │ ├── AdminWindow.java # Staff/Admin panel │ ├── PetTableModel.java # Format pet data for display │ └── ApplicationTableModel.java # Format application data │ └── (all compiled class files)
Inheritance User (Parent) ├─ Adopter (Child) └─ Staff (Child)

How to run: download all files. In the eclipse app, import all of these files into one java project. Run Main.Java, Login page will shows up and can login with the credentials store in the adoptioncenterData.txt as adopter or staff/admin. If logout, will be back to the login page
