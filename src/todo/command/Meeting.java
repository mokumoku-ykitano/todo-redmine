package todo.command;

import todo.TodoControler;
import todo.dto.ExecutingTodo;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Meeting extends Command {

	private String title = "打ち合わせ";

	@Override
	public void execute() throws TodoException {
		// 実行中のtodoがある場合、stopする
		if (TodoControler.isExecutingTodo()) {
			Command stopCommand = new Stop();
			stopCommand.execute();
			stopCommand.showMessage();
		}
		TodoControler.setExecutingTodo(new ExecutingTodo(title));
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.start", title));
	}

}
