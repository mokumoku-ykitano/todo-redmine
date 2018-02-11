package todo.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

import todo.TodoLogic;
import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class LoadIssue extends Command {

	@Override
	public void execute() throws TodoException {

		List<Todo> todoList = TodoLogic.loadTodoList();
		List<Todo> newTodoList = new ArrayList<>();

		todoList.stream().filter(todo -> StringUtil.isEmpty(todo.issueId)).forEach(todo -> newTodoList.add(todo));

		loadIssueList().getResults().stream().forEach(issue -> newTodoList.add(new Todo(issue)));
		try {
			TodoLogic.writeTodoList(newTodoList);
		} catch (IOException e) {
			throw new TodoException(e, "error.command.loadIssue.list", newTodoList);
		}
	}

	/**
	 * Redmineから読み込んだIsuueリストを返します。
	 * 
	 * @return Isuueリスト
	 * @throws TodoException
	 */
	private ResultsWrapper<Issue> loadIssueList() throws TodoException {

		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		String uri = todoProp.getString("REDMINE_URL_HOST");
		String apiAccessKey = todoProp.getString("API_ACCESS_KEY");

		RedmineManager manager = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
		IssueManager issueManager = manager.getIssueManager();

		Map<String, String> params = new HashMap<String, String>();
		params.put("assigned_to_id", "me");
		params.put("sort", todoProp.getString("SORT"));
		try {
			return issueManager.getIssues(params);
		} catch (RedmineException e) {
			throw new TodoException(e, "error.command.loadIssue");
		}
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.loadIssue.finish"));
	}

	@Override
	public void after() throws TodoException {
		Command listCommand = new todo.command.List();
		listCommand.execute();
	}

}
