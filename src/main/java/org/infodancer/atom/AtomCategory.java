package org.infodancer.atom;

public class AtomCategory 
{
	String term;
	String scheme;
	String label;
	
	public String getTerm()
	{
		return term;
	}
	
	public void setTerm(String term)
	{
		this.term = term;
	}
	
	public String getScheme()
	{
		return scheme;
	}
	
	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<category");
		if (term != null)
		{
			result.append(" term=\"");
			result.append(AtomUtility.escapeValues(term));
			result.append("\"");
		}
		if (label != null)
		{
			result.append(" label=\"");
			result.append(AtomUtility.escapeValues(label));
			result.append("\"");
		}
		if (scheme != null)
		{
			result.append(" scheme=\"");
			result.append(AtomUtility.escapeValues(scheme));
			result.append("\"");
		}
		result.append(" />\n");
		return result.toString();
	}
}
