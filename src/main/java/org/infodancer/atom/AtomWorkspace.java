package org.infodancer.atom;

import java.util.List;

public class AtomWorkspace
{
	String title;
	List<AtomCollection> collections;
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<workspace>");
		if (title != null)
		{
			result.append("<atom:title>");
			result.append(title);
			result.append("</atom:title>");
		}
		result.append("</workspace>");
		return result.toString();
	}
}
