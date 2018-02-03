package todo.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import todo.exception.TodoException;

public final class FilesUtil {

	/*
	 * インスタンス化禁止
	 */
	private FilesUtil() {

	}

	/**
	 * 存在しない親ディレクトリから全て作成します。
	 * 
	 * @param pathText
	 * @throws IOException
	 */
	public static void createDirectories(String pathText) throws IOException {
		Files.createDirectories(Paths.get(pathText));
	}

	/**
	 * ファイルに書き込みます。<br>
	 * ファイルが存在しない場合、新規作成します。<br>
	 * 内容を全て上書きします。
	 * 
	 * @param filePath
	 * @param lines
	 * @throws IOException
	 */
	public static void write(Path filePath, List<String> lines) throws IOException {
		Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE);
	}

	/**
	 * ファイルを読み込んで、引数のクラス型リストを返します。
	 * 
	 * @param filePath
	 * @param mapper
	 * @param clazz
	 * @return リスト
	 * @throws TodoException
	 */
	public static <T> List<T> loadList(Path filePath, ObjectMapper mapper, Class<T> clazz) throws TodoException {
		if (Files.exists(filePath)) {
			CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
			// 既存リストの読み込み
			try (InputStream is = Files.newInputStream(filePath)) {
				return mapper.readValue(is, javaType);
			} catch (Exception e) {
				throw new TodoException(e, "error.util.load.list");
			}
		} else {
			return new ArrayList<>();
		}
	}

}
