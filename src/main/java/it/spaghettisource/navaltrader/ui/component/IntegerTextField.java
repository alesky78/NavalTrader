package it.spaghettisource.navaltrader.ui.component;

import javax.swing.JFormattedTextField;

public class IntegerTextField  extends JFormattedTextField{

	public IntegerTextField(Integer value){
		setValue(value);
		setEditable(false);
	}
	
	public void setValue(Double amount){
		super.setValue(amount.intValue());		
	}
	
	public void setValue(Integer amount){
		super.setValue(amount);
	}	
	
	public void setValue(Object value){
		throw new UnsupportedOperationException();
	}	
	
	public Integer getValue(){
		return (Integer) super.getValue();
	}		
	
}
