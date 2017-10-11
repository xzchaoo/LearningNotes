/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.xzchaoo.learn.jdk8time;

import org.junit.Test;

import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class PeriodTest {
	@Test
	public void test1() {
		//计算两个日期的间隔时间
		Period p = Period.ofDays(2);
		System.out.println(p);
		System.out.println(p.getDays());
		System.out.println(p.getMonths());
		System.out.println(ZoneOffset.ofHours(8));
		ZoneId.getAvailableZoneIds();
	}
}
