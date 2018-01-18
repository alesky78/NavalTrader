package it.spaghettisource.navaltrader.ui.component;

import java.text.Format;
import java.text.NumberFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class TableCellPercentageRenderer extends DefaultTableCellRenderer{

	private Format formatter;

	public static TableCellPercentageRenderer getRenderer(){
		return new TableCellPercentageRenderer( NumberFormat.getPercentInstance() );
	}	

	public TableCellPercentageRenderer(Format formatter){
		this.formatter = formatter;
	}

	public void setValue(Object value){

		try{
			if (value != null){
				value = formatter.format(value);
			}
		}
		catch(IllegalArgumentException e) {}

		super.setValue(value);
	}

}
