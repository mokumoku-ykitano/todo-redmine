package todo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import todo.dto.json.Todo;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.FilesUtil;

public class TodoLogic {

	/*
	 * インスタンス化禁止
	 */
	private TodoLogic() {

	}

	/**
	 * Todoで使用するフォルダを全て作成します。
	 * 
	 * @throws TodoException
	 */
	public static void createTodoDirectories() throws TodoException {
		try {
			FilesUtil.createDirectories(getFullTodoLogDirectoryPathText());
		} catch (IOException e) {
			throw new TodoException(e, "error.util.create.directories");
		}
	}

	/**
	 * Todoログフォルダのフルパスを取得します。
	 * 
	 * @return Todoログフォルダのフルパス
	 */
	public static String getFullTodoLogDirectoryPathText() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + "log/";
	}

	/**
	 * Todoログのフルパスを取得します。
	 * 
	 * @param date
	 * @return Todoログのフルパス
	 */
	private static String getFullTodoLogPathText(Date date) {
		SimpleDateFormat todoLogFileName = new SimpleDateFormat("yyyy-MM-dd");
		return getFullTodoLogDirectoryPathText() + todoLogFileName.format(date) + ".log";
	}

	/**
	 * Todoリストを読み込んで、リストに変換して返します。<br>
	 * Todoリストが存在しない場合、空のリストを返します。
	 * 
	 * @param todoListPath
	 * @return Todoリスト
	 * @throws TodoException
	 */
	public static List<Todo> loadTodoList() throws TodoException {
		Path todoListPath = getTodoListPath();
		return FilesUtil.loadList(todoListPath, new ObjectMapper(), Todo.class);
	}

	/**
	 * TodoリストのPathオブジェクトを取得します。
	 * 
	 * @return TodoリストのPathオブジェクト
	 */
	private static Path getTodoListPath() {
		return Paths.get(getFullTodoListPathText());
	}

	/**
	 * Todoリストのフルパスを取得します。
	 * 
	 * @return Todoリストのフルパス
	 */
	private static String getFullTodoListPathText() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + todoProp.getString("TODO_LIST_FILE_NAME");
	}

	/**
	 * todoリストを引数の内容で上書きします。
	 * 
	 * @param todoList
	 * @throws TodoException
	 * @throws IOException
	 */
	public static void writeTodoList(List<Todo> todoList) throws TodoException, IOException {
		FilesUtil.write(getTodoListPath(), makeJsonText(todoList));
	}

	/**
	 * 引数からJSON文字列を作成します。
	 * 
	 * @param object
	 * @return JSON文字列
	 * @throws TodoException
	 */
	private static List<String> makeJsonText(Object object) throws TodoException {
		return makeJsonText(createIndentObjectMapper(), object);
	}

	/**
	 * 整形するオブジェクトマッパーを生成します。
	 * 
	 * @return 整形するオブジェクトマッパー
	 */
	private static ObjectMapper createIndentObjectMapper() {
		return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}

	/**
	 * 引数からJSON文字列を作成します。
	 * 
	 * @param objectMapper
	 * @param object
	 * @return JSON文字列リスト
	 * @throws TodoException
	 */
	private static List<String> makeJsonText(ObjectMapper objectMapper, Object object) throws TodoException {
		try {
			String jsonText = objectMapper.writeValueAsString(object);
			List<String> list = new ArrayList<>();
			list.add(jsonText);
			return list;
		} catch (JsonProcessingException e) {
			throw new TodoException(e, "error.util.json");
		}
	}

	/**
	 * Todoログを引数の内容で上書きします。
	 * 
	 * @param todologList
	 * @throws TodoException
	 * @throws IOException
	 */
	public static void writeTodoLog(List<TodoLog> todologList) throws TodoException, IOException {
		FilesUtil.write(getTodoLogPath(new Date()), makeJsonText(createTodoLogMapper(), todologList));
	}

	/**
	 * Todoログ用のマッパーを生成します。
	 * 
	 * @return Todoログ用のマッパー
	 */
	private static ObjectMapper createTodoLogMapper() {
		ObjectMapper objectMapper = createIndentObjectMapper();
		SimpleDateFormat todoLogDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		objectMapper.setDateFormat(todoLogDate);
		return objectMapper;
	}

	/**
	 * TodoログのPathオブジェクトを取得します。
	 * 
	 * @return TodoログのPathオブジェクト
	 */
	private static Path getTodoLogPath(Date date) {
		return Paths.get(getFullTodoLogPathText(date));
	}

	/**
	 * Todoログを読み込んで、リストに変換して返します。<br>
	 * Todoログが存在しない場合、空のリストを返します。
	 * 
	 * @return Todoログリスト
	 * @throws TodoException
	 */
	public static List<TodoLog> loadTodoLogList() throws TodoException {
		return loadTodoLogList(new Date());
	}

	/**
	 * Todoログを読み込んで、リストに変換して返します。<br>
	 * Todoログが存在しない場合、空のリストを返します。
	 * 
	 * @return Todoログリスト
	 * @throws TodoException
	 */
	public static List<TodoLog> loadTodoLogList(Date date) throws TodoException {
		Path todoListPath = getTodoLogPath(date);
		return FilesUtil.loadList(todoListPath, createTodoLogMapper(), TodoLog.class);
	}
}
