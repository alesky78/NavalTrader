package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;

public class CurrencyTextField extends JFormattedTextField {

	public CurrencyTextField(Double amount){
		super(NumberFormat.getCurrencyInstance());
		setValue(amount);
		setEditable(false);
	}
	
	public void setValue(Double amount){
		super.setValue(amount);
		if(amount>=0){
			setForeground(new Color(2657556));
		}else{
			setForeground(Color.RED);
		}		
	}
	
	public void setValue(Integer amount){
		super.setValue((double)amount);
	}	
	
	public void setValue(Object value){
		throw new UnsupportedOperationException();
	}	
	
	public Double getValue(){
		return (Double) super.getValue();
	}	
	
}
