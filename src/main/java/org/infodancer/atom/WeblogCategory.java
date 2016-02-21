package org.infodancer.atom;

/**
 * This class exists only for export purposes.
 * @author matthew
 *
 */
public class WeblogCategory
{
	String columnName;
	String tableName;
	
	public WeblogCategory(String name)
	{
		this.tableName = name;
		this.columnName = name;
	}

	public WeblogCategory(String tableName, String columnName)
	{
		this.tableName = tableName;
		this.columnName = columnName;
	}
}
