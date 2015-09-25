package view.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.CierreCaja;
import modelo.ReciboPago;

public class TmCierres extends AbstractTableModel  {
	
	final private String []columnNames= {
			"codigo","Fecha", "Factura Inicio", "Factura Final","Efectivo","Tarjeta","Credito","Total Venta"
		};
	private List<CierreCaja> cierres=new ArrayList<CierreCaja>();

	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return cierres.size();
	}
	@Override
	public String getColumnName(int columnIndex) {
	        return columnNames[columnIndex];
	        
	  }
	@Override
    public Class getColumnClass(int columnIndex) {
		//        return getValueAt(0, columnIndex).getClass();
        return String.class;
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			return cierres.get(rowIndex).getId();
		case 1:
			return cierres.get(rowIndex).getFecha();
		case 2:
			return cierres.get(rowIndex).getNoFacturaInicio();
		case 3:
			return cierres.get(rowIndex).getNoFacturaFinal();
		case 4:
			return cierres.get(rowIndex).getEfectivo();
		case 5:
			return cierres.get(rowIndex).getTarjeta();
		case 6:
			return cierres.get(rowIndex).getCredito();
		case 7:
			return cierres.get(rowIndex).getTotal();
			default:
				return null;
		}
	}
	
	public void limpiar(){
		cierres.clear();
		fireTableDataChanged();
	}
	public void elimiar(int row){
		cierres.remove(row);
		fireTableDataChanged();
	}
	
	public CierreCaja getRecibo(int row){
		return cierres.get(row);
		
	}
	
	public void agregarPago(CierreCaja r){
		cierres.add(r);
		fireTableDataChanged();
	}

}
