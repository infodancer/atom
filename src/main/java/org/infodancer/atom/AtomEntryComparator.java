package org.infodancer.atom;

/**
 * Compares AtomEntry objects by their updated date.
 * @author matthew
 */

public class AtomEntryComparator implements java.util.Comparator<AtomEntry>
{
	@Override
	public int compare(AtomEntry arg0, AtomEntry arg1)
	{
		java.util.Date arg0published = arg0.getPublished();
		java.util.Date arg1published = arg1.getPublished();
		if ((arg0published != null) && (arg1published != null))
		{
			long arg0long = arg0published.getTime();
			long arg1long = arg1published.getTime();
			if (arg1long != arg0long)
			{
				long result = (arg1long - arg0long);
				if (result >= 1) return 1;
				else if (result <= -1) return -1;
				else return 0;
			}
			else return 0;
		}
		else
		{
			if ((arg0published == null) && (arg1published == null)) return 0;
			else if (arg0published != null) return -1;
			else return 0;
		}
	}
}
