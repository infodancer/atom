package org.infodancer.atom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AtomUtility
{
	/**
	 * Converts a Java date to Atom format.
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static java.util.Date convertDate(String date) throws ParseException
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			return format.parse(date);
		}
		else return new Date();
	}

	/**
	 * Converts a Java date to Atom format.
	 * @param date
	 * @return
	 */
	public static String convertDate(java.util.Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			return format.format(date);
		}
		else return "";
	}

	public static String escapeValues(String content)
	{
		
		content = content.replaceAll("&", "&amp;");
		content = content.replaceAll("\"", "&quot;");
		content = content.replaceAll("\u0016", "");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		return content;
	}
}
