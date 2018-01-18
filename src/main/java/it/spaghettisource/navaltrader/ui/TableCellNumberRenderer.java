package it.spaghettisource.navaltrader.ui;

import java.text.NumberFormat;

import javax.swing.SwingConstants;

public class TableCellNumberRenderer extends TableCellFormatRenderer
{
	/*
	 *  Use the specified number formatter and right align the text
	 */
	public TableCellNumberRenderer(NumberFormat formatter)
	{
		super(formatter);
		setHorizontalAlignment( SwingConstants.RIGHT );
	}

	/*
	 *  Use the default currency formatter for the default locale
	 */
	public static TableCellNumberRenderer getCurrencyRenderer()
	{
		return new TableCellNumberRenderer( NumberFormat.getCurrencyInstance() );
	}

	/*
	 *  Use the default integer formatter for the default locale
	 */
	public static TableCellNumberRenderer getIntegerRenderer()
	{
		return new TableCellNumberRenderer( NumberFormat.getIntegerInstance() );
	}

	/*
	 *  Use the default percent formatter for the default locale
	 */
	public static TableCellNumberRenderer getPercentRenderer()
	{
		return new TableCellNumberRenderer( NumberFormat.getPercentInstance() );
	}
}
