package data;

import java.util.HashSet;
import java.util.Set;

/**
 * Course entity. @author MyEclipse Persistence Tools
 */

public class Course implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set studentAndCourses = new HashSet(0);
	private Set grades = new HashSet(0);

	// Constructors

	/** default constructor */
	public Course() {
	}

	/** minimal constructor */
	public Course(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Course(Integer id, String name, Set studentAndCourses, Set grades) {
		this.id = id;
		this.name = name;
		this.studentAndCourses = studentAndCourses;
		this.grades = grades;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getStudentAndCourses() {
		return this.studentAndCourses;
	}

	public void setStudentAndCourses(Set studentAndCourses) {
		this.studentAndCourses = studentAndCourses;
	}

	public Set getGrades() {
		return this.grades;
	}

	public void setGrades(Set grades) {
		this.grades = grades;
	}

}