package org.infodancer.atom;

public class AtomText
{
	public static enum Element { SUMMARY, CONTENT };
	String type;
	String value;
	
	public AtomText()
	{
		this.type = "text";
	}
	
	protected String toString(Element element)
	{
		if ((value != null) && (value.trim().length() > 0))
		{
			StringBuilder result = new StringBuilder();
			result.append("\n<");
			if (Element.SUMMARY.equals(element))
			{
				result.append("summary");
			}
			else if (Element.CONTENT.equals(element))
			{
				result.append("content");
			}
			result.append(" type=\"");
			result.append(type);
			result.append("\">\n");
			if ("xhtml".equalsIgnoreCase(type))
			{
				result.append("<div xmlns=\"http://www.w3.org/1999/xhtml\">\n");
				result.append(value);
				result.append("</div>");
			}
			else result.append(AtomUtility.escapeValues(value));

			result.append("\n</");
			if (Element.SUMMARY.equals(element))
			{
				result.append("summary");
			}
			else if (Element.CONTENT.equals(element))
			{
				result.append("content");
			}
			result.append(">\n");
			return result.toString();
		}
		else return "";
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
