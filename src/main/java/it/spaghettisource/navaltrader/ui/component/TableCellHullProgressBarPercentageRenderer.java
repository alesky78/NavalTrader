package it.spaghettisource.navaltrader.ui.component;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import it.spaghettisource.navaltrader.ui.ColorUtils;

public class TableCellHullProgressBarPercentageRenderer extends DefaultTableCellRenderer{


	private JProgressBar bar;

	public static TableCellHullProgressBarPercentageRenderer getRenderer(){
		return new TableCellHullProgressBarPercentageRenderer();
	}	

	public TableCellHullProgressBarPercentageRenderer(){
		bar = new JProgressBar();		
		bar.setStringPainted(true);
		
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Integer castValue = (Integer)value;
		
		bar.setValue(castValue);
		bar.setString(castValue+"%");
	
		bar.setForeground(ColorUtils.getTransitionRedToGreen(castValue));		
		
		return bar;
	}	

}
