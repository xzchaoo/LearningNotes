/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.xzchaoo.learn.jodatime;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by xzchaoo on 2016/6/2 0002.
 */
public class TestDatetime {
	@Test
	public void test_datetime_default_timezone() {
		//默认就是本地时间
		DateTime now = DateTime.now();
		assertEquals(now.getZone().toTimeZone(), TimeZone.getDefault());
	}

	@Test
	public void test_datetime() {
		DateTime.Property d = DateTime.now().dayOfWeek();
		System.out.println(d.setCopy("星期三"));
		System.out.println(d.getAsShortText(Locale.SIMPLIFIED_CHINESE));
		System.out.println(d.getAsText(Locale.SIMPLIFIED_CHINESE));
		System.out.println(d.getAsText(Locale.JAPANESE));
	}
}
