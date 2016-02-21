package org.infodancer.atom;

public class AtomContent extends AtomText
{
	public String toString()
	{
		return toString(AtomText.Element.CONTENT);
	}
}
