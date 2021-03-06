package modelo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Factura;
import modelo.NumberToLetterConverter;
import modelo.Usuario;

public class UsuarioDao {
	
	//private PreparedStatement getFecha=null;
	//private Connection conexionBD=null;
	private Conexion conexion;
	//private PreparedStatement agregarFactura=null;
	//private PreparedStatement seleccionarFacturasPendientes=null;
	//private PreparedStatement seleccionarFacturas=null;
	//private PreparedStatement elimiarTem = null;
	private PreparedStatement comprobarAdmin = null;
	private PreparedStatement insertar = null;
	private PreparedStatement setEventON = null;
	
	public int idUsuarioGuardado=0;
	
	//private DetalleFacturaDao detallesDao=null;
	
	public UsuarioDao(Conexion conn){
		conexion =conn;
	}
	
	public boolean comprobarAdmin(String pwd){
		
		boolean resultado=false;

		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT * FROM usuario where permiso='administrador' and clave=?";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			comprobarAdmin = con.prepareStatement(sql);
			comprobarAdmin.setString(1, pwd);
			
			res = comprobarAdmin.executeQuery();
			while(res.next()){
				resultado=true;
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
				resultado=false;
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(comprobarAdmin != null)comprobarAdmin.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
		
	
		
		return resultado;
	}

	public boolean setLogin(Usuario user) {
		
		Usuario unUsuario=new Usuario();
		ResultSet res=null;
		PreparedStatement buscarUser=null;
		Connection conn=null;
		boolean existe=false;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarUser=conn.prepareStatement("SELECT usuario, nombre_completo, clave, permiso,tipo_permiso FROM usuario WHERE usuario = ? AND clave = ?");
			buscarUser.setString(1, user.getUser());
			buscarUser.setString(2, user.getPwd());
			
			
			res = buscarUser.executeQuery();
			while(res.next()){
				existe=true;
				unUsuario.setNombre(res.getString("nombre_completo"));
				unUsuario.setUser(res.getString("usuario"));
				unUsuario.setPwd(res.getString("clave"));
				unUsuario.setPermiso(res.getString("permiso"));
				unUsuario.setTipoPermiso(res.getInt("tipo_permiso"));
				
				
				
			 }
			conexion.setUsuarioLogin(unUsuario);
			
			setEventON=conn.prepareStatement("SET GLOBAL event_scheduler=on;");
			setEventON.executeQuery();
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try{
					if(res != null) res.close();
	                if(buscarUser != null)buscarUser.close();
	                if(setEventON != null)setEventON.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
				excepcionSql.printStackTrace();
			//	conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			
		
		return existe;
			
		
		
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar usuario>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrar(Usuario myUsuario){
		boolean resultado=false;
		ResultSet rs=null;
		
		Connection conn=null;
		//int idFactura=0;
		
		String sql= "INSERT INTO usuario("
				+ "usuario,"
				+ "nombre_completo,"
				+ "clave,"
				+ "permiso,"
				+ "tipo_permiso)"
				+ " VALUES (?,?,?,?,?)";
		
		try 
		{
			
						
			conn=Conexion.getPoolConexion().getConnection();
			insertar=conn.prepareStatement(sql);
			insertar.setString(1,myUsuario.getUser() );
			insertar.setString(2, myUsuario.getNombre());
			insertar.setString(3, myUsuario.getPwd());
			insertar.setString(4, myUsuario.getPermiso());
			insertar.setInt(5, myUsuario.getTipoPermiso());
			
			
			
			
			
			insertar.executeUpdate();//se guarda el encabezado de la factura
			rs=insertar.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				//idFactura=rs.getInt(1);
				idUsuarioGuardado=rs.getInt(1);
				
			}
			
					
			resultado= true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				if(rs != null) rs.close();
	            if(insertar != null)insertar.close();
	            if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
		
		
		
		return resultado;
	}

}
