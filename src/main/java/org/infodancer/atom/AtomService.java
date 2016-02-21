package org.infodancer.atom;

import java.util.List;

public class AtomService
{
	List<AtomWorkspace> workspaces;
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<service xmlns=\"http://www.w3.org/2007/app\"");
		result.append("         xmlns=\"http://www.w3.org/2005/atom\">\n");
		for (AtomWorkspace workspace : workspaces)
		{
			result.append(workspace.toString());
		}
		result.append("</service>\n");
		return result.toString();
	}
}
