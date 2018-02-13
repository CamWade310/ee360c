import java.util.Comparator;
import java.lang.Math;

public class Student {
	private int index;
	private double gpa;
	private double x;
	private double y;

	public Student(int index, double gpa, double x, double y) {
		this.index = index;
		this.gpa = gpa;
		this.x = x;
		this.y = y;
	}
	
	int getIndex() {
		return index;
	}

	double getGPA() {
		return gpa;
	}

	double getX() {
		return x;
	}

	double getY() {
		return y;
	}
}

class StudentComparator implements Comparator<Student> {
	// advisor's location
	double x;
	double y;
	
	StudentComparator(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compare(Student s1, Student s2) {
		// NOTE: less preferred student should be closer to index 0
		if(s1.getGPA() < s2.getGPA()) {
			return -1;
		}
		else if(s1.getGPA() > s2.getGPA()) {
			return 1;
		}

		if(get_distance(x, y, s1.getX(), s1.getY()) > get_distance(x, y, s2.getX(), s2.getY())) {
			return -1;
		}
		else if(get_distance(x, y, s1.getX(), s1.getY()) < get_distance(x, y, s2.getX(), s2.getY())) {
			return 1;
		}
		
		// code should never get down here
		return 0;
	}

	private double get_distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));	
	}
}
