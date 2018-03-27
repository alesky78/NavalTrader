package it.spaghettisource.navaltrader.geometry;


/**
 * The values goes between -179 and 180
 * 
 * @author Alessandro
 *
 */
public class Angle {
	
	
	private double value = 0;
	
	public Angle(double value) {
		value = 0;
	}
	
	public void resetAngle(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public int getValueInt() {
		return (int)value;
	}	

	/**
	 * add value between -179 to 180
	 * 
	 * @param toAdd
	 */
	public void addToAngle(double toAdd) {
		value += toAdd;
		if(value>180) {
			value = -180 + value%180;
		}else if(value<-180) {
			value =  180 - value%180;
		}
	}
	
	public void rotateTo(double newValue, double move) {
		if(value ==  newValue) {
			//do nothing
		}else if(value > 0 && newValue> 0) {
			if(newValue>value) {
				addToAngle(move);
			}else if(value > newValue){
				addToAngle(-move);				
			}
		}else if(value < 0 && newValue< 0) {
			if(newValue>value) {
				addToAngle(move);
			}else if(value > newValue){
				addToAngle(-move);				
			}
		}else  if(value > 0 && newValue< 0) {
			double oppositAngle = -180 + value;
			if(oppositAngle<newValue) {
				addToAngle(-move);
			}else if(oppositAngle > newValue){
				addToAngle(move);				
			}
		}else  if(value < 0 && newValue> 0) {
			double oppositAngle = 180 + value;
			if(oppositAngle<newValue) {
				addToAngle(-move);
			}else if(oppositAngle > newValue){
				addToAngle(move);				
			}
		}else  if(value != 0 && newValue == 0) {
			if(value>0) {
				addToAngle(-move);
			}else if(value<0){
				addToAngle(move);				
			}
		}else  if(value == 0 && newValue != 0) {
			if(newValue>0) {
				addToAngle(move);
			}else if(newValue<0){
				addToAngle(-move);				
			}
		}
		
		
	}

}
