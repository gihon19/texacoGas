package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Usuario;
import modelo.dao.UsuarioDao;
import view.ViewCrearUsuario;

public class CtlUsuario implements ActionListener, WindowListener {
	
	private Conexion conexion=null;
	private ViewCrearUsuario view=null;
	private Usuario myUsuario=null;
	private UsuarioDao myDao=null;

	public CtlUsuario(ViewCrearUsuario v,Conexion con) {
		// TODO Auto-generated constructor stub
		conexion=con;
		view=v;
		view.contectarCtl(this);
		myUsuario=new Usuario();
		myDao=new UsuarioDao(conexion);
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch(comando){
		case "GUARDAR":
			if(validarDatos()){
				setUsuario();
				if(myDao.registrar(myUsuario)){
					JOptionPane.showMessageDialog(view, "Se guardo el usuario");
					view.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(view, "No guardo el usuario");
				}
			}
			//JOptionPane.showMessageDialog(view, "Mando a guardar");
			break;
		case "CANCELAR":
			view.setVisible(false);
			
			break;
		}
	}

	private void setUsuario() {
		// TODO Auto-generated method stub
		myUsuario.setNombre(view.getTxtNombre().getText());
		myUsuario.setUser(view.getTxtUsuario().getText());
		myUsuario.setPwd(view.getPwdPassword().getText());
		if(view.getCbxTipoUsuario().getSelectedIndex()==0){
			myUsuario.setTipoPermiso(2);
			myUsuario.setPermiso("opereador");
		}
		if(view.getCbxTipoUsuario().getSelectedIndex()==1){
			myUsuario.setTipoPermiso(1);
			myUsuario.setPermiso("administrador");
		}
	}

	private boolean validarDatos() {
		// TODO Auto-generated method stub
		boolean resul=false;
		char [] pwd=view.getPwdPassword().getPassword();
		char [] pwdConfir=view.getPwdConfirmepwd().getPassword();
		
		if(view.getTxtNombre().getText().length()==0){
			JOptionPane.showMessageDialog(view, "Escriba el nombre del usuario");
			view.getTxtNombre().requestFocusInWindow();
		}else
			if(view.getTxtUsuario().getText().length()==0){
				JOptionPane.showMessageDialog(view, "Escriba el usuario");
				view.getTxtUsuario().requestFocusInWindow();
			}else
				if(view.getPwdPassword().getPassword().length==0){
					JOptionPane.showMessageDialog(view, "Escriba la contraseña");
					view.getPwdPassword().requestFocusInWindow();
				}else
					if(view.getPwdConfirmepwd().getPassword().length==0){
						JOptionPane.showMessageDialog(view, "Confirme la contraseña");
						view.getPwdConfirmepwd().requestFocusInWindow();
					}else
						if(!(new String(view.getPwdPassword().getPassword()).equals(new String(view.getPwdConfirmepwd().getPassword()) ))){
							JOptionPane.showMessageDialog(view, "Las contraseña no coinciden");
							view.getPwdPassword().setText("");
							view.getPwdConfirmepwd().setText("");
							view.getPwdPassword().requestFocusInWindow();
						}else{
							resul=true;
						}
		return resul;
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		view.setVisible(true);
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

}
