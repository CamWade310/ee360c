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
		// int[] advisor_matching = new int[n];
		// ArrayList<Integer> student_matching = marriage.getStudentMatching();
		int[][] advisor_preferences = get_advisor_preferences(n, marriage.getStudentGPAs(), marriage.getAdviserLocations(), marriage.getStudentLocations());
		
		// DEBUG: Testing if get_advisor_preferences worked
		for(int advisor_index=0; advisor_index<n; advisor_index++) {
			System.out.println("Advisor " + advisor_index + ": " + Arrays.toString(advisor_preferences[advisor_index]));
		}
		// for(int student_index=0; student_index<n; student_index++) {
		// 	advisor_matching[student_matching.get(student_index)]= student_index;
		// }

		// // check each student
		// for(int student_index=0; student_index<n; student_index++) {
		// 	// keep going until j reaches the advisor their currently matched with
		// 	for(int j=0; j<n; j++) {
		// 		// check if the advisor is matched with someone lower than student
		// 		int advisor_index = student_matching.get(student_index);
		// 		
		// 	}
		// }
		return false; /* TODO remove this line */
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
	 * Determines the preference lists for advisors based on student GPA and location
	 *
	 * @return advisors' preference list, indexes are students, values are priority
	 */
	private int[][]  get_advisor_preferences(int n, ArrayList<Double> student_GPAs, ArrayList<Coordinate> advisor_locations, ArrayList<Coordinate> student_locations) {
		int[][] advisor_preferences = new int[n][n];

		for(int advisor_index=0; advisor_index<n; advisor_index++) {
			// creates a list of students and sort them
			ArrayList<Student> students = new ArrayList<Student>();
			for(int student_index=0; student_index<n; student_index++) {
				Student student = new Student(student_index, student_GPAs.get(student_index), student_locations.get(student_index).x, student_locations.get(student_index).y);
				students.add(student);
			}
			Collections.sort(students, new StudentComparator(advisor_locations.get(advisor_index).x, advisor_locations.get(advisor_index).y));
			
			// places sorted list of students into advisor's preference list
			for(int priority=0; priority<n; priority++) {
				advisor_preferences[advisor_index][students.get(priority).getIndex()] = priority;
			}
		}
		return advisor_preferences;
	}
}

