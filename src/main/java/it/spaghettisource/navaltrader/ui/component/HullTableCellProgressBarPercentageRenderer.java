package it.spaghettisource.navaltrader.ui.component;

import java.awt.Component;
import java.text.Format;
import java.text.NumberFormat;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import it.spaghettisource.navaltrader.ui.ColorUtils;

public class HullTableCellProgressBarPercentageRenderer extends DefaultTableCellRenderer{

	private Format formatter;
	private JProgressBar bar;

	public static HullTableCellProgressBarPercentageRenderer getRenderer(){
		return new HullTableCellProgressBarPercentageRenderer( NumberFormat.getPercentInstance() );
	}	

	public HullTableCellProgressBarPercentageRenderer(Format formatter){
		bar = new JProgressBar();		
		bar.setStringPainted(true);
		this.formatter = formatter;
		
	}


	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Double newValue = (Double)value;
		
		bar.setValue(new Double(newValue*100).intValue());
		bar.setString(formatter.format(value));
	
		bar.setForeground(ColorUtils.getTransitionRedToGreen(newValue));		
		
		return bar;
	}	

}
