package Procesos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Clases.clientesDAO;
import Clases.funciones_productos;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cobros {
	cliente cli1 = new cliente();
	clientesDAO cDAO = new clientesDAO();

	
	JPanel filtro = new JPanel();
	JPanel ventas = new JPanel();

	String[] resultados = new String[50];
	JComboBox combo_filtro = new JComboBox(resultados);
	Object[] ob = new Object[5];

	JTextField txtNombre = new JTextField();
	JTextField txtCorreo = new JTextField();
	JTextField txtNit = new JTextField();
	JTextField txtGenero = new JTextField();

	JTable tabla_clientes;
	JScrollPane sp_clientes;

	Object[][] agregar_productos = new Object[10][5];
	int aumento = 0;
	double total;


	private void crear() {
		filtro.setLayout(null);
		ventas.setLayout(null);
		filtro.setBackground(Color.white);
		ventas.setBackground(Color.white);
		filtro.setBounds(20, 30, 830, 250);
		ventas.setBounds(20, 300, 830, 280);
	}

	private void botones() {
		JLabel filtros = new JLabel("Filtrar por: ");
		filtros.setLocation(50, 30);
		filtros.setSize(100, 15);
		filtros.setFont(new Font("Serig", Font.PLAIN, 15));
		// Subrayado
		Font font = filtros.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		filtros.setFont(font.deriveFont(attributes));

		filtro.add(filtros);

		JLabel nombre = new JLabel("Nombre: ");
		nombre.setBounds(180, 30, 100, 15);
		filtro.add(nombre);

		JLabel correo = new JLabel("Correo: ");
		correo.setBounds(180, 90, 100, 15);
		filtro.add(correo);

		JLabel nit = new JLabel("Nit: ");
		nit.setBounds(525, 30, 100, 15);
		filtro.add(nit);

		JLabel genero = new JLabel("Genero: ");
		genero.setBounds(525, 90, 100, 15);
		filtro.add(genero);

		txtNombre.setBounds(280, 25, 200, 25);
		filtro.add(txtNombre);

		txtCorreo.setBounds(280, 85, 200, 25);
		filtro.add(txtCorreo);

		txtNit.setBounds(580, 25, 200, 25);
		filtro.add(txtNit);

		txtGenero.setBounds(580, 85, 200, 25);
		filtro.add(txtGenero);

		JButton btnFiltro = new JButton("Aplicar Filtro");
		btnFiltro.setBounds(180, 150, 600, 30);
		filtro.add(btnFiltro);

		ActionListener funcion_filtro = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					aplicar_filtro();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		btnFiltro.addActionListener(funcion_filtro);

		JLabel l2 = new JLabel("Filtrados: ");
		l2.setLocation(50, 200);
		l2.setSize(100, 15);
		l2.setFont(new Font("Serig", Font.PLAIN, 15));
		// Subrayado
		Font font1 = l2.getFont();
		Map<TextAttribute, Object> attributes1 = new HashMap<>(font1.getAttributes());
		attributes1.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		l2.setFont(font.deriveFont(attributes1));
		filtro.add(l2);

		JLabel cliente = new JLabel("Cliente");
		cliente.setBounds(190, 200, 100, 15);
		filtro.add(cliente);

		combo_filtro.setBounds(250, 200, 250, 20);
		filtro.add(combo_filtro);

		JButton nuevo = new JButton("Nuevo Cliente");
		nuevo.setBounds(600, 200, 150, 30);
		filtro.add(nuevo);

		// Funcion crear
		ActionListener funcion_crear = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					crear_cliente();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};
		nuevo.addActionListener(funcion_crear);
	}

	private void crear_cliente() throws ClassNotFoundException {

		JFrame crear = new JFrame();
		JPanel p1 = new JPanel();
		p1.setLayout(null);

		// etiquetas
		JLabel lblCodigo = new JLabel();
		JLabel lblNombre = new JLabel();
		JLabel lblNit = new JLabel();
		JLabel lblCorreo = new JLabel();
		JLabel lblGenero = new JLabel();

		// cajas de texto
		JTextField txtCodigo = new JTextField();
		JTextField txtNombre = new JTextField();
		JTextField txtNit = new JTextField();
		JTextField txtCorreo = new JTextField();
		JTextField txtGenero = new JTextField();

		// Boton
		JButton btnGuardar = new JButton();

		lblCodigo.setText("Codigo:");
		lblCodigo.setFont(new Font("Serig", Font.PLAIN, 25));
		lblCodigo.setBounds(50, 80, 100, 80);
		lblCodigo.setVisible(true);
		p1.add(lblCodigo);

		lblNombre.setText("Nombre:");
		lblNombre.setFont(new Font("Serig", Font.PLAIN, 25));
		lblNombre.setBounds(50, 180, 180, 80);
		lblNombre.setVisible(true);
		p1.add(lblNombre);

		lblNit.setText("NIT:");
		lblNit.setFont(new Font("Serig", Font.PLAIN, 25));
		lblNit.setBounds(50, 280, 180, 80);
		lblNit.setVisible(true);
		p1.add(lblNit);

		lblCorreo.setText("Correo:");
		lblCorreo.setFont(new Font("Serig", Font.PLAIN, 25));
		lblCorreo.setBounds(50, 380, 100, 80);
		lblCorreo.setVisible(true);
		p1.add(lblCorreo);

		lblGenero.setText("Genero:");
		lblGenero.setFont(new Font("Serig", Font.PLAIN, 25));
		lblGenero.setBounds(50, 480, 150, 80);
		lblGenero.setVisible(true);
		p1.add(lblGenero);

		crear.setTitle("Crear");
		crear.setLocationRelativeTo(null);
		crear.setBounds(500, 150, 600, 700);
		crear.setVisible(true);
		p1.setBackground(Color.cyan);
		crear.add(p1);

		// jtextfield
		txtCodigo.setBounds(250, 100, 200, 40);
		txtNombre.setBounds(250, 200, 200, 40);
		txtNit.setBounds(250, 300, 200, 40);
		txtCorreo.setBounds(250, 400, 200, 40);
		txtGenero.setBounds(250, 500, 200, 40);

		p1.add(txtCodigo);
		p1.add(txtNombre);
		p1.add(txtNit);
		p1.add(txtCorreo);
		p1.add(txtGenero);

		// boton
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(200, 570, 150, 60);
		p1.add(btnGuardar);

		// Funcionalidad
		ActionListener ingresar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String Codigo = txtCodigo.getText();
				String Nombre = txtNombre.getText();
				String Nit = txtNit.getText();
				String Correo = txtCorreo.getText();
				String Genero = txtGenero.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Nit) || isBlank(Correo) || isBlank(Genero)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					cli1.setCodigo(Integer.parseInt(Codigo));
					cli1.setNombre(Nombre);
					cli1.setNit(Integer.parseInt(Nit));
					cli1.setCorreo(Correo);
					cli1.setGenero(Genero);
					cDAO.crear(cli1);
					JOptionPane.showMessageDialog(null, "Datos Ingresados Correctamente");
					crear.setVisible(false);
					combo_filtro.removeAllItems();
					ListarNombre();

				}
			}
		};

		// Acción del evento
		btnGuardar.addActionListener(ingresar);

	}

	public static boolean isBlank(String str) {
		return str.trim().isEmpty();
	}

	private void aplicar_filtro() throws ClassNotFoundException {
		String Nombre = txtNombre.getText();
		String Correo = txtCorreo.getText();
		String Nit = txtNit.getText();
		String Genero = txtGenero.getText();

		int x = 0;

		if (isBlank(Nombre)) {
			if (isBlank(Correo)) {
				if (isBlank(Nit)) {
					if (isBlank(Genero)) {
						JOptionPane.showMessageDialog(null, "Por favor llena un filtro I");

					} else {
						// filtro genero
						x = 4;
					}
				} else {
					// filtro Nit
					x = 3;
				}
			} else {
				// filtro correo
				x = 2;
			}

		} else {
			// filtro nombre
			x = 1;
		}

		switch (x) {
		case 1:
			nombre_filtro(Nombre);
			break;
		case 2:
			correo_filtro(Correo);
			break;
		case 3:
			nit_filtro(Integer.parseInt(Nit));
			break;
		case 4:
			genero_filtro(Genero);
			break;
		default:
			break;
		}
	}

	private void nombre_filtro(String nombre) {
		combo_filtro.removeAllItems();

		String Nombre = txtNombre.getText();
		String Correo = txtCorreo.getText();
		String Nit = txtNit.getText();
		String Genero = txtGenero.getText();

		int contador = 0;
		boolean respuesta = false;
		List<cliente> ListarCliente = cDAO.listar();
		for (int i = 0; i < ListarCliente.size(); i++) {

			ob[1] = ListarCliente.get(i).getNombre();

			if (nombre.equals(ob[1])) {
				resultados[contador] = Nombre;
				contador++;
				respuesta = true;
			}
		}
		if (respuesta == false) {
			JOptionPane.showMessageDialog(null, "Nombre no existente");
		}

		if (respuesta == true) {
			combo_filtro.setVisible(false);
			combo_filtro = new JComboBox(resultados);
			combo_filtro.setBounds(250, 200, 250, 20);
			filtro.add(combo_filtro);
		} else {
			if (isBlank(Correo)) {
				if (isBlank(Nit)) {
					if (isBlank(Genero)) {
						JOptionPane.showMessageDialog(null, "Por favor llena un filtro");
					} else {
						genero_filtro(Genero);
					}
				} else {
					nit_filtro(Integer.parseInt(Nit));
				}
			} else {
				correo_filtro(Correo);
			}
		}
	}

	private void correo_filtro(String correo) {
		combo_filtro.removeAllItems();

		String Nit = txtNit.getText();
		String Genero = txtGenero.getText();

		boolean respuesta = false;
		int contador = 0;
		List<cliente> ListarCliente = cDAO.listar();
		for (int i = 0; i < ListarCliente.size(); i++) {
			ob[2] = ListarCliente.get(i).getCorreo();
			ob[1] = ListarCliente.get(i).getNombre();

			if (correo.equals(ob[2] + "")) {
				resultados[contador] = ob[1] + "";
				contador++;
				respuesta = true;
			}
		}
		if (respuesta == false) {
			JOptionPane.showMessageDialog(null, "Correo no existente");
		}
		if (respuesta == true) {
			combo_filtro.setVisible(false);
			combo_filtro = new JComboBox(resultados);
			combo_filtro.setBounds(250, 200, 250, 20);
			filtro.add(combo_filtro);
		} else {
			if (isBlank(Nit)) {
				if (isBlank(Genero)) {
					JOptionPane.showMessageDialog(null, "Por favor llena un filtro");
				} else {
					 genero_filtro(Genero);
				}

			} else {
				nit_filtro(Integer.parseInt(Nit));

			}
		}
	}

	private void nit_filtro(int nit) {
		combo_filtro.removeAllItems();
		String Nit = txtNit.getText();
		String Genero = txtGenero.getText();

		boolean respuesta = false;
		int contador = 0;
		List<cliente> ListarCliente = cDAO.listar();
		for (int i = 0; i < ListarCliente.size(); i++) {
			ob[3] = ListarCliente.get(i).getNit();
			ob[1] = ListarCliente.get(i).getNombre();

			if ((nit + "").equals(ob[3] + "")) {
				resultados[contador] = ob[1] + "";
				contador++;
				respuesta = true;
			}
		}
		if (respuesta == false) {
			JOptionPane.showMessageDialog(null, "Nit no existente");
		}
		if (respuesta == true) {
			combo_filtro.setVisible(false);
			combo_filtro = new JComboBox(resultados);
			combo_filtro.setBounds(250, 200, 250, 20);
			filtro.add(combo_filtro);
		} else {
			if (isBlank(Genero)) {
				JOptionPane.showMessageDialog(null, "Por favor llena un filtro");
			} else {
				genero_filtro(Genero);
			}

		}

	}

	private void genero_filtro(String genero) {
		combo_filtro.removeAllItems();
		List<cliente> ListarCliente = cDAO.listar();
		boolean respuesta = false;
		int contador = 0;

		for (int i = 0; i < ListarCliente.size(); i++) {
			ob[4] = ListarCliente.get(i).getGenero();
			ob[1] = ListarCliente.get(i).getNombre();

			if (genero.equals(ob[4] + "")) {
				resultados[contador] = ob[1] + "";
				contador++;
				respuesta = true;
			}
		}
		if (respuesta == false) {
			JOptionPane.showMessageDialog(null, "Genero no existente");
		}
		if (respuesta == true) {
			combo_filtro.setVisible(false);
			combo_filtro = new JComboBox(resultados);
			combo_filtro.setBounds(250, 200, 250, 20);
			filtro.add(combo_filtro);
		} else {
			JOptionPane.showMessageDialog(null, "Datos no encontrados");
		}

	}

	private void modulo_ventas() {
		JLabel l1_fecha = new JLabel("Fecha");
		l1_fecha.setBounds(400, 5, 50, 30);
		ventas.add(l1_fecha);

		JLabel l1_ayuda = new JLabel();
		l1_ayuda.setBounds(480, 5, 100, 30);
		ventas.add(l1_ayuda);

		JLabel l2_factura = new JLabel("No.");
		l2_factura.setBounds(700, 5, 50, 30);
		ventas.add(l2_factura);

		JLabel l2_ayuda = new JLabel();
		l2_ayuda.setBounds(780, 5, 50, 30);
		ventas.add(l2_ayuda);

		JLabel l3_codigo = new JLabel("Codigo");
		l3_codigo.setBounds(100, 50, 50, 30);
		ventas.add(l3_codigo);

		JLabel l4_cantidad = new JLabel("Cantidad");
		l4_cantidad.setBounds(400, 50, 50, 30);
		ventas.add(l4_cantidad);

		JLabel l5_total = new JLabel("Total");
		l5_total.setBounds(600, 230, 80, 20);
		ventas.add(l5_total);

		JTextField t1_codigo = new JTextField();
		t1_codigo.setBounds(180, 50, 180, 30);
		ventas.add(t1_codigo);

		JTextField t2_cantidad = new JTextField();
		t2_cantidad.setBounds(480, 50, 180, 30);
		ventas.add(t2_cantidad);

		JTextField t3_total = new JTextField();
		t3_total.setBounds(700, 230, 100, 30);
		t3_total.setEnabled(false);
		ventas.add(t3_total);

		JButton agregar = new JButton("Agregar");
		agregar.setBounds(700, 50, 100, 30);
		ventas.add(agregar);

		ActionListener funcion_comprar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				funciones_productos fp = new funciones_productos();
				producto x = new producto();
				
				x = fp.buscar_productos(Integer.parseInt(t1_codigo.getText()), Integer.parseInt(t2_cantidad.getText()));
				if (x == null) {
					JOptionPane.showMessageDialog(null, "Ingresa nuevamente");

				} else {
					agregar_productos[aumento][0] = x.getCodigo();
					agregar_productos[aumento][1] = x.getNombre();
					agregar_productos[aumento][2] = Integer.parseInt(t2_cantidad.getText());
					agregar_productos[aumento][3] = x.getPrecio();
					agregar_productos[aumento][4] = (Integer.parseInt(t2_cantidad.getText()) * x.getPrecio());
					if (agregar_productos[0][4] == agregar_productos[aumento][4]) {
						total = (Integer.parseInt(t2_cantidad.getText()) * x.getPrecio());
						t3_total.setText(total + "");
					} else {
						total = total + (Integer.parseInt(t2_cantidad.getText()) * x.getPrecio());
						t3_total.setText(total+"");
					}
					aumento++;
					sp_clientes.setVisible(false);
					tabla();
				}
			}
		};

		agregar.addActionListener(funcion_comprar);

		JButton vender = new JButton("VENDER");
		vender.setBounds(100, 230, 380, 25);
		vender.setBackground(Color.green);
		ventas.add(vender);

	}

	private void tabla() {
		String[] datos = { "Codigo", "Nombre", "Cantidad", "Precio", "Subtotal" };
		tabla_clientes = new JTable(agregar_productos, datos);
		sp_clientes = new JScrollPane(tabla_clientes);
		sp_clientes.setBounds(80, 120, 700, 100);
		ventas.add(sp_clientes);
	}

	public void ListarNombre() {
		List<cliente> ListarCliente = cDAO.listar();
		
		String[] Nombre = new String[ListarCliente.size()];
		int contador = 0;
		boolean respuesta = false;
		for (int i = 0; i < ListarCliente.size(); i++) {
			ob[1] = ListarCliente.get(i).getNombre();
			Nombre[contador] = ob[1] + "";
			contador++;
			respuesta = true;
		}
		if (respuesta == true) {
			combo_filtro.setVisible(false);
			combo_filtro = new JComboBox(Nombre);
			combo_filtro.setBounds(250, 200, 250, 20);
			filtro.add(combo_filtro);
		}

	}

	public void ejecutar() {
		crear();
		ListarNombre();
		modulo_ventas();
		botones();
		tabla();
	}

}
