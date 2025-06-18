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
import java.util.List;

import static com.tecnolofix.reparacion_electronicos.Models.OrdenReparacion.TipoOrden.REPARACION;

public class OrdenReparacionDAOImp implements OrdenReparacionDAO {


    @Override
    public boolean registrarRevision(OrdenReparacion reparacion, Dispositivo dispositivo, Cliente cliente) {
        String sql = "INSERT INTO Orden_reparacion " +
                "(ID, Fecha_ing, Fecha_eg, Tipo_falla, Descripcion, Tipo_orden, Estado, Fk_cliente, Fk_dispositivo, Fk_tecnico, Fk_garantia) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, reparacion.getId());
            stmt.setDate(2, java.sql.Date.valueOf(reparacion.getFechaIng()));
            stmt.setDate(3, java.sql.Date.valueOf(reparacion.getFechaEg()));
            stmt.setString(4, reparacion.getTipoFalla().name());
            stmt.setString(5, reparacion.getDescripcion());
            stmt.setString(6, reparacion.getTipoOrden().name());
            stmt.setString(7, reparacion.getEstado().name());
            stmt.setInt(8, cliente.getId());
            stmt.setInt(9, dispositivo.getId());
            stmt.setInt(10, reparacion.getFkTecnico());
            stmt.setInt(11, reparacion.getFkGarantia());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenesConDispositivo() {
        ArrayList<OrdenConDispositivo> lista = new ArrayList<>();

        String sql = "SELECT " +
                "o.ID AS idOrden, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden, " +
                "d.ID AS idDispositivo, d.Marca, d.Modelo, d.Numero_serie " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                OrdenConDispositivo orden = new OrdenConDispositivo();

                orden.setId(rs.getInt("idOrden"));

                Date fechaIngSQL = rs.getDate("fecha_ing");
                LocalDate fechaIng = null;
                if (fechaIngSQL!= null) {
                    fechaIng = fechaIngSQL.toLocalDate();
                }orden.setFechaIng(fechaIng);



                Date fechaEgSQL = rs.getDate("fecha_eg");
                LocalDate fechaEg = null;
                if (fechaEgSQL!= null) {
                    fechaEg = fechaEgSQL.toLocalDate();
                }orden.setFechaEg(fechaEg);

                orden.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("tipo_falla").toUpperCase()));

                orden.setDescripcion(rs.getString("descripcion"));

                orden.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("tipo_orden").toUpperCase()));

                orden.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("estado").toUpperCase()));


                orden.setIdDispositivo(rs.getInt("idDispositivo"));
                orden.setNombreDispositivo(rs.getString("nombre"));
                orden.setMarcaDispositivo(rs.getString("marca"));

                orden.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("tipo_dispo").toUpperCase()));


                orden.setObservacionesDispositivo(rs.getString("obervaciones"));

                lista.add(orden);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean enlazaOrdenConTecnico(int idOrden, int idTecnico) {
        String sql = "UPDATE Orden_reparacion SET Fk_tecnico = ? WHERE ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idTecnico);
            stmt.setInt(2, idOrden);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Si al menos una fila fue actualizada, fue exitoso

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<OrdenConDispositivo> getAllOrdenesConDispositivo() {
        ArrayList<OrdenConDispositivo> lista = new ArrayList<>();

        String sql = "SELECT " +
                "o.ID AS idOrden, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden, " +
                "d.ID AS idDispositivo, d.Nombre, d.Marca, d.Modelo, d.Tipo_dispositivo, d.Observaciones " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID " +
                "WHERE o.Estado NOT IN ('ASIGNADO', 'CANCELADO')";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                OrdenConDispositivo orden = new OrdenConDispositivo();

                orden.setId(rs.getInt("idOrden"));

                Date fechaIngSQL = rs.getDate("Fecha_ing");
                LocalDate fechaIng = (fechaIngSQL != null) ? fechaIngSQL.toLocalDate() : null;
                orden.setFechaIng(fechaIng);

                Date fechaEgSQL = rs.getDate("Fecha_eg");
                LocalDate fechaEg = (fechaEgSQL != null) ? fechaEgSQL.toLocalDate() : null;
                orden.setFechaEg(fechaEg);

                orden.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("Tipo_falla").toUpperCase()));
                orden.setDescripcion(rs.getString("Descripcion"));
                orden.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("Estado").toUpperCase()));
                orden.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("Tipo_orden").toUpperCase()));

                orden.setIdDispositivo(rs.getInt("idDispositivo"));
                orden.setNombreDispositivo(rs.getString("Nombre"));
                orden.setMarcaDispositivo(rs.getString("Marca"));
                orden.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispositivo").toUpperCase()));
                orden.setObservacionesDispositivo(rs.getString("Observaciones"));

                lista.add(orden);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public OrdenCompleta obtenerRevisionCompleta(int idOrden) {
        OrdenCompleta ordenCompleta = null;

        String sql = "SELECT " +
                "o.ID, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden, " +
                "d.ID AS d_ID, d.Nombre AS d_Nombre, d.Marca, d.Modelo, d.Tipo_dispositivo, d.Observaciones, " +
                "c.ID AS c_ID, c.Nombre AS c_Nombre, c.Correo, c.Telefono, c.Direccion, " +
                "t.ID AS t_ID, t.Nombre AS t_Nombre, t.Especialidad, " +
                "g.ID AS g_ID, g.Tipo_garantia, g.Fecha_inicio, g.Fecha_fin " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID " +
                "INNER JOIN Cliente c ON o.Fk_cliente = c.ID " +
                "LEFT JOIN Tecnico t ON o.Fk_tecnico = t.ID " +
                "LEFT JOIN Garantia g ON o.Fk_garantia = g.ID " +
                "WHERE o.ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idOrden);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ordenCompleta = new OrdenCompleta();

                // Datos de la orden
                ordenCompleta.setId(rs.getInt("ID"));
                Date fi = rs.getDate("Fecha_ing");
                ordenCompleta.setFechaIng(fi != null ? fi.toLocalDate() : null);
                Date fe = rs.getDate("Fecha_eg");
                ordenCompleta.setFechaEg(fe != null ? fe.toLocalDate() : null);
                ordenCompleta.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("Tipo_falla").toUpperCase()));
                ordenCompleta.setDescripcion(rs.getString("Descripcion"));
                ordenCompleta.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("Estado").toUpperCase()));
                ordenCompleta.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("Tipo_orden").toUpperCase()));

                // Dispositivo
                Dispositivo disp = new Dispositivo();
                disp.setId(rs.getInt("d_ID"));
                disp.setNombre(rs.getString("d_Nombre"));
                disp.setMarca(rs.getString("Marca"));
                disp.setTipoDispo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispositivo").toUpperCase()));
                disp.setObservaciones(rs.getString("Observaciones"));
                ordenCompleta.setIdDispositivo(disp.getId());
                ordenCompleta.setNombreDispositivo(disp.getNombre());
                ordenCompleta.setMarcaDispositivo(disp.getMarca());
                ordenCompleta.setTipoDispositivo(disp.getTipoDispo());
                ordenCompleta.setObservacionesDispositivo(disp.getObservaciones());

                // Cliente
                Cliente cli = new Cliente();
                cli.setId(rs.getInt("c_ID"));
                cli.setNombre(rs.getString("c_Nombre"));
                cli.setTelefono(rs.getString("Telefono"));
                cli.setCorreo(rs.getString("Correo"));
                ordenCompleta.setCliente(cli);

                // Técnico (opcional)
                int idTec = rs.getInt("t_ID");
                if (!rs.wasNull()) {
                    Tecnico tec = new Tecnico();
                    tec.setId(idTec);
                    tec.setNombre(rs.getString("t_Nombre"));
                    tec.setEspecialidad(Tecnico.Especialidad.valueOf(rs.getString("especialidad").toUpperCase()));
                    ordenCompleta.setTecnico(tec);
                }

                // Garantía (opcional)
                int idGar = rs.getInt("g_ID");
                if (!rs.wasNull()) {
                    Garantia gar = new Garantia();
                    gar.setId(idGar);
                    Date fInicio = rs.getDate("Fecha_inicio");
                    Date fFin = rs.getDate("Fecha_fin");
                    gar.setFechaInicio(fInicio != null ? fInicio.toLocalDate() : null);
                    gar.setFechaFin(fFin != null ? fFin.toLocalDate() : null);
                    ordenCompleta.setGarantia(gar);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordenCompleta;
    }

    @Override
    public OrdenCompleta obtenerReparacionCompleta(int idOrden) {
        OrdenCompleta ordenCompleta = null;

        String sql = "SELECT " +
                "o.ID, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Tipo_orden, o.Estado, o.Fk_garantia, " +
                "d.ID AS idDispositivo, d.Nombre AS nombreDispositivo, d.Marca, d.Tipo_dispositivo, d.Observaciones AS observacionesDispositivo, " +
                "c.ID AS idCliente, c.Nombre AS nombreCliente, c.Correo, c.Telefono, c.Direccion, " +
                "t.ID AS idTecnico, t.Nombre AS nombreTecnico, t.Especialidad, " +
                "g.ID AS idGarantia, g.Fecha_inicio, g.Fecha_fin " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID " +
                "INNER JOIN Cliente c ON o.Fk_cliente = c.ID " +
                "LEFT JOIN Tecnico t ON o.Fk_tecnico = t.ID " +
                "LEFT JOIN Garantia g ON o.Fk_garantia = g.ID " +
                "WHERE o.ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idOrden);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ordenCompleta = new OrdenCompleta();

                // Orden
                ordenCompleta.setId(rs.getInt("ID"));

                Date fechaIngSQL = rs.getDate("Fecha_ing");
                if (fechaIngSQL != null) {
                    ordenCompleta.setFechaIng(fechaIngSQL.toLocalDate());
                }

                Date fechaEgSQL = rs.getDate("Fecha_eg");
                if (fechaEgSQL != null) {
                    ordenCompleta.setFechaEg(fechaEgSQL.toLocalDate());
                }

                ordenCompleta.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("Tipo_falla").toUpperCase()));
                ordenCompleta.setDescripcion(rs.getString("Descripcion"));
                ordenCompleta.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("Tipo_orden").toUpperCase()));
                ordenCompleta.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("Estado").toUpperCase()));
                ordenCompleta.setFkGarantia(rs.getInt("Fk_garantia"));

                // Dispositivo
                ordenCompleta.setIdDispositivo(rs.getInt("idDispositivo"));
                ordenCompleta.setNombreDispositivo(rs.getString("nombreDispositivo"));
                ordenCompleta.setMarcaDispositivo(rs.getString("Marca"));
                ordenCompleta.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispositivo").toUpperCase()));
                ordenCompleta.setObservacionesDispositivo(rs.getString("observacionesDispositivo"));

                // Cliente
                Cliente cli = new Cliente();
                cli.setId(rs.getInt("idCliente"));
                cli.setNombre(rs.getString("nombreCliente"));
                cli.setTelefono(rs.getString("Telefono"));
                cli.setCorreo(rs.getString("Correo"));
                ordenCompleta.setCliente(cli);

                // Técnico (puede ser null)
                int idTec = rs.getInt("idTecnico");
                if (!rs.wasNull()) {
                    Tecnico tec = new Tecnico();
                    tec.setId(idTec);
                    tec.setNombre(rs.getString("nombreTecnico"));
                    tec.setEspecialidad(Tecnico.Especialidad.valueOf(rs.getString("especialidad").toUpperCase()));

                    ordenCompleta.setTecnico(tec);
                }

                // Garantía (puede ser null)
                int idGarantia = rs.getInt("idGarantia");
                if (!rs.wasNull()) {
                    Garantia gar = new Garantia();
                    gar.setId(idGarantia);
                    gar.setFechaInicio(rs.getDate("Fecha_inicio").toLocalDate());
                    gar.setFechaFin(rs.getDate("Fecha_fin").toLocalDate());
                    ordenCompleta.setGarantia(gar);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordenCompleta;
    }

    @Override
    public boolean cancelarRevision(int idRevision, LocalDate fechaEgreso) {
        String sql = "UPDATE Orden_reparacion SET Estado = ?, Fecha_eg = ? WHERE ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, OrdenReparacion.Estado.CANCELADO.name());
            stmt.setDate(2, java.sql.Date.valueOf(fechaEgreso));
            stmt.setInt(3, idRevision);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean marcarParaReparacion(int idRevision) {
        String sql = "UPDATE Orden_reparacion SET Estado = ? WHERE Tipo_orden = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Estado destino
            stmt.setString(1, OrdenReparacion.Estado.ASIGNADO.name());

            // Tipo de orden a filtrar
            stmt.setString(2, OrdenReparacion.TipoOrden.REPARACION.name());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<HerramientaConCantidad> obtenerHerramientasConCantidad() {
        ArrayList<HerramientaConCantidad> lista = new ArrayList<>();

        String sql = "SELECT ID, Nombre, Tipo, Marca, Cantidad FROM Herramienta";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                HerramientaConCantidad herramienta = new HerramientaConCantidad();

                herramienta.setId(rs.getInt("id"));
                herramienta.setNombre(rs.getString("nombre"));
                herramienta.setDescripcion(rs.getString("descripcion"));  // si es ENUM, puedes usar .name()
                herramienta.setStockDisponible(rs.getInt("stock_disponible"));
                herramienta.setStockEnUso(rs.getInt("stock_en_uso"));
                herramienta.setStockMantenimiento(rs.getInt("stock_mantenimiento"));

                lista.add(herramienta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenReparacionConDispositivo() {
        ArrayList<OrdenConDispositivo> lista = new ArrayList<>();

        String sql = "SELECT " +
                "o.ID AS idOrden, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden, " +
                "d.ID AS idDispositivo, d.Nombre, d.Marca, d.Tipo_dispositivo, d.Observaciones " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                OrdenConDispositivo orden = new OrdenConDispositivo();

                orden.setId(rs.getInt("idOrden"));

                Date fechaIngSQL = rs.getDate("Fecha_ing");
                orden.setFechaIng(fechaIngSQL != null ? fechaIngSQL.toLocalDate() : null);

                Date fechaEgSQL = rs.getDate("Fecha_eg");
                orden.setFechaEg(fechaEgSQL != null ? fechaEgSQL.toLocalDate() : null);

                orden.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("Tipo_falla").toUpperCase()));
                orden.setDescripcion(rs.getString("Descripcion"));
                orden.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("Tipo_orden").toUpperCase()));
                orden.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("Estado").toUpperCase()));

                orden.setIdDispositivo(rs.getInt("idDispositivo"));
                orden.setNombreDispositivo(rs.getString("Nombre"));
                orden.setMarcaDispositivo(rs.getString("Marca"));
                orden.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispositivo").toUpperCase()));
                orden.setObservacionesDispositivo(rs.getString("Observaciones"));

                lista.add(orden);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public HerramientasPiezasConCosto obtenerHerramientasPiezasConCosto(int idReparacion) {
        HerramientasPiezasConCosto resultado = new HerramientasPiezasConCosto();
        ArrayList<PiezaConCantidad> piezas = new ArrayList<>();
        double costoTotal = 0.0;

        try (DB db = new DB()) {
            Connection conn = db.getConnection();

            String sql = "SELECT p.ID, p.Nombre, up.Cantidad, up.Costo_unitario " +
                    "FROM Uso_pieza up " +
                    "INNER JOIN Pieza p ON up.Fk_pieza = p.ID " +
                    "WHERE up.Fk_orden = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idReparacion);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    PiezaConCantidad pc = new PiezaConCantidad();
                    pc.setId(rs.getString("id")); // Usa getInt si el id es int
                    pc.setNombre(rs.getString("Nombre"));

                    // Agrega estos si los tienes en tu clase y si quieres incluirlos:
                    pc.setStock(rs.getInt("Cantidad"));
                    pc.setCosto(rs.getDouble("Costo_unitario"));

                    piezas.add(pc);
                    costoTotal += rs.getInt("Cantidad") * rs.getDouble("Costo_unitario");
                }
            }

            resultado.setPiezas(piezas);
            resultado.setCostoTotalPiezas(costoTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public boolean entregarReparacion(int idReparacion, Garantia garantia) {
        String sql = "UPDATE Orden_reparacion SET estado = ?, Fk_garantia = ?, fecha_eg = ? WHERE ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, OrdenReparacion.Estado.ENTREGADO.name()); // Cambia el estado a ENTREGADO
                stmt.setInt(2, garantia.getId());                           // Asigna la garantía
                stmt.setDate(3, Date.valueOf(LocalDate.now()));             // Fecha actual como fecha de egreso
                stmt.setInt(4, idReparacion);                               // ID de la orden

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenesDeTecnico(int idTecnico) {
        ArrayList<OrdenConDispositivo> lista = new ArrayList<>();

        String sql = "SELECT " +
                "o.ID AS idOrden, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden, " +
                "d.ID AS idDispositivo, d.Nombre, d.Marca, d.Modelo, d.Numero_serie, d.Tipo_dispositivo, d.Observaciones_dispositivo " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID " +
                "WHERE o.Fk_tecnico = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idTecnico);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    OrdenConDispositivo orden = new OrdenConDispositivo();

                    // Datos de la orden
                    orden.setId(rs.getInt("idOrden"));

                    Date fechaIngSQL = rs.getDate("fecha_ing");
                    if (fechaIngSQL != null) {
                        orden.setFechaIng(fechaIngSQL.toLocalDate());
                    }

                    Date fechaEgSQL = rs.getDate("fecha_eg");
                    if (fechaEgSQL != null) {
                        orden.setFechaEg(fechaEgSQL.toLocalDate());
                    }

                    orden.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("tipo_falla").toUpperCase()));
                    orden.setDescripcion(rs.getString("descripcion"));
                    orden.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("estado").toUpperCase()));
                    orden.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("tipo_orden").toUpperCase()));

                    // Datos del dispositivo
                    orden.setIdDispositivo(rs.getInt("idDispositivo"));
                    orden.setNombreDispositivo(rs.getString("nombre"));
                    orden.setMarcaDispositivo(rs.getString("marca"));
                    orden.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("tipo_dispositivo").toUpperCase()));
                    orden.setObservacionesDispositivo(rs.getString("observaciones_dispositivo"));

                    lista.add(orden);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean cambiarEstadoReparacion(int idReparacion, String estado) {
        String sql = "UPDATE Orden_reparacion SET Estado = ? WHERE ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, estado.toUpperCase());  // Asegura coincidencia con Enum o convención en BD
                stmt.setInt(2, idReparacion);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarDescripcionReparacion(int idReparacion, String descripcion) {
        String sql = "UPDATE Orden_reparacion SET Descripcion = ? WHERE ID = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, descripcion);
                stmt.setInt(2, idReparacion);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                }ordenrep.setFechaEg(fechaEg);


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
