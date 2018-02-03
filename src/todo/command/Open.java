package todo.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ResourceBundle;

import todo.TodoLogic;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Open extends Command {

	private String fileName;

	@Override
	public void setArguments(String[] args) {
		if (args == null || args.length == 0) {
			fileName = StringUtil.getTodayText() + ".log";
			return;
		}
		try {
			LocalDate.parse(args[0], DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
			fileName = args[0] + ".log";
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.open.argument"));
		}
	}

	@Override
	public void execute() throws TodoException {

		System.out.println(fileName + "を開きます。");

		Runtime runtime = Runtime.getRuntime();
		String directoryPath = TodoLogic.getFullTodoLogDirectoryPathText();
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		String editorPath = todoProp.getString("EDITOR_PATH");
		try {
			if (StringUtil.isEmpty(editorPath)) {
				runtime.exec(directoryPath + fileName);
			} else {
				runtime.exec("\"" + editorPath + "\" " + directoryPath + fileName);
			}
		} catch (IOException e) {
			throw new TodoException(e, "error.command.open.file", fileName);
		}
	}

}
