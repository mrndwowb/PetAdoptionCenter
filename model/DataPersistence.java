package model;

//interface for file saving and loading
public interface DataPersistence {
	void saveToFile(String filePath);
    void loadFromFile(String filePath);
}