package todo.dto.json;

import com.taskadapter.redmineapi.bean.Issue;

public class Todo {

	/** 連番 */
	public int serialNo;
	/** タイトル */
	public String title;
	/** ID */
	public String issueId;

	public Todo() {

	}

	public Todo(String title) {
		this.title = title;
	}

	public Todo(Issue issue) {
		this.title = issue.getSubject();
		this.issueId = issue.getId().toString();
	}

}
