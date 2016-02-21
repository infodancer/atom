package org.infodancer.atom;

import java.util.List;

public class AtomCollection
{
	String title;
	String link;
	String categoryLink;
	List<String> mediatypes;
	List<AtomCategory> categories;
	boolean categoriesFixed = true;
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<collection href=\"");
		result.append(link);
		result.append("\">");
		if (title != null)
		{
			result.append("<atom:title>");
			result.append(title);
			result.append("</atom:title>");
		}
		
		if ((categories == null) || (categories.isEmpty()))
		{
			if (categoryLink != null)
			{
				result.append("<categories href=\"");
				result.append(categoryLink);
				result.append("\" />");			
			}
		}
		
		for (String type : mediatypes)
		{
			result.append("<accept>");
			result.append(type);
			result.append("</accept>");
		}
	
		
		result.append("</collection>");
		return result.toString();
	}
}
