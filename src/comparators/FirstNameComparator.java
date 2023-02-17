package comparators;

import dataClasses.*;

/**
 * Compares people by First Name (Strictly speaking their full names), age, ID in that priority
 * 
 *
 */

public class FirstNameComparator extends personComparator {

	public FirstNameComparator() {
		
		super();
	}
	
	public int compare(Person person1, Person person2) {
		
		int firstNameCompare = person1.getFirstName().compareTo(person2.getFirstName());
		int lastNameCompare = person1.getFirstName().compareTo(person2.getFirstName());
		int ageCompare = person1.getAge() - person2.getAge();
		int IDCompare = person1.getUniqueID() - person2.getUniqueID();
		
		//If last name, first name and age are the same, then compare by ID
		if (firstNameCompare == 0 && lastNameCompare == 0 && ageCompare == 0) {
			
			return IDCompare;
		}
		
		//If first name and last name are the same, compare by age
		else if (firstNameCompare == 0 && lastNameCompare == 0) {
			
			return ageCompare;
		}
		
		else if(firstNameCompare == 0) {
			
			return lastNameCompare;
		}
		
		else {
			
			return firstNameCompare;
		}
		
	}
}
