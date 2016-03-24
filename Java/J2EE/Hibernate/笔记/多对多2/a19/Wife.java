package a19;

import java.util.HashSet;
import java.util.Set;

/**
 * Wife entity. @author MyEclipse Persistence Tools
 */

public class Wife implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set husbandWifes = new HashSet(0);

	// Constructors

	/** default constructor */
	public Wife() {
	}

	/** full constructor */
	public Wife(String name, Set husbandWifes) {
		this.name = name;
		this.husbandWifes = husbandWifes;
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

	public Set getHusbandWifes() {
		return this.husbandWifes;
	}

	public void setHusbandWifes(Set husbandWifes) {
		this.husbandWifes = husbandWifes;
	}

}