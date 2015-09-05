package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import modelo.Conexion;
import modelo.Factura;
import modelo.ReciboPago;

public class ReciboPagoDao {
	
	private Conexion conexion;
	private PreparedStatement insertar=null;
	private PreparedStatement insertar2=null;
	private PreparedStatement todos=null;
	private PreparedStatement buscar=null;
	private PreparedStatement actualizar=null;
	private PreparedStatement eliminar=null;
	private CuentaPorCobrarDao myCuentaCobrarDao=null;
	private ClienteDao myClienteDao=null;
	public int idUltimoRecibo=0;

	public ReciboPagoDao(Conexion conn) {
		conexion=conn;
		myClienteDao=new ClienteDao(conexion);
		myCuentaCobrarDao=new CuentaPorCobrarDao(conexion);
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrar(ReciboPago myRecibo)
	{
		//JOptionPane.showConfirmDialog(null, myCliente);
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			insertar=con.prepareStatement( "INSERT INTO recibo_pago(fecha,codigo_cliente,total_letras,total,concepto,usuario) VALUES (now(),?,?,?,?,?)");
			
			insertar.setInt(1, myRecibo.getCliente().getId());
			insertar.setString(2, myRecibo.getTotalLetras());
			insertar.setBigDecimal(3, myRecibo.getTotal());
			insertar.setString(4, myRecibo.getConcepto());
			insertar.setString(5, conexion.getUsuarioLogin().getUser());
						
			resultado=insertar.executeUpdate();
			
			rs=insertar.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				//this.setIdClienteRegistrado(rs.getInt(1));
				myRecibo.setNoRecibo(rs.getInt(1));
				this.idUltimoRecibo=rs.getInt(1);
			}
			
			//se establece en el concepto en numero de recibo con que se pago
			String concepto=myRecibo.getConcepto();
			concepto=concepto+" con recibo no. "+myRecibo.getNoRecibo();
			myRecibo.setConcepto(concepto);
			
			this.myCuentaCobrarDao.reguistrarDebito(myRecibo);
			
			
			for(int x=0;x<myRecibo.getFacturas().size();x++){
				this.registrarFacturasPagadas(myRecibo.getFacturas().get(x).getIdFactura(), myRecibo.getNoRecibo());
			}
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
            return false;
		}
		finally
		{
			try{
				if(rs!=null)rs.close();
				 if(insertar != null)insertar.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFacturasPagadas(int idFactura, int idRecibo)
	{
		//JOptionPane.showConfirmDialog(null, myCliente);
		int resultado=0;
		//ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			insertar2=con.prepareStatement( "INSERT INTO detalle_pago(no_recibo_pago,no_factura_pagada) VALUES (?,?)");
			
			insertar2.setInt(1,idRecibo );
			insertar2.setInt(2,idFactura);
			
						
			resultado=insertar2.executeUpdate();
			
					
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
            return false;
		}
		finally
		{
			try{
				//if(rs!=null)rs.close();
				 if(insertar2 != null)insertar2.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<ReciboPago> todosRecibo(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="select DATE_FORMAT(recibo_pago.fecha, '%d/%m/%Y') as fecha,no_recibo,codigo_cliente,total_letras,total,concepto,usuario from recibo_pago ORDER BY recibo_pago.no_recibo DESC";
        //Statement stmt = null;
       	List<ReciboPago> pagos=new ArrayList<ReciboPago>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			todos = con.prepareStatement(sql);
			
			res = todos.executeQuery();
			while(res.next()){
				ReciboPago un=new ReciboPago();
				existe=true;
				un.setFecha(res.getString("fecha"));
				un.setConcepto(res.getString("concepto"));
				un.setNoRecibo(res.getInt("no_recibo"));
				un.setTotal(res.getBigDecimal("total"));
				
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				un.setCliente(unCliente);
				
				
				pagos.add(un);
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(todos != null)todos.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return pagos;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public ReciboPago reciboPorNo(int noRecibo){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="select DATE_FORMAT(recibo_pago.fecha, '%d/%m/%Y') as fecha,"
    			+ " no_recibo, "
    			+ "codigo_cliente, "
    			+ "total_letras, "
    			+ "total, "
    			+ " concepto,"
    			+ " usuario "
    			+ " from "
    			+ " recibo_pago "
    			+ " WHERE "
    			+ " recibo_pago.no_recibo =? "
    			+ " ORDER BY recibo_pago.no_recibo DESC";
        //Statement stmt = null;
       	ReciboPago un=new ReciboPago();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			todos = con.prepareStatement(sql);
			
			todos.setInt(1, noRecibo);
			
			res = todos.executeQuery();
			while(res.next()){
				
				existe=true;
				un.setFecha(res.getString("fecha"));
				un.setConcepto(res.getString("concepto"));
				un.setNoRecibo(res.getInt("no_recibo"));
				un.setTotal(res.getBigDecimal("total"));
				
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				un.setCliente(unCliente);
				
				
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(todos != null)todos.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return un;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar los recibo por fecha>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<ReciboPago>  reciboPorFecha(String fecha1, String fecha2){//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
        String sql="select DATE_FORMAT(recibo_pago.fecha, '%d/%m/%Y') as fecha,"
    			+ " no_recibo, "
    			+ "codigo_cliente, "
    			+ "total_letras, "
    			+ "total, "
    			+ " concepto,"
    			+ " usuario "
    			+ " from "
    			+ " recibo_pago "
    			+ " WHERE "
    			+ " DATE_FORMAT(recibo_pago.fecha, '%d/%m/%Y')>=? and DATE_FORMAT(recibo_pago.fecha, '%d/%m/%Y')<=? "
    			+ " ORDER BY recibo_pago.no_recibo DESC";
        //Statement stmt = null;
       	List<ReciboPago> pagos=new ArrayList<ReciboPago>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			
			todos = con.prepareStatement(sql);
			todos.setString(1, fecha1);
			todos.setString(2, fecha2);
			res = todos.executeQuery();
			while(res.next()){
				ReciboPago un=new ReciboPago();
				existe=true;
				un.setFecha(res.getString("fecha"));
				un.setConcepto(res.getString("concepto"));
				un.setNoRecibo(res.getInt("no_recibo"));
				un.setTotal(res.getBigDecimal("total"));
				
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				un.setCliente(unCliente);
				
				
				pagos.add(un);
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(todos != null)todos.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return pagos;
			}
			else return null;
		}

}
