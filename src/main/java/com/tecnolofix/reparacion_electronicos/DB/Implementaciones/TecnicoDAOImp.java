package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.TecnicoDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TecnicoDAOImp implements TecnicoDAO {

    @Override
    public boolean loginTecnico(Tecnico t) {
        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tecnicos WHERE usuario=? AND contraseña=?");
            stmt.setString(1, t.getUsuario());
            stmt.setString(2,t.getContraseña());
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Tecnico> getAllTecnicos() {
        return null;
    }
}
