package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenHerramientasDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.OrdenHerramientas;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrdenHerramientasDAOImp implements OrdenHerramientasDAO {
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

    public ArrayList<OrdenHerramientas> getAllOrdenHerramientas() {
        ArrayList<OrdenHerramientas> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orden_herramientas");

            while (rs.next()) {
                OrdenHerramientas ordenherra = new OrdenHerramientas();
                ordenherra.setId(rs.getInt("id"));
                ordenherra.setFkOrden(rs.getInt("fk_orden"));
                ordenherra.setFkHerramienta(rs.getInt("fk_herramienta"));
                ordenherra.setCantidadUsada(rs.getInt("cantidad_usada"));

                lista.add(ordenherra);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}
