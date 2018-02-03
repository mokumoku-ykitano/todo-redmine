package todo.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import todo.TodoLogic;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Total extends Command {

	private int totalDays;

	@Override
	public void setArguments(String[] args) throws TodoException {
		if (args == null || args.length == 0) {
			totalDays = 7;
			return;
		}

		if (StringUtil.isNotNumber(args[0])) {
			throw new IllegalArgumentException(createArgumentErrorMessage());
		}

		totalDays = Integer.parseInt(args[0]);

		if (totalDays < 1) {
			throw new IllegalArgumentException(createArgumentErrorMessage());
		}
	}

	private String createArgumentErrorMessage() {
		return MessageUtil.getMessage("error.command.total.argumnet");
	}

	@Override
	public void execute() throws TodoException {

		Calendar cal = Calendar.getInstance();
		List<TodoLog> logList = null;
		List<TodoLog> sumList = new ArrayList<>();

		for (int i = 0; i < totalDays; i++) {

			logList = TodoLogic.loadTodoLogList(cal.getTime());
			// ログを集計
			sumUpTodoLogList(sumList, logList);

			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		// 作業時間の降順でソート
		sumList.sort(Comparator.comparing(TodoLog::getWorkingMinutes).reversed());

		// 集計結果を表示
		sumList.forEach(todoLog -> System.out.println(todoLog.createDateAndWorkingHourText()));
	}

	/**
	 * 作業時間を集計し、リストを返します。
	 * 
	 * @param logList
	 * @return 集計後のリスト
	 */
	private void sumUpTodoLogList(List<TodoLog> sumList, List<TodoLog> logList) {

		TodoLog target = null;

		for (TodoLog todoLog : logList) {
			if (sumList.contains(todoLog)) {
				int index = sumList.indexOf(todoLog);
				target = sumList.get(index);
				// 作業時間を加算
				target.addWorkingMinutes(todoLog);
				// 作業日時を再設定
				target.resetWorkingDate(todoLog);
			} else {
				sumList.add(todoLog);
			}
		}
	}

}
