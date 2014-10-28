import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public class MyParser {

	public static enum Fields {
		CATEGORIES, MEDIA_CONTENT, MEDIA_THUMBNAIL, LINK, DESCRIPTION, DC_CREATOR, AUTHOR, PUB_DATE, TITLE
	}

	private static final Pattern p = Pattern.compile(Patterns.ITEM_PATTERN, Pattern.DOTALL);
	private Scanner scanner;


	public MyParser(String str) {
		scanner = new Scanner(str);
	}

	public MyParser(InputStream in) {
		scanner = new Scanner(in);
	}


	public String findWithinHorizon() {
		return scanner.findWithinHorizon(p, 0);
	}

	public Map<Fields, String> match() {
		MatchResult matchResult = scanner.match();
		return convertMatchResultToMap(matchResult);
	}

	private HashMap<Fields, String> convertMatchResultToMap(MatchResult matchResult) {
		HashMap<Fields, String> resultMap = new HashMap<Fields, String>();

		resultMap.put(Fields.TITLE, matchResult.group(1));
		resultMap.put(Fields.PUB_DATE, matchResult.group(2));
		resultMap.put(Fields.AUTHOR, matchResult.group(3));
		resultMap.put(Fields.DC_CREATOR, matchResult.group(4));
		resultMap.put(Fields.DESCRIPTION, matchResult.group(5));
		resultMap.put(Fields.LINK, matchResult.group(6));
		resultMap.put(Fields.MEDIA_THUMBNAIL, matchResult.group(7));
		resultMap.put(Fields.MEDIA_CONTENT, matchResult.group(8));
		resultMap.put(Fields.CATEGORIES, categoriesToString(matchResult.group(9)));

		return resultMap;
	}

	private String categoriesToString(String matchResult) {
		String categories = "";
		StringTokenizer st = new StringTokenizer(matchResult);
		while (st.hasMoreTokens()) {
			String s = st.nextToken().trim().replaceAll("<category>", "").replaceAll("</category>", "");

			if (s.length() > 0) {
				if (categories.length() > 0) {
					categories += "; ";
				}
				categories += s;
			}
		}
		return categories;
	}

	public void close() {
		scanner.close();
	}


	// public static final String CATEGORIES = "categories";
	// public static final String MEDIA_CONTENT = "media:content";
	// public static final String MEDIA_THUMBNAIL = "media:thumbnail";
	// public static final String LINK = "link";
	// public static final String DESCRIPTION = "description";
	// public static final String DC_CREATOR = "dc:creator";
	// public static final String AUTHOR = "author";
	// public static final String PUB_DATE = "pubDate";
	// public static final String TITLE = "title";
	
	private static class Patterns {
		private static final String EMPTY_PATTERN = "\\s*";
		private static final String TAG_CONTENT_PATTERN = "([^<]*)";
		private static final String TITLE_PATTERN = "<title>" + TAG_CONTENT_PATTERN + "</title>";
		private static final String PUB_DATE_PATTERN = "<pubDate>" + TAG_CONTENT_PATTERN + "</pubDate>";
		private static final String AUTHOR_PATTERN = "<author>" + TAG_CONTENT_PATTERN + "</author>";
		private static final String DC_CREATOR_PATTERN = "<dc:creator>" + TAG_CONTENT_PATTERN + "</dc:creator>";
		private static final String DESCRIPTION_PREFIX_PATTERN = "<description>" + EMPTY_PATTERN + "<!\\[CDATA\\[";
		private static final String DESCRIPTION_POSTFIX_PATTERN = "\\]\\]>" + EMPTY_PATTERN + "</description>";
		private static final String DESCRIPTION_CONTENT_PATTERN = "([^\\]]*)";
		private static final String DESCROPTION_PATTERN = DESCRIPTION_PREFIX_PATTERN + DESCRIPTION_CONTENT_PATTERN + DESCRIPTION_POSTFIX_PATTERN;
		private static final String LINK_PATTERN = "<link>" + TAG_CONTENT_PATTERN + "</link>";
		private static final String ATTRIBUTE_CONTENT_PATTERN = "([^\"]*)";
		private static final String MEDIA_THUMBNAIL_PATTERN = "<media:thumbnail url=\"" + ATTRIBUTE_CONTENT_PATTERN + "\"/>";
		private static final String MEDIA_CONTENT_PATTERN = "<media:content url=\"" + ATTRIBUTE_CONTENT_PATTERN + "\"/>";
		private static final String CATEGORY_PATTERN = "<category>" + TAG_CONTENT_PATTERN + "</category>";
		private static final String CATEGORIES_PATTERN = "<categories>((" + EMPTY_PATTERN + CATEGORY_PATTERN + EMPTY_PATTERN + ")*)</categories>";
	
		private static final String ITEM_PATTERN = "<item>" + EMPTY_PATTERN + TITLE_PATTERN + EMPTY_PATTERN + PUB_DATE_PATTERN + EMPTY_PATTERN + AUTHOR_PATTERN
				+ EMPTY_PATTERN + DC_CREATOR_PATTERN + EMPTY_PATTERN + DESCROPTION_PATTERN + EMPTY_PATTERN + LINK_PATTERN + EMPTY_PATTERN
				+ MEDIA_THUMBNAIL_PATTERN + EMPTY_PATTERN + MEDIA_CONTENT_PATTERN + EMPTY_PATTERN + CATEGORIES_PATTERN + EMPTY_PATTERN + "</item>";
	}
}
