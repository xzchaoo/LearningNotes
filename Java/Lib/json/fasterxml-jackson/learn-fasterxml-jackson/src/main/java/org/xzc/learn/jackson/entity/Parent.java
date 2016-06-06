package org.xzc.learn.jackson.entity;

/**
 * Created by xzchaoo on 2016/6/5 0005.
 */

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("p")
@JsonSubTypes({@JsonSubTypes.Type(Child1.class), @JsonSubTypes.Type(Child2.class)})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Parent {
	private Integer id;
	private String name;

	/*
		@JsonTypeId
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
