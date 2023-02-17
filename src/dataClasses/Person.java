package dataClasses;

public class Person {

	private String firstName;
	private String lastName;
	private int age;
	private int uniqueID;
	
	private static int idTracker = 0;
	
	public Person(String firstName, String lastName, int age) {
				
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		
		//Assign Unique Identifier
		this.uniqueID = idTracker;
		idTracker++;
	}
	
	//Create a person with the specified ID (and won't increment the static tracker)
	//I foresee a possibility of needing this in the future
	public Person(String firstName, String lastName, int age, int id) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.uniqueID = id;
	}

	public static void resetIDTracker() {
		
		idTracker = 0;	
	}
	
	//Made this before I had the idea to create a second constructor
	public static void compensateIDTracker() {
		
		idTracker--;
	}
	
	
	public String getFullName() {
		
		return firstName + " " + lastName;
	}
	
	public String getFullNameReversed() {
		
		return lastName + " " + firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public static int getIdTracker() {
		return idTracker;
	}
	
	public String toString() {
		
		return String.format("%s %s     Age: %d     ID: %d", firstName, lastName, age, uniqueID);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
