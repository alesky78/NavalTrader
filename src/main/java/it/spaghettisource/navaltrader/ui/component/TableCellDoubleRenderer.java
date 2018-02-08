package it.spaghettisource.navaltrader.ui.component;

import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.table.DefaultTableCellRenderer;

public class TableCellDoubleRenderer extends DefaultTableCellRenderer{

	private Format formatter;

	public static TableCellDoubleRenderer getRenderer(){
		return new TableCellDoubleRenderer(new DecimalFormat(".##"));
	}	

	public TableCellDoubleRenderer(Format formatter){
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
