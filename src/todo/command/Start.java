package todo.command;

import java.util.List;

import todo.TodoControler;
import todo.TodoLogic;
import todo.dto.ExecutingTodo;
import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Start extends Command {

	private String todoTitle;

	@Override
	public void setArguments(String[] args) throws TodoException {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(createArgumentErrorMessage());
		}
		if (StringUtil.isNotNumber(args[0])) {
			throw new IllegalArgumentException(createArgumentErrorMessage());
		}

		int todoIndex = Integer.parseInt(args[0]) - 1;
		List<Todo> todoList = TodoLogic.loadTodoList();

		if (todoIndex < 0 || todoIndex >= todoList.size()) {
			throw new IllegalArgumentException(createArgumentErrorMessage());
		}

		Todo todo = todoList.get(todoIndex);
		todoTitle = todo.title;
	}

	private String createArgumentErrorMessage() {
		return MessageUtil.getMessage("error.command.argument.todoNumber", "開始", "start");
	}

	@Override
	public void execute() throws TodoException {
		// 実行中のtodoがある場合、stopする
		if (TodoControler.isExecutingTodo()) {
			Command stopCommand = new Stop();
			stopCommand.execute();
			stopCommand.showMessage();
		}
		TodoControler.setExecutingTodo(new ExecutingTodo(todoTitle));
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.start", todoTitle));
	}

}
