package Procesos;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Clases.conexion;
import Clases.vendedoresDAO;

public class ventas_menu {

	Connection con;
	conexion cn = new conexion();
	PreparedStatement ps;
	ResultSet rs;

	String Qcontraseña;

	// botones
	JButton crear = new JButton();
	JButton carga = new JButton();
	JButton actualizar = new JButton();
	JButton eliminar = new JButton();
	JButton pdf = new JButton();

	// cajas de texto de actualizar
	private JTextField txtCodigo = new JTextField();
	private JTextField txtNombre = new JTextField();
	private JTextField txtCaja = new JTextField();
	private JTextField txtVentas = new JTextField();
	private JTextField txtGenero = new JTextField();
	private JPasswordField txtContrasena = new JPasswordField();

	// Matriz donde se almacena la informacion temporal
	Object ob[] = new Object[6];
	vendedor ven1 = new vendedor();
	vendedoresDAO vDAO = new vendedoresDAO();

	// tabla y complemento
	JTable tabla_vendedores = new JTable();
	JScrollPane sp_vendedores = new JScrollPane(tabla_vendedores);
	DefaultTableModel modelo = new DefaultTableModel();

	private void botones() {
		crear.setText("Crear");
		crear.setBounds(550, 100, 130, 70);

		// Funcion crear
		ActionListener funcion_crear = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				crear();

			}
		};

		// Acción del evento
		crear.addActionListener(funcion_crear);

		carga.setText("Carga Masiva");
		carga.setBounds(730, 100, 130, 70);

		ActionListener funcion_carga = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					carga_masiva();
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		};
		// Accion del evento
		carga.addActionListener(funcion_carga);

		actualizar.setText("Actualizar");
		actualizar.setBounds(550, 260, 130, 70);

		ActionListener funcion_actualizar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabla_vendedores.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione el campo para modificar");
				}
				else {
					txtCodigo.setEnabled(false);
					SeleccionTabla();
					SeleccionarPassword();
					actualizar();
					tabla_vendedores.clearSelection();
				}

			}
		};
		// Accion del evento
		actualizar.addActionListener(funcion_actualizar);

		eliminar.setText("Eliminar");
		eliminar.setBounds(730, 260, 130, 70);

		ActionListener funcion_eliminar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabla_vendedores.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione el campo a eliminar");
				}
				else {
					eliminar();	
				}
			}
		};
		eliminar.addActionListener(funcion_eliminar);

		pdf.setText("Exportar pdf");
		pdf.setBounds(550, 420, 310, 70);
		ActionListener funcion_pdf = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					generar_pdf();
				} catch (FileNotFoundException | DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		pdf.addActionListener(funcion_pdf);

	}

	private String leerarchivo() {

		JPanel c1 = new JPanel();
		JFileChooser fc = new JFileChooser();
		int op = fc.showOpenDialog(c1);
		String content = "";
		if (op == JFileChooser.APPROVE_OPTION) {

			File pRuta = fc.getSelectedFile();
			String ruta = pRuta.getAbsolutePath();
			File archivo = null;
			FileReader fr = null;
			BufferedReader br = null;

			try {
				archivo = new File(ruta);
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);
				String linea = "";

				while ((linea = br.readLine()) != null) {

					content += linea + "\n";
				}
				return content;

			} catch (FileNotFoundException ex) {
				String resp = (String) JOptionPane.showInputDialog(null, "No se encontro el archivo");
			} catch (IOException ex) {
				String resp = (String) JOptionPane.showInputDialog(null, "No se pudo abrir el archivo");
			} finally {
				try {
					if (null != fr) {
						fr.close();
					}
				} catch (Exception e2) {
					String resp = (String) JOptionPane.showInputDialog(null, "No se encontro el archivo");
					return "";

				}
			}
			return content;

		}
		return null;
	}

	private void tabla() {
		modelo.addColumn("Codigo");
		modelo.addColumn("Nombre");
		modelo.addColumn("Caja");
		modelo.addColumn("Ventas");
		modelo.addColumn("Genero");
		tabla_vendedores.setModel(modelo);
		sp_vendedores.setBounds(10, 10, 500, 600);
		ListarVendedores();

	}

	private void crear() {

		JFrame crear = new JFrame();
		JPanel p1 = new JPanel();
		p1.setLayout(null);

		// etiquetas
		JLabel l1 = new JLabel();
		JLabel l2 = new JLabel();
		JLabel l3 = new JLabel();
		JLabel l4 = new JLabel();
		JLabel l5 = new JLabel();
		JLabel l6 = new JLabel();

		// cajas de texto
		JTextField t1 = new JTextField();
		JTextField t2 = new JTextField();
		JTextField t3 = new JTextField();
		JTextField t4 = new JTextField();
		JTextField t5 = new JTextField();
		JTextField t6 = new JTextField();

		// Boton
		JButton b1 = new JButton();

		l1.setText("Codigo:");
		l1.setFont(new Font("Serig", Font.PLAIN, 25));
		l1.setBounds(50, 50, 100, 80);
		l1.setVisible(true);
		p1.add(l1);

		l2.setText("Nombre:");
		l2.setFont(new Font("Serig", Font.PLAIN, 25));
		l2.setBounds(50, 150, 180, 80);
		l2.setVisible(true);
		p1.add(l2);

		l3.setText("Caja:");
		l3.setFont(new Font("Serig", Font.PLAIN, 25));
		l3.setBounds(50, 250, 180, 80);
		l3.setVisible(true);
		p1.add(l3);

		l4.setText("Ventas:");
		l4.setFont(new Font("Serig", Font.PLAIN, 25));
		l4.setBounds(50, 350, 100, 80);
		l4.setVisible(true);
		p1.add(l4);

		l5.setText("Genero:");
		l5.setFont(new Font("Serig", Font.PLAIN, 25));
		l5.setBounds(50, 450, 150, 80);
		l5.setVisible(true);
		p1.add(l5);

		l6.setText("Password:");
		l6.setFont(new Font("Serig", Font.PLAIN, 25));
		l6.setBounds(50, 550, 150, 80);
		l6.setVisible(true);
		p1.add(l6);

		crear.setTitle("Vendedores");
		crear.setLocationRelativeTo(null);
		crear.setBounds(500, 150, 600, 800);
		crear.setVisible(true);
		p1.setBackground(Color.cyan);
		crear.add(p1);

		// jtextfield
		t1.setBounds(250, 60, 200, 40);
		t2.setBounds(250, 160, 200, 40);
		t3.setBounds(250, 260, 200, 40);
		t4.setBounds(250, 360, 200, 40);
		t5.setBounds(250, 460, 200, 40);
		t6.setBounds(250, 560, 200, 40);

		p1.add(t1);
		p1.add(t2);
		p1.add(t3);
		p1.add(t4);
		p1.add(t5);
		p1.add(t6);

		// boton
		b1.setText("Agregar");
		b1.setBounds(200, 650, 150, 60);
		p1.add(b1);

		// Funcionalidad
		ActionListener ingresar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String Codigo = t1.getText();
				String Nombre = t2.getText();
				String Caja = t3.getText();
				String Ventas = t4.getText();
				String Genero = t5.getText();
				String Password = t6.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Caja) || isBlank(Ventas) || isBlank(Genero)
						|| isBlank(Password)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					ven1.setCodigo(Integer.parseInt(Codigo));
					ven1.setNombre(Nombre);
					ven1.setCaja(Integer.parseInt(Caja));
					ven1.setVentas(Integer.parseInt(Ventas));
					ven1.setGenero(Genero);
					ven1.setPassword(Password);
					vDAO.crear(ven1);
					JOptionPane.showMessageDialog(null, "Datos Ingresados Correctamente");
					crear.setVisible(false);
					LimpiarTabla();
					ListarVendedores();

				}

			}
		};

		// Acción del evento
		b1.addActionListener(ingresar);
	}

	public static boolean isBlank(String str) {
		return str.trim().isEmpty();
	}

	public void LimpiarTabla() {
		for (int i = 0; i < modelo.getRowCount(); i++) {
			modelo.removeRow(i);
			i = i - 1;
		}
	}

	@SuppressWarnings("unchecked")
	private void ListarVendedores() {
		List<vendedor> ListarVend = vDAO.listar();
		for (int i = 0; i < ListarVend.size(); i++) {
			ob[0] = ListarVend.get(i).getCodigo();
			ob[1] = ListarVend.get(i).getNombre();
			ob[2] = ListarVend.get(i).getCaja();
			ob[3] = ListarVend.get(i).getVentas();
			ob[4] = ListarVend.get(i).getGenero();
			modelo.addRow(ob);
		}
		tabla_vendedores.setModel(modelo);
	}

	private void carga_masiva() {
		String archivo_retorno = leerarchivo();

		JsonParser parse = new JsonParser();
		JsonArray matriz = parse.parse(archivo_retorno).getAsJsonArray();

		if (matriz.size() > 0) {
			for (int i = 0; i < matriz.size(); i++) {
				JsonObject objeto = matriz.get(i).getAsJsonObject();
				ven1.setCodigo(objeto.get("codigo").getAsInt());
				ven1.setNombre(objeto.get("nombre").getAsString());
				ven1.setCaja(objeto.get("caja").getAsInt());
				ven1.setVentas(objeto.get("ventas").getAsInt());
				ven1.setGenero(objeto.get("genero").getAsString());
				ven1.setPassword(objeto.get("password").getAsString());
				vDAO.crear(ven1);
			}
			JOptionPane.showMessageDialog(null, "Datos ingresados correctamente");
			LimpiarTabla();
			ListarVendedores();
		} else {
			JOptionPane.showMessageDialog(null, "Archivo JSON vacio");
		}

	}

	private void actualizar() {

		JFrame Jactualizar = new JFrame();
		JPanel p1 = new JPanel();
		// Boton
		JButton btnActualizar = new JButton();
		p1.setLayout(null);

		// etiquetas
		JLabel lblCodigo = new JLabel();
		JLabel lblNombre = new JLabel();
		JLabel lblCaja = new JLabel();
		JLabel lblVentas = new JLabel();
		JLabel lblGenero = new JLabel();
		JLabel lblContrasena = new JLabel();

		lblCodigo.setText("Codigo:");
		lblCodigo.setFont(new Font("Serig", Font.PLAIN, 25));
		lblCodigo.setBounds(50, 50, 100, 80);
		lblCodigo.setVisible(true);
		p1.add(lblCodigo);

		lblNombre.setText("Nombre:");
		lblNombre.setFont(new Font("Serig", Font.PLAIN, 25));
		lblNombre.setBounds(50, 150, 180, 80);
		lblNombre.setVisible(true);
		p1.add(lblNombre);

		lblCaja.setText("Caja:");
		lblCaja.setFont(new Font("Serig", Font.PLAIN, 25));
		lblCaja.setBounds(50, 250, 180, 80);
		lblCaja.setVisible(true);
		p1.add(lblCaja);

		lblVentas.setText("Ventas:");
		lblVentas.setFont(new Font("Serig", Font.PLAIN, 25));
		lblVentas.setBounds(50, 350, 100, 80);
		lblVentas.setVisible(true);
		p1.add(lblVentas);

		lblGenero.setText("Genero:");
		lblGenero.setFont(new Font("Serig", Font.PLAIN, 25));
		lblGenero.setBounds(50, 450, 150, 80);
		lblGenero.setVisible(true);
		p1.add(lblGenero);

		lblContrasena.setText("Password:");
		lblContrasena.setFont(new Font("Serig", Font.PLAIN, 25));
		lblContrasena.setBounds(50, 550, 150, 80);
		lblContrasena.setVisible(true);
		p1.add(lblContrasena);

		Jactualizar.setTitle("Actualizar Datos");
		Jactualizar.setLocationRelativeTo(null);
		Jactualizar.setBounds(500, 30, 600, 800);
		Jactualizar.setVisible(true);
		p1.setBackground(Color.cyan);
		Jactualizar.add(p1);

		// jtextfield
		txtCodigo.setBounds(250, 60, 200, 40);
		txtNombre.setBounds(250, 160, 200, 40);
		txtCaja.setBounds(250, 260, 200, 40);
		txtVentas.setBounds(250, 360, 200, 40);
		txtGenero.setBounds(250, 460, 200, 40);
		txtContrasena.setBounds(250, 560, 200, 40);

		p1.add(txtCodigo);
		p1.add(txtNombre);
		p1.add(txtCaja);
		p1.add(txtVentas);
		p1.add(txtGenero);
		p1.add(txtContrasena);

		// boton
		btnActualizar.setText("Actualizar");
		btnActualizar.setBounds(200, 650, 150, 60);
		p1.add(btnActualizar);

		// funcionalidad
		ActionListener actualizar = new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String Codigo = txtCodigo.getText();
				String Nombre = txtNombre.getText();
				String Caja = txtCaja.getText();
				String Ventas = txtVentas.getText();
				String Genero = txtGenero.getText();
				String Contraseña = txtContrasena.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Caja) || isBlank(Ventas) || isBlank(Genero)
						|| isBlank(Contraseña)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					ven1.setCodigo(Integer.parseInt(Codigo));
					ven1.setNombre(Nombre);
					ven1.setCaja(Integer.parseInt(Caja));
					ven1.setVentas(Integer.parseInt(Ventas));
					ven1.setGenero(Genero);
					ven1.setPassword(Contraseña);
					vDAO.modificar(ven1);

					JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
					Jactualizar.setVisible(false);
					Jactualizar.dispose();
					LimpiarTabla();
					ListarVendedores();
				}

			}
		};
		btnActualizar.addActionListener(actualizar);

	}

	@SuppressWarnings("unused")
	private void SeleccionTabla() {
		MouseListener seleccionar = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int fila = tabla_vendedores.rowAtPoint(e.getPoint());
				txtCodigo.setText(tabla_vendedores.getValueAt(fila, 0).toString());
				txtNombre.setText(tabla_vendedores.getValueAt(fila, 1).toString());
				txtCaja.setText(tabla_vendedores.getValueAt(fila, 2).toString());
				txtVentas.setText(tabla_vendedores.getValueAt(fila, 3).toString());
				txtGenero.setText(tabla_vendedores.getValueAt(fila, 4).toString());

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

		};
		tabla_vendedores.addMouseListener(seleccionar);

	}

	private boolean SeleccionarPassword() {
		String consulta = "select password_vendedor from vendedores where codigo_vendedor = '" + txtCodigo.getText() + "'";

		try {
			con = cn.getConexion();
			ps = con.prepareStatement(consulta);
			rs = ps.executeQuery();
			while (rs.next()) {
				Qcontraseña = rs.getString("password_vendedor");
			}
			
			txtContrasena.setText(Qcontraseña);
			return true;
			
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}

	private void generar_pdf() throws DocumentException, FileNotFoundException {
		FileOutputStream gen = new FileOutputStream("Vendedores.pdf");
		Document documento = new Document();

		PdfWriter.getInstance(documento, gen);
		documento.open();

		Paragraph parrafo = new Paragraph("Vendedores");
		parrafo.setAlignment(1);
		documento.add(parrafo);
		documento.add(new Paragraph("\n"));

		List<vendedor> ListarVend = vDAO.listar();

		for (int i = 0; i < ListarVend.size(); i++) {
			if(ob[0] == null) {
				break;
		}else {
			ob[0] = ListarVend.get(i).getCodigo();
			ob[1] = ListarVend.get(i).getNombre();
			ob[2] = ListarVend.get(i).getCaja();
			ob[3] = ListarVend.get(i).getVentas();
			ob[4] = ListarVend.get(i).getGenero();
			ob[5] = ListarVend.get(i).getPassword();
			documento.add(new Paragraph("Código: " + ob[0] + " " + "\nNombre: " + ob[1] + " " + "\nCaja: "
							+ ob[2]+ " " + "\nVentas: " + ob[3] + " " + "\nGenero: " 
					+ ob[4]	+ " " + "\nPassword: "+ob[5]));
			documento.add(new Paragraph("\n\n"));
		}

	}
		documento.close();
		JOptionPane.showMessageDialog(null, "El archivo se creo correctamente");
		try {
			File vendedores_doc = new File("Vendedores.pdf");
			Desktop.getDesktop().open(vendedores_doc);
		} catch (Exception e) {
			
		}

	}

	private void eliminar() {
		int codigo;
		int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar?");
		if (confirmacion == 0) {
			codigo = Integer.parseInt(txtCodigo.getText());
			vDAO.eliminar(codigo);
			JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
			LimpiarTabla();
			ListarVendedores();
		}
	}

	public void ejecutar() throws ClassNotFoundException {
		botones();
		tabla();

	}

}
