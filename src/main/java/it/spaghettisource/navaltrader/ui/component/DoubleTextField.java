package it.spaghettisource.navaltrader.ui.component;

import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;

public class DoubleTextField  extends JFormattedTextField{

	public DoubleTextField(Double value){
		super(new DecimalFormat(".##"));
		setValue(value);
		setEditable(false);
	}
	
	public void setValue(Double amount){
		super.setValue(amount);		
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
