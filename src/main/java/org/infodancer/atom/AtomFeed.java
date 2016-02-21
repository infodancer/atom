package org.infodancer.atom;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

public class AtomFeed 
{
	Long id;
	String atomid;
	String title;
	String subtitle;
	String generator;
	AtomRights rights;
	java.util.Date updated;
	
	Collection<AtomLink> links = new ArrayList<AtomLink>();
	Collection<AtomEntry> entries = new ArrayList<AtomEntry>();
	Collection<AtomAuthor> authors = new ArrayList<AtomAuthor>();;
	Collection<AtomCategory> categories = new ArrayList<AtomCategory>();
	Collection<AtomContributor> contributors = new ArrayList<AtomContributor>();
		
	public void addEntry(AtomEntry entry)
	{
		this.entries.add(entry);
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

	public void writeTo(java.io.OutputStream output) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		writeTo(writer);
	}

	/**
	 * Writes the feed to the provided OutputStream in Atom XML format.
	 * @throws IOException
	 */
	public void writeTo(Writer writer) throws IOException
	{
		writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		writer.write("<feed xmlns=\"http://www.w3.org/2005/Atom\">\n");

		if (atomid != null)
		{
			writer.write("<id>");
			writer.write(atomid);
			writer.write("</id>\n");
		}
		
		if (title != null)
		{
			if (title.trim().length() > 0)
			{
				writer.write("<title type=\"text\">");
				writer.write(title);
				writer.write("</title>\n");
			}
			else throw new AtomException("Title must not be blank!");
		}
		else throw new AtomException("Title is a mandatory element!");
		
		if (subtitle != null)
		{
			if (subtitle.trim().length() > 0)
			{
				writer.write("<subtitle type=\"text\">");
				writer.write(subtitle);
				writer.write("</subtitle>\n");
			}
			else throw new AtomException("Subtitle must not be blank!");
		}
		
		writer.write("<generator uri=\"http://www.infodancer.org/projects/atom\" version=\"1.0\">");
		writer.write("Infodancer Atom Toolkit");
		writer.write("</generator>\n");

		if (updated == null) 
		{
			long recentTime = 0;
			AtomEntry recentEntry = null;
			
			for (AtomEntry entry : entries)
			{
				long entryUpdated = entry.getUpdated().getTime();
				if (entryUpdated > recentTime)
				{
					recentEntry = entry;
					recentTime = entryUpdated;
				}
			}
			if (recentEntry != null) updated = recentEntry.getUpdated();
			else updated = new java.util.Date();
		}
		
		if (updated != null)
		{
			writer.write("<updated>");
			writer.write(AtomUtility.convertDate(updated));
			writer.write("</updated>\n");
		}

		if (!authors.isEmpty())
		{
			writer.write("<rights>");
			writer.write("Copyright ");
			writer.write(AtomUtility.convertDate(updated));
			if (authors.size() > 0)
			{
				boolean first = true;
				writer.write(" by");
				for (AtomAuthor author : authors)
				{
					if (first) first = false;
					else writer.write(",");
					String authorName = author.getName();
					String authorEmail = author.getEmail();
					if ((authorName != null) && (authorName.trim().length() > 0))
					{
						writer.write(" ");
						writer.write(authorName);
					}
					if ((authorEmail != null) && (authorEmail.trim().length() > 0))
					{
						writer.write(" ");
						writer.write(authorEmail);
					}
				}
			}
			writer.write("</rights>\n");
		}
		
		if (authors.size() > 0)
		{
			for (AtomAuthor author : authors)
			{
				writer.write(author.toString());
				writer.write("\n");
			}
		}

		if (contributors.size() > 0)
		{
			for (AtomContributor contributor : contributors)
			{
				writer.write(contributor.toString());
				writer.write("\n");
			}
		}
		
		for (AtomCategory category : categories)
		{
			writer.write(category.toString());
		}
		
		for (AtomLink link : links)
		{
			writer.write(link.toString());
		}
		
		int entrycount = 0;
		System.out.println("Exporting " + entries.size() + " entries...");
		for (AtomEntry entry : entries)
		{
			try
			{
				writer.write(entry.toString());
				writer.write("\n");
				entrycount++;
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Exported " + entrycount + " entries...");
		
		writer.write("</feed>\n");
		writer.flush();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSubtitle()
	{
		return subtitle;
	}

	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}

	public String getGenerator()
	{
		return generator;
	}

	public void setGenerator(String generator)
	{
		this.generator = generator;
	}

	public AtomRights getRights()
	{
		return rights;
	}

	public void setRights(AtomRights rights)
	{
		this.rights = rights;
	}

	public java.util.Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(java.util.Date updated)
	{
		this.updated = updated;
	}

	public Collection<AtomLink> getLinks()
	{
		return links;
	}

	public void setLinks(Collection<AtomLink> links)
	{
		this.links = links;
	}

	public Collection<AtomEntry> getEntries()
	{
		return entries;
	}

	public void setEntries(Collection<AtomEntry> entries)
	{
		this.entries = entries;
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

	/**
	 * The Atom ID is a unique IRI identifying the feed.
	 * The Atom ID is not the same as the database id.
	 * @return
	 */
	public String getAtomid()
	{
		return atomid;
	}
	
	public void setAtomid(String atomid)
	{
		this.atomid = atomid;
	}
}
