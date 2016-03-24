package a18;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Wife {
	private int id;
	private String name;
	private Set<Husband> husbands = new HashSet<Husband>();

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ManyToMany
	public Set<Husband> getHusbands() {
		return husbands;
	}

	public void setHusbands(Set<Husband> husbands) {
		this.husbands = husbands;
	}
}