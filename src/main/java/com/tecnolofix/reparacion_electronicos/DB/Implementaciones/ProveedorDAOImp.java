package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.ProveedorDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Proveedor;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.*;
import java.util.ArrayList;

public class ProveedorDAOImp implements ProveedorDAO {

    public boolean loginTecnico(Tecnico t) {
        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM Tecnicos WHERE usuario=? AND contraseña=?"
            );
            stmt.setString(1, t.getUsuario());
            stmt.setString(2, t.getContraseña());
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // devuelve true si encuentra coincidencia

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Proveedor> getAllProveedores() {
        ArrayList<Proveedor> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Proveedores");

            while (rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setId(rs.getInt("id"));
                prov.setNombre(rs.getString("nombre"));
                prov.setCorreo(rs.getString("correo"));
                prov.setTelefono(rs.getString("telefono"));
                prov.setDireccion(rs.getString("direccion"));

                lista.add(prov);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public boolean registrarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO Proveedor (Id, Nombre, Correo, Telefono, Direccion) VALUES (?, ?, ?, ?, ?)";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, proveedor.getId());
            stmt.setString(2, proveedor.getNombre());
            stmt.setString(3, proveedor.getCorreo());
            stmt.setString(4, proveedor.getTelefono());
            stmt.setString(5, proveedor.getDireccion());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
