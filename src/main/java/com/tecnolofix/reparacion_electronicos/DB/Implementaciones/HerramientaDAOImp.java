package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.HerramientaDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Herramienta;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;

import java.sql.*;
import java.util.ArrayList;

public class HerramientaDAOImp implements HerramientaDAO {

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
    public ArrayList<Herramienta> getAllHerramientas() {
        ArrayList<Herramienta> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Herramientas");

            while (rs.next()) {
                Herramienta herra = new Herramienta();
                herra.setId(rs.getInt("id"));
                herra.setNombre(rs.getString("nombre"));
                herra.setDescripcion(rs.getString("descripcion"));
                herra.setStockDisponible(rs.getInt("stock_disponible"));
                herra.setStockEnUso(rs.getInt("stock_en_uso"));
                herra.setStockMantenimiento(rs.getInt("stock_mantenimiento"));

                lista.add(herra);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public boolean actualizarStockDisponible(int idHerramienta, int stockDisponible, int stockEnUso) {
        String sql = """
        UPDATE Herramientas SET stock_disponible=stock_disponible+(?), stock_en_uso=stock_en_uso+(?) WHERE id=?;
        """;
        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stockDisponible);
            stmt.setInt(2, stockEnUso);
            stmt.setInt(3, idHerramienta);
            return stmt.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
