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
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ValueRange;

import static org.junit.Assert.*;

public class ChronoUnitTest {
	@Test
	public void test_daysBetween() {
		LocalDate date1 = LocalDate.of(2017, 1, 1);
		LocalDate date2 = LocalDate.of(2017, 1, 8);
		int days = (int) ChronoUnit.DAYS.between(date1, date2);
		assertEquals(7, days);
	}

}
