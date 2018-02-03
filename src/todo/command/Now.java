package todo.command;

import todo.TodoControler;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Now extends Command {

	@Override
	public void execute() throws TodoException {
		if (TodoControler.isExecutingTodo()) {
			System.out.println(MessageUtil.getMessage("info.command.now", TodoControler.getExecutingTodo().title));
		} else {
			System.out.println(MessageUtil.getMessage("info.command.noExecuting"));
		}
	}

}
