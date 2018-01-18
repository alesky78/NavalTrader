package it.spaghettisource.navaltrader.ui;

import java.text.DateFormat;
import java.text.Format;

import javax.swing.table.DefaultTableCellRenderer;

/*
 *	Use a formatter to format the cell Object
 */
public class TableCellFormatRenderer extends DefaultTableCellRenderer
{
	private Format formatter;

	/*
	 *   Use the specified formatter to format the Object
	 */
	public TableCellFormatRenderer(Format formatter)
	{
		this.formatter = formatter;
	}

	public void setValue(Object value)
	{
		//  Format the Object before setting its value in the renderer

		try
		{
			if (value != null)
				value = formatter.format(value);
		}
		catch(IllegalArgumentException e) {}

		super.setValue(value);
	}

	/*
	 *  Use the default date/time formatter for the default locale
	 */
	public static TableCellFormatRenderer getDateTimeRenderer()
	{
		return new TableCellFormatRenderer( DateFormat.getDateTimeInstance() );
	}

	/*
	 *  Use the default time formatter for the default locale
	 */
	public static TableCellFormatRenderer getTimeRenderer()
	{
		return new TableCellFormatRenderer( DateFormat.getTimeInstance() );
	}
}
