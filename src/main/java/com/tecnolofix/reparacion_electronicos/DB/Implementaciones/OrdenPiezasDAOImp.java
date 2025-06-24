package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenPiezasDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.OrdenPiezas;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrdenPiezasDAOImp implements OrdenPiezasDAO {
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

    public ArrayList<OrdenPiezas> getAllOrdenPiezas() {
        ArrayList<OrdenPiezas> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orden_piezas");

            while (rs.next()) {
                OrdenPiezas ordenpie = new OrdenPiezas();
                ordenpie.setId(rs.getInt("id"));
                ordenpie.setFkOrden(rs.getInt("fk_orden"));
                ordenpie.setFkPieza(rs.getString("fk_pieza"));
                ordenpie.setCantidad(rs.getInt("cantidad"));

                lista.add(ordenpie);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public boolean registrarPiezaUsada(int idOrden, String idPieza, int cantidadUsada) {

        String sql = """
        INSERT INTO Orden_piezas(fk_orden, fk_pieza, cantidad) VALUES (?,?,?);
        """;

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idOrden);
            stmt.setString(2, idPieza);
            stmt.setInt(3, cantidadUsada);

            int rowsInserted = stmt.executeUpdate();
            if(rowsInserted == 0) return false;

            sql = """
            UPDATE Piezas SET stock=stock-? WHERE id=?;
            """;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cantidadUsada);
            stmt.setString(2, idPieza);
            return stmt.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
