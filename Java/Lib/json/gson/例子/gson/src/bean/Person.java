package bean;

import java.util.List;

import annotation.FooAnnotation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

@FooAnnotation
public class Person {
	@Expose(serialize=false,deserialize=false)
	@Since(1.5)
	@SerializedName("xoo5")
	private int id;
	private String name;
	private List<String> items;
	private Shabi shabi;

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", items=" + items + ", age=" + age + "]";
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public int getId222() {
		return id;
	}

	public void setId222(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private int age;

	public Shabi getShabi() {
		return shabi;
	}

	public void setShabi(Shabi shabi) {
		this.shabi = shabi;
	}
}
