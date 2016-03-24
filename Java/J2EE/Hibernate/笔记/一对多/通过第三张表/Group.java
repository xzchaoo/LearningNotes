package xzc.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Where;

//@Proxy(lazy = false)
@Entity
@Table(name = "_group")
//@Where(clause = "id=44") 这个条件会以and的形式出现在所有语句后面
public class Group {
	private int id;
	private String name;
	private Set<Person> persons;

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

	@OneToMany(cascade=CascadeType.ALL)//(mappedBy = "group", fetch = FetchType.LAZY)
	@JoinTable(name = "pg", joinColumns = @JoinColumn(name = "g_id"), inverseJoinColumns = @JoinColumn(name = "p_id"))
	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}
}
