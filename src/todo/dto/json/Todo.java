package todo.dto.json;

public class Todo {

	/** 連番 */
	public int serialNo;
	/** タイトル */
	public String title;

	public Todo() {

	}

	public Todo(String title) {
		this.title = title;
	}

}
