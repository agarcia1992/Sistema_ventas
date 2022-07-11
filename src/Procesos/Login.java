package Procesos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import Clases.conexion;

public class Login{
	// atributos
	JFrame inicio = new JFrame();
	JPanel p1 = new JPanel();
	JLabel l1 = new JLabel();
	JLabel l2 = new JLabel();
	JTextField txtUsuario = new JTextField();
	JPasswordField txtContrasena = new JPasswordField();
	JButton b1 = new JButton();
	Object[][] vendedores = new Object[400][6];
	conexion con = new conexion();
	Connection reg = con.getConexion();
	Statement stmt = null;
	ResultSet rs = null;

	// metodos
	public void inicio_session() {
		inicio.setTitle("Login");
		inicio.setLocationRelativeTo(null);
		// x = ancho y = alto
		inicio.setBounds(500, 300, 500, 500);
		inicio.setVisible(true);
		p1.setBackground(Color.cyan);
		p1.setBounds(500, 300, 500, 500);
		p1.setLayout(null);
		inicio.add(p1);
		
	}

	// etiquetas y jtextfiels
	private void etiquetas() {
		l1.setText("Usuario");
		l1.setBounds(50, 100, 50, 80);
		l1.setVisible(true);
		p1.add(l1);

		l2.setText("Contraseña");
		l2.setBounds(50, 230, 80, 80);
		l2.setVisible(true);
		p1.add(l2);

		txtUsuario.setBounds(200, 100, 200, 40);
		txtContrasena.setBounds(200, 230, 200, 40);

		p1.add(txtUsuario);
		p1.add(txtContrasena);
	}

	private void botones() {
		b1.setText("Ingresar");
		b1.setBounds(200, 380, 100, 40);
		p1.add(b1);

		// Funcionalidad de Boton
		ActionListener ingresar = new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSession();
				inicio.setVisible(false);
				inicio.dispose();
				
		}
			
		};
		b1.addActionListener(ingresar);
	}
	
	public void ejecutar() {
		inicio_session();
		etiquetas();
		botones();
	}

	public void iniciarSession() {
		String Qusuario = null;
		String Qcontrasena = null;
		String consulta = "select usuario_admin, contrasenia_admin from administrador where usuario_admin = '"+txtUsuario.getText()+"'";
		try {
			stmt = reg.createStatement();
			rs = stmt.executeQuery(consulta);
				while(rs.next()){
					Qusuario = rs.getString("usuario_admin");
					Qcontrasena = rs.getString("contrasenia_admin");
					
				}
		} catch (Exception e) {
			
		}
		
		String usuario = txtUsuario.getText();
		String contraseña = txtContrasena.getText();
		
		if(usuario.isBlank() || contraseña.isBlank()) {
			JOptionPane.showMessageDialog(null, "Ingresa un usuario y contraseña");
		}
		else {
			if(usuario.equals(Qusuario) && contraseña.equals(Qcontrasena)) {
				JOptionPane.showMessageDialog(null, "Bienvenido");
				MenuPrincipal mp = new MenuPrincipal();
				mp.ejecutar();
			}
		}
	}
	public static void main(String[] args) {
		Login L1 = new Login();
		L1.ejecutar();
	}
}
