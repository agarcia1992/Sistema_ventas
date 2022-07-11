package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Procesos.producto;
import Procesos.sucursal;


public class productosDAO {
	PreparedStatement ps;
    ResultSet rs;
    Connection con;    
    conexion acceso = new conexion();
    Object [][] listarProductos;
    
    public void crear (producto pro) {
    	String sql = "insert into productos values(?,?,?,?,?)";
    	
    	try {
    		con = acceso.getConexion();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getDescripcion());
            ps.setInt(4, pro.getCantidad());
            ps.setDouble(5, pro.getPrecio());
            ps.executeUpdate();
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	
        
    }
    
    public void modificar(producto pro) {
    	String sql = "update productos set nombre_producto=?, descripcion_producto=?, cantidad_producto=?, precio_producto=? where codigo_producto=?";
        
    	try {
    		con = acceso.getConexion();
            ps = con.prepareStatement(sql);            
            ps.setString(1, pro.getNombre());
            ps.setString(2, pro.getDescripcion());
            ps.setInt(3, pro.getCantidad());
            ps.setDouble(4, pro.getPrecio());
            ps.setInt(5, pro.getCodigo());
            ps.executeUpdate();
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	
    }
    
    public void eliminar(int id) {
        String sql = "delete from productos where codigo_producto=?";
        try {
            con = acceso.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
        	System.out.println(e);
        }
    }
    
    public List listar() {
    	List<producto> ListarPro = new ArrayList();
		String consulta = "select * from productos";
        try {
            con = acceso.getConexion();
            ps = con.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()) {
            	producto pro = new producto();
            	pro.setCodigo(rs.getInt("codigo_producto"));
            	pro.setNombre(rs.getString("nombre_producto"));
            	pro.setDescripcion(rs.getString("descripcion_producto"));
            	pro.setCantidad(rs.getInt("cantidad_producto"));
            	pro.setPrecio(rs.getDouble("precio_producto"));
            	ListarPro.add(pro);
            	
            }
                  	
        } catch (Exception e) {        	
            System.out.println(e.toString());
        }
    	
    	return ListarPro;
    }

}
