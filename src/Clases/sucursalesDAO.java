package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Procesos.sucursal;

public class sucursalesDAO {
	PreparedStatement ps;
    ResultSet rs;
    Connection con;    
    conexion acceso = new conexion();
    Object [][] listarSucursales;
    
    
    public void crear (sucursal su) {
    	String sql = "insert into sucursales(codigo_sucursal,nombre_sucursal,direccion_sucursal,correo_sucursal,telefono)values(?,?,?,?,?)";
    	
    	try {
    		con = acceso.getConexion();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, su.getCodigo());
            ps.setString(2, su.getNombre());
            ps.setString(3, su.getDireccion());
            ps.setString(4, su.getCorreo());
            ps.setInt(5, su.getTelefono());
            ps.executeUpdate();
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	
        
    }
    
    public void modificar(sucursal s) {
    	String sql = "update sucursales set nombre_sucursal=?, direccion_sucursal=?, correo_sucursal=?, telefono=? where codigo_sucursal=?";
        
    	try {
    		con = acceso.getConexion();
            ps = con.prepareStatement(sql);            
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getDireccion());
            ps.setString(3, s.getCorreo());
            ps.setInt(4, s.getTelefono());
            ps.setInt(5, s.getCodigo());
            ps.executeUpdate();
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	
    }
    
    public void eliminar(int id) {
        String sql = "delete from sucursales where codigo_sucursal=?";
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
    	List<sucursal> ListarS = new ArrayList();
		String consulta = "select * from sucursales";
        try {
            con = acceso.getConexion();
            ps = con.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()) {
            	sucursal su = new sucursal();
            	su.setCodigo(rs.getInt("codigo_sucursal"));
            	su.setNombre(rs.getString("nombre_sucursal"));
            	su.setDireccion(rs.getString("direccion_sucursal"));
            	su.setCorreo(rs.getString("correo_sucursal"));
            	su.setTelefono(rs.getInt("telefono"));
            	ListarS.add(su);
            	
            }
                  	
        } catch (Exception e) {        	
            System.out.println(e.toString());
        }
    	
    	return ListarS;
    }
}
