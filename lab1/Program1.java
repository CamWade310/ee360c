/*
 * Name: Dayoung Lee
 * EID: dl29923
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

import java.util.Arrays;


/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching marriage) {
		int n = marriage.getNumberOfAdvisers();
		ArrayList<Integer> student_matching = marriage.getStudentMatching();
		ArrayList<ArrayList<Integer>> adviser_preferences = get_adviser_preferences(n, marriage.getStudentGPAs(), marriage.getAdviserLocations(), marriage.getStudentLocations());
		ArrayList<ArrayList<Integer>> student_preferences = marriage.getStudentPreference();
	
		// DEBUG: Testing if get_adviser_preferences worked
		// for(int adviser_index=0; adviser_index<n; adviser_index++) {
		// 	System.out.println("adviser " + adviser_index + ": " + Arrays.toString(adviser_preferences[adviser_index]));
		// }
		
		for(int student_1=0; student_1<n; student_1++) {
			int adviser_1 = student_matching.get(student_1);
			
			for(int i=0; i<n; i++) {
				int adviser_2 = student_preferences.get(student_1).get(i);
				int student_2 = student_matching.indexOf(adviser_2);
				if(adviser_2 == adviser_1) {
					break;
				}
				if(adviser_preferences.get(adviser_2).indexOf(student_1) < adviser_preferences.get(adviser_2).indexOf(student_2)) {
					// DEBUG: Output what the matching was
					// didn't actually do it
					return false;
				}	
			}
		}

		return true;
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching marriage) {
		int n = marriage.getNumberOfAdvisers();
		ArrayList<ArrayList<Integer>> adviser_preferences = get_adviser_preferences(n, marriage.getStudentGPAs(), marriage.getAdviserLocations(), marriage.getStudentLocations());
		ArrayList<ArrayList<Integer>> student_preferences = marriage.getStudentPreference();
		int[] adviser_preference_index = new int[n];

		ArrayList<Integer> student_matching = new ArrayList<Integer>();
		for(int adviser_index=0; adviser_index<n; adviser_index++) {
			student_matching.add(-1);
		}

		while(student_matching.contains(-1)) {
			// offer_list: index is students, values are list of advisers who are giving offers
			ArrayList<ArrayList<Integer>> offer_list = new ArrayList<ArrayList<Integer>>();
			for(int i=0; i<n; i++) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				offer_list.add(list);
			}
			
			// populate the offer_list
			for(int adviser=0; adviser<n; adviser++) {
				if(student_matching.indexOf(adviser) == -1) {
					int student = adviser_preferences.get(adviser).get(adviser_preference_index[adviser]);
					adviser_preference_index[adviser]++;
					offer_list.get(student).add(adviser);
				}	
			}
			
			// have a list of preferences already sorted using comparator,
			// add the current match of adviser
			// take the one from the first index
			// matching
			for(int student=0; student<n; student++) {
				for(int offer_index=0; offer_index<student_offer_size; offer_index++) {
					int adviser = student_matching.get(student);
					// if student doesnt have adviser, take adviser
					if(adviser == -1) {
						student_matching.set(student, adviser);
					}
					// if student has adviser, choose which one
					else {
						if() {

						}
					}
				}
			}
		}
			
		// start loop, advisors index
		// create ArrayList with all advisers have no student, call indexOf, if indexOf returns -1, then the adviser does not have a student
		// check if adviser has student
		// ArrayList of Integers (offers), index is adviser, value is student offered, -1 if adviser does not give offer
		// keep finding indexOf student, and compare offers
		// if student doesn't have adviser, take it
		// if student does have adviser, compare in preference list
		// if higher, swap
		// if lower, ignore offer
		// need something to keep track of index of each adviser's preference list

		// matching is an ArrayList<Integer>, index is student, value is adviser

		marriage.setResidentMatching(student_matching);
		return null;
    }

	/**
	 * Determines the preference lists for advisers based on student GPA and location
	 *
	 * @return advisers' preference list, indexes are priority (0 is most preferred), values are students
	 */
	private ArrayList<ArrayList<Integer>> get_adviser_preferences(int n, ArrayList<Double> student_GPAs, ArrayList<Coordinate> adviser_locations, ArrayList<Coordinate> student_locations) {
		ArrayList<ArrayList<Integer>> adviser_preferences = new ArrayList<ArrayList<Integer>>();

		for(int adviser_index=0; adviser_index<n; adviser_index++) {
			// creates a list of students and sort them
			ArrayList<Student> students = new ArrayList<Student>();
			for(int student_index=0; student_index<n; student_index++) {
				Student student = new Student(student_index, student_GPAs.get(student_index), student_locations.get(student_index).x, student_locations.get(student_index).y);
				students.add(student);
			}
			Collections.sort(students, new StudentComparator(adviser_locations.get(adviser_index).x, adviser_locations.get(adviser_index).y));
			
			// places sorted list of students into adviser's preference list
			ArrayList<Integer> preference = new ArrayList<Integer>();
			for(int priority=0; priority<n; priority++) {
				preference.add(students.get(priority).getIndex());
			}
			adviser_preferences.add(preference);
		}
		return adviser_preferences;
	}
}

class AdviserComparator implements Comparator<int> {
	private ArrayList<Integer> student_preferences;
	
	AdviserComparator(ArrayList<Integer> student_preferences) {
		this.student_preferences = student_preferences;
	}

	@Override
	public int compare(int a1, int a2) {
		// TODO: change this
		// NOTE: more  preferred student should be closer to index 0
		if(s1.getGPA() > s2.getGPA()) {
			return -1;
		}
		else if(s1.getGPA() < s2.getGPA()) {
			return 1;
		}

		if(get_distance(x, y, s1.getX(), s1.getY()) < get_distance(x, y, s2.getX(), s2.getY())) {
			return -1;
		}
		else if(get_distance(x, y, s1.getX(), s1.getY()) > get_distance(x, y, s2.getX(), s2.getY())) {
			return 1;
		}
		
		// code should never get down here
		return 0;
	}

	private double get_distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));	
	}
}
