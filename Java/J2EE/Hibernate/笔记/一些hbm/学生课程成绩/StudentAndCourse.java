package data;

/**
 * StudentAndCourse entity. @author MyEclipse Persistence Tools
 */

public class StudentAndCourse implements java.io.Serializable {

	// Fields

	private StudentAndCourseId id;

	// Constructors

	/** default constructor */
	public StudentAndCourse() {
	}

	/** full constructor */
	public StudentAndCourse(StudentAndCourseId id) {
		this.id = id;
	}

	// Property accessors

	public StudentAndCourseId getId() {
		return this.id;
	}

	public void setId(StudentAndCourseId id) {
		this.id = id;
	}

}