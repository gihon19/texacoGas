package view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import controlador.CtlUsuario;
import view.botones.BotonCancelar;
import view.botones.BotonGuardar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewCrearUsuario extends JDialog {
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JPasswordField pwdPassword;
	private JComboBox cbxTipoUsuario;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JLabel lblConfirmeContrasea;
	private JPasswordField pwdConfirmepwd;

	public ViewCrearUsuario(Window view) {
		
		super(view,"Crear usuarios",Dialog.ModalityType.DOCUMENT_MODAL);
		
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre Completo");
		lblNombre.setBounds(10, 7, 181, 14);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(10, 28, 308, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 55, 308, 14);
		getContentPane().add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(10, 76, 308, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContasea = new JLabel("Contase\u00F1a");
		lblContasea.setBounds(10, 103, 141, 14);
		getContentPane().add(lblContasea);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(10, 124, 306, 20);
		getContentPane().add(pwdPassword);
		
		JLabel lblTipoUsuario = new JLabel("Tipo Usuario");
		lblTipoUsuario.setBounds(10, 199, 189, 14);
		getContentPane().add(lblTipoUsuario);
		
		cbxTipoUsuario = new JComboBox();
		cbxTipoUsuario.setModel(new DefaultComboBoxModel(new String[] {"Operador", "Administrador"}));
		cbxTipoUsuario.setBounds(10, 220, 308, 20);
		getContentPane().add(cbxTipoUsuario);
		
		btnGuardar = new BotonGuardar();
		btnGuardar.setText("Guardar");
		btnGuardar.setLocation(22, 273);
		//btnGuardar.setBounds(10, 228, 89, 23);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new BotonCancelar();
		btnCancelar.setText("Cancelar");
		btnCancelar.setLocation(172, 273);
		//btnCancelar.setBounds(177, 228, 89, 23);
		getContentPane().add(btnCancelar);
		
		lblConfirmeContrasea = new JLabel("Confirme Contrase\u00F1a");
		lblConfirmeContrasea.setBounds(10, 151, 181, 14);
		getContentPane().add(lblConfirmeContrasea);
		
		pwdConfirmepwd = new JPasswordField();
		pwdConfirmepwd.setBounds(10, 172, 308, 20);
		getContentPane().add(pwdConfirmepwd);
		// TODO Auto-generated constructor stub
		
		this.setSize(340, 363);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		Dimension dime=new Dimension(340, 363);
		this.setPreferredSize(dime);
		this.pack();
	}
	
	public JTextField getTxtNombre(){
		return txtNombre;
	}
	public JTextField getTxtUsuario(){
		return txtUsuario;
	}
	public JPasswordField getPwdPassword(){
		return pwdPassword;
	}
	public JPasswordField getPwdConfirmepwd(){
		return pwdConfirmepwd;
	}
	
	public JComboBox getCbxTipoUsuario(){
		return cbxTipoUsuario;
	}
	
	public void contectarCtl(CtlUsuario c){
		btnGuardar.addActionListener(c);
		btnGuardar.setActionCommand("GUARDAR");
		
		btnCancelar.addActionListener(c);
		btnCancelar.setActionCommand("CANCELAR");
		
		this.addWindowListener(c);
	}
}
