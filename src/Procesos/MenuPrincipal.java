package Procesos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPrincipal {

	JFrame principal = new JFrame();
	JTabbedPane pestañas = new JTabbedPane(); // Esta funcion crea pestañas

	JPanel sucursales = new JPanel();
	JPanel clientes = new JPanel();
	JPanel vendedores = new JPanel();
	JPanel productos = new JPanel();

	Sucursales_menu sm = new Sucursales_menu();
	ventas_menu vm = new ventas_menu();
	productos_menu pm = new productos_menu();
	clientes_menu cm = new clientes_menu();
	
	private void valores_iniciales() {
		
		//Configuracion del Panel principal
		principal.setTitle("Modulo Administrador");
		principal.setLocationRelativeTo(null);
		principal.setBounds(350, 100, 900, 700);
		principal.setVisible(true);
		JButton cerrar = new JButton("Cerrar Sesion");
		cerrar.setBackground(Color.red);
		cerrar.setForeground(Color.white);
		cerrar.setBounds(700,40,150,20);
		
		ActionListener funcion_cerrar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login log = new Login();
				log.ejecutar();
				principal.setVisible(false);
				
			}
			
		};
		cerrar.addActionListener(funcion_cerrar);
		
		//se agrego boton cerrar
		principal.add(cerrar);
		//se agrego las pestañas la pantalla principal con sus colores por pestaña
		principal.add(pestañas);
		sucursales.setBackground(Color.cyan);
		clientes.setBackground(Color.white);
		vendedores.setBackground(Color.green);
		productos.setBackground(Color.gray);
		
		
		//Configuracion del Layout en los panel's
		sucursales.setLayout(null);
		clientes.setLayout(null);
		vendedores.setLayout(null);
		productos.setLayout(null);
		
		//Se agrega las pestañas
		pestañas.addTab("Sucursales",sucursales);
		pestañas.addTab("Clientes", clientes);
		pestañas.addTab("Vendedores", vendedores);
		pestañas.addTab("Productos", productos);
		
		try {
			sm.ejecutar();
			vm.ejecutar();
			pm.ejecutar();
			cm.ejecutar();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sucursales.add(sm.crear);
		sucursales.add(sm.carga);
		sucursales.add(sm.actualizar);
		sucursales.add(sm.eliminar);
		sucursales.add(sm.pdf);
		sucursales.add(sm.sp);
		
		vendedores.add(vm.crear);
		vendedores.add(vm.carga);
		vendedores.add(vm.actualizar);
		vendedores.add(vm.eliminar);
		vendedores.add(vm.pdf);
		vendedores.add(vm.sp_vendedores);
		
		productos.add(pm.crear);
		productos.add(pm.carga);
		productos.add(pm.actualizar);
		productos.add(pm.eliminar);
		productos.add(pm.pdf);
		productos.add(pm.sp_productos);
		
		clientes.add(cm.crear);
		clientes.add(cm.carga);
		clientes.add(cm.actualizar);
		clientes.add(cm.eliminar);
		clientes.add(cm.pdf);
		clientes.add(cm.sp_clientes);
		
	}
	
	public void ejecutar() {
		valores_iniciales();
	}
	public static void main(String[] args) {
		MenuPrincipal mp1 = new MenuPrincipal();
		mp1.ejecutar();
	}
}
