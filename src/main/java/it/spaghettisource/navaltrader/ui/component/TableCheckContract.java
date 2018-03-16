package it.spaghettisource.navaltrader.ui.component;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import it.spaghettisource.navaltrader.ui.model.TransportContractTableRow;


/**
 * it work with the class {@link TableCellRenderCheckContract} 
 * 
 * 
 * 
 * @author Alessandro
 *
 */
public class TableCheckContract extends JTable {
	
	
	private List<TransportContractTableRow> source;
	
    /**
     * Constructs a <code>JTable</code> that is initialized with
     * <code>dm</code> as the data model, a default column model,
     * and a default selection model.
     *
     * @param dm        the data model for the table
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public TableCheckContract(TableModel dm,List<TransportContractTableRow> source) {
        super(dm, null, null);
        this.source = source;
		setDefaultRenderer(Object.class, new TableCellRenderCheckContract(source) );       
    }

    
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		TransportContractTableRow contract = source.get(this.convertRowIndexToModel(rowIndex));
		
		if(contract.isSelectable()){
	    	super.changeSelection(rowIndex, columnIndex, toggle, extend);			
		}

    }

}
