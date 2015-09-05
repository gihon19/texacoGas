package view.tablemodel;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import modelo.Departamento;
public class CbxTmDepartamento extends DefaultComboBoxModel {
	
	private Vector<Departamento> depts;

	public CbxTmDepartamento() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getSize() {
		  return depts.size();
		 }
	
	@Override
	public Object getElementAt(int index) {
		  return depts.get(index);
		 }
	
	public void setLista(Vector<Departamento> im){
		depts=im;
	}
	
	public int buscarImpuesto(Departamento m){
		int index=-1;
		
		for(int c=0;c<depts.size();c++){
			
			if(depts.get(c).getId()==m.getId()){
				
				index=c;
			}
		}
		
		return index;
	}

}
