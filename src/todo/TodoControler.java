package todo;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import todo.command.Command;
import todo.dto.ExecutingTodo;

public class TodoControler {

	private static final Logger logger = Logger.getLogger(TodoControler.class.getSimpleName());

	/** 実行中のtodoオブジェクト */
	private static ExecutingTodo executingTodo;

	public static void main(String[] args) throws Exception {

		// ログの設定
		LogManager.getLogManager()
				.readConfiguration(TodoControler.class.getClassLoader().getResourceAsStream("logging.properties"));

		// 必要なフォルダを全て作成する
		TodoLogic.createTodoDirectories();

		try (Scanner scanner = new Scanner(System.in)) {

			boolean doContinue = true;
			String inputText = null;
			Command command = null;

			while (doContinue) {
				inputText = scanner.nextLine();
				try {
					command = CommandFactory.createAndSetArguments(inputText);
					command.execute();
					command.showMessage();
					command.after();
					doContinue = command.nextCommandWaitIs();
				} catch (Exception e) {
					System.err.println(e.getMessage());
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
				System.out.println();
			}
		}
	}

	public static ExecutingTodo getExecutingTodo() {
		return executingTodo;
	}

	public static void setExecutingTodo(ExecutingTodo executingTodo) {
		TodoControler.executingTodo = executingTodo;
	}

	public static boolean isExecutingTodo() {
		return executingTodo != null;
	}

	public static boolean isNotExecutingTodo() {
		return !isExecutingTodo();
	}

}
