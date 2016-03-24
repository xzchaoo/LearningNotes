package data;

import java.util.HashSet;
import java.util.Set;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set grades = new HashSet(0);
	private Set studentAndCourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Student(Integer id, String name, Set grades, Set studentAndCourses) {
		this.id = id;
		this.name = name;
		this.grades = grades;
		this.studentAndCourses = studentAndCourses;
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

	public Set getGrades() {
		return this.grades;
	}

	public void setGrades(Set grades) {
		this.grades = grades;
	}

	public Set getStudentAndCourses() {
		return this.studentAndCourses;
	}

	public void setStudentAndCourses(Set studentAndCourses) {
		this.studentAndCourses = studentAndCourses;
	}

}