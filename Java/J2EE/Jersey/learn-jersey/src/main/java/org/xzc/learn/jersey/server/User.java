package org.xzc.learn.jersey.server;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xzchaoo on 2016/5/17 0017.
 */
@XmlRootElement
public class User {
	public int id;
	@XmlElement(name = "name")
	public String username;
	@XmlAttribute(name="d")
	public double money;

	public User(int id, String username, double money) {
		this.id = id;
		this.username = username;
		this.money = money;
	}

	public User() {
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", username='" + username + '\'' +
			", money=" + money +
			'}';
	}
}
