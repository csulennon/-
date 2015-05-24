package com.kongderui.taskmanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public static String getCurrentTimeString() {
		return format.format(new Date());
	}

	public static String getFormatDateTime(long time) {
		return format.format(new Date(time));
	}

	public static String getHumanReadableTimeString(int min) {
		String str = "";

		if (min < 60) {
			str = min + "����";
		} else if (min < 60 * 24) {
			str = (min / 60) + "Сʱ" + (min % 60) + "����";
		} else {
			str = (min / (60 * 24)) + "��" + ((min % (60 * 24)) / 60) + "Сʱ" + (min % 60) + "����";
		}
		return str;
	}

}
