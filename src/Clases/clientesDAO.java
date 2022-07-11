package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Procesos.cliente;

public class clientesDAO {
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	conexion acceso = new conexion();
	Object[][] listarClientes;

	public void crear(cliente cli) {
		String sql = "insert into clientes values(?,?,?,?,?)";
		try {
			con = acceso.getConexion();
			ps = con.prepareStatement(sql);

			ps.setInt(1, cli.getCodigo());
			ps.setString(2, cli.getNombre());
			ps.setInt(3, cli.getNit());
			ps.setString(4, cli.getCorreo());
			ps.setString(5, cli.getGenero());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void modificar(cliente cli) {
		String sql = "update clientes set nombre_cliente=?, nit=?, correo_cliente=?, genero_cliente=? where codigo_cliente=?";

		try {
			con = acceso.getConexion();
			ps = con.prepareStatement(sql);
			ps.setString(1, cli.getNombre());
			ps.setInt(2, cli.getNit());
			ps.setString(3, cli.getCorreo());
			ps.setString(4, cli.getGenero());
			ps.setInt(5, cli.getCodigo());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void eliminar(int id) {
		String sql = "delete from clientes where codigo_cliente=?";
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
		List<cliente> ListarCli = new ArrayList();
		String consulta = "select * from clientes";
		try {
			con = acceso.getConexion();
			ps = con.prepareStatement(consulta);
			rs = ps.executeQuery();

			while (rs.next()) {
				cliente cli = new cliente();
				cli.setCodigo(rs.getInt("codigo_cliente"));
				cli.setNombre(rs.getString("nombre_cliente"));
				cli.setNit(rs.getInt("nit"));
				cli.setCorreo(rs.getString("correo_cliente"));
				cli.setGenero(rs.getString("genero_cliente"));
				ListarCli.add(cli);

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return ListarCli;
	}
}
