package org.xzc.learn.jodatime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.DateTimeZoneBuilder;

import java.util.Date;

/**
 * Created by xzchaoo on 2016/6/2 0002.
 */
public class Main {
	public static void main(String[] args) {
		LocalTime lt = new LocalTime();
		System.out.println(lt.secondOfMinute().getName());
		System.out.println(lt);
		System.out.println(Weeks.weeksBetween(new LocalDate(1994, 5, 21), LocalDate.now()).getWeeks());
		System.out.println(Years.yearsBetween(new LocalDate(1994, 5, 21), LocalDate.now()).getYears());

		Date now = new Date();
		// Date -> DateTime
		DateTime dt = new DateTime(now);

		//DateTime -> 三种本地类型
		//dt.toLocalDate();
		//dt.toLocalDateTime();
		//dt.toLocalTime();

		LocalDateTime ldt = new LocalDateTime();
		//ldt.toDateTime();
		//ldt.toDateTime(zone);

		// ldt.toLocalDate()

		//LocalDateTime -> LocalTime
		//ldt.toLocalTime()

		//Interval i = new Interval(start, end);
		//i.toDurationMillis()//毫秒

		//Interval -> Period
		//i.toPeriod();

		//Duration d=Duration.standardDays()
		Duration d = Duration.standardMinutes(5);

		//Period p = new Period(1, PeriodType.months());
		//3秒的period
		Period p = new Period(3000, PeriodType.seconds());
		System.out.println(p);
		System.out.println(DateTime.now().plus(p));

		p = new Period();
		MutablePeriod mp = new MutablePeriod();
		mp.setYears(1);
		mp.setMonths(1);
		System.out.println(DateTime.now().plus(mp));

		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy--MM--dd");
		System.out.println(dtf.print(System.currentTimeMillis()));
	}
}
