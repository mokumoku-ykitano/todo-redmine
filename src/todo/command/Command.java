package todo.command;

import todo.exception.TodoException;
import todo.util.MessageUtil;

public abstract class Command {

	/**
	 * コマンドの引数を設定します。<br>
	 * 引数のチェックも行います。
	 * 
	 * @param args
	 */
	public void setArguments(String[] args) throws TodoException {

	}

	/**
	 * コマンドを実行します。
	 */
	public abstract void execute() throws TodoException;

	/**
	 * コマンド実行後にメッセージを表示します。
	 */
	public void showMessage() {

	}

	/**
	 * コマンド実行後の後処理を行います。
	 */
	public void after() throws TodoException {

	}

	/**
	 * 次の入力を待つかどうか。<br>
	 * 終了する場合はfalseを返す。
	 * 
	 * @return true(次の入力を待つ) / false(終了する)
	 */
	public boolean nextCommandWaitIs() {
		return true;
	}

	/**
	 * ヘルプで表示する文字列を取得します。
	 * 
	 * @return ヘルプで表示する文字列
	 */
	public String getHelpText() {
		String command = this.getClass().getSimpleName().toLowerCase();
		// コマンド + コマンド説明文
		return String.format("%-8s", command) + MessageUtil.getMessage("info.command.help." + command);
	}

}
