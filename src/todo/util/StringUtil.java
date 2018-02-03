package todo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class StringUtil {

	/*
	 * インスタンス化禁止
	 */
	private StringUtil() {

	}

	/**
	 * 文字列が空か評価します。
	 * 
	 * @param text
	 * @return 文字列がnullまたは空文字列ならtrue
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}

	/**
	 * 文字列が空でないか評価します。
	 * 
	 * @param text
	 * @return 文字列がnullでないまたは空文字列でければtrue
	 */
	public static boolean isNotEmpty(String text) {
		return !isEmpty(text);
	}

	/**
	 * 先頭の文字を大文字に変換します。
	 * 
	 * @param text
	 * @return 変換後の文字列
	 */
	public static String capitalize(String text) {
		if (isEmpty(text)) {
			return text;
		}
		char chars[] = text.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	/**
	 * 文字列が数値か評価します。
	 * 
	 * @param text
	 * @return 数値の場合、true
	 */
	public static boolean isNumber(String text) {
		if (isEmpty(text)) {
			return false;
		}

		final int size = text.length();
		for (int i = 0; i < size; i++) {
			final char chr = text.charAt(i);
			if (chr < '0' || '9' < chr) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 文字列が数値でないか評価します。
	 * 
	 * @param text
	 * @return 数値でないの場合、true
	 */
	public static boolean isNotNumber(String text) {
		return !isNumber(text);
	}

	/**
	 * 今日の日付文字列を取得します。
	 * 
	 * @return 今日の日付文字列(yyyy-MM-dd)
	 */
	public static String getTodayText() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

}
