package todo.command;

import java.util.LinkedHashMap;
import java.util.Map;

import todo.exception.TodoException;

public class Alias extends Command {

	private static Map<String, String> commandAlias;

	static {
		commandAlias = new LinkedHashMap<>();
		commandAlias.put("a", "add");
		commandAlias.put("d", "delete");
		commandAlias.put("e", "end");
		commandAlias.put("h", "help");
		commandAlias.put("i", "inquiry");
		commandAlias.put("l", "list");
		commandAlias.put("m", "meeting");
		commandAlias.put("n", "now");
		commandAlias.put("o", "open");
		commandAlias.put("q", "quit");
		commandAlias.put("st", "start");
		commandAlias.put("sp", "stop");
		commandAlias.put("t", "total");
	}

	@Override
	public void execute() throws TodoException {
		commandAlias.forEach((k, v) -> System.out.println(String.format("%-3s", k) + v));
	}

	public static String getCommandName(String text) {
		return commandAlias.get(text);
	}

}
