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
import java.util.Iterator;
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

import Clases.clientesDAO;

public class clientes_menu {
	// botones
	JButton crear = new JButton();
	JButton carga = new JButton();
	JButton actualizar = new JButton();
	JButton eliminar = new JButton();
	JButton pdf = new JButton();

	// Matriz
	Object ob[] = new Object[5];
	cliente cli1 = new cliente();
	clientesDAO cDAO = new clientesDAO();

	// cajas de texto para Actualizar

	private JTextField txtCodigo = new JTextField();
	private JTextField txtNombre = new JTextField();
	private JTextField txtNit = new JTextField();
	private JTextField txtCorreo = new JTextField();
	private JTextField txtGenero = new JTextField();

	// tabla y complemento
	JTable tabla_clientes = new JTable();
	JScrollPane sp_clientes = new JScrollPane(tabla_clientes);
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
				if(tabla_clientes.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione el campo para modificar");
				}
				else {
					txtCodigo.setEnabled(false);
					SeleccionTabla();
					actualizar();
					tabla_clientes.clearSelection();
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
				if(tabla_clientes.getSelectedRow() == -1) {
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
		modelo.addColumn("Nit");
		modelo.addColumn("Correo");
		modelo.addColumn("Genero");
		tabla_clientes.setModel(modelo);
		sp_clientes.setBounds(10, 10, 500, 600);
		ListarClientes();

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

		l3.setText("NIT:");
		l3.setFont(new Font("Serig", Font.PLAIN, 25));
		l3.setBounds(50, 280, 180, 80);
		l3.setVisible(true);
		p1.add(l3);

		l4.setText("Correo:");
		l4.setFont(new Font("Serig", Font.PLAIN, 25));
		l4.setBounds(50, 380, 100, 80);
		l4.setVisible(true);
		p1.add(l4);

		l5.setText("Genero:");
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
				String Nit = t3.getText();
				String Correo = t4.getText();
				String Genero = t5.getText();

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
					LimpiarTabla();
					ListarClientes();

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
	private void ListarClientes() {
		List<cliente> ListarClientes = cDAO.listar();
		for (int i = 0; i < ListarClientes.size(); i++) {
			ob[0] = ListarClientes.get(i).getCodigo();
			ob[1] = ListarClientes.get(i).getNombre();
			ob[2] = ListarClientes.get(i).getNit();
			ob[3] = ListarClientes.get(i).getCorreo();
			ob[4] = ListarClientes.get(i).getGenero();
			modelo.addRow(ob);
		}
		tabla_clientes.setModel(modelo);
	}

	private void carga_masiva() {
		String archivo_retorno = leerarchivo();

		JsonParser parse = new JsonParser();
		JsonArray matriz = parse.parse(archivo_retorno).getAsJsonArray();

		if (matriz.size() > 0) {
			for (int i = 0; i < matriz.size(); i++) {
				JsonObject objeto = matriz.get(i).getAsJsonObject();
				cli1.setCodigo(objeto.get("codigo").getAsInt());
				cli1.setNombre(objeto.get("nombre").getAsString());
				cli1.setNit(objeto.get("nit").getAsInt());
				cli1.setCorreo(objeto.get("correo").getAsString());
				cli1.setGenero(objeto.get("genero").getAsString());
				cDAO.crear(cli1);
			}
			JOptionPane.showMessageDialog(null, "Datos ingresados correctamente");
			LimpiarTabla();
			ListarClientes();
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
			JLabel lblNit = new JLabel();
			JLabel lblCorreo = new JLabel();
			JLabel lblGenero = new JLabel();

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

			lblNit.setText("Nit:");
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

			Jactualizar.setTitle("Actualizar Datos");
			Jactualizar.setLocationRelativeTo(null);
			Jactualizar.setBounds(500, 150, 600, 700);
			Jactualizar.setVisible(true);
			p1.setBackground(Color.cyan);
			Jactualizar.add(p1);

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
			btnActualizar.setText("Actualizar");
			btnActualizar.setBounds(200, 570, 150, 60);
			p1.add(btnActualizar);

			// funcionalidad
			ActionListener actualizar = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String Codigo = txtCodigo.getText();
					String Nombre = txtNombre.getText();
					String Nit = txtNit.getText();
					String Correo = txtCorreo.getText();
					String Genero = txtGenero.getText();

					if (isBlank(Codigo) || isBlank(Nombre) || isBlank(Nit) || isBlank(Correo)
							|| isBlank(Genero)) {
						JOptionPane.showMessageDialog(null, "Llenar los campos faltantes");
					} else {
						cli1.setCodigo(Integer.parseInt(Codigo));
						cli1.setNombre(Nombre);
						cli1.setNit(Integer.parseInt(Nit));
						cli1.setCorreo(Correo);
						cli1.setGenero(Genero);
						cDAO.modificar(cli1);
						JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
						Jactualizar.setVisible(false);
						Jactualizar.dispose();
						LimpiarTabla();
						ListarClientes();
					}

				}

			};
			// Accion del evento
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
				int fila = tabla_clientes.rowAtPoint(e.getPoint());
				txtCodigo.setText(tabla_clientes.getValueAt(fila, 0).toString());
				txtNombre.setText(tabla_clientes.getValueAt(fila, 1).toString());
				txtNit.setText(tabla_clientes.getValueAt(fila, 2).toString());
				txtCorreo.setText(tabla_clientes.getValueAt(fila, 3).toString());
				txtGenero.setText(tabla_clientes.getValueAt(fila, 4).toString());

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
		tabla_clientes.addMouseListener(seleccionar);

	}


	private void generar_pdf() throws DocumentException, FileNotFoundException {
		FileOutputStream gen = new FileOutputStream("Clientes.pdf");
		Document documento = new Document();

		PdfWriter.getInstance(documento, gen);
		documento.open();

		Paragraph parrafo = new Paragraph("Clientes");
		parrafo.setAlignment(1);
		documento.add(parrafo);
		documento.add(new Paragraph("\n"));

		List<cliente> ListarClie = cDAO.listar();

		for (int i = 0; i < ListarClie.size(); i++) {
			if(ob[0] == null) {
				break;
		}else {
			ob[0] = ListarClie.get(i).getCodigo();
			ob[1] = ListarClie.get(i).getNombre();
			ob[2] = ListarClie.get(i).getNit();
			ob[3] = ListarClie.get(i).getCorreo();
			ob[4] = ListarClie.get(i).getGenero();
			documento.add(new Paragraph("Código: " + ob[0] + " " + "\nNombre: " + ob[1] + " " + "\nNit: "
							+ ob[2]+ " " + "\nCorreo: " + ob[3] + " " + "\nGenero: " + ob[4]));
			documento.add(new Paragraph("\n\n"));
		}

	}
		documento.close();
		JOptionPane.showMessageDialog(null, "El archivo se creo correctamente");
		try {
			File clientes_doc = new File("Clientes.pdf");
			Desktop.getDesktop().open(clientes_doc);
		} catch (Exception e) {
			
		}

	}

	private void eliminar() {
		int codigo;
		int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar?");
		if (confirmacion == 0) {
			codigo = Integer.parseInt(txtCodigo.getText());
			cDAO.eliminar(codigo);
			JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
			LimpiarTabla();
			ListarClientes();
		}
	}

	public void ejecutar() throws ClassNotFoundException {
		botones();
		tabla();

	}

}
