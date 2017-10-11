/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.xzchaoo.learn.jdk8time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * {@link LocalDate}
 * 表示一个本地日期
 */
public class LocalDateTest {
	@Test
	public void test1() {
		//LocalDate today = LocalDate.now();

		LocalDate date = LocalDate.of(2012, 2, 28);
		date = date.plusDays(1);
		//这个对象是不可变 并且线程安全的
		//格式
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		//dtf.parse()
		assertEquals("2012/02/29", dtf.format(date));
		assertEquals(3, date.getDayOfWeek().getValue());

		//调整某一天
		assertEquals("2012/02/01", dtf.format(date.withDayOfMonth(1)));
	}

	@Test
	public void LocalDate_to_LocalDateTime() {
		LocalDate now = LocalDate.now();
		LocalDateTime ldt = now.atStartOfDay();
		System.out.println(ldt);
		//精度到纳秒 现在没有毫秒了
		ldt = now.atTime(1, 1, 1, 1);
		System.out.println(ldt);
	}

	@Test
	public void test_parseLocalDate() {
		//不推荐使用 建议使用确定的格式
		System.out.println(LocalDate.parse("2017-08-18"));
	}
}
