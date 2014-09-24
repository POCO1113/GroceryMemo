package com.example.shoppingitemlist;

import java.util.Calendar;

import android.widget.Toast;

public class MyTools {
	private MainActivity activity;

	public MyTools(MainActivity activity) {
		this.activity = activity;
	}

	/*
	 * 現在の年月日・時分を返すメソッド return String
	 */
	public static String currentTime() {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// 現在の年月日・時分を返す
		return "" + year + "/" + month + "/" + day + " " + " " + hour + ":"
				+ minute;
	}

	/*
	 * Toast表示のメソッド化
	 */
	public void showToast(String message) {
		Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show();
	}

	/*
	 * スペースが含まれているかどうか判定。 含まれていないときは0または−１を返す
	 */
	public static int checkMultiItems(String listsStr) {
		int point = listsStr.indexOf(" ");
		return point;
	}

}
