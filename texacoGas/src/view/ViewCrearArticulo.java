package view;



import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;

import controlador.CtlArticulo;

import java.awt.Font;

import javax.swing.JList;
import javax.swing.DefaultComboBoxModel;

import view.botones.BotonActualizar;
import view.botones.BotonCancelar;
import view.botones.BotonGuardar;
import view.rendes.RenderizadorTablaFactura;
import view.rendes.RoundJTextField;
import view.rendes.RtPrecios;
import view.tablemodel.ComboBoxImpuesto;
import view.tablemodel.ListaModeloCodBarra;
import view.tablemodel.TmPrecios;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;


public class ViewCrearArticulo extends JDialog {
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblMarca;
	private JComboBox cbxImpuesto ;
	private JLabel lblImpuesto;
	private Font myFont;
	private BotonCancelar btnCancelar;
	private BotonGuardar btnGuardar;
	private BotonActualizar btnActualizar;
	
	private JTextField txtMarca;
	private JButton btnBuscar;
	private JList listCodigos;
	private JTextField txtCodigo;
	private JComboBox cbxTipo;
	private TmPrecios modeloPrecio;
	
	
	private JMenuItem mntmEliminar;
	

	
	private JPopupMenu menuContextual; // permite al usuario seleccionar el color
	
	
	
	//se crea el modelo de la lista de los impuestos
	private ComboBoxImpuesto modeloImpuesto;//=new ComboBoxImpuesto();
	
	private ListaModeloCodBarra modeloCodBarra;
	private JTextField txtPrecio;
	private JTable tblPrecios;
	private JScrollPane scrollPane_1;
	private JPanel panel;
	private JPanel panel_1;
	private Color colorBorde;
	private JPanel panel_2;
	private JPanel panel_3,panel_4,panel_5;
	
	

	public ViewCrearArticulo(ViewListaArticulo view) {
		
super(view,"Agregar Articulos",Dialog.ModalityType.DOCUMENT_MODAL);
		
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setResizable(false);
		getContentPane().setLayout(null);
		
		myFont=new Font("Verdana", Font.PLAIN, 12);
		
		colorBorde=Color.DARK_GRAY;
		
		
		menuContextual = new JPopupMenu(); // crea el men� contextual
		
		//opcion del menu flotante
		mntmEliminar = new JMenuItem("Eliminar");
		menuContextual.add(mntmEliminar);
		modeloImpuesto=new ComboBoxImpuesto();
		
		
		//botones
		btnGuardar = new BotonGuardar();
		btnGuardar.setLocation(28, 577);
		//tnCancelar.setLocation(42, 175);
		getContentPane().add(btnGuardar);
		
		btnActualizar=new BotonActualizar();
		btnActualizar.setLocation(10, 577);
		getContentPane().add(btnActualizar);
		btnActualizar.setVisible(false);
		
		btnCancelar = new BotonCancelar();
		btnCancelar.setSize(128, 45);
		//btnCancelar.setBounds(212, 175, 135, 39);
		btnCancelar.setLocation(184, 577);
		getContentPane().add(btnCancelar);
		
		modeloCodBarra=new ListaModeloCodBarra();
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(136, 577, 257, 20);
		txtPrecio.setVisible(false);
		getContentPane().add(txtPrecio);
		txtPrecio.setColumns(10);
		this.modeloPrecio=new TmPrecios();
		
		RtPrecios renderizador = new RtPrecios();
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.WHITE),
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY)
	              ));
		panel.setBounds(0, 0, 341, 69);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		//Nombre Articulo
		lblNombre=new JLabel();
		lblNombre.setBounds(23, 7, 76, 23);
		panel.add(lblNombre);
		lblNombre.setText("Nombre");
		lblNombre.setFont(myFont);
		
		txtNombre=new JTextField(30);
		txtNombre.setBounds(23, 37, 289, 23);
		panel.add(txtNombre);
		txtNombre.setFont(myFont);
		
		panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createCompoundBorder(
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.WHITE),
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY)
	              ));
		panel_1.setBounds(0, 73,341, 69);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(23, 11, 46, 14);
		panel_1.add(lblTipo);
		lblTipo.setFont(myFont);
		
		cbxTipo = new JComboBox();
		cbxTipo.setBounds(23, 36, 289, 20);
		panel_1.add(cbxTipo);
		cbxTipo.setModel(new DefaultComboBoxModel(new String[] {"Bienes", "Servicio"}));
		
		panel_2 = new JPanel();
		panel_2.setBorder(BorderFactory.createCompoundBorder(
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.WHITE),
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY)
	              ));
		panel_2.setBounds(0, 153, 341, 69);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		
		//Marca
		lblMarca = new JLabel("Marca");
		lblMarca.setBounds(23, 7, 89, 23);
		panel_2.add(lblMarca);
		lblMarca.setFont(myFont);
		
		txtMarca = new JTextField();
		txtMarca.setBounds(23, 37, 270, 23);
		panel_2.add(txtMarca);
		txtMarca.setEditable(false);
		txtMarca.setColumns(10);
		
		btnBuscar = new JButton("...");
		btnBuscar.setBounds(292, 37, 18, 23);
		panel_2.add(btnBuscar);
		
		panel_3 = new JPanel();
		panel_3.setBorder(BorderFactory.createCompoundBorder(
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.WHITE),
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY)
	              ));
		panel_3.setBounds(0, 222, 341, 69);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		
		//Impuesto
		lblImpuesto = new JLabel("Impuesto");
		lblImpuesto.setBounds(23, 7, 89, 23);
		panel_3.add(lblImpuesto);
		lblImpuesto.setFont(myFont);
		
		
		
		
		cbxImpuesto = new JComboBox();
		modeloImpuesto=new ComboBoxImpuesto();
		cbxImpuesto.setModel(modeloImpuesto);//para poder mostrar el formulario en modo dise�o comente esta linea
		cbxImpuesto.setBounds(23, 37, 289, 23);
		panel_3.add(cbxImpuesto);
		cbxImpuesto.setFont(myFont);
		
		panel_4 = new JPanel();
		panel_4.setBorder(BorderFactory.createCompoundBorder(
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.WHITE),
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY)
	              ));
		panel_4.setBounds(0, 288, 341, 147);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
				
		JLabel lblCodigoBarra = new JLabel("Codigo Barra");
		lblCodigoBarra.setBounds(23, 9, 89, 14);
		panel_4.add(lblCodigoBarra);
		lblCodigoBarra.setFont(myFont);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(23, 32, 289, 20);
		panel_4.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 61, 289, 76);
		panel_4.add(scrollPane);
		listCodigos = new JList();
		listCodigos.setSize(257, 99);
		listCodigos.setLocation(119, 0);
		listCodigos.setModel(modeloCodBarra);
		listCodigos.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(listCodigos);
		
		panel_5 = new JPanel();
		panel_5.setBorder(BorderFactory.createCompoundBorder(
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.WHITE),
	              BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY)
	              ));
		panel_5.setBounds(0, 434, 341, 132);
		getContentPane().add(panel_5);
		panel_5.setLayout(null);
		//tblPrecios.setBounds(118, 362, 1, 1);
		//getContentPane().add(table);
		
		
		
		tblPrecios = new JTable();
		tblPrecios.setBounds(118, 350, 257, 56);
		getContentPane().add(tblPrecios);
		tblPrecios.setModel(modeloPrecio);
		tblPrecios.setDefaultRenderer(String.class, renderizador);
		
		scrollPane_1 = new JScrollPane(tblPrecios);
		scrollPane_1.setBounds(23, 32, 289, 90);
		panel_5.add(scrollPane_1);
		
		JLabel lblOtrosPrecios = new JLabel("Precios Venta");
		lblOtrosPrecios.setBounds(23, 7, 95, 14);
		panel_5.add(lblOtrosPrecios);
		lblOtrosPrecios.setFont(myFont);
		
		JLabel lblPrecio = new JLabel("Precio Venta");
		lblPrecio.setBounds(24, 591, 89, 14);
		getContentPane().add(lblPrecio);
		lblPrecio.setFont(myFont);
		lblPrecio.setVisible(false);
		
		setSize(347,663);
		
		//centrar la ventana en la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		
		
		
		
		
		
		/*
		super(view,"Agregar Articulos",Dialog.ModalityType.DOCUMENT_MODAL);
		
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setResizable(false);
		getContentPane().setLayout(null);
		
		myFont=new Font("Verdana", Font.PLAIN, 12);
		
		colorBorde=Color.LIGHT_GRAY;
		
		
		menuContextual = new JPopupMenu(); // crea el men� contextual
		
		//opcion del menu flotante
		mntmEliminar = new JMenuItem("Eliminar");
		menuContextual.add(mntmEliminar);
		
		
		//Impuesto
		lblImpuesto = new JLabel("Impuesto");
		lblImpuesto.setBounds(10, 166, 89, 23);
		lblImpuesto.setFont(myFont);
		getContentPane().add(lblImpuesto);
		
		
		
		
		cbxImpuesto = new JComboBox();
		modeloImpuesto=new ComboBoxImpuesto();
		//cbxImpuesto.setModel(modeloImpuesto);//para poder mostrar el formulario en modo dise�o comente esta linea
		cbxImpuesto.setBounds(10, 200, 257, 23);
		cbxImpuesto.setFont(myFont);
		getContentPane().add(cbxImpuesto);
		
		
		//botones
		btnGuardar = new BotonGuardar();
		btnGuardar.setLocation(10, 518);
		//tnCancelar.setLocation(42, 175);
		getContentPane().add(btnGuardar);
		
		btnActualizar=new BotonActualizar();
		btnActualizar.setLocation(10, 518);
		getContentPane().add(btnActualizar);
		btnActualizar.setVisible(false);
		
		btnCancelar = new BotonCancelar();
		btnCancelar.setSize(128, 45);
		//btnCancelar.setBounds(212, 175, 135, 39);
		btnCancelar.setLocation(148, 518);
		getContentPane().add(btnCancelar);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setLayout(new FloawLayout());
		scrollPane.setBounds(10, 290, 257, 76);
		
		getContentPane().add(scrollPane);
		
		modeloCodBarra=new ListaModeloCodBarra();
		listCodigos = new JList();
		listCodigos.setSize(257, 99);
		listCodigos.setLocation(119, 0);
		listCodigos.setModel(modeloCodBarra);
		listCodigos.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(listCodigos);
		
				
		JLabel lblCodigoBarra = new JLabel("Codigo Barra");
		lblCodigoBarra.setFont(myFont);
		lblCodigoBarra.setBounds(10, 234, 89, 14);
		getContentPane().add(lblCodigoBarra);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(10, 259, 257, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio Venta");
		lblPrecio.setFont(myFont);
		lblPrecio.setBounds(10, 377, 89, 14);
		lblPrecio.setVisible(false);
		getContentPane().add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(144, 543, 257, 20);
		txtPrecio.setVisible(false);
		getContentPane().add(txtPrecio);
		txtPrecio.setColumns(10);
		
		JLabel lblOtrosPrecios = new JLabel("Precios Venta");
		lblOtrosPrecios.setFont(myFont);
		lblOtrosPrecios.setBounds(34, 545, 95, 14);
		getContentPane().add(lblOtrosPrecios);
		this.modeloPrecio=new TmPrecios();
		
		RtPrecios renderizador = new RtPrecios();
		//tblPrecios.setBounds(118, 362, 1, 1);
		//getContentPane().add(table);
		
		
		
		tblPrecios = new JTable();
		tblPrecios.setBounds(118, 350, 257, 56);
		getContentPane().add(tblPrecios);
		tblPrecios.setModel(modeloPrecio);
		tblPrecios.setDefaultRenderer(String.class, renderizador);
		
		scrollPane_1 = new JScrollPane(tblPrecios);
		scrollPane_1.setBounds(10, 402, 257, 90);
		getContentPane().add(scrollPane_1);
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, colorBorde));
		panel.setBounds(0, 0, 297, 57);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		//Nombre Articulo
		lblNombre=new JLabel();
		lblNombre.setBounds(10, 3, 76, 23);
		panel.add(lblNombre);
		lblNombre.setText("Nombre");
		lblNombre.setFont(myFont);
		
		txtNombre=new JTextField(30);
		txtNombre.setBounds(10, 29, 257, 23);
		panel.add(txtNombre);
		txtNombre.setFont(myFont);
		
		panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, colorBorde));
		panel_1.setBounds(0, 57,297, 57);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(10, 7, 46, 14);
		panel_1.add(lblTipo);
		lblTipo.setFont(myFont);
		
		cbxTipo = new JComboBox();
		cbxTipo.setBounds(10, 28, 257, 20);
		panel_1.add(cbxTipo);
		cbxTipo.setModel(new DefaultComboBoxModel(new String[] {"Bienes", "Servicio"}));
		
		panel_2 = new JPanel();
		panel_2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, colorBorde));
		panel_2.setBounds(0, 114, 297, 57);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		
		//Marca
		lblMarca = new JLabel("Marca");
		lblMarca.setBounds(10, 3, 89, 23);
		panel_2.add(lblMarca);
		lblMarca.setFont(myFont);
		
		txtMarca = new JTextField();
		txtMarca.setBounds(10, 29, 257, 23);
		panel_2.add(txtMarca);
		txtMarca.setEditable(false);
		txtMarca.setColumns(10);
		
		btnBuscar = new JButton("...");
		btnBuscar.setBounds(248, 29, 18, 23);
		panel_2.add(btnBuscar);
		
		panel_3 = new JPanel();
		panel_3.setBounds(0, 171, 297, 57);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		setSize(468,599);
		
		//centrar la ventana en la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);*/
		
	}
	
	public void conectarCtl(CtlArticulo m){
		
		btnGuardar.addActionListener(m);
		btnGuardar.setActionCommand("GUARDAR");
		
		
		btnBuscar.addActionListener(m);
		btnBuscar.setActionCommand("BUSCAR");
		
		btnActualizar.addActionListener(m);
		btnActualizar.setActionCommand("ACTUALIZAR");

		btnCancelar.addActionListener(m);
		btnCancelar.setActionCommand("CANCELAR");
		
		txtCodigo.addActionListener(m);
		txtCodigo.setActionCommand("NUEVOCODIGO");
		 
		listCodigos.addKeyListener(m);
		listCodigos.addMouseListener(m);
		
		mntmEliminar.addActionListener(m);
		mntmEliminar.setActionCommand("ELIMINARCODIGO");
	}
	public TmPrecios getModeloPrecio(){
		return modeloPrecio;
	}
	public JTable getTablaPrecios(){
		return this.tblPrecios;
	}
	public BotonActualizar getBtnActualizar(){
		return btnActualizar;
	}
	public JButton getBtnGuardar(){
		return btnGuardar;
	}
	public JTextField getTxtMarca(){
		return txtMarca;
	}
	public JTextField getTxtNombre(){
		return txtNombre;
	}
	public JComboBox getCbxImpuesto(){
		return cbxImpuesto;
	}
	
	public JComboBox getCbxTipo(){
		return cbxTipo;
	}
	
	public ComboBoxImpuesto getListaCbxImpuesto(){
		return modeloImpuesto;
	}
	
	public ListaModeloCodBarra getModeloCodBarra(){
		return modeloCodBarra;
	}
	public JTextField getTxtCodigo(){
		return txtCodigo;
	}
	
	public void configActualizar(){
		btnGuardar.setVisible(false);
		btnActualizar.setVisible(true);
		this.setTitle("Actualizar Articulo");
	}
	public JList getListCodigos(){
		return listCodigos;
	}
	public JTextField getTxtPrecio(){
		return txtPrecio;
	}
	public JPopupMenu getMenuContextual(){
		return menuContextual;
		
	}
}
