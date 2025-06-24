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
    public boolean registrarCompras(ArrayList<Pieza> piezasAdd,ArrayList<Pieza> piezasUpdate,ArrayList<Pieza> compraTotal, int idProveedor) {
        String sql = """
        INSERT INTO Piezas(id, nombre, descripcion, stock, costo) VALUES (?,?,?,?,?);
        """;

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Pieza pieza : piezasAdd) {
                stmt.setString(1, pieza.getId());
                stmt.setString(2, pieza.getNombre());
                stmt.setString(3, pieza.getDescripcion());
                stmt.setInt(4, pieza.getStock());
                stmt.setDouble(5, pieza.getCosto());
                stmt.addBatch();  // Agrega a un lote para ejecutar todo junto
            }
            int[] resultado = stmt.executeBatch();
            for(int r: resultado) {
                if(r==Statement.EXECUTE_FAILED) return false;
            }

            sql= """
            UPDATE Piezas SET stock=stock+?, costo=? WHERE id=?;
            """;
            stmt = conn.prepareStatement(sql);
            for (Pieza pieza : piezasUpdate) {
                stmt.setInt(1, pieza.getStock());
                stmt.setDouble(2, pieza.getCosto());
                stmt.setString(3, pieza.getId());
                System.out.println("%d %f %s".formatted(pieza.getStock(), pieza.getCosto(), pieza.getId()));
                stmt.addBatch();
            }
            resultado = stmt.executeBatch();
            for(int r: resultado) {
                if(r==Statement.EXECUTE_FAILED) return false;
            }

            sql= """
            INSERT INTO Compras(cantidad,precio,fk_proveedor,fk_pieza) VALUES(?,?,?,?);
            """;
            stmt = conn.prepareStatement(sql);
            for(Pieza pieza : compraTotal) {
                stmt.setInt(1,pieza.getStock());
                stmt.setDouble(2,pieza.getCosto());
                stmt.setInt(3,idProveedor);
                stmt.setString(4,pieza.getId());
                stmt.addBatch();
            }
            resultado = stmt.executeBatch();
            for(int r: resultado) {
                if(r==Statement.EXECUTE_FAILED) return false;
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
