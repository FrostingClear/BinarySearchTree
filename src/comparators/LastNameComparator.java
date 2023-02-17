package comparators;

import dataClasses.Person;

/**
 * Compares names based on last name, first name, age, id in that priority
 * 
 */
public class LastNameComparator extends personComparator{


	public LastNameComparator() {
		
		super();
	}

	@Override
	public int compare(Person person1, Person person2) {
		
		int nameCompare = person1.getFullNameReversed().compareTo(person2.getFullNameReversed());
		int ageCompare = person1.getAge() - person2.getAge();
		int IDCompare = person1.getUniqueID() - person2.getUniqueID();
		
		if (nameCompare == 0 && ageCompare == 0) {
			
			return IDCompare;
		}
		
		else if (nameCompare == 0) {
			
			return ageCompare;
		}
		
		else {
			
			return nameCompare;
		}
		
	}

}
