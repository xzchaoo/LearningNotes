/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.xzchaoo.learn.jdk8time;

import org.junit.Test;

import java.time.LocalTime;

/**
 * 感觉这个类应该会比较少用
 */
public class LocalTimeTest {
	@Test
	public void test1() {
		LocalTime now = LocalTime.now();
		System.out.println(now);
		System.out.println(LocalTime.of(1, 1, 1));
	}
}
