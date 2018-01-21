package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.text.Format;
import java.text.NumberFormat;

import javax.swing.table.DefaultTableCellRenderer;

/*
 *	Use a formatter to format the cell Object
 */
public class TableCellCurrentyRenderer extends DefaultTableCellRenderer{

	private Format formatter;

	public static TableCellCurrentyRenderer getRenderer(){
		return new TableCellCurrentyRenderer( NumberFormat.getCurrencyInstance() );
	}	

	public TableCellCurrentyRenderer(Format formatter){
		this.formatter = formatter;
	}

	public void setValue(Object value){

		try{
				
			if(value instanceof Double ){
				if((Double)value >= 0){
					setForeground(new Color(2657556));
				}else{
					setForeground(Color.RED);
				}		
			}			
			
			if (value != null){
				value = formatter.format(value);
			}
			
		}
		catch(IllegalArgumentException e) {}

		super.setValue(value);
	}

}
