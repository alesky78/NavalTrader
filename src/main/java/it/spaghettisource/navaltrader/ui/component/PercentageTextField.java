package it.spaghettisource.navaltrader.ui.component;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;

public class PercentageTextField extends JFormattedTextField {

	public PercentageTextField(Double amount){
		super(NumberFormat.getPercentInstance());
		setValue(amount);
		setEditable(false);		
	}
	
	
}
