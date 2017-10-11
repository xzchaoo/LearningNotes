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
import java.time.temporal.ValueRange;

import static org.junit.Assert.*;

public class RangeTest {
	@Test
	public void test1() {
		LocalDate date1 = LocalDate.of(2017, 1, 1);
		ValueRange dayRange = date1.range(ChronoField.DAY_OF_MONTH);
		assertEquals(1, dayRange.getMinimum());
		assertEquals(31, dayRange.getMaximum());
	}
}
