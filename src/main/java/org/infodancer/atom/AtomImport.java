package org.infodancer.atom;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Imports an Atom feed from an XML file.
 * @author matthew
 */

public class AtomImport 
{
	private static final String GENERATOR = "generator";
	private static final String ID = "id";
	private static final String UPDATED = "updated";
	private static final String PUBLISHED = "published";
	private static final String ENTRY = "entry";
	private static final String LABEL = "label";
	private static final String SCHEME = "scheme";
	private static final String SUMMARY = "summary";
	private static final String TERM = "term";
	private static final String CATEGORY = "category";
	private static final String TYPE = "type";
	private static final String TEXT = "text";
	private static final String CONTENT = "content";
	private static final String REL = "rel";
	private static final String HREFLANG = "hreflang";
	private static final String HREF = "href";
	private static final String LINK = "link";
	private static final String AUTHOR = "author";
	private static final String EMAIL = "email";
	private static final String URI = "uri";
	private static final String NAME = "name";
	private static final String CONTRIBUTOR = "contributor";
	private static final String SUBTITLE = "subtitle";
	private static final String TITLE = "title";
	private static final String FEED = "feed";
	private static final String DBID = "dbid";
	private static final String COMMENTS = "comments";

	public static AtomFeed importFeed(java.io.File file) 
	throws ParserConfigurationException, SAXException, IOException, ParseException
	{
        if (file.exists())
        {
        	return importFeed(new FileInputStream(file));
        }
        else return null;
	}

	public static AtomFeed importFeed(java.io.InputStream input) 
	throws ParserConfigurationException, SAXException, IOException, ParseException
	{
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(input);
        return parseAtomDocument(document);
	}
	
	public static AtomEntry importEntry(java.io.InputStream input) 
	throws ParserConfigurationException, SAXException, IOException, ParseException
	{
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(input);
        return parseAtomEntry(document);
	}
	
	private static AtomFeed parseAtomDocument(Document doc) throws ParseException
	{
		NodeList nodes = doc.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			String name = node.getNodeName();
			if (FEED.equalsIgnoreCase(name))
			{
				return parseAtomFeed(node);
			}
		}
		return null;
	}

	private static AtomFeed parseAtomFeed(Node feed) throws ParseException
	{
		if (FEED.equalsIgnoreCase(feed.getNodeName()))
		{
			AtomFeed result = new AtomFeed();
			NodeList nodes = feed.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (TITLE.equalsIgnoreCase(name))
				{
					String title = extractValue(child);
					if (title != null)
					{
						result.setTitle(title);
					}
				}
				else if (SUBTITLE.equalsIgnoreCase(name))
				{
					String subtitle = extractValue(child);
					if (subtitle != null)
					{
						result.setSubtitle(subtitle);
					}
				}
				else if (GENERATOR.equalsIgnoreCase(name))
				{
					String generator = extractValue(child);
					if (generator != null)
					{
						result.setGenerator(generator);
					}
				}
				else if (UPDATED.equalsIgnoreCase(name))
				{
					Date updated = parseDate(child);
					if (updated != null)
					{
						result.setUpdated(updated);
					}
				}
				else if (CATEGORY.equalsIgnoreCase(name))
				{
					AtomCategory category = parseCategory(child);
					if (category != null)
					{
						result.addCategory(category);
					}
				}
				else if (ENTRY.equalsIgnoreCase(name))
				{
					AtomEntry entry = parseAtomEntry(child);
					if (entry != null)
					{
						result.addEntry(entry);
					}
					else System.out.println("Entry was null!");
				}
				else if (DBID.equalsIgnoreCase(name))
				{
					result.setId(parseLong(child));
				}
			}
			return result;
		}
		else throw new RuntimeException(feed.getNodeName() + " is not an Atom feed!");
	}

	private static Date parseDate(Node child)
	{
		String value = extractValue(child);
		if ((value != null) && (value.trim().length() > 0))
		{
			try
			{
				Date result = AtomUtility.convertDate(value);
				return result;
			}
			
			// Don't die just because we can't parse a date value
			catch (Exception e)
			{
				System.out.println(value + " is not a valid Atom date");
				return null;
			}
		}
		else return null;
	}

	private static AtomEntry parseAtomEntry(Node node)
	{
		if (ENTRY.equalsIgnoreCase(node.getNodeName()))
		{
			AtomEntry result = new AtomEntry();
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (TITLE.equalsIgnoreCase(name))
				{
					result.setTitle(extractValue(child));
				}
				else if (DBID.equalsIgnoreCase(name))
				{
					result.setId(parseLong(child));
				}
				else if (ID.equalsIgnoreCase(name))
				{
					result.setAtomid(extractValue(child));
				}
				else if (UPDATED.equalsIgnoreCase(name))
				{
					result.setUpdated(parseDate(child));
				}
				else if (PUBLISHED.equalsIgnoreCase(name))
				{
					result.setPublished(parseDate(child));
				}
				else if (CATEGORY.equalsIgnoreCase(name))
				{
					AtomCategory category = parseCategory(child);
					result.addCategory(category);
				}
				else if (SUMMARY.equalsIgnoreCase(name))
				{
					AtomSummary summary = parseSummary(child);
					result.setSummary(summary);
				}
				else if (CONTENT.equalsIgnoreCase(name))
				{
					AtomContent content = parseContent(child);
					result.setContent(content);
				}
				else if (LINK.equalsIgnoreCase(name))
				{
					AtomLink link = parseLink(child);
					result.addLink(link);
				}
				else if (AUTHOR.equalsIgnoreCase(name))
				{
					AtomAuthor author = parseAuthor(child);
					result.addAuthor(author);
				}
				else if (CONTRIBUTOR.equalsIgnoreCase(name))
				{
					AtomContributor contributor = parseContributor(child);
					result.addContributor(contributor);					
				}
				else if (COMMENTS.equalsIgnoreCase(name))
				{
					List<AtomEntry> comments = parseComments(child);
					for (AtomEntry comment : comments)
					{
						result.addComment(comment);
					}
				}
			}			
			return result;
		}		
		else return null;
	}

	private static List<AtomEntry> parseComments(Node node)
	{
		if (COMMENTS.equalsIgnoreCase(node.getNodeName()))
		{
			List<AtomEntry> result = new LinkedList<AtomEntry>();
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
			{
				Node child = children.item(i);
				String childname = child.getNodeName();
				if (ENTRY.equalsIgnoreCase(childname))
				{
					AtomEntry entry = parseAtomEntry(child);
					result.add(entry);
				}
			}
			return result;
		}
		return null;
	}

	private static Long parseLong(Node node)
	{
		String value = node.getTextContent();
		return Long.decode(value);
	}

	private static AtomContributor parseContributor(Node node)
	{
		if (CONTRIBUTOR.equalsIgnoreCase(node.getNodeName()))
		{
			AtomContributor result = new AtomContributor();
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
			{
				Node child = children.item(i);
				String childname = child.getNodeName();
				if (NAME.equalsIgnoreCase(childname))
				{
					String childvalue = child.getTextContent();
					result.setName(childvalue);
				}
				else if (URI.equalsIgnoreCase(childname))
				{
					String childvalue = child.getTextContent();
					result.setUri(childvalue);
				}
				else if (EMAIL.equalsIgnoreCase(childname))
				{
					String childvalue = child.getTextContent();
					result.setEmail(childvalue);
				}
			}
			return result;
		}
		return null;
	}

	private static AtomAuthor parseAuthor(Node node)
	{
		if (AUTHOR.equalsIgnoreCase(node.getNodeName()))
		{
			AtomAuthor result = new AtomAuthor();
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
			{
				Node child = children.item(i);
				String childname = child.getNodeName();
				if (NAME.equalsIgnoreCase(childname))
				{
					String childvalue = child.getTextContent();
					result.setName(childvalue);
				}
				else if (URI.equalsIgnoreCase(childname))
				{
					String childvalue = child.getTextContent();
					result.setUri(childvalue);
				}
				else if (EMAIL.equalsIgnoreCase(childname))
				{
					String childvalue = child.getTextContent();
					result.setEmail(childvalue);
				}
			}
			
			return result;
		}
		return null;
	}

	private static AtomLink parseLink(Node node)
	{
		if (LINK.equalsIgnoreCase(node.getNodeName()))
		{
			AtomLink result = new AtomLink();
			NamedNodeMap attributes = node.getAttributes();
			Node hrefNode = attributes.getNamedItem(HREF);
			if (hrefNode != null)
			{
				String value = hrefNode.getNodeValue();
				result.setHref(value);
			}			
			Node relNode = attributes.getNamedItem(REL);
			if (relNode != null)
			{
				String value = relNode.getNodeValue();
				result.setRel(value);
			}			

			Node hreflangNode = attributes.getNamedItem(HREFLANG);
			if (hreflangNode != null)
			{
				String value = hreflangNode.getNodeValue();
				result.setHreflang(value);
			}			
			
			return result;
		}
		return null;
	}

	private static AtomContent parseContent(Node node)
	{
		if (CONTENT.equalsIgnoreCase(node.getNodeName()))
		{
			AtomContent result = new AtomContent();
			NamedNodeMap attributes = node.getAttributes();
			Node typeNode = attributes.getNamedItem(TYPE);
			if (typeNode != null)
			{
				String type = typeNode.getNodeValue();				
				result.setType(type);
			}
			else result.setType(TEXT);
			String value = node.getTextContent();
			result.setValue(value);
			return result;
		}
		return null;
	}

	private static AtomSummary parseSummary(Node node)
	{
		if (SUMMARY.equalsIgnoreCase(node.getNodeName()))
		{
			AtomSummary result = new AtomSummary();
			NamedNodeMap attributes = node.getAttributes();
			Node typeNode = attributes.getNamedItem(TYPE);
			if (typeNode != null)
			{
				String type = typeNode.getNodeValue();				
				result.setType(type);
			}
			else result.setType(TEXT);
			String value = node.getTextContent();
			result.setValue(value);
			return result;			
		}
		return null;
	}

	private static AtomCategory parseCategory(Node node)
	{
		if (CATEGORY.equalsIgnoreCase(node.getNodeName()))
		{
			NamedNodeMap attributes = node.getAttributes();
			Node term = attributes.getNamedItem(TERM);
			Node scheme = attributes.getNamedItem(SCHEME);
			Node label = attributes.getNamedItem(LABEL);
			AtomCategory result = new AtomCategory();
			if (term != null)
			{
				result.setTerm(term.getNodeValue());
			}
			if (scheme != null)
			{
				result.setScheme(scheme.getNodeValue());
			}
			if (label != null)
			{
				result.setLabel(label.getNodeValue());
			}
			return result;
		}
		return null;
	}

	private static String extractValue(Node node)
	{
		return node.getTextContent();
	}	
}
