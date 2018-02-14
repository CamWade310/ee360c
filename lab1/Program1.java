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
		int[] adviser_preferences_index = new int[n];

		// student_matching: index is student, value is adviser (-1 if empty)
		// initially all students are free
		ArrayList<Integer> student_matching = new ArrayList<Integer>();
		for(int student=0; student<n; student++) {
			student_matching.add(-1);
		}

		// free_advisers: index is adviser, value is free (0), not free (-1)
		// initially all advisers are free
		ArrayList<Integer> free_advisers = new ArrayList<Integer>();
		for(int adviser=0; adviser<n; adviser++) {
			free_advisers.add(0);
		}

		int adviser = free_advisers.indexOf(0);
		while(adviser != -1 && adviser_preferences_index[adviser] != n) {
			int student = adviser_preferences.get(adviser).get(adviser_preferences_index[adviser]);
			adviser_preferences_index[adviser]++;
			
			if(student_matching.get(student) == -1) {
				student_matching.set(student, adviser);
				free_advisers.set(adviser, -1);
			}
			else {
				int adviser_current = student_matching.get(student);
				if(student_preferences.get(student).indexOf(adviser) < student_preferences.get(student).indexOf(adviser_current)) {
					student_matching.set(student, adviser);
					free_advisers.set(adviser, -1);
					free_advisers.set(adviser_current, 0);
				}
			}

			adviser = free_advisers.indexOf(0);
		}

		marriage.setResidentMatching(student_matching);
		return marriage;
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

