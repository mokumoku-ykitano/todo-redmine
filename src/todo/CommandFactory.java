package todo;

import java.util.Arrays;

import todo.command.Alias;
import todo.command.Command;
import todo.exception.TodoException;
import todo.util.StringUtil;

public final class CommandFactory {

	/*
	 * インスタンス化禁止
	 */
	private CommandFactory() {

	}

	/**
	 * 引数を設定したコマンドオブジェクトを生成します。
	 * 
	 * @param inputText
	 * @return コマンドオブジェクト
	 * @throws TodoException
	 */
	public static Command createAndSetArguments(String inputText) throws TodoException {
		// 入力文字列(コマンド、引数)を配列化
		String[] text = inputText.split(" ");
		Command command = create(makeCommandName(text[0]));
		// 引数のみの配列を作成
		String[] args = Arrays.stream(text).filter(arg -> !arg.equals(text[0])).toArray(size -> new String[size]);
		// 引数設定時にチェックを行う
		command.setArguments(args);
		return command;
	}

	/*
	 * 省略コマンド名から完全コマンド名を作成します。
	 */
	private static String makeCommandName(String text) {
		String commandName = Alias.getCommandName(text);
		return StringUtil.isEmpty(commandName) ? text : commandName;
	}

	/**
	 * コマンドオブジェクトを生成します。
	 * 
	 * @param commandName
	 * @return コマンドオブジェクト
	 * @throws TodoException
	 */
	public static Command create(String commandName) throws TodoException {
		try {
			// パッケージ名 + クラス名
			String fullClassName = makeFullClassName(commandName);
			return (Command) Class.forName(fullClassName).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new TodoException(e, "error.command.create");
		}
	}

	/*
	 * (パッケージ名 + クラス名)の文字列を作成します。
	 */
	private static String makeFullClassName(String className) {
		return Command.class.getPackage().getName() + "." + StringUtil.capitalize(className);
	}

}
