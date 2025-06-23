package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.PiezaDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Pieza;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.*;
import java.util.ArrayList;

public class PiezaDAOImp implements PiezaDAO {

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
    public ArrayList<Pieza> getAllPiezas() {
        ArrayList<Pieza> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Piezas");

            while (rs.next()) {
                Pieza piez = new Pieza();
                piez.setId(rs.getString("id"));
                piez.setNombre(rs.getString("nombre"));
                piez.setDescripcion(rs.getString("descripcion"));
                piez.setStock(rs.getInt("stock"));
                piez.setCosto(rs.getDouble("costo"));

                lista.add(piez);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public boolean registrarCompras(ArrayList<Pieza> piezas, int idProveedor) {
        String sql = "INSERT INTO Compras (nombre, cantidad, precio_unit, id_proveedor, fecha_compra) " +
                "VALUES (?, ?, ?, ?, NOW())";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Pieza pieza : piezas) {
                stmt.setString(1, pieza.getNombre());
                stmt.setInt(2, pieza.getStock());
                stmt.setDouble(3, pieza.getCosto());
                stmt.setInt(4, idProveedor);
                stmt.addBatch();  // Agrega a un lote para ejecutar todo junto
            }

            int[] resultados = stmt.executeBatch();  // Ejecuta todos los INSERTS juntos

            // Verificamos si todos los inserts fueron exitosos
            for (int result : resultados) {
                if (result == Statement.EXECUTE_FAILED) {
                    return false;  // Si alguna falla, retornamos false
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
