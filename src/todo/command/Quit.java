package todo.command;

import todo.TodoControler;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Quit extends Command {

	@Override
	public void execute() throws TodoException {
		// 実行中のtodoがある場合、stopする
		if (TodoControler.isExecutingTodo()) {
			Command stopCommand = new Stop();
			stopCommand.execute();
			stopCommand.showMessage();
		}
		System.out.println(MessageUtil.getMessage("info.command.quit"));
	}

	@Override
	public boolean nextCommandWaitIs() {
		return false;
	}

}
