package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.botones.BotonActualizar;
import view.botones.BotonCancelar;
import view.botones.BotonGuardar;
import controlador.CtlCliente;

public class ViewCrearCliente extends JDialog{
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtMovil;
	private JTextField txtRtn;
	
	private BotonCancelar btnCancelar;
	private BotonActualizar btnActualizar;
	private BotonGuardar btnGuardar;
	private JTextField txtLimitecredito;
	
	public ViewCrearCliente() {
		setTitle("Crear Cliente");
		
		this.setSize(451,359);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(21, 11, 60, 14);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(116, 8, 286, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(21, 51, 64, 14);
		getContentPane().add(lblDireccion);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(116, 48, 286, 20);
		getContentPane().add(txtDireccion);
		txtDireccion.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(21, 94, 60, 14);
		getContentPane().add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(116, 91, 286, 20);
		getContentPane().add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblMovil = new JLabel("Movil:");
		lblMovil.setBounds(21, 131, 64, 14);
		getContentPane().add(lblMovil);
		
		txtMovil = new JTextField();
		txtMovil.setBounds(116, 128, 286, 20);
		getContentPane().add(txtMovil);
		txtMovil.setColumns(10);
		
		JLabel lblRtn = new JLabel("RTN:");
		lblRtn.setBounds(21, 175, 60, 14);
		getContentPane().add(lblRtn);
		
		txtRtn = new JTextField();
		txtRtn.setBounds(116, 172, 286, 20);
		getContentPane().add(txtRtn);
		txtRtn.setColumns(10);
		
		// botones de accion
		btnCancelar = new BotonCancelar();
		btnCancelar.setLocation(280, 264);
		getContentPane().add(btnCancelar);
		
		btnGuardar = new BotonGuardar();	
		btnGuardar.setLocation(52, 264);
		getContentPane().add(btnGuardar);
		
		btnActualizar=new BotonActualizar();
		btnActualizar.setLocation(52, 264);
		btnActualizar.setVisible(false);
		getContentPane().add(btnActualizar);
		
		JLabel lblLimiteCredito = new JLabel("Limite Credito:");
		lblLimiteCredito.setBounds(21, 208, 84, 14);
		getContentPane().add(lblLimiteCredito);
		
		txtLimitecredito = new JTextField();
		txtLimitecredito.setEnabled(false);
		txtLimitecredito.setBounds(116, 205, 286, 20);
		getContentPane().add(txtLimitecredito);
		txtLimitecredito.setColumns(10);
		
		
	}
	public JTextField getTxtLimitecredito(){
		return txtLimitecredito;
	}
	public JTextField getTxtNombre(){
		return txtNombre;
	}
	public  JTextField getTxtDireccion(){
		return  txtDireccion;
	}
	public JTextField getTxtTelefono(){
		return txtTelefono;
	}
	public JTextField getTxtMovil(){
		return txtMovil;
	}
	public JTextField getTxtRtn(){
		return txtRtn;
	}
	public BotonActualizar getBtnActualizar(){
		return btnActualizar;
	}
	public BotonGuardar getBtnGuardar(){
		return btnGuardar;
	}
	public void conectarControlador(CtlCliente c){
		
		btnCancelar.addActionListener(c);
		btnCancelar.setActionCommand("CANCELAR");
		
		btnGuardar.addActionListener(c);
		btnGuardar.setActionCommand("GUARDAR");
		
		btnActualizar.addActionListener(c);
		btnActualizar.setActionCommand("ACTUALIZAR");
	}
	public void configActualizar() {
		// TODO Auto-generated method stub
		this.btnActualizar.setVisible(true);
		this.btnGuardar.setVisible(false);
		
	}
}
