package Procesos;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Clases.sucursalesDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.testutils.ITextTest;

public class Sucursales_menu {

	// botones
	JButton crear = new JButton();
	JButton carga = new JButton();
	JButton actualizar = new JButton();
	JButton eliminar = new JButton();
	JButton pdf = new JButton();

	// cajas de texto actualizar
	private JTextField txtCodigo = new JTextField();
	private JTextField txtNombre = new JTextField();
	private JTextField txtDireccion = new JTextField();
	private JTextField txtCorreo = new JTextField();
	private JTextField txtTelefono = new JTextField();

	// Clase Sucursal y DAO
	sucursal s1 = new sucursal();
	sucursalesDAO sDAO = new sucursalesDAO();
	
	Object[][] sucursales;
	Object[] ob = new Object[5];

	// tabla y complemento
	JTable tabla = new JTable();
	JScrollPane sp = new JScrollPane(tabla);
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
				txtCodigo.setEnabled(false);
				if(tabla.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione el campo para modificar");
				}
				else {
					SeleccionTabla();
					actualizar();
					tabla.clearSelection();
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
				if(tabla.getSelectedRow() == -1) {
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
		modelo.addColumn("Direccion");
		modelo.addColumn("Correo");
		modelo.addColumn("Teléfono");
		tabla.setModel(modelo);
		sp.setBounds(10, 10, 500, 600);
		ListarSucursales();

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

		// cajas de texto
		JTextField t1 = new JTextField();
		JTextField t2 = new JTextField();
		JTextField t3 = new JTextField();
		JTextField t4 = new JTextField();
		JTextField t5 = new JTextField();

		// Boton
		JButton b1 = new JButton();

		l1.setText("Codigo:");
		l1.setFont(new Font("Serig", Font.PLAIN, 25));
		l1.setBounds(50, 80, 100, 80);
		l1.setVisible(true);
		p1.add(l1);

		l2.setText("Nombre:");
		l2.setFont(new Font("Serig", Font.PLAIN, 25));
		l2.setBounds(50, 180, 180, 80);
		l2.setVisible(true);
		p1.add(l2);

		l3.setText("Direccion:");
		l3.setFont(new Font("Serig", Font.PLAIN, 25));
		l3.setBounds(50, 280, 180, 80);
		l3.setVisible(true);
		p1.add(l3);

		l4.setText("Correo:");
		l4.setFont(new Font("Serig", Font.PLAIN, 25));
		l4.setBounds(50, 380, 100, 80);
		l4.setVisible(true);
		p1.add(l4);

		l5.setText("Telefono:");
		l5.setFont(new Font("Serig", Font.PLAIN, 25));
		l5.setBounds(50, 480, 150, 80);
		l5.setVisible(true);
		p1.add(l5);

		crear.setTitle("Crear");
		crear.setLocationRelativeTo(null);
		crear.setBounds(500, 150, 600, 700);
		crear.setVisible(true);
		p1.setBackground(Color.cyan);
		crear.add(p1);

		// jtextfield
		t1.setBounds(250, 100, 200, 40);
		t2.setBounds(250, 200, 200, 40);
		t3.setBounds(250, 300, 200, 40);
		t4.setBounds(250, 400, 200, 40);
		t5.setBounds(250, 500, 200, 40);

		p1.add(t1);
		p1.add(t2);
		p1.add(t3);
		p1.add(t4);
		p1.add(t5);

		// boton
		b1.setText("Guardar");
		b1.setBounds(200, 570, 150, 60);
		p1.add(b1);

		// Funcionalidad
		ActionListener ingresar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String Codigo = t1.getText();
				String Nombre = t2.getText();
				String Direccion = t3.getText();
				String Correo = t4.getText();
				String Telefono = t5.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Direccion) || isBlank(Correo) || isBlank(Telefono)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					s1.setCodigo(Integer.parseInt(Codigo));
					s1.setNombre(Nombre);
					s1.setDireccion(Direccion);
					s1.setCorreo(Correo);
					s1.setTelefono(Integer.parseInt(Telefono));
					sDAO.crear(s1);
					JOptionPane.showMessageDialog(null, "Datos Ingresados Correctamente");
					crear.setVisible(false);
					LimpiarTabla();
					ListarSucursales();

				}
			}

		};

		// Acción del evento
		b1.addActionListener(ingresar);

	}

	public static boolean isBlank(String str) {
		return str.trim().isEmpty();
	}

	@SuppressWarnings("unchecked")
	private void ListarSucursales() {
		List<sucursal> ListarSuc = sDAO.listar();
		for (int i = 0; i < ListarSuc.size(); i++) {
			ob[0] = ListarSuc.get(i).getCodigo();
			ob[1] = ListarSuc.get(i).getNombre();
			ob[2] = ListarSuc.get(i).getDireccion();
			ob[3] = ListarSuc.get(i).getCorreo();
			ob[4] = ListarSuc.get(i).getTelefono();
			modelo.addRow(ob);
		}
		tabla.setModel(modelo);
	}

	public void LimpiarTabla() {
		for (int i = 0; i < modelo.getRowCount(); i++) {
			modelo.removeRow(i);
			i = i - 1;
		}
	}

	private void carga_masiva() {
		String archivo_retorno = leerarchivo();

		JsonParser parse = new JsonParser();
		JsonArray matriz = parse.parse(archivo_retorno).getAsJsonArray();
		
		if(matriz.size() > 0) {
			for (int i = 0; i < matriz.size(); i++) {
				JsonObject objeto = matriz.get(i).getAsJsonObject();
				s1.setCodigo(objeto.get("codigo").getAsInt());
				s1.setNombre(objeto.get("nombre").getAsString());
				s1.setDireccion(objeto.get("direccion").getAsString());
				s1.setCorreo(objeto.get("correo").getAsString());
				s1.setTelefono(objeto.get("telefono").getAsInt());
				sDAO.crear(s1);

			}
			JOptionPane.showMessageDialog(null, "Datos ingresados correctamente");
			LimpiarTabla();
			ListarSucursales();
		}
		else {
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
		JLabel lblDireccion = new JLabel();
		JLabel lblCorreo = new JLabel();
		JLabel lblTelefono = new JLabel();

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

		lblDireccion.setText("Direccion:");
		lblDireccion.setFont(new Font("Serig", Font.PLAIN, 25));
		lblDireccion.setBounds(50, 280, 180, 80);
		lblDireccion.setVisible(true);
		p1.add(lblDireccion);

		lblCorreo.setText("Correo:");
		lblCorreo.setFont(new Font("Serig", Font.PLAIN, 25));
		lblCorreo.setBounds(50, 380, 100, 80);
		lblCorreo.setVisible(true);
		p1.add(lblCorreo);

		lblTelefono.setText("Telefono:");
		lblTelefono.setFont(new Font("Serig", Font.PLAIN, 25));
		lblTelefono.setBounds(50, 480, 150, 80);
		lblTelefono.setVisible(true);
		p1.add(lblTelefono);

		Jactualizar.setTitle("Actualizar Datos");
		Jactualizar.setLocationRelativeTo(null);
		Jactualizar.setBounds(500, 150, 600, 700);
		Jactualizar.setVisible(true);
		p1.setBackground(Color.cyan);
		Jactualizar.add(p1);

		// jtextfield
		txtCodigo.setBounds(250, 100, 200, 40);
		txtNombre.setBounds(250, 200, 200, 40);
		txtDireccion.setBounds(250, 300, 200, 40);
		txtCorreo.setBounds(250, 400, 200, 40);
		txtTelefono.setBounds(250, 500, 200, 40);

		p1.add(txtCodigo);
		p1.add(txtNombre);
		p1.add(txtDireccion);
		p1.add(txtCorreo);
		p1.add(txtTelefono);

		// boton
		btnActualizar.setText("Actualizar");
		btnActualizar.setBounds(200, 570, 150, 60);
		p1.add(btnActualizar);

		// funcionalidad
		ActionListener actualizar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String Codigo = txtCodigo.getText();
				String Nombre = txtNombre.getText();
				String Direccion = txtDireccion.getText();
				String Correo = txtCorreo.getText();
				String Telefono = txtTelefono.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Direccion) || isBlank(Correo) || isBlank(Telefono)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					s1.setCodigo(Integer.parseInt(Codigo));
					s1.setNombre(Nombre);
					s1.setDireccion(Direccion);
					s1.setCorreo(Correo);
					s1.setTelefono(Integer.parseInt(Telefono));
					sDAO.modificar(s1);
					JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
					Jactualizar.setVisible(false);
					Jactualizar.dispose();
					LimpiarTabla();
					ListarSucursales();
				}

			}
		};
		btnActualizar.addActionListener(actualizar);

		tabla.clearSelection();
	}

	@SuppressWarnings("unused")
	private void SeleccionTabla() {
		MouseListener seleccionar = new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.rowAtPoint(e.getPoint());
				txtCodigo.setText(tabla.getValueAt(fila, 0).toString());
				txtNombre.setText(tabla.getValueAt(fila, 1).toString());
				txtDireccion.setText(tabla.getValueAt(fila, 2).toString());
				txtCorreo.setText(tabla.getValueAt(fila, 3).toString());
				txtTelefono.setText(tabla.getValueAt(fila, 4).toString());	
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

		
		};
		tabla.addMouseListener(seleccionar);
		
	}

	private void generar_pdf() throws DocumentException, FileNotFoundException {
		
		FileOutputStream gen = new FileOutputStream("Sucursales.pdf");
		Document documento = new Document();

		PdfWriter.getInstance(documento, gen);
		documento.open();

		Paragraph parrafo = new Paragraph("Sucursales");
		parrafo.setAlignment(1);
		documento.add(parrafo);
		documento.add(new Paragraph("\n"));
		
		@SuppressWarnings("unchecked")
		List<sucursal> ListarSuc = sDAO.listar();
		
		for (int i = 0; i < ListarSuc.size(); i++) {
			if(ob[0] == null) {
				break;
			}
			else {
				ob[0] = ListarSuc.get(i).getCodigo();
				ob[1] = ListarSuc.get(i).getNombre();
				ob[2] = ListarSuc.get(i).getDireccion();
				ob[3] = ListarSuc.get(i).getCorreo();
				ob[4] = ListarSuc.get(i).getTelefono();
				
				documento.add(new Paragraph("Código: " +ob[0]+ " " + "\nNombre: " +ob[1] + " "
						+ "\nDirección: " + ob[2] + " " + "\nCorreo: " +ob[3] + " " + "\nTeléfono: "
						+ ob[4]));
				documento.add(new Paragraph("\n\n"));
			}
			
		}
		documento.close();
		JOptionPane.showMessageDialog(null, "El archivo se creo correctamente");
		try {
			File sucursales_doc = new File("Sucursales.pdf");
			Desktop.getDesktop().open(sucursales_doc);
		} catch (Exception e) {
			
		}
	}

	private void eliminar() {
			int codigo;
			int confirmacion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar?");
			if (confirmacion == 0) {
				codigo = Integer.parseInt(txtCodigo.getText());
				sDAO.eliminar(codigo);
				JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
				LimpiarTabla();
				ListarSucursales();
			}
	}

	public void ejecutar() throws ClassNotFoundException {
		botones();
		tabla();

	}
}
