package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.Models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;

public class OrdenReparacionDAOImp implements OrdenReparacionDAO {
    @Override
    public boolean registrarRevision(OrdenReparacion reparacion, Dispositivo dispositivo, Cliente cliente) {
        return true;
    }

    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenesConDispositivo() {
        return null;
    }

    @Override
    public boolean enlazaOrdenConTecnico(int idOrden, int idTecnico) {
        return false;
    }

    @Override
    public ArrayList<OrdenConDispositivo> getAllOrdenesConDispositivo() {
        return null;
    }

    @Override
    public OrdenCompleta obtenerRevisionCompleta(int idOrden) {
        return null;
    }

    @Override
    public OrdenCompleta obtenerReparacionCompleta(int idOrden) {
        return null;
    }

    @Override
    public boolean cancelarRevision(int idRevision, LocalDate fechaEgreso) {
        return false;
    }

    @Override
    public boolean marcarParaReparacion(int idRevision) {
        return false;
    }

    @Override
    public ArrayList<HerramientaConCantidad> obtenerHerramientasConCantidad() {
        return null;
    }

    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenReparacionConDispositivo() {
        return null;
    }

    @Override
    public HerramientasPiezasConCosto obtenerHerramientasPiezasConCosto(int idReparacion) {
        return null;
    }

    @Override
    public boolean entregarReparacion(int idReparacion, Garantia garantia) {
        return false;
    }

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

    public ArrayList<OrdenReparacion> getAllOrdenReparacion(){
        ArrayList<OrdenReparacion> lista = new ArrayList<>();

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orden_reparacion");

            while (rs.next()) {
                OrdenReparacion ordenrep = new OrdenReparacion();
                ordenrep.setId(rs.getInt("id"));

                Date fechaIngSQL = rs.getDate("fecha_ing");
                LocalDate fechaIng = null;
                if (fechaIngSQL!= null) {
                    fechaIng = fechaIngSQL.toLocalDate();
                }ordenrep.setFechaIng(fechaIng);

                Date fechaEgSQL = rs.getDate("fecha_eg");
                LocalDate fechaEg = null;
                if (fechaEgSQL!= null) {
                    fechaEg = fechaEgSQL.toLocalDate();
                }ordenrep.setFechaIng(fechaIng);


                ordenrep.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("tipo_falla").toUpperCase()));

                ordenrep.setDescripcion(rs.getString("descripcion"));

                ordenrep.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("tipo_orden").toUpperCase()));

                ordenrep.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("estado").toUpperCase()));


                ordenrep.setFkCliente(rs.getInt("fk_cliente"));
                ordenrep.setFkDispositivo(rs.getInt("fk_dispositivo"));
                ordenrep.setFkTecnico(rs.getInt("fk_tecnico"));
                ordenrep.setFkGarantia(rs.getInt("fk_garantia"));




                lista.add(ordenrep);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}
