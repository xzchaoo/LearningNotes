/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.xzchaoo.learn.jdk8time;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterTest {
	@Test
	public void test1() {
		//不可变 & 线程安全
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(dtf.format(LocalDateTime.now()));
	}

	@Test
	public void test2() {
		DateTimeFormatter dtf = new DateTimeFormatterBuilder()
			.appendValue(ChronoField.YEAR)
			.appendLiteral('-')
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.appendLiteral('/')
			.appendValue(ChronoField.DAY_OF_MONTH)
			.toFormatter();
		System.out.println(dtf.format(LocalDateTime.now()));
	}
}
