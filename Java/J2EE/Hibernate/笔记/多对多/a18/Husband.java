package a18;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Husband {
	private int id;
	private String name;
	private Set<Wife> wifes = new HashSet<Wife>();

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
	public Set<Wife> getWifes() {
		return wifes;
	}

	public void setWifes(Set<Wife> wifes) {
		this.wifes = wifes;
	}
}
