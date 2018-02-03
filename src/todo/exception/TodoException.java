package todo.exception;

import todo.util.MessageUtil;

public class TodoException extends Exception {

	private static final long serialVersionUID = -8306543173313159012L;

	public TodoException(String message) {
		super(message);
	}

	public TodoException(Throwable cause, String key, Object... arguments) {
		super(MessageUtil.getMessage(key, arguments), cause);
	}

}
