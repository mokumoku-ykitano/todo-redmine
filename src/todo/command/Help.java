package todo.command;

import java.util.LinkedList;
import java.util.MissingResourceException;

import todo.CommandFactory;
import todo.exception.TodoException;

public class Help extends Command {

	private static java.util.List<String> helpList;

	static {
		helpList = new LinkedList<>();
		helpList.add("Add");
		helpList.add("Alias");
		helpList.add("Delete");
		helpList.add("End");
		helpList.add("Help");
		helpList.add("Inquiry");
		helpList.add("List");
		helpList.add("Meeting");
		helpList.add("Now");
		helpList.add("Quit");
		helpList.add("Start");
		helpList.add("Stop");
		helpList.add("Sum");
		helpList.add("Total");
	}

	@Override
	public void execute() throws TodoException {
		helpList.forEach(className -> {
			try {
				System.out.println(CommandFactory.create(className).getHelpText());
			} catch (TodoException | MissingResourceException e) {
				e.printStackTrace();
			}
		});
	}

}
