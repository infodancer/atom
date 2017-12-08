package org.infodancer.atom;

public class AtomLink 
{
	String rel;
	String type;
	String href;
	String hreflang;
	
	public String getRel()
	{
		return rel;
	}
	
	public void setRel(String rel)
	{
		this.rel = rel;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getHref()
	{
		return href;
	}
	
	public void setHref(String href)
	{
		this.href = href;
	}
	
	public String getHreflang()
	{
		return hreflang;
	}
	
	public void setHreflang(String hreflang)
	{
		this.hreflang = hreflang;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<link");
		if (href != null)
		{
			result.append(" href=\"");
			result.append(AtomUtility.escapeValues(href));
			result.append("\"");
		}
		else throw new AtomException("Link must include a href!");
		
		if (rel != null)
		{
			result.append(" rel=\"");
			result.append(rel);
			result.append("\"");
		}

		if (type != null)
		{
			result.append(" type=\"");
			result.append(type);
			result.append("\"");
		}
		result.append(" />\n");
		return result.toString();
	}
}
