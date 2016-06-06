package org.xzc.learn.jodatime;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Created by xzchaoo on 2016/6/2 0002.
 */
public class TestDatetime {
	@Test
	public void test_datetime_default_timezone() {
		DateTime now = DateTime.now();
		assertEquals(now.getZone().toTimeZone(), TimeZone.getDefault());
	}

	@Test
	public void test_datetime() {
		DateTime.Property d = DateTime.now().dayOfWeek();
		System.out.println(d.setCopy("星期三"));
		System.out.println(d.getAsShortText(Locale.SIMPLIFIED_CHINESE));
		System.out.println(d.getAsText(Locale.SIMPLIFIED_CHINESE));
	}
}
