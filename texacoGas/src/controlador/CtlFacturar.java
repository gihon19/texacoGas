package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import modelo.AbstractJasperReports;
import modelo.Articulo;
import modelo.dao.ArticuloDao;
import modelo.dao.CierreCajaDao;
import modelo.dao.EmpleadoDao;
import modelo.dao.PrecioArticuloDao;
import modelo.Cliente; 
import modelo.dao.ClienteDao;
import modelo.Conexion;
import modelo.DetalleFactura;
import modelo.Empleado;
import modelo.Factura;
import modelo.dao.FacturaDao;
import view.ViewCambioPago;
import view.ViewCargarVenderor;
import view.ViewFacturar;
import view.ViewListaArticulo;
import view.ViewListaClientes;
import view.ViewListaFactura;

public class CtlFacturar  implements ActionListener, MouseListener, TableModelListener, WindowListener, KeyListener  {
	
	private ViewFacturar view;
	private Factura myFactura=null;
	private FacturaDao facturaDao=null;//=new FacturaDao();
	private ClienteDao clienteDao=null;//=new ClienteDao();
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private EmpleadoDao myEmpleadoDao=null;
	private PrecioArticuloDao preciosDao=null;
	private Cliente myCliente=null;
	private Conexion conexion=null;
	private int filaPulsada=0;
	private boolean resultado=false;
	
	private int tipoView=1;
	private int netBuscar=0;
	private static final Pattern numberPattern=Pattern.compile("-?\\d+");
	//private ViewListaArticulo viewListaArticulo=null;
	//private CtlArticuloBuscar ctlArticulo=null;
	
	public CtlFacturar(ViewFacturar v,Conexion conn){
		view=v;
		view.conectarContralador(this);
		conexion=conn;	
		//se inicializan atributos de la factura
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		clienteDao=new ClienteDao(conexion);
		facturaDao=new FacturaDao(conexion);
		myEmpleadoDao=new EmpleadoDao(conexion);
		preciosDao=new PrecioArticuloDao(conexion);
		this.setEmptyView();
		view.getTxtNombrecliente().requestFocusInWindow();
		//cargarComboBox();
		/*view.getModeloTabla().agregarDetalle();
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		clienteDao=new ClienteDao(conexion);
		facturaDao=new FacturaDao(conexion);
		
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		//view.setVisible(true);
		this.view.getTxtIdcliente().setText("1");;
		this.view.getTxtNombrecliente().setText("Cliente Normal");
		
		this.view.getTxtBuscar().requestFocusInWindow();*/
		
		//this.view.setVisible(true);
		//la ventana buscar articulo y su controlador
		//viewListaArticulo=new ViewListaArticulo(this.view);
		//view.getTxtBuscar().requestFocusInWindow();
		//ctlArticulo =new CtlArticuloBuscar(viewListaArticulo,conexion);
		//viewListaArticulo.conectarControladorBuscar(ctlArticulo);
	
		
	}
	
	private void cargarComboBox(){
		//se crea el objeto para obtener de la bd los impuestos
		myEmpleadoDao=new EmpleadoDao(conexion);
	
		//se obtiene la lista de los impuesto y se le pasa al modelo de la lista
		this.view.getModeloEmpleados().setLista(myEmpleadoDao.todoEmpleadosVendedores());
		
		
		//se remueve la lista por defecto
		this.view.getCbxEmpleados().removeAllItems();
	
		this.view.getCbxEmpleados().setSelectedIndex(0);
	}
	private static boolean isNumber(String string){
		return string !=null && numberPattern.matcher(string).matches();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		//JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(comando){
		
		/*case "CREDITO":
			view.getPanelDetalleVehiculo().setVisible(true);
			break;
		case "CONTADO":
			view.getPanelDetalleVehiculo().setVisible(false);
			break;*/
		case "BUSCARARTICULO2":
				if(myArticulo!=null){
					//conseguir los precios del producto
					myArticulo.setPreciosVenta(this.preciosDao.getPreciosArticulo(myArticulo.getId()));
					
					//agregar el articulo a las lista
					this.view.getModeloTabla().setArticulo(myArticulo);
					//this.view.getModelo().getDetalle(row).setCantidad(1);
					
					//calcularTotal(this.view.getModeloTabla().getDetalle(row));
					calcularTotales();
					this.view.getModeloTabla().agregarDetalle();
					view.getTxtArticulo().setText("");
					view.getTxtPrecio().setText("");
					view.getTxtBuscar().setText("");
					selectRowInset();
					
				}else{
					String busca=this.view.getTxtBuscar().getText();
					//if(this.isNumber(busca)){
						
						this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(busca);
						if(myArticulo!=null){
							
							//conseguir los precios del producto
							myArticulo.setPreciosVenta(this.preciosDao.getPreciosArticulo(myArticulo.getId()));
							this.view.getModeloTabla().setArticulo(myArticulo);
							//this.view.getModelo().getDetalle(row).setCantidad(1);
							
							//calcularTotal(this.view.getModeloTabla().getDetalle(row));
							calcularTotales();
							this.view.getModeloTabla().agregarDetalle();
							view.getTxtBuscar().setText("");
							selectRowInset();
							
						}else{
							JOptionPane.showMessageDialog(view, "No se encontro el articulo");
							view.getTxtBuscar().setText("");
							view.getTxtBuscar().requestFocusInWindow();
						}
						
					//}
				}
				netBuscar=0;
			break;
		case "BUSCARCLIENTE":
			myCliente=null;
			myCliente=clienteDao.buscarCliente(Integer.parseInt(this.view.getTxtIdcliente().getText()));
			
			if(myCliente!=null){
				this.view.getTxtNombrecliente().setText(myCliente.getNombre());
				view.getTxtRtn().setText(myCliente.getRtn());
				view.getTxtModelo().requestFocusInWindow();
			}else{
				this.view.getTxtIdcliente().setText("2001");
				this.view.getTxtNombrecliente().setText("Consumidor final");
				JOptionPane.showMessageDialog(view, "Cliente no encontrado");
			}
			
			break;
		case "ACTUALIZAR":
			this.actualizar();
			break;
		case "BUSCARARTICULO":
			this.buscarArticulo();
		break;
		
		case "CERRAR":
			this.salir();
		break;
		case "BUSCARCLIENTES":
			this.buscarCliente();
			break;
		case "COBRAR":
			this.cobrar();
			break;
		case "GUARDAR":
			this.guardar();
			break;
			
		case "CIERRECAJA":
			this.cierreCaja();
			break;
		case "PENDIENTES":
			this.showPendientes();
			break;
		case "CREDITO":
			setCredito();
			break;
		case "CONTADO":
			setContado();
			break;
			
		case "SELEC":
			this.view.getTableDetalle().changeSelection(0,0, false, false);
			this.view.getTableDetalle().requestFocus();
			break;
			
		
		}
		
	}
	
	
	private void setContado(){
		setEmptyView();
		view.getTxtRtn().setEditable(true);
		view.getTxtNombrecliente().requestFocusInWindow();
	}
	private void setCredito(){
		setEmptyView();
		view.getRdbtnCredito().setSelected(true);
		view.getTxtRtn().setEditable(false);
		view.getTxtIdcliente().setText("");
		view.getTxtNombrecliente().setText("");
	 	view.getTxtIdcliente().requestFocusInWindow();
	}
	


	private void setFactura(){
		
		//sino se ingreso un cliente en particular que coge el cliente por defecto
		if(myCliente==null){
			myCliente=new Cliente();
			myCliente.setId(Integer.parseInt(this.view.getTxtIdcliente().getText()));
			myCliente.setNombre(this.view.getTxtNombrecliente().getText());
			myCliente.setRtn(view.getTxtRtn().getText());
			
		}
		
		if(this.view.getRdbtnContado().isSelected()){
			myFactura.setTipoFactura(1);
			myFactura.setEstadoPago(1);
		}
		
		if(this.view.getRdbtnCredito().isSelected()){
			myFactura.setTipoFactura(2);
			myFactura.setEstadoPago(0);
		}
		
		if(view.getTxtNoplaca().getText().trim().length()!=0){
			myFactura.setPlacaVehiculo(view.getTxtNoplaca().getText());
		}
		if(view.getTxtModelo().getText().trim().length()!=0){
			myFactura.setModeloVehiculo(view.getTxtModelo().getText());
		}
		if(view.getTxtKilometraje().getText().trim().length()!=0){
			myFactura.setKilometrajeVehiculo(view.getTxtKilometraje().getText());
		}
		
		myFactura.setCliente(myCliente);
		myFactura.setDetalles(this.view.getModeloTabla().getDetalles());
		myFactura.setFecha(facturaDao.getFechaSistema());
		
		//Se establece el vendedor seleccionado
		/*Empleado emp= (Empleado) this.view .getCbxEmpleados().getSelectedItem();
		myFactura.setVendedor(emp);*/
		
		
		//myArticulo.setImpuestoObj(imp);
		//JOptionPane.showMessageDialog(view, myCliente);*/
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
		int colum=e.getColumn();
		int row=e.getFirstRow();
		//JOptionPane.showMessageDialog(view, myArticulo);
		//JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(e.getType()){
		
		
		
		
			case TableModelEvent.UPDATE:
				
				//Se recoge el id de la fila marcada
		        int identificador=0; 
				
				//se ingreso un id o codigo de barra en la tabla
				if(colum==0){
					identificador=(int)this.view.getModeloTabla().getValueAt(row, 0);
			        myArticulo=this.view.getModeloTabla().getDetalle(row).getArticulo();
			        
					
			        //se ingreso un codigo de barra y si el articulo en la bd 
			        if(myArticulo.getId()==-2){
						String cod=this.view.getModeloTabla().getDetalle(row).getArticulo().getCodBarra().get(0).getCodigoBarra();
						this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(cod);
						
					}else{//sino se ingreso un codigo de barra se busca por id de articulo
						this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					}
					
					//si se encuentra  el articulo por codigo de barra o por id se calcula los totales y se agrega 
					if(myArticulo!=null){
						
						//se estable en articulo en la tabla
						this.view.getModeloTabla().setArticulo(myArticulo, row);
						//se calcula los totales
						//calcularTotales();
						
						
						boolean toggle = false;
						boolean extend = false;
						
							
						this.view.getTableDetalle().changeSelection(row,colum+7, toggle, extend);
						this.view.getTableDetalle().requestFocus();
					    
					    
					    
						//se agrega otra fila en la tabla
						//this.view.getModeloTabla().agregarDetalle();
						
					}else{//si no se encuentra
						
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
						//sino se encuentra se estable un id de -1 para que sea eliminado el articulo en la tabla
						this.view.getModeloTabla().getDetalle(row).getArticulo().setId(-1);
						
						//se agrega la nueva fila de la tabla
						this.view.getModeloTabla().agregarDetalle();
						
						// se vuelve a calcular los totales
						calcularTotales();
						
					}
					
					
					
					
					
				}
				
				//se cambia la cantidad en la tabla
				if(colum==3){
					//JOptionPane.showMessageDialog(view, "entro");
					//se obtien la cantidad y el precio de compra por unidad
					BigDecimal cantidad=view.getModeloTabla().getDetalle(row).getCantidad();
					BigDecimal precioVenta= new BigDecimal(view.getModeloTabla().getDetalle(row).getArticulo().getPrecioVenta());
					
					BigDecimal total=cantidad.multiply(precioVenta);
					
					view.getModeloTabla().getDetalle(row).setTotal(total.setScale(0, BigDecimal.ROUND_HALF_EVEN));
					
					calcularTotales();
					//this.view.getModeloTabla().agregarDetalle();
					
					boolean toggle = false;
					boolean extend = false;
					
						
					this.view.getTableDetalle().changeSelection(row,colum+4, toggle, extend);
					this.view.getTableDetalle().requestFocus();
					
					
					
				}
				
				//se agrego un descuento a la tabla
				if(colum==6){
					calcularTotales();
					view.getTxtBuscar().requestFocusInWindow();
					//JOptionPane.showMessageDialog(view, "Modifico el Descuento "+this.view.getModeloTabla().getDetalle(row).getDescuentoItem().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
				}
				if(colum==7){
					
					BigDecimal total=view.getModeloTabla().getDetalle(row).getTotal();
					BigDecimal precioVenta= new BigDecimal(view.getModeloTabla().getDetalle(row).getArticulo().getPrecioVenta());
					//BigDecimal precio=new BigDecimal(view.getModeloTabla().getDetalle(row).getArticulo().getPrecioVenta());
					BigDecimal cantidad=total.divide(precioVenta,2,BigDecimal.ROUND_HALF_EVEN);
					
					
					view.getModeloTabla().getDetalle(row).setCantidad(cantidad);
					this.view.getModeloTabla().agregarDetalle();
					calcularTotales();
					boolean toggle = false;
					boolean extend = false;
					//this.view.getTableDetalle().requestFocus();
					//se agrega la nueva fila de la tabla
					//this.view.getModelo().agregarDetalle();
						
					this.view.getTableDetalle().changeSelection(row+1,0, toggle, extend);
					this.view.getTableDetalle().requestFocus();
					
					
					/*calcularTotales();
					//se agrega otra fila en la tabla
					this.view.getModeloTabla().agregarDetalle();
					boolean toggle = false;
					boolean extend = false;
					this.view.getTableDetalle().requestFocus();
					//se agrega la nueva fila de la tabla
					//this.view.getModelo().agregarDetalle();
						
					this.view.getTableDetalle().changeSelection(row+1,0, toggle, extend);*/
				}
				//JOptionPane.showMessageDialog(view, "se edito el total columna :"+colum);
				//view.getTxtBuscar().requestFocusInWindow();
			break;
		}
		
	}
	
	public boolean esValido(Character caracter)
    {
        char c = caracter.charValue();
        if ( !(Character.isLetter(c) //si es letra
                || c == ' ' //o un espacio
                || c == 8 //o backspace
                || (Character.isDigit(c))
            ))
            return false;
        else
            return true;
    }
	
	
public void calcularTotales(){
	
	//se establecen los totales en cero
	this.myFactura.resetTotales();
	
	for(int x=0; x<this.view.getModeloTabla().getDetalles().size();x++){
		
		DetalleFactura detalle=this.view.getModeloTabla().getDetalle(x);
		
		
		if(detalle.getArticulo().getId()!=-1)
			if(detalle.getCantidad().doubleValue()!=0 && detalle.getArticulo().getPrecioVenta()!=0){
				
				
				
				//se obtien la cantidad y el precio de compra por unidad
				BigDecimal cantidad=detalle.getCantidad();
				BigDecimal precioVenta= new BigDecimal(detalle.getArticulo().getPrecioVenta());
				
				//se calcula el total del item
				BigDecimal totalItem=detalle.getTotal();//cantidad.multiply(precioVenta);
				
				int desc=detalle.getDescuento();
			
				if(desc==1)
				{
					BigDecimal des=totalItem.multiply(new BigDecimal(0.05));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);
					
				}else if(desc==2){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.10));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==3){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.15));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==4){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.20));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==5){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.25));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==6){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.30));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==7){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.35));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==8){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.40));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==9){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.45));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==10){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.50));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}
				
				
				
				//se obtiene el impuesto del articulo 
				BigDecimal porcentaImpuesto =new BigDecimal(detalle.getArticulo().getImpuestoObj().getPorcentaje());
				BigDecimal porImpuesto=new BigDecimal(0);
				porImpuesto=porcentaImpuesto.divide(new BigDecimal(100));
				porImpuesto=porImpuesto.add(new BigDecimal(1));
						//new BigDecimal(((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1);
				
				
				
				//se calcula el total sin  el impuesto;
				BigDecimal totalsiniva= new BigDecimal("0.0");
				totalsiniva=totalItem.divide(porImpuesto,2,BigDecimal.ROUND_HALF_EVEN);//.divide(porImpuesto);// (totalItem)/(porcentaImpuesto);
			
				
				//se calcula el total de impuesto del item
				BigDecimal impuestoItem=totalItem.subtract(totalsiniva);//-totalsiniva;
				
				
				
				//se estable el total y impuesto en el modelo
				myFactura.setTotal(totalItem);
				if(porcentaImpuesto.intValue()==15){
					myFactura.setTotalImpuesto(impuestoItem);
				}
				if(porcentaImpuesto.intValue()==18){
					myFactura.setTotalImpuesto18(impuestoItem);
				}
				myFactura.setSubTotal(totalsiniva);
				//myFactura.getDetalles().add(detalle);
				myFactura.setTotalDescuento(detalle.getDescuentoItem());
				
				detalle.setSubTotal(totalsiniva.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				detalle.setImpuesto(impuestoItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				//myFactura.getDetalles()
				
				//se establece en la y el impuesto en el item de la vista
				//detalle.setImpuesto(impuesto2.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				detalle.setTotal(totalItem.setScale(0, BigDecimal.ROUND_HALF_EVEN));
				
				//se establece el total e impuesto en el vista
				this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtImpuesto18().setText(""+myFactura.getTotalImpuesto18().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtDescuento().setText(""+myFactura.getTotalDescuento().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				
				view.getModeloTabla().fireTableDataChanged();
				this.selectRowInset();
				
				
			
				
				//this.view.getModelo().fireTableDataChanged();
			}//fin del if
		
	}//fin del for
	}
	
public void calcularTotal(DetalleFactura detalle){
		
		if(detalle.getCantidad().doubleValue()!=0 && detalle.getArticulo().getPrecioVenta()!=0){
			
			//se obtien la cantidad y el precio de compra por unidad
			BigDecimal cantidad=detalle.getCantidad();
			BigDecimal precioVenta= new BigDecimal(detalle.getArticulo().getPrecioVenta());
			
			
			
			//se obtiene el impuesto del articulo 
			BigDecimal porcentaImpuesto =new BigDecimal(detalle.getArticulo().getImpuestoObj().getPorcentaje());
			BigDecimal porImpuesto=new BigDecimal(0);
			porImpuesto=porcentaImpuesto.divide(new BigDecimal(100));
			porImpuesto=porImpuesto.add(new BigDecimal(1));
					//new BigDecimal(((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1);
			
			//se calcula el total del item
			BigDecimal totalItem=cantidad.multiply(precioVenta);
			
			//se calcula el total sin  el impuesto;
			BigDecimal totalsiniva= new BigDecimal("0.0");
			totalsiniva=totalItem.divide(porImpuesto,2,BigDecimal.ROUND_HALF_EVEN);//.divide(porImpuesto);// (totalItem)/(porcentaImpuesto);
		
			
			//se calcula el total de impuesto del item
			BigDecimal impuestoItem=totalItem.subtract(totalsiniva);//-totalsiniva;
			
			
			
			//se estable el total y impuesto en el modelo
			myFactura.setTotal(totalItem);
			myFactura.setTotalImpuesto(impuestoItem);
			myFactura.setSubTotal(totalsiniva);
			//myFactura.getDetalles().add(detalle);
			
			detalle.setSubTotal(totalsiniva.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			detalle.setImpuesto(impuestoItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			//myFactura.getDetalles()
			
			//se establece en la y el impuesto en el item de la vista
			//detalle.setImpuesto(impuesto2.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			detalle.setTotal(totalItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			
			//se establece el total e impuesto en el vista
			this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			
			
			
			
		
			
			//this.view.getModelo().fireTableDataChanged();
		}
	}

	

	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		/*if(e.getComponent()==this.view.getTxtBuscar()){
			//JOptionPane.showMessageDialog(view, "2");
			JOptionPane.showMessageDialog(view, view.getTxtBuscar().getText());
			this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText());
			
			JOptionPane.showMessageDialog(view, myArticulo);
			if(myArticulo!=null){
				view.getTxtArticulo().setText(myArticulo.getArticulo());
				view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
				
			}
		}*/
		
		
		//Recoger qu� fila se ha pulsadao en la tabla
		filaPulsada = this.view.getTableDetalle().getSelectedRow();
		
		//filaPulsada = this.view.getTableDetalle().getSelectedRow();
		int columnaSelec=view.getTableDetalle().getSelectedColumn();
		
		
		if(e.getComponent()==this.view.getTableDetalle()){
			
			
			if(e.getKeyCode()==KeyEvent.VK_ENTER && !view.getTableDetalle().isEditing()){
				
				
				
						
				if(view.getTableDetalle().getSelectedColumn()==7){
					
					//JOptionPane.showMessageDialog(view, "fila: "+filaPulsada+" , columna: "+columnaSelec+" , No filas:"+view.getModeloTabla().getRowCount());
					//JOptionPane.showMessageDialog(view,"Enter en la tabla");
					
					if(view.getModeloTabla().getDetalle(filaPulsada).getTotal().doubleValue()>0){
						this.view.getModeloTabla().agregarDetalle();
						
						//JOptionPane.showMessageDialog(view, "fila: "+filaPulsada+" , columna: "+columnaSelec+" , No filas:"+view.getModeloTabla().getRowCount());
						
						boolean toggle = false;
						boolean extend = false;
						
							
						this.view.getTableDetalle().changeSelection(filaPulsada,0, toggle, extend);
						this.view.getTableDetalle().requestFocus();
					}else{
						view.getModeloTabla().eliminarDetalle(filaPulsada);
						this.view.getModeloTabla().agregarDetalle();
						
						boolean toggle = false;
						boolean extend = false;
						
							
						this.view.getTableDetalle().changeSelection(view.getModeloTabla().getRowCount()-1,0, toggle, extend);
						this.view.getTableDetalle().requestFocus();
					}
				}
				
			}
		}
		
		
		if(e.getKeyCode()==KeyEvent.VK_F1){
			buscarArticulo();
		}else
			if(e.getKeyCode()==KeyEvent.VK_F2){
				cobrar();
			}else
				if(e.getKeyCode()==KeyEvent.VK_F3){
					buscarCliente();
				}else
					if(e.getKeyCode()==KeyEvent.VK_F4){
						guardar();
					}else
						if(e.getKeyCode()==KeyEvent.VK_F5){
							view.getRdbtnContado().setSelected(true);
							this.setContado();
						}else
						if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
							//salir();
						}else
							if(e.getKeyCode()==KeyEvent.VK_DELETE){
								 
								 if(filaPulsada>=0){
									 this.view.getModeloTabla().eliminarDetalle(filaPulsada);
									 this.calcularTotales();
									//se agrega la nueva fila de la tabla
									this.view.getModeloTabla().agregarDetalle();
								 }
							}else
								if(e.getKeyCode()==KeyEvent.VK_F6){
									view.getRdbtnCredito().setSelected(true);
									this.setCredito();
								}else
									if(e.getKeyCode()==KeyEvent.VK_F7){
										actualizar();
									}else
										if(e.getKeyCode()==KeyEvent.VK_DOWN){
											
											this.netBuscar++;
											this.buscarMasOmenos(netBuscar);
										}else
											if(e.getKeyCode()==KeyEvent.VK_UP){
												if(netBuscar>=1){
													this.netBuscar--;
													this.buscarMasOmenos(netBuscar);
												}
													
											}else
												if(e.getKeyCode()==KeyEvent.VK_LEFT){
													if(filaPulsada>=0){
														 this.view.getModeloTabla().getDetalle(filaPulsada).getArticulo().netPrecio();
														 this.calcularTotales();
													 }
												}else
													if(e.getKeyCode()==KeyEvent.VK_RIGHT){
														if(filaPulsada>=0){
															 this.view.getModeloTabla().getDetalle(filaPulsada).getArticulo().lastPrecio();
															 this.calcularTotales();
														 }
													}
								 
							
								
	}
	private void showPendientes() {
		// TODO Auto-generated method stub
		ViewListaFactura vistaFacturars=new ViewListaFactura(this.view);
		CtlFacturaLista ctlFacturas=new CtlFacturaLista(vistaFacturars,conexion );
		vistaFacturars.dispose();
		ctlFacturas=null;
	}
	private void cierreCaja() {
		// TODO Auto-generated method stub
		CierreCajaDao cierre=new CierreCajaDao(conexion);
		
		if(cierre.registrarCierre())
		{
			try {
				//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
				AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 4, cierre.idUltimoRequistro);
				
				AbstractJasperReports.Imprimir2();
				JOptionPane.showMessageDialog(view, "Se realizo el corte correctamente.");
				//AbstractJasperReports.showViewer(view);
				
				//this.view.setModal(false);
				//AbstractJasperReports.imprimierFactura();
				
				
				
				
				/*if(!cierre.registrarCierre()){
					JOptionPane.showMessageDialog(view, "No se guardo el cierre de corte. Vuelva a hacer el corte.");
				}else{
					AbstractJasperReports.Imprimir2();
					JOptionPane.showMessageDialog(view, "Se realizo el corte correctamente.");
				}*/
				
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(view, "No se guardo el cierre de corte. Vuelva a hacer el corte.");
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
		filaPulsada = this.view.getTableDetalle().getSelectedRow();
		
		char caracter = e.getKeyChar();
		
		
		
		
		if(e.getComponent()==this.view.getTxtNombrecliente()&& e.getKeyCode()!=KeyEvent.VK_F5 && e.getKeyCode()!=KeyEvent.VK_F6){
			view.getTxtIdcliente().setText("-1");
			view.getRdbtnContado().setSelected(true);
			view.getTxtRtn().setEditable(true);
			
		}
		
		//para quitar los simnos mas o numero que ingrese en la busqueda
		if(e.getComponent()==this.view.getTxtBuscar()){
			Character caracter1 = new Character(e.getKeyChar());
	        if (!esValido(caracter1))
	        {
	           String texto = "";
	           for (int i = 0; i < view.getTxtBuscar().getText().length(); i++)
	                if (esValido(new Character(view.getTxtBuscar().getText().charAt(i))))
	                    texto += view.getTxtBuscar().getText().charAt(i);
	           			view.getTxtBuscar().setText(texto);
	                //view.getToolkit().beep();
	        }
		}
		
		//que no se la fecha de arriba y abajo
		if(e.getKeyCode()!=KeyEvent.VK_DOWN && e.getKeyCode()!= KeyEvent.VK_UP && e.getKeyCode()!= KeyEvent.VK_ENTER)
		
		//se comprueba que hay algo que buscar
		if(e.getComponent()==this.view.getTxtBuscar()&&view.getTxtBuscar().getText().trim().length()!=0){
			//JOptionPane.showMessageDialog(view, "2");
			//JOptionPane.showMessageDialog(view, view.getTxtBuscar().getText());
			this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText());
			
			//JOptionPane.showMessageDialog(view, myArticulo);
			if(myArticulo!=null){
				view.getTxtArticulo().setText(myArticulo.getArticulo());
				view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
				netBuscar=0;
				netBuscar++;
				
			}
			else{
				myArticulo=null;
				view.getTxtArticulo().setText("");
				view.getTxtPrecio().setText("");
			}
		}
		else{
			myArticulo=null;
			view.getTxtArticulo().setText("");
			view.getTxtPrecio().setText("");
		}
		
		
		if(caracter=='+'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModeloTabla().masCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		if(caracter=='-'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModeloTabla().restarCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		
		
	}
		
	private void buscarMasOmenos(int p){
		//se comprueba que hay algo que buscar
				if(view.getTxtBuscar().getText().trim().length()!=0){
					//JOptionPane.showMessageDialog(view, "2");
					//JOptionPane.showMessageDialog(view, view.getTxtBuscar().getText());
					this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText(),p);
					
					//JOptionPane.showMessageDialog(view, myArticulo);
					if(myArticulo!=null){
						view.getTxtArticulo().setText(myArticulo.getArticulo());
						view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
						
					}
					else{
						myArticulo=null;
						view.getTxtArticulo().setText("");
						view.getTxtPrecio().setText("");
					}
				}
				else{
					myArticulo=null;
					view.getTxtArticulo().setText("");
					view.getTxtPrecio().setText("");
				}
	}
	
	private void salir(){
		//facturaDao.desconectarBD();
		//this.clienteDao.desconectarBD();
		//this.myArticuloDao.desconectarBD();
		//this.myFactura.setIdFactura(-1);
		this.view.setVisible(false);
		
		
	}
	private void guardar(){
		
		setFactura();
		facturaDao.registrarFacturaTemp(myFactura);
		myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
		resultado=true;
		//this.view.setVisible(false);
		setEmptyView();
		
	}
	private void actualizar() {
		// TODO Auto-generated method stub
		setFactura();
		facturaDao.actualizarFacturaTemp(myFactura);
		this.view.setVisible(false);
		
	}
	private void cobrar(){
		
		
		//verificamos que se agregaron articulos a la factura
		if(view.getModeloTabla().getRowCount()>1){
			
			ViewCargarVenderor viewVendedor=new ViewCargarVenderor(view);
			CtlCargarVendedor ctlVendedor=new CtlCargarVendedor(viewVendedor,conexion);
			
			boolean resulVendedor=ctlVendedor.cargarVendedor();
			
			if(resulVendedor)//verifica si ingreso el codigo del bombero
			{
				//se estable el vendedor en la myFactura
				myFactura.setVendedor(ctlVendedor.getVendetor());
				
				//verifica que la factura sea al contado
				if(!view.getRdbtnCredito().isSelected()){
					
					
			
					//se muestra la vista para cobrar y introducir el cambio
					ViewCambioPago viewPago=new ViewCambioPago(this.view);
					CtlCambioPago ctlPago=new CtlCambioPago(viewPago,myFactura.getTotal());
					//se muestra y ventana del cobro y se devuelve un resultado del cobro
					boolean resulPago=ctlPago.pagar();
					
					//se procede a verificar si se cobro
					if(resulPago)
					{
						//si la forma de pago fue en efectivo
						if(ctlPago.getFormaPago()==1){
							myFactura.setPago(ctlPago.getEfectivo());
							myFactura.setCambio(ctlPago.getCambio());
							myFactura.setTipoPago(1);
						}
						//si la forma de pago fue con tarjeta de credito o debito
						if(ctlPago.getFormaPago()==2){
							myFactura.setPago(myFactura.getTotal());
							myFactura.setCambio(new BigDecimal(00));
							myFactura.setTipoPago(2);
							myFactura.setObservacion(ctlPago.getRefencia());
						}
						//se completa los datos para la factura
						setFactura();
						
						//se registra la factura
						boolean resul=facturaDao.registrarFactura(myFactura);
							
						if(resul){
							myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
							
								try {
									/*this.view.setVisible(false);
									this.view.dispose();*/
									//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul.jasper",myFactura.getIdFactura() );
									AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 1, myFactura.getIdFactura());
									//AbstractJasperReports.showViewer(view);
									AbstractJasperReports.imprimierFactura();
									
									//myFactura=null;
									setEmptyView();
									
									//si la view es de actualizacion al cobrar se cierra la view
									if(this.tipoView==2){
										myFactura=null;
										view.setVisible(false);
									}
									//myFactura.
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							
						}else{
							JOptionPane.showMessageDialog(view, "No se guardo la factura", "Error Base de Datos", JOptionPane.ERROR_MESSAGE);
							this.view.setVisible(false);
							this.view.dispose();
						}//fin el if donde se guarda la factura
						
					}//fin de la ventana en cobro
				
				}else{//si la factura es al credito se procede a guardar e imprimir 
					setFactura();
					myFactura.setTipoPago(3);
					
					BigDecimal saldo=this.myCliente.getSaldoCuenta();
					BigDecimal limite=this.myCliente.getLimiteCredito();
					BigDecimal nuevoSaldo=saldo.add(this.myFactura.getTotal());
					
					if(nuevoSaldo.doubleValue()>limite.doubleValue()){
						JOptionPane.showMessageDialog(view, "El Cliente no tiene suficiente credito.");
					}else{
						//no se necesita el cambio porque es al credito
						myFactura.setCambio(new BigDecimal(0));
						//se registra la factura al credito
						boolean resul=facturaDao.registrarFactura(myFactura);
						
						//si se facturo bien se muestra la factura a imprimir
						if(resul){
							myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
							
								try {
									/*this.view.setVisible(false);
									this.view.dispose();*/
									//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul.jasper",myFactura.getIdFactura() );
									AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 6, myFactura.getIdFactura());
									AbstractJasperReports.imprimirA2();
									//AbstractJasperReports.showViewer(view);
									
									//myFactura=null;
									setEmptyView();
									
									//si la view es de actualizacion al cobrar se cierra la view
									if(this.tipoView==2){
										myFactura=null;
										view.setVisible(false);
									}
									//myFactura.
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							
						}else{
							JOptionPane.showMessageDialog(view, "No se guardo la factura", "Error Base de Datos", JOptionPane.ERROR_MESSAGE);
							this.view.setVisible(false);
							this.view.dispose();
						}//fin el if donde se guarda la factura
					}//fin del if donde se comprueba el limite de credito
				}//fin del else para comprobar si es factura al contado
					
						
			}//sin del if donde se pide el codigo del vendedor
			
			
		}
		else{//fin del if donde se verifica que hay articulos que facturar
				JOptionPane.showMessageDialog(view, "Para cobrar debe agregar articulos primero.");
			}
	}
	private void buscarArticulo(){
	
		//se llama el metodo que mostrar la ventana para buscar el articulo
		ViewListaArticulo viewListaArticulo=new ViewListaArticulo(view);
		CtlArticuloBuscar ctlArticulo=new CtlArticuloBuscar(viewListaArticulo,conexion);
		
		viewListaArticulo.pack();
		ctlArticulo.view.getTxtBuscar().setText("");
		ctlArticulo.view.getTxtBuscar().selectAll();
		//ctlArticulo.view.getTxtBuscar().requestFocus(true);
		//ctlArticulo.view.getTxtBuscar().selectAll();
		view.getTxtBuscar().requestFocusInWindow();
		viewListaArticulo.conectarControladorBuscar(ctlArticulo);
		Articulo myArticulo1=ctlArticulo.buscarArticulo(view);
		
		//JOptionPane.showMessageDialog(view, myArticulo1);
		//se comprueba si le regreso un articulo valido
		if(myArticulo1!=null && myArticulo1.getId()!=-1){
			
			myArticulo1.setPreciosVenta(this.preciosDao.getPreciosArticulo(myArticulo1.getId()));
			this.view.getModeloTabla().setArticulo(myArticulo1);
			//this.view.getModelo().getDetalle(row).setCantidad(1);
			
			//calcularTotal(this.view.getModeloTabla().getDetalle(row));
			calcularTotales();
			this.view.getModeloTabla().agregarDetalle();
			
			selectRowInset();
		}
		
		myArticulo=null;
		viewListaArticulo.dispose();
		ctlArticulo=null;
		
	}
	private void setEmptyView(){
		//se estable la tabla de detalles vacia
		view.getModeloTabla().setEmptyDetalles();
		
		//se agrega una fila vacia a la tabla detalle
		view.getModeloTabla().agregarDetalle();
		
		
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		
		//se estable un cliente generico para la factura
		this.view.getTxtIdcliente().setText("2001");;
		this.view.getTxtNombrecliente().setText("Consumidor final");
		view.getRdbtnContado().setSelected(true);
		view.getTxtRtn().setEditable(true);
		view.getTxtRtn().setText("");
		//view.getPanelDetalleVehiculo().setVisible(false);
		
		this.myCliente=null;
		
		this.view.getTxtArticulo().setText("");
		this.view.getTxtBuscar().setText("");
		this.view.getTxtDescuento().setText("");
		this.view.getTxtKilometraje().setText("");
		this.view.getTxtModelo().setText("");
		this.view.getTxtNoplaca().setText("");
		this.view.getTxtImpuesto().setText("0.00");
		this.view.getTxtImpuesto18().setText("0.00");
		this.view.getTxtPrecio().setText("0.00");
		this.view.getTxtSubtotal().setText("0.00");
		this.view.getTxtTotal().setText("0.00");
		this.myFactura.setObservacion("");
		this.myFactura.setKilometrajeVehiculo("");
		this.myFactura.setModeloVehiculo("");
		this.myFactura.setPlacaVehiculo("");
		
		
		
		//se estable el focus de la view en la caja de texto buscar
		view.getTxtNombrecliente().requestFocusInWindow();
		
	}
	private void buscarCliente(){
		//se crea la vista para buscar los cliente
		ViewListaClientes viewListaCliente=new ViewListaClientes (this.view);
		
		CtlClienteBuscar ctlBuscarCliente=new CtlClienteBuscar(viewListaCliente,conexion);
		
		boolean resulCliente=ctlBuscarCliente.buscarCliente(view);
		//se comprueba si le regreso un articulo valido
		if(resulCliente){
			myCliente=ctlBuscarCliente.getCliente();
			this.view.getTxtIdcliente().setText(""+myCliente.getId());;
			this.view.getTxtNombrecliente().setText(myCliente.getNombre());
			view.getTxtRtn().setText(myCliente.getRtn());
		
		}else{
			JOptionPane.showMessageDialog(view, "No se encontro el cliente");
			this.view.getTxtIdcliente().setText("2001");;
			this.view.getTxtNombrecliente().setText("Consumidor final");
		}
		viewListaCliente.dispose();
		ctlBuscarCliente=null;
	}

	
	
	private void selectRowInset(){
		
		int row = this.view.getTableDetalle().getRowCount () - 2;
	    int col = 1;
	    boolean toggle = false;
	    boolean extend = false;
	    this.view.getTableDetalle().changeSelection(row, 0, toggle, extend);
	    this.view.getTableDetalle().changeSelection(row, col, toggle, extend);
	    this.view.getTableDetalle().addColumnSelectionInterval(0, 7);
		
		/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
		/*int row =  this.view.geTableDetalle().getRowCount () - 2;
		JOptionPane.showMessageDialog(view, row);
		/Rectangle rect = this.view.geTableDetalle().getCellRect(row, 0, true);
		this.view.geTableDetalle().scrollRectToVisible(rect);
		this.view.geTableDetalle().clearSelection();*/
		//this.view.geTableDetalle().setRowSelectionInterval(row, row);
		//view.geTableDetalle().setRowSelectionInterval(row, row);
		//view.geTableDetalle().clearSelection();
		//view.geTableDetalle().addRowSelectionInterval(row,row);
		//TablaModeloFactura modelo = (TablaModeloFactura)this.view.geTableDetalle().getModel();
		//modelo.fireTableDataChanged();
		//this.view.getModeloTabla().fireTableDataChanged();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//facturaDao.desconectarBD();
		//this.clienteDao.desconectarBD();
		//this.myArticuloDao.desconectarBD();
		//this.myFactura.setIdFactura(-1);
		this.view.setVisible(false);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void cargarFacturaView(){
		
		this.view.getTxtIdcliente().setText(""+myFactura.getCliente().getId());;
		this.view.getTxtNombrecliente().setText(myFactura.getCliente().getNombre());
		
		//se establece el total e impuesto en el vista
		this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		
		this.view.getModeloTabla().setDetalles(myFactura.getDetalles());
	}


	public Factura actualizarFactura(Factura f) {
		// TODO Auto-generated method stub
		this.myFactura=f;
		cargarFacturaView();
		this.view.getBtnGuardar().setVisible(false);
		this.view.getBtnActualizar().setVisible(true);
		this.view.getModeloTabla().agregarDetalle();
		tipoView=2;
		this.view.setVisible(true);
		
		//para controlar que es una actualizacion la que se hace
		
		
		return myFactura;
		
	}


	public boolean getAccion() {
		view.setVisible(true);
		return resultado;
	}


	public void viewFactura(Factura f) {
		// TODO Auto-generated method stub
		this.myFactura=f;
		cargarFacturaView();
		this.view.getPanelAcciones().setVisible(false);
		this.view.setVisible(true);
	}


	public Factura getFactura() {
		// TODO Auto-generated method stub
		return this.myFactura;
	}

}
