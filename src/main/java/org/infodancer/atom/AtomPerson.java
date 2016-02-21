package org.infodancer.atom;

public class AtomPerson
{
	String type;
	String uri;
	String name;
	String email;
	
	public AtomPerson(String type)
	{
		this.type = type;
	}
	
	public String getUri() 
	{
		return uri;
	}
	
	public void setUri(String uri) 
	{
		this.uri = uri;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<");
		result.append(type);
		result.append(">\n");
		
		String name = getName();
		if (name != null)
		{
			result.append("<name>");
			result.append(name);
			result.append("</name>\n");
		}

		String email = getEmail();
		if (email != null)
		{
			result.append("<email>");
			result.append(email);
			result.append("</email>\n");
		}

		String uri = getUri();
		if (uri != null)
		{
			result.append("<uri>");
			result.append(uri);
			result.append("</uri>\n");
		}
		result.append("</");
		result.append(type);
		result.append(">\n");
		return result.toString();
	}

}
