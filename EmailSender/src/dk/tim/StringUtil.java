package dk.tim;

public class StringUtil {
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
}
