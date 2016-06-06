package org.xzc.learn.jackson.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by xzchaoo on 2016/6/5 0005.
 */
@JsonTypeName("c1")
public class Child1 extends Parent {
	private String p1;

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}
}
