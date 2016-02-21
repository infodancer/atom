package org.infodancer.atom;

import java.util.Collection;
import java.util.LinkedList;

public class AtomEntry 
{
	// This ID file exists only to facilitate Weblog exports; not part of Atom spec
	Long id;
	String atomid;
	String title;
	java.util.Date updated;
	java.util.Date published;
	
	AtomRights rights;
	AtomSummary summary;
	AtomContent content;
	
	Collection<AtomLink> links = new LinkedList<AtomLink>();
	Collection<AtomAuthor> authors = new LinkedList<AtomAuthor>();
	/** TODO Remove this after exports are complete **/
	@Deprecated
	Collection<AtomEntry> comments = new LinkedList<AtomEntry>();
	Collection<AtomCategory> categories = new LinkedList<AtomCategory>();
	Collection<AtomContributor> contributors = new LinkedList<AtomContributor>();
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<entry>\n");
		if (title != null)
		{
			if (title.trim().length() > 0)
			{
				result.append("<title type=\"text\">");
				result.append(AtomUtility.escapeValues(title));
				result.append("</title>\n");
			}
			else throw new AtomException("Title cannot be blank!");
		}
		else throw new AtomException("Title is a required field for AtomEntry!");
		
		// TODO Remove this when ready to discard old weblog
		/*
		if (id != null)
		{
			result.append("<dbid>");
			result.append(Long.toString(id));
			result.append("</dbid>\n");
		}
		 */
		
		if (atomid != null)
		{
			result.append("<id>");
			result.append(atomid);
			result.append("</id>\n");
		}
		else 
		{
			throw new AtomException("WeblogEntry<" + id + "> is missing required atomid field!");
		}
		
		result.append("<updated>");
		result.append(AtomUtility.convertDate(updated));
		result.append("</updated>\n");

		result.append("<published>");
		result.append(AtomUtility.convertDate(published));
		result.append("</published>\n");

		for (AtomCategory category : categories)
		{
			result.append(category.toString());
		}
		
		for (AtomAuthor author : authors)
		{
			result.append(author.toString());
		}
		
		for (AtomContributor contributor : contributors)
		{
			result.append(contributor.toString());
		}

		for (AtomLink link : links)
		{
			result.append(link.toString());
		}
		
		if (summary != null)
		{
			result.append(summary.toString());
		}

		if (content != null)
		{
			result.append(content.toString());
		}
		
		/*
		if (!comments.isEmpty())
		{
			result.append("<comments>\n");
			for (AtomEntry comment : comments)
			{
				result.append(comment.toString());
			}
			result.append("</comments>\n");
		}
		*/
		result.append("</entry>\n");
		return result.toString();
	}

	public Collection<AtomEntry> getComments()
	{
		return comments;
	}

	public void setComments(Collection<AtomEntry> comments)
	{
		this.comments = comments;
	}
	
	public void addComment(AtomEntry comment)
	{
		this.comments.add(comment);
	}
	
	@Deprecated
	public Long getId()
	{
		return id;
	}
	
	@Deprecated
	public void setId(Long id)
	{
		this.id = id;
	}

	public String getAtomid()
	{
		return atomid;
	}

	public void setAtomid(String atomid)
	{
		this.atomid = atomid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public java.util.Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(java.util.Date updated)
	{
		this.updated = updated;
	}

	public java.util.Date getPublished()
	{
		return published;
	}

	public void setPublished(java.util.Date published)
	{
		this.published = published;
	}

	public AtomRights getRights()
	{
		return rights;
	}

	public void setRights(AtomRights rights)
	{
		this.rights = rights;
	}

	public Collection<AtomLink> getLinks()
	{
		return links;
	}

	public void setLinks(Collection<AtomLink> links)
	{
		this.links = links;
	}

	public void addLink(AtomLink link)
	{
		this.links.add(link);
	}
	
	public Collection<AtomContributor> getContributors()
	{
		return contributors;
	}

	public void setContributors(Collection<AtomContributor> contributors)
	{
		this.contributors = contributors;
	}

	public void addContributor(AtomContributor contributor)
	{
		this.contributors.add(contributor);
	}
	
	public Collection<AtomAuthor> getAuthors()
	{
		return authors;
	}

	public void setAuthors(Collection<AtomAuthor> authors)
	{
		this.authors = authors;
	}

	public void addAuthor(AtomAuthor author)
	{
		this.authors.add(author);
	}
	
	public Collection<AtomCategory> getCategories()
	{
		return categories;
	}

	public void setCategories(Collection<AtomCategory> categories)
	{
		this.categories = categories;
	}

	public void addCategory(AtomCategory category)
	{
		this.categories.add(category);
	}

	public AtomSummary getSummary()
	{
		return summary;
	}

	public void setSummary(AtomSummary summary)
	{
		this.summary = summary;
	}

	public AtomContent getContent()
	{
		return content;
	}

	public void setContent(AtomContent content)
	{
		this.content = content;
	}
}
