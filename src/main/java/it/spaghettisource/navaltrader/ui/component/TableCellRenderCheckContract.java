package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import it.spaghettisource.navaltrader.ui.model.TransportContractTableRow;

public class TableCellRenderCheckContract  extends DefaultTableCellRenderer{

	private List<TransportContractTableRow> contracts;
	
    public TableCellRenderCheckContract(List<TransportContractTableRow> contracts) {
        super();
        this.contracts = contracts;
        setOpaque(true);
    } 
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
    	TransportContractTableRow contract = contracts.get(table.convertRowIndexToModel(row));
    	
    	if(isSelected) {
        	return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);    		
    	}else if(!contract.isSelectable()){
            setForeground(Color.black);        
            setBackground(Color.red);
            setText(value !=null ? value.toString() : "");
            return this;            
        }else {
            setForeground(Color.black);        
            setBackground(Color.white);
            setText(value !=null ? value.toString() : "");
            return this;            
        } 

    }
    
}
