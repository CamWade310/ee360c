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
		int[] adviser_matching = new int[n];
		ArrayList<Integer> student_matching = marriage.getStudentMatching();
		int[][] adviser_preferences = get_adviser_preferences(n, marriage.getStudentGPAs(), marriage.getAdviserLocations(), marriage.getStudentLocations());
		ArrayList<ArrayList<Integer>> student_preferences = marriage.getStudentPreference();
	
		// DEBUG: Testing if get_adviser_preferences worked
		// for(int adviser_index=0; adviser_index<n; adviser_index++) {
		// 	System.out.println("adviser " + adviser_index + ": " + Arrays.toString(adviser_preferences[adviser_index]));
		// }
		
		// create adviser_matching
		for(int student_index=0; student_index<n; student_index++) {
			adviser_matching[student_matching.get(student_index)] = student_index;;
		}

		for(int student_1=0; student_1<n; student_1++) {
			int adviser_1 = student_matching.get(student_1);
			
			for(int i=0; i<n; i++) {
				int adviser_2 = student_preferences.get(student_1).get(i);
				int student_2 = adviser_matching[adviser_2];
				if(adviser_2 == adviser_1) {
					break;
				}
				if(adviser_preferences[adviser_2][student_1] > adviser_preferences[adviser_2][student_2]) {
					return false;
				}	
			}
		}

		return true;

		// go through student_index
		// find adviser who is matched to student_index in student_matching
		// go through student_pref to check higher priority advisers than adviser1
		// if adviser_index reaches adviser1, then that student is stable
		// check who adviser_index matched to in adviser_matching
		// check adviser_pref if student2 > student1
		// if true, not stable, retunr false;
		//
		//
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching marriage) {
        /* TODO implement this function */
        return null; /* TODO remove this line */
    }

	/**
	 * Determines the preference lists for advisers based on student GPA and location
	 *
	 * @return advisers' preference list, indexes are students, values are priority
	 */
	private int[][]  get_adviser_preferences(int n, ArrayList<Double> student_GPAs, ArrayList<Coordinate> adviser_locations, ArrayList<Coordinate> student_locations) {
		int[][] adviser_preferences = new int[n][n];

		for(int adviser_index=0; adviser_index<n; adviser_index++) {
			// creates a list of students and sort them
			ArrayList<Student> students = new ArrayList<Student>();
			for(int student_index=0; student_index<n; student_index++) {
				Student student = new Student(student_index, student_GPAs.get(student_index), student_locations.get(student_index).x, student_locations.get(student_index).y);
				students.add(student);
			}
			Collections.sort(students, new StudentComparator(adviser_locations.get(adviser_index).x, adviser_locations.get(adviser_index).y));
			
			// places sorted list of students into adviser's preference list
			for(int priority=0; priority<n; priority++) {
				adviser_preferences[adviser_index][students.get(priority).getIndex()] = priority;
			}
		}
		return adviser_preferences;
	}
}

