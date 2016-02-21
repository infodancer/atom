package org.infodancer.atom;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class AtomTest extends TestCase
{
	private static final Logger logger = Logger.getLogger("AtomTest");
	
	public void setUp()
	{
		
	}
	
	public void tearDown()
	{
		
	}
	
	public void testSimpleAtomFeed()
	{
		AtomFeed feed = new AtomFeed();
		feed.setAtomid("http://example.org/");
		feed.setTitle("Example Feed");
		feed.setUpdated(new java.util.Date());
		AtomAuthor author = new AtomAuthor();
		author.setName("John Doe");
		author.setEmail("john.doe@example.org");
		author.setEmail("http://www.example.org/johndoe");
		feed.addAuthor(author);
		
		AtomEntry entry = new AtomEntry();
		entry.addAuthor(author);
		entry.setTitle("Atom-Powered Robots Run Amok");
		entry.setAtomid("http://example.org/2003/12/13/atom03");
		AtomLink link = new AtomLink();
		link.setHref("http://example.org/2003/12/13/atom03");
		entry.addLink(link);
		AtomSummary summary = new AtomSummary();
		summary.setType("text");
		summary.setValue("Some text.");
		entry.setSummary(summary);
		entry.setUpdated(new java.util.Date());
		entry.setPublished(new java.util.Date());
		feed.addEntry(entry);
	}
	
	public void testAtomFeedImport() throws ParseException
	{
		File directory = new File("feeds");
		
		// Only run this test if an import file exists
		if ((directory.exists()) && (directory.isDirectory()))
		{
			for (File file : directory.listFiles())
			{
				try
				{
					int commentcount = 0;
					System.out.println("Importing " + file.getAbsolutePath());
					AtomFeed feed = AtomImport.importFeed(file);
					System.out.println("Imported " + feed.getCategories().size() + " categories.");
					System.out.println("Imported " + feed.getEntries().size() + " entries.");
					for (AtomEntry entry : feed.getEntries())
					{
						if (!entry.getComments().isEmpty())
						{
							commentcount += entry.getComments().size();
						}
					}
					System.out.println("Imported " + commentcount + " comments.");
				}
				
				// Parse exceptions indicate a problem with the feed file
				catch (ParseException e)
				{
					e.printStackTrace();
				}

				// Parse exceptions indicate a problem with the feed file
				catch (SAXException e)
				{
					e.printStackTrace();
				}
				
				// ParserConfigurationException indicate a problem with... something that isn't me?
				catch (ParserConfigurationException e)
				{
					e.printStackTrace();
				}
				
				// IOExceptions mean we can't read the file...
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
