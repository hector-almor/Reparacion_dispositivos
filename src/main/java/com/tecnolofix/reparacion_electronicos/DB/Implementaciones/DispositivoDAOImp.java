package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.DispositivoDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Dispositivo;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DispositivoDAOImp implements DispositivoDAO {
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

    public ArrayList<Dispositivo> getAllDispositivos() {
        ArrayList<Dispositivo> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Dispositivo");

            while (rs.next()) {
                Dispositivo dispo = new Dispositivo();
                dispo.setId(rs.getInt("id"));
                dispo.setNombre(rs.getString("nombre"));
                dispo.setMarca(rs.getString("marca"));
                dispo.setTipoDispo(Dispositivo.TipoDispositivo.valueOf(rs.getString("tipo_dispo").toUpperCase()));
                dispo.setObservaciones(rs.getString("observaciones"));

                lista.add(dispo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}
