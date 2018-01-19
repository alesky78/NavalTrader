package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;

public class CurrencyTextField extends JFormattedTextField {

	public CurrencyTextField(Double amount){
		super(NumberFormat.getCurrencyInstance());
		setValue(amount);
		
	}
	
	public void setValue(Double amount){
		super.setValue(amount);
		if(amount>=0){
			setForeground(new Color(2657556));
		}else{
			setForeground(Color.RED);
		}		
	}
	
}
