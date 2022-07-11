package Clases;

import java.sql.*;

import javax.swing.JOptionPane;

import Procesos.producto;

public class funciones_productos {
	PreparedStatement ps;
    ResultSet rs;
    Connection con;    
    conexion acceso = new conexion();
    producto p = new producto ();
    
    public producto buscar_productos(int id, int cantidad) {
    	String consulta = "select * from productos";
    	
    	try {
			con = acceso.getConexion();
			ps = con.prepareStatement(consulta);
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				if(id == rs.getInt("codigo_producto")) {
					if(cantidad < rs.getInt("cantidad_producto")) {
						p.setCodigo(rs.getInt("codigo_producto"));
						p.setNombre(rs.getString("nombre_producto"));
						p.setCantidad(rs.getInt("cantidad_producto"));
						p.setPrecio(rs.getDouble("precio_producto"));
						return p;
					}else {
						JOptionPane.showMessageDialog(null, "Cantidad solicitada mayor al inventario, se cuenta con: "+rs.getInt("cantidad_producto"));
					}
				}
				
				}
				
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
    	
    }
}
