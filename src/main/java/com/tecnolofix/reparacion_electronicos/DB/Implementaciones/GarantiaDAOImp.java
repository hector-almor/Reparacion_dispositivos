package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.GarantiaDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Garantia;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;

public class GarantiaDAOImp implements GarantiaDAO {
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

    public ArrayList<Garantia> getAllGarantias() {
        ArrayList<Garantia> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Garantias");

            while (rs.next()) {
                Garantia garan = new Garantia();
                garan.setId(rs.getInt("id"));

                Date fechaInicioSql = rs.getDate("fecha_inicio");
                LocalDate fechaInicio = null;
                if (fechaInicioSql != null) {
                    fechaInicio = fechaInicioSql.toLocalDate();
                }garan.setFechaInicio(fechaInicio);

                garan.setDuracion(rs.getInt("duracion"));

                Date fechaFinSql = rs.getDate("fecha_fin");
                LocalDate fechaFin = null;
                if (fechaFinSql != null) {
                    fechaFin = fechaInicioSql.toLocalDate();
                }garan.setFechaInicio(fechaFin);

                garan.setCobertura(rs.getString("cobertura"));

                lista.add(garan);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

}
