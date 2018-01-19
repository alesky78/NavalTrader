package it.spaghettisource.navaltrader.ui.component;

import java.awt.Component;
import java.text.Format;
import java.text.NumberFormat;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellProgressBarPercentageRenderer extends DefaultTableCellRenderer{

	private Format formatter;
	private JProgressBar bar;

	public static TableCellProgressBarPercentageRenderer getRenderer(){
		return new TableCellProgressBarPercentageRenderer( NumberFormat.getPercentInstance() );
	}	

	public TableCellProgressBarPercentageRenderer(Format formatter){
		bar = new JProgressBar();		
		bar.setStringPainted(true);
		this.formatter = formatter;
	}


	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		bar.setValue(new Double(((Double)value)*100).intValue());
		bar.setString(formatter.format(value));
		return bar;
	}	

}
