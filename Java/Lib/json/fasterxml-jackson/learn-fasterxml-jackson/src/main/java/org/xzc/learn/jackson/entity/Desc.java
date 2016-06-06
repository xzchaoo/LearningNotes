package org.xzc.learn.jackson.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by xzchaoo on 2016/6/4 0004.
 */
//@JsonIgnoreType
public class Desc {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonValue
	@Override
	public String toString() {
		return "content====" + content;
	}
}
