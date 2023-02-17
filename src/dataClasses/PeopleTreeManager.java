package dataClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import application.BTreeMain;
import comparators.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import personTreeAlgorithms.PersonTrees;

/**
 * Initialises and manages the Binary Trees (of type Person) that the user interacts with. 
 * 
 * 
 *
 */
public class PeopleTreeManager {

	BinaryTree<Person> firstNameTree;
	BinaryTree<Person> lastNameTree;
	BinaryTree<Person> ageTree;
	
	ArrayList<BinaryTree<Person>> allTrees;
	BinaryTree<Person> activeTree = null;
	
	ArrayList<Person> samplePeoplePool = loadSamplePeoplePool();
	ArrayList<Person> peopleListUnsorted; //Well actually it'll be coincidentally "sorted" by ID
	
	//Load in either the test or the already generated dataset;
	public PeopleTreeManager(String dataset) {
		
		//For dataset switching
		if (dataset.equals("standard")) {
		
			peopleListUnsorted = this.loadInGeneratedSample();
		}
		
		else if (dataset.equals("test")) {
			
			peopleListUnsorted = this.loadInTestList();
		}
		
		
		firstNameTree = new BinaryTree<Person>("First Name Tree", copyOf(peopleListUnsorted), new FirstNameComparator());
		firstNameTree.buildTree();
		
		lastNameTree = new BinaryTree<Person>("Last Name Tree", copyOf(peopleListUnsorted), new LastNameComparator());
		lastNameTree.buildTree();
		
		ageTree = new BinaryTree<Person>("Age Tree", copyOf(peopleListUnsorted), new AgeComparator());
		ageTree.buildTree();
		
		allTrees = this.assignTrees();
		
	}
	
	//Constructor used when generating a sample of people user specified size
	public PeopleTreeManager(int sampleSizeToGenerate) {
		
		
		createRandomSample(sampleSizeToGenerate);
			
		peopleListUnsorted = this.loadInGeneratedSample();
		
		firstNameTree = new BinaryTree<Person>("First Name Tree", copyOf(peopleListUnsorted), new FirstNameComparator());
		firstNameTree.buildTree();
		
		lastNameTree = new BinaryTree<Person>("Last Name Tree", copyOf(peopleListUnsorted), new LastNameComparator());
		lastNameTree.buildTree();
		
		ageTree = new BinaryTree<Person>("Age Tree", copyOf(peopleListUnsorted), new AgeComparator());
		ageTree.buildTree();
		
		allTrees = this.assignTrees();
	}
		
	
	
	
	//I want each tree to have their own "companion" array that is freely re-organizable without affect the other trees
	//Not sure whether that really came into play
	private ArrayList<Person> copyOf(ArrayList<Person> peopleList){
		
		ArrayList<Person> peopleListToBeSorted = new ArrayList<Person>();
		
		for (Person person : peopleListUnsorted) {
			
			peopleListToBeSorted.add(person);
		}
		
		return peopleListToBeSorted;
	}
	
	
	private ArrayList<Person> loadInGeneratedSample(){
		
		ArrayList<Person> generatedSample = new ArrayList<Person>();
		
		try {
			
			Scanner scanner = new Scanner(new File("./src/generatedSample.txt"));
			
			Person.resetIDTracker();
			
			while (scanner.hasNextLine()) {
				
				String fname = scanner.next();
				String lname = scanner.next();
				int age = scanner.nextInt();
				
				scanner.nextLine();
				
				Person newPerson = new Person(fname, lname, age);
				generatedSample.add(newPerson);
			}
			
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		}
		
		
		return generatedSample;
		
	}
	
	public void activateAutoBalance() {
		
		for (BinaryTree<Person> tree : allTrees) {
			
			tree.setAutoBalancingOn(true);
		}
	}
	
	public void StopAutoBalance() {
		
		for (BinaryTree<Person> tree : allTrees) {
			
			tree.setAutoBalancingOn(false);
		}
	}
	
	public void addRandomPerson(BTreeMain main, ObservableList<String> list, TextArea message) {
		
		Person newPerson = null;
		
		//Add the person into the common list of people, I may be able to make use of this in future
		//Could also adapt this code in future to make more random people
		for (int i = 0; i < 1; i++) {
			
			int min = 0;
			int max = samplePeoplePool.size() - 1;
		
			//Randomly choose a first name, last name and age from the sample pool
			int fNameselection = (int) Math.floor(Math.random()*(max - min + 1) + min);
			int lNameselection = (int) Math.floor(Math.random()*(max - min + 1) + min);
			int ageSelection = (int) Math.floor(Math.random()*(max - min + 1) + min);
			
			String fName = samplePeoplePool.get(fNameselection).getFirstName();
			String lName = samplePeoplePool.get(lNameselection).getLastName();
			int age = samplePeoplePool.get(ageSelection).getAge();
			
			newPerson = new Person(fName, lName, age);
			
			peopleListUnsorted.add(newPerson);
		}
		
		//Patch up fix, remove if doesn't fix the issue
		if (allTrees == null) {
			
			this.assignTrees();
		}
		
		//Add the person into each tree using binary traversal
		//Updates the observable list for the GUI
		for (BinaryTree<Person> tree : allTrees) {
			
			tree.getDataList().add(newPerson);
			tree.getDataList().sort(tree.getRelevantComparator());
			
			Node<Person> newNode = new Node<Person>(newPerson);
			
			tree.addNode(newNode);
			
			tree.assignBalanceHeights(tree.getRoot());
			
			list.add(newNode.getStoredData().getFullName() + " has been added into " + tree.getName());
			list.add(PersonTrees.personNodeAddedString(newNode));
			
			
			String skew = "The Tree is currently balanced";
			if (!PersonTrees.wholeTreeBalanced(tree)) {
				
				skew = "The Tree is currently out of balance";
			}
			
			list.add(skew);
			list.add("");
		}
		
		String toPrint = "We've added in the person to ALL THREE Trees, see below:";
;		String fullMessage = String.format("%s\n\nTHE ACTIVE TREE HAS NOT CHANGED!!!\n\nNote that when you restart this program the new people you added will be purged from the tree", toPrint);
		
		main.getUserMessages().setText(fullMessage);
			
	}
	
	/**
	 * Overwrites the existing cohort of people with a new randomly generated set
	 * 
	 * @param sizeOfRandomSample
	 */
	private void createRandomSample(int sizeOfRandomSample) {
		
		ArrayList<Person> randomSample = new ArrayList<Person>();
		
		//We want the static ID value to reset so this NEW sample has appropriate ID's
		//ie. 0, 1, 2 etc and not 32, 54, 22 etc. due to the random selection process
		Person.resetIDTracker();
		
		for (int i = 0; i < sizeOfRandomSample; i++) {
		
			int min = 0;
			int max = samplePeoplePool.size() - 1;
		
			//Randomly choose a first name, last name and age from the sample pool
			int fNameselection = (int) Math.floor(Math.random()*(max - min + 1) + min);
			int lNameselection = (int) Math.floor(Math.random()*(max - min + 1) + min);
			int ageSelection = (int) Math.floor(Math.random()*(max - min + 1) + min);
			
			String fName = samplePeoplePool.get(fNameselection).getFirstName();
			String lName = samplePeoplePool.get(lNameselection).getLastName();
			int age = samplePeoplePool.get(ageSelection).getAge();
			
			Person samplePerson = new Person(fName, lName, age);
			
			randomSample.add(samplePerson);
		}
		
			
		try {
			PrintStream printer = new PrintStream(new File("./src/generatedSample.txt"));
			
			
			
			for (int i = 0; i < sizeOfRandomSample; i++) {
				
				Person samplePerson = randomSample.get(i);
				
				printer.printf("%s %s %d %d\n", samplePerson.getFirstName(), samplePerson.getLastName(), samplePerson.getAge(), samplePerson.getUniqueID());
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Loads in a fairly large pool of names, which serve as "inspiration"
	 * when creating a cohort of names for the tree
	 * @return
	 */
	private ArrayList<Person> loadSamplePeoplePool() {
		
		ArrayList<Person> samplePeoplePool = new ArrayList<Person>();
		
		try {
			
			Scanner sc = new Scanner(new File("./src/sampleNames.txt"));
			
			while (sc.hasNextLine()) {
				
				String fName = sc.next().strip();
				String lName = sc.next().strip().toUpperCase();
				int age = (int) Math.floor(Math.random()*(100 - 1 + 1) + 1); //Age between 1 - 100
			
				Person person = new Person(fName, lName, age);
				
				samplePeoplePool.add(person);
				
				sc.nextLine();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
		}
		
		return samplePeoplePool;
	}
	

	
	/**
	 * Loads in a very basic list for testing purposes
	 * @return
	 */
	private ArrayList<Person> loadInTestList() {
		
		ArrayList<Person> testList = new ArrayList<Person>();
		
		try {
			
			Scanner scanner = new Scanner(new File("./src/testPeople.txt"));
			
			Person.resetIDTracker();

			
			while (scanner.hasNextLine()) {
							
				String fname = scanner.next();
				String lname = scanner.next();
				int age = scanner.nextInt();
				
				Person newPerson = new Person(fname, lname, age);
				testList.add(newPerson);
			}
			
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		}
				
		return testList;
	}
	
	public ArrayList<BinaryTree<Person>> assignTrees(){
		
		ArrayList<BinaryTree<Person>> allTrees = new ArrayList<BinaryTree<Person>>();
		
		allTrees.add(firstNameTree);
		allTrees.add(lastNameTree);
		allTrees.add(ageTree);
		
		return allTrees;
				
	}
	
	public BinaryTree<Person> getFirstNameTree() {
		return firstNameTree;
	}





	public BinaryTree<Person> getLastNameTree() {
		return lastNameTree;
	}





	public BinaryTree<Person> getAgeTree() {
		return ageTree;
	}





	public ArrayList<BinaryTree<Person>> getAllTrees() {
		return allTrees;
	}





	public ArrayList<Person> getPeopleListUnsorted() {
		return peopleListUnsorted;
	}

	public BinaryTree<Person> getActiveTree() {
		return activeTree;
	}

	public void setActiveTree(BinaryTree<Person> relevantTree) {
		this.activeTree = relevantTree;
	}


	
	
	
}
