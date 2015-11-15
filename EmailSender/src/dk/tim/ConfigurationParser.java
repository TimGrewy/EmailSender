package dk.tim;

import static dk.tim.StringUtil.isEmpty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigurationParser {
	private static String LOGIN_PROPERTY = "login";
	private static String PASSWORD_PROPERTY = "passwordEncrypted";
	private Map<String, String> collect;

	/**
	 * Example configuation file
	 * login = xxx
	 * passwordEncrypted = xxx
	 * 
	 * Use Encrypter.java's main method to encrypt your password
	 */
	public ConfigurationParser(String filePath) {
		try {
			Path path = Paths.get(filePath);
			Stream<String> lines;
			lines = Files.lines(path);
			collect = lines.collect(Collectors.toMap(x -> x.split("=")[0].trim(), x -> x.split("=")[1].trim()));
			lines.close();
			if (isEmpty(getLogin()) || isEmpty(getDecryptedPassword())) {
				throw new IllegalArgumentException("The file " + filePath + "must conatin \"+LOGIN_PROPERTY+\" and \"+PASSWORD_PROPERTY+\" to be valid for this program.");
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public String getLogin() {
		return collect.get(LOGIN_PROPERTY);
	}

	public String getDecryptedPassword() {
		String passEncrypter = collect.get(PASSWORD_PROPERTY);
		return Encrypter.decrypt(passEncrypter);
	}

	@Override
	public String toString() {
		return String.format("ConfigurationParser [collect=%s]", collect);
	}
}