package dk.tim;

import static dk.tim.StringUtil.isEmpty;

/**
 *	project for sending an email using a jar file
 *	- I plan on calling this jar file from other java programs
 *
 *	Usage: java -java emailsender.jar to@gmail.com "subject" "message" (optinal: pathToCinfigurationFile)
 *	- Needs a configuration-file which includes the from email(must be gmail) and password
 *
 * Export by 
 * * Focus on the Java project
 * * Select File -> export
 * * Select runnable jar file
 * 
 * 
 * Call from other Java program:
		Process proc = Runtime.getRuntime().exec("java -jar C:\\A\\emailsender.jar timgrewy@gmail.com \"Test igen\" \"message\" C:\\A\\configuration.txt");
		proc.waitFor();
		java.io.InputStream is = proc.getInputStream();
		byte b[] = new byte[is.available()];
		is.read(b, 0, b.length);
		System.out.println("msg: " + new String(b));
 */
public class EmailSenderMain {

	public static void main(String[] args) {
		validateInput(args);
		String path;
		if (args.length == 4) {
			path = args[3]; //4th position is the configuration file
		} else {
			path = "configuration.txt";
		}
		ConfigurationParser configurationParser = new ConfigurationParser(path);
		final String username = configurationParser.getLogin();
		final String password = configurationParser.getDecryptedPassword();

		EmailSender sender = new EmailSender(username, password);
		String to = args[0];
		String header = args[1];
		String message = args[2];
		sender.sendEmail(to, header, message);
		System.out.println("Sent");
	}

	private static void validateInput(String[] args) {
		if (args == null || args.length < 3) {
			throw new IllegalArgumentException("You must provide a set of arguments, either {<TO>, <HEADER>, <MESSASGE>} or {<TO>, <HEADER>, <MESSASGE>, <CONFIGURATIONFILE>}");
		}
		String to = args[0];
		String header = args[1];
		//		String message = args[2];
		if (args.length == 3) {
			if (isEmpty(to) || isEmpty(header)) {
				throw new IllegalArgumentException("Neither 'to' or 'header' can be empty!");
			}
			if (to.indexOf("@") == -1) {
				throw new IllegalArgumentException("The 'to' email must contain '@'");
			}
		}
		if (args.length == 4) {
			String confgirationFile = args[3];
			if (isEmpty(confgirationFile)) {
				throw new IllegalArgumentException("confgirationFile can not be empty!");
			}
		}
	}
}