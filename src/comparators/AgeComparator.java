package comparators;

import dataClasses.Person;

public class AgeComparator extends personComparator  {

	/**
	 * Compares based on age, full name, ID in that priority
	 * 
	 */
	public AgeComparator() {
		
		super();
	}

	
	@Override
	public int compare(Person person1, Person person2) {
		
		int nameCompare = person1.getFullName().compareTo(person2.getFullName());
		int ageCompare = person1.getAge() - person2.getAge();
		int IDCompare = person1.getUniqueID() - person2.getUniqueID();
		
		if (ageCompare == 0 && nameCompare == 0) {
			
			return IDCompare;
		}
		
		else if (ageCompare == 0) {
			
			return nameCompare;
		}
		
		else {
			
			return ageCompare;
		}
	}
	
	

}
