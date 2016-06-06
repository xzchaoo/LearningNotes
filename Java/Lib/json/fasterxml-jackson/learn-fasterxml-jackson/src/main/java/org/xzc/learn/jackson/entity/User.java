package org.xzc.learn.jackson.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xzchaoo on 2016/6/4 0004.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"o2", "o3"}, ignoreUnknown = true)
@JsonPropertyOrder({"password", "username", "id"})
@JsonRootName("kk")
public class User {
	public interface Basic {
	}

	public interface Detail extends Basic {
	}

	@JsonView(Basic.class)
	private Integer id;
	@JsonView(Basic.class)
	private String username;
	private String password;

	@JsonView(Detail.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@JsonView(Detail.class)
	private Date createAt;


	private Desc desc;

	@JsonProperty("asdf")
	private double money;

	@JsonIgnore
	private String o1;

	private String o2;
	private String o3;

	@JsonIgnore
	private Map<String, Object> others = new HashMap<>();

	@JsonAnySetter
	public void setOther(String name, Object value) {
		others.put(name, value);
	}

	public Map<String, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<String, Object> others) {
		this.others = others;
	}

	@JsonView(Detail.class)
	@JsonUnwrapped
	private A1 a1;

	public User() {
	}

	public A1 getA1() {
		return a1;
	}

	public void setA1(A1 a1) {
		this.a1 = a1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getO1() {
		return o1;
	}

	public void setO1(String o1) {
		this.o1 = o1;
	}

	public String getO2() {
		return o2;
	}

	public void setO2(String o2) {
		this.o2 = o2;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public Desc getDesc() {
		return desc;
	}

	public void setDesc(Desc desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", username='" + username + '\'' +
			", password='" + password + '\'' +
			", birthday=" + birthday +
			", createAt=" + createAt +
			", desc=" + desc +
			", money=" + money +
			", o1='" + o1 + '\'' +
			", o2='" + o2 + '\'' +
			", o3='" + o3 + '\'' +
			'}';
	}
}

