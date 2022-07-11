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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import Clases.productosDAO;

public class productos_menu {

	// botones
	JButton crear = new JButton();
	JButton carga = new JButton();
	JButton actualizar = new JButton();
	JButton eliminar = new JButton();
	JButton pdf = new JButton();

	// Matriz, Clases y DAO
	Object[] ob = new Object[5];
	producto pro1 = new producto();
	productosDAO pDAO = new productosDAO();

	// cajas de texto para Actualizar
	private JTextField txtCodigo = new JTextField();
	private JTextField txtNombre = new JTextField();
	private JTextField txtDescripcion = new JTextField();
	private JTextField txtCantidad = new JTextField();
	private JTextField txtPrecio = new JTextField();

	// tabla y complemento
	JTable tabla_productos = new JTable();
	JScrollPane sp_productos = new JScrollPane(tabla_productos);
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
				if(tabla_productos.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione el campo para modificar");
				}
				else {
					txtCodigo.setEnabled(false);
					SeleccionTabla();
					actualizar();
					tabla_productos.clearSelection();
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
				if(tabla_productos.getSelectedRow() == -1) {
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
		modelo.addColumn("Descripcion");
		modelo.addColumn("Cantidad");
		modelo.addColumn("Precio");
		tabla_productos.setModel(modelo);
		sp_productos.setBounds(10, 10, 500, 600);
		ListarProductos();

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

		l3.setText("Descripcion:");
		l3.setFont(new Font("Serig", Font.PLAIN, 25));
		l3.setBounds(50, 280, 180, 80);
		l3.setVisible(true);
		p1.add(l3);

		l4.setText("Cantidad:");
		l4.setFont(new Font("Serig", Font.PLAIN, 25));
		l4.setBounds(50, 380, 100, 80);
		l4.setVisible(true);
		p1.add(l4);

		l5.setText("Precio:");
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
				String Descripcion = t3.getText();
				String Cantidad = t4.getText();
				String Precio = t5.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Descripcion) || isBlank(Cantidad)
						|| isBlank(Precio)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					pro1.setCodigo(Integer.parseInt(Codigo));
					pro1.setNombre(Nombre);
					pro1.setDescripcion(Descripcion);
					pro1.setCantidad(Integer.parseInt(Cantidad));
					pro1.setPrecio(Double.parseDouble(Precio));
					pDAO.crear(pro1);
					JOptionPane.showMessageDialog(null, "Datos Ingresados Correctamente");
					crear.setVisible(false);
					LimpiarTabla();
					ListarProductos();

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
	private void ListarProductos() {
		List<producto> ListarProducto = pDAO.listar();
		for (int i = 0; i < ListarProducto.size(); i++) {
			ob[0] = ListarProducto.get(i).getCodigo();
			ob[1] = ListarProducto.get(i).getNombre();
			ob[2] = ListarProducto.get(i).getDescripcion();
			ob[3] = ListarProducto.get(i).getCantidad();
			ob[4] = ListarProducto.get(i).getPrecio();
			modelo.addRow(ob);
		}
		tabla_productos.setModel(modelo);
	}

	private void carga_masiva() {
		String archivo_retorno = leerarchivo();

		JsonParser parse = new JsonParser();
		JsonArray matriz = parse.parse(archivo_retorno).getAsJsonArray();

		if (matriz.size() > 0) {
			for (int i = 0; i < matriz.size(); i++) {
				JsonObject objeto = matriz.get(i).getAsJsonObject();
				pro1.setCodigo(objeto.get("codigo").getAsInt());
				pro1.setNombre(objeto.get("nombre").getAsString());
				pro1.setDescripcion(objeto.get("descripcion").getAsString());
				pro1.setCantidad(objeto.get("cantidad").getAsInt());
				pro1.setPrecio(objeto.get("precio").getAsDouble());
				pDAO.crear(pro1);
			}
			JOptionPane.showMessageDialog(null, "Datos ingresados correctamente");
			LimpiarTabla();
			ListarProductos();
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
		JLabel lblDescripcion = new JLabel();
		JLabel lblCantdad = new JLabel();
		JLabel lblPrecio = new JLabel();

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

		lblDescripcion.setText("Descripcion:");
		lblDescripcion.setFont(new Font("Serig", Font.PLAIN, 25));
		lblDescripcion.setBounds(50, 280, 180, 80);
		lblDescripcion.setVisible(true);
		p1.add(lblDescripcion);

		lblCantdad.setText("Cantidad:");
		lblCantdad.setFont(new Font("Serig", Font.PLAIN, 25));
		lblCantdad.setBounds(50, 380, 150, 80);
		lblCantdad.setVisible(true);
		p1.add(lblCantdad);

		lblPrecio.setText("Precio:");
		lblPrecio.setFont(new Font("Serig", Font.PLAIN, 25));
		lblPrecio.setBounds(50, 480, 150, 80);
		lblPrecio.setVisible(true);
		p1.add(lblPrecio);

		Jactualizar.setTitle("Actualizar Datos");
		Jactualizar.setLocationRelativeTo(null);
		Jactualizar.setBounds(500, 150, 600, 700);
		Jactualizar.setVisible(true);
		p1.setBackground(Color.cyan);
		Jactualizar.add(p1);

		// jtextfield
		txtCodigo.setBounds(250, 100, 200, 40);
		txtNombre.setBounds(250, 200, 200, 40);
		txtDescripcion.setBounds(250, 300, 200, 40);
		txtCantidad.setBounds(250, 400, 200, 40);
		txtPrecio.setBounds(250, 500, 200, 40);

		p1.add(txtCodigo);
		p1.add(txtNombre);
		p1.add(txtDescripcion);
		p1.add(txtCantidad);
		p1.add(txtPrecio);

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
				String Descripcion = txtDescripcion.getText();
				String Cantidad = txtCantidad.getText();
				String Precio = txtPrecio.getText();

				if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Descripcion) || isBlank(Cantidad)
						|| isBlank(Precio)) {
					JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
				} else {
					pro1.setCodigo(Integer.parseInt(Codigo));
					pro1.setNombre(Nombre);
					pro1.setDescripcion(Descripcion);
					pro1.setCantidad(Integer.parseInt(Cantidad));
					pro1.setPrecio(Double.parseDouble(Precio));
					pDAO.modificar(pro1);
					JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
					Jactualizar.setVisible(false);
					Jactualizar.dispose();
					LimpiarTabla();
					ListarProductos();
				}

			}

		};
		// Accion del evento
		btnActualizar.addActionListener(actualizar);
		tabla_productos.clearSelection();

	}

	@SuppressWarnings("unused")
	private void SeleccionTabla() {
		MouseListener seleccionar = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int fila = tabla_productos.rowAtPoint(e.getPoint());
				txtCodigo.setText(tabla_productos.getValueAt(fila, 0).toString());
				txtNombre.setText(tabla_productos.getValueAt(fila, 1).toString());
				txtDescripcion.setText(tabla_productos.getValueAt(fila, 2).toString());
				txtCantidad.setText(tabla_productos.getValueAt(fila, 3).toString());
				txtPrecio.setText(tabla_productos.getValueAt(fila, 4).toString());

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
		tabla_productos.addMouseListener(seleccionar);

	}

	@SuppressWarnings("unchecked")
	private void generar_pdf() throws DocumentException, FileNotFoundException {
		FileOutputStream gen = new FileOutputStream("Productos.pdf");
		Document documento = new Document();

		PdfWriter.getInstance(documento, gen);
		documento.open();

		Paragraph parrafo = new Paragraph("Productos");
		parrafo.setAlignment(1);
		documento.add(parrafo);
		documento.add(new Paragraph("\n"));

		List<producto> ListarProd = pDAO.listar();

		for (int i = 0; i < ListarProd.size(); i++) {
			if(ob[0] == null) {
				break;
		}else {
			ob[0] = ListarProd.get(i).getCodigo();
			ob[1] = ListarProd.get(i).getNombre();
			ob[2] = ListarProd.get(i).getDescripcion();
			ob[3] = ListarProd.get(i).getCantidad();
			ob[4] = ListarProd.get(i).getPrecio();
			documento.add(new Paragraph("Código: " + ob[0] + " " + "\nNombre: " + ob[1] + " " + "\nDescripcion: "
							+ ob[2]+ " " + "\nCantidad: " + ob[3] + " " + "\nPrecio: " + ob[4]));
			documento.add(new Paragraph("\n\n"));
		}

	}
		documento.close();
		JOptionPane.showMessageDialog(null, "El archivo se creo correctamente");
		try {
			File productos_doc = new File("Productos.pdf");
			Desktop.getDesktop().open(productos_doc);
		} catch (Exception e) {
			
		}

	}

	private void eliminar() {
		int codigo;
		int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar?");
		if (confirmacion == 0) {
			codigo = Integer.parseInt(txtCodigo.getText());
			pDAO.eliminar(codigo);
			JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
			LimpiarTabla();
			ListarProductos();
		}
	}

	public void ejecutar() throws ClassNotFoundException {
		botones();
		tabla();

	}

}
