package todo.command;

import java.io.IOException;
import java.util.List;

import todo.TodoLogic;
import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Delete extends Command {

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
		return MessageUtil.getMessage("error.command.argument.todoNumber", "削除", "delete");
	}

	@Override
	public void execute() throws TodoException {
		try {
			List<Todo> todoList = TodoLogic.loadTodoList();
			Todo removeTarget = todoList.stream().filter(todo -> todo.title.equals(todoTitle)).findFirst().orElse(null);
			// 見つかれば削除する
			if (removeTarget != null) {
				todoList.remove(removeTarget);
				TodoLogic.writeTodoList(todoList);
			}
		} catch (IOException e) {
			throw new TodoException(e, "error.command.delete", todoTitle);
		}
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.delete.finish", todoTitle));
	}

	@Override
	public void after() throws TodoException {
		Command listCommand = new todo.command.List();
		listCommand.execute();
	}

}
