package app;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/11/23.
 */
public class App {
	public static void main(String[] args) {
		System.out.println(StringUtils.substringBetween("abc", "a", "c"));
	}
}
