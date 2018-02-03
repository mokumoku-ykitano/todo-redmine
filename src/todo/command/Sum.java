package todo.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import todo.TodoLogic;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Sum extends Command {

	@Override
	public void execute() throws TodoException {

		List<TodoLog> logList = TodoLogic.loadTodoLogList();

		if (logList.size() == 0) {
			System.out.println(MessageUtil.getMessage("info.command.sum.nothing"));
			return;
		}

		// ログを集計
		List<TodoLog> sumUpTodoLogList = makeSumUpTodoLogList(logList);

		// 作業時間の降順でソート
		sumUpTodoLogList.sort(Comparator.comparing(TodoLog::getWorkingMinutes).reversed());

		// 集計結果を表示
		sumUpTodoLogList.forEach(todoLog -> System.out.println(todoLog.createWorkingHourText()));
	}

	/**
	 * 作業時間を集計し、リストを返します。
	 * 
	 * @param logList
	 * @return 集計後のリスト
	 */
	private List<TodoLog> makeSumUpTodoLogList(List<TodoLog> logList) {

		List<TodoLog> sumList = new ArrayList<>();
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

		return sumList;
	}

}
