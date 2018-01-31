package it.spaghettisource.navaltrader.ui.component;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;

public class TextFieldPercentage extends JFormattedTextField {

	public TextFieldPercentage(Double amount){
		super(NumberFormat.getPercentInstance());
		setValue(amount);
		setEditable(false);		
	}
	
	
}
