package xzc.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "_person")
@BatchSize(size = 2)
// 也可以不指定这项 则默认表名为person
/*@TableGenerator(name = "p_tg",// 这个生成器的名字
table = "ids_tb",// 对应的表的名字 表有两个列 第一列用于表示该行用于哪个表 第二行表示下一个id值
pkColumnName = "p_id",// 第一列的column name
pkColumnValue = "persono",// 我们的person表对应的value
initialValue = 0,// 初始值为0
valueColumnName = "next_p_id"// 第二列的column name
)*/
public class Person {
	private int id;
	private String name;
	private String sex;
	private Date birthday;
	private BigDecimal money;
	private String description;
	private boolean married;
	private int version;
	private Group group;

	@Id
	// @GeneratedValue(strategy = GenerationType.TABLE, generator = "p_tg")
	// 默认就是auto,在mysql下它表现为主键自动增长
	// 它还有另外一项属性generator当类型是 SequenceGenerator or TableGenerator
	// 的时候才会起作用 具体看java ee api解释
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinTable(name = "pg", joinColumns = @JoinColumn(name = "p_id"), inverseJoinColumns = @JoinColumn(name = "g_id"))
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
