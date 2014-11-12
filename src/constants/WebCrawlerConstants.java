package constants;

import java.util.regex.Pattern;

public class WebCrawlerConstants {
	public static final String YEAR = "2014";
	public static final String WEB_URL_PATH = "http://mail-archives.apache.org/mod_mbox/maven-users/";
	public static final Pattern Main_URL_PATTERN = Pattern.compile("href=\"(" + YEAR + "\\d{2}.mbox/date.*?)\"");
	public static final Pattern DIGIT_PATTERN = Pattern.compile("(\\d+)");

	public static enum Month {
		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC
	}
}
