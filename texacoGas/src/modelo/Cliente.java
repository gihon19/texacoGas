package modelo;

import java.math.BigDecimal;

public class Cliente {
	
	private int id=-1;
	private String nombre;
	private String direccion;
	private String telefono;
	private String celular;
	private String rtn;
	private BigDecimal limiteCredito=new BigDecimal(0.0);
	private BigDecimal saldoCuenta=new BigDecimal(0.0);
	
	
	public void setLimiteCredito(BigDecimal t){
		limiteCredito=limiteCredito.add(t);
	}
	public BigDecimal getLimiteCredito(){
		return limiteCredito;
	}
	public void setSaldoCuenta(BigDecimal t){
		saldoCuenta=saldoCuenta.add(t);
	}
	public BigDecimal getSaldoCuenta(){
		return saldoCuenta;
	}
	
	public void resetTotales(){
		
		limiteCredito=BigDecimal.ZERO;
		saldoCuenta=BigDecimal.ZERO;
	}
	
	public String getRtn(){
		return rtn;
	}
	public void setRtn(String r){
		rtn=r;
	}
	public String getCelular(){
		return celular;
	}
	public void setCelular(String c){
		celular=c;
	}
	public String getTelefono(){
		return telefono;
	}
	public void setTelefono(String t){
		telefono=t;
	}
	
	public String getDereccion(){
		return direccion;
	}
	public void setDireccion(String d){
		direccion=d;
	}
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String n){
		nombre=n;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int i){
		id=i;
	}
	@Override
	public String toString(){
		return "Id:"+id+", rtn:"+rtn+", nombre:"+nombre+", direccion:"+direccion+", telefono:"+telefono+", celular:"+celular;
	}
}
