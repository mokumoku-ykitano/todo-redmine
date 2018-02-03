package todo.command;

import todo.TodoLogic;
import todo.dto.json.Todo;
import todo.exception.TodoException;

public class List extends Command {

	@Override
	public void execute() throws TodoException {
		java.util.List<Todo> todoList = TodoLogic.loadTodoList();
		todoList.forEach(todo -> System.out.println(todoList.indexOf(todo) + 1 + ":" + todo.title));
	}

}