package todo.dto;

import java.util.Date;

public class ExecutingTodo {

	public ExecutingTodo(String title) {
		this.title = title;
		startDate = new Date();
	}

	/** 実行中のtodoタイトル */
	public String title;
	/** 開始日時 */
	public Date startDate;

}
