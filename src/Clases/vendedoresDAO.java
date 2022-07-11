package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Procesos.vendedor;

public class vendedoresDAO {
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	conexion acceso = new conexion();
	Object[][] listarClientes;

	public void crear(vendedor ven) {
		String sql = "insert into vendedores values(?,?,?,?,?,?)";
		try {
			con = acceso.getConexion();
			ps = con.prepareStatement(sql);

			ps.setInt(1, ven.getCodigo());
			ps.setString(2, ven.getNombre());
			ps.setInt(3, ven.getCaja());
			ps.setInt(4, ven.getVentas());
			ps.setString(5, ven.getGenero());
			ps.setString(6, ven.getPassword());
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void modificar(vendedor ven) {
		String sql = "update vendedores set nombre_vendedor=?, caja_vendedor=?, ventas_vendedor=?, genero_vendedor=?, password_vendedor =? where codigo_vendedor=?";
		try {
			con = acceso.getConexion();
			ps = con.prepareStatement(sql);
			ps.setString(1, ven.getNombre());
			ps.setInt(2, ven.getCaja());
			ps.setInt(3, ven.getVentas());
			ps.setString(4, ven.getGenero());
			ps.setString(5, ven.getPassword());
			ps.setInt(6, ven.getCodigo());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void eliminar(int id) {
		String sql = "delete from vendedores where codigo_vendedor=?";
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
		List<vendedor> ListarVen = new ArrayList();
		String consulta = "select * from vendedores";
		try {
			con = acceso.getConexion();
			ps = con.prepareStatement(consulta);
			rs = ps.executeQuery();

			while (rs.next()) {
				vendedor ven = new vendedor();
				ven.setCodigo(rs.getInt("codigo_vendedor"));
				ven.setNombre(rs.getString("nombre_vendedor"));
				ven.setCaja(rs.getInt("caja_vendedor"));
				ven.setVentas(rs.getInt("ventas_vendedor"));
				ven.setGenero(rs.getString("genero_vendedor"));
				ven.setPassword(rs.getString("password_vendedor"));
				ListarVen.add(ven);

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return ListarVen;
	}

}
