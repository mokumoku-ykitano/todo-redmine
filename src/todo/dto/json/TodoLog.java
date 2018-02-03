package todo.dto.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TodoLog {

	/** 開始日時 */
	public Date startDate;
	/** 終了日時 */
	public Date endDate;
	/** タイトル */
	public String title;
	/** 作業時間(分) */
	public long workingMinutes;

	/*
	 * jackson用コンストラクタ
	 */
	public TodoLog() {

	}

	public TodoLog(Date startDate, Date endDate, String title) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		workingMinutes = (endDate.getTime() - startDate.getTime()) / (1000 * 60);
	}

	/**
	 * 作業時間を加算します。
	 * 
	 * @param todoLog
	 */
	public void addWorkingMinutes(TodoLog todoLog) {
		workingMinutes += todoLog.workingMinutes;
	}

	/**
	 * 作業時間を再設定します。
	 * 
	 * @param todoLog
	 */
	public void resetWorkingDate(TodoLog todoLog) {
		if (startDate.compareTo(todoLog.startDate) > 0) {
			startDate = todoLog.startDate;
		}
		if (endDate.compareTo(todoLog.endDate) < 0) {
			endDate = todoLog.endDate;
		}
	}

	/**
	 * 作業時間の文字列を作成します。
	 * 
	 * @return タイトル + 作業時間(時分)
	 */
	public String createWorkingHourText() {
		StringBuilder text = new StringBuilder();
		text.append(title);
		text.append(": ");
		text.append(getHourAndMinuteText());
		return text.toString();
	}

	/**
	 * 作業時間(分)を作業時間(時分)に変換して返します。
	 * 
	 * @return 作業時間(時分)
	 */
	private String getHourAndMinuteText() {
		if (workingMinutes <= 0) {
			return "0分";
		}
		long hour = workingMinutes / 60;
		long minute = workingMinutes % 60;
		if (hour == 0) {
			return minute + "分";
		} else {
			return hour + "時間 " + minute + "分";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoLog other = (TodoLog) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public long getWorkingMinutes() {
		return workingMinutes;
	}

	@JsonIgnore
	public String getStartDateText() {
		return getDateText(startDate);
	}

	@JsonIgnore
	public String getEndDateText() {
		return getDateText(endDate);
	}

	private String getDateText(Date date) {
		SimpleDateFormat todoLogDate = new SimpleDateFormat("yyyy/MM/dd");
		return todoLogDate.format(date);
	}

	public String createDateAndWorkingHourText() {
		StringBuilder showLogText = new StringBuilder();
		showLogText.append(getStartDateText());
		showLogText.append(" ");
		showLogText.append(getEndDateText());
		showLogText.append(" ");
		showLogText.append(createWorkingHourText());
		return showLogText.toString();
	}

}
