package a19;

import java.util.HashSet;
import java.util.Set;

/**
 * Husband entity. @author MyEclipse Persistence Tools
 */

public class Husband implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set husbandWifes = new HashSet(0);
	private Set wifeHusbands = new HashSet(0);

	// Constructors

	/** default constructor */
	public Husband() {
	}

	/** full constructor */
	public Husband(String name, Set husbandWifes, Set wifeHusbands) {
		this.name = name;
		this.husbandWifes = husbandWifes;
		this.wifeHusbands = wifeHusbands;
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

	public Set getWifeHusbands() {
		return this.wifeHusbands;
	}

	public void setWifeHusbands(Set wifeHusbands) {
		this.wifeHusbands = wifeHusbands;
	}

}