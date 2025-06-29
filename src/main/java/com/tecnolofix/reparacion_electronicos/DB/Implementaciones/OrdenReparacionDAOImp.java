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

            //Insertar cliente
            String sqlCliente = "INSERT INTO Clientes (nombre, telefono,correo) VALUES (?,?,?)";
            PreparedStatement psCliente = conn.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            psCliente.setString(1, cliente.getNombre());
            psCliente.setString(2, cliente.getTelefono());
            psCliente.setString(3, cliente.getCorreo());
            psCliente.executeUpdate();

            ResultSet rsCliente = psCliente.getGeneratedKeys();
            int idCliente = 0;
            if (rsCliente.next()) {
                idCliente = rsCliente.getInt(1);
            }

            //Insertar dispositivo
            String sqlDispositivo = "INSERT INTO Dispositivo (nombre, marca, tipo_dispo, observaciones) VALUES (?, ?, ?, ?)";
            PreparedStatement psDispositivo = conn.prepareStatement(sqlDispositivo, Statement.RETURN_GENERATED_KEYS);
            psDispositivo.setString(1, dispositivo.getNombre());
            psDispositivo.setString(2, dispositivo.getMarca());
            psDispositivo.setString(3, dispositivo.getTipoDispo().name());
            psDispositivo.setString(4, dispositivo.getObservaciones());
            psDispositivo.executeUpdate();

            ResultSet rsDispositivo = psDispositivo.getGeneratedKeys();
            int idDispositivo = 0;
            if (rsDispositivo.next()) {
                idDispositivo = rsDispositivo.getInt(1);
            }

            //Reparación
            stmt.setInt(1, reparacion.getId());
            stmt.setDate(2, Date.valueOf(reparacion.getFechaIng()));
            if (reparacion.getFechaEg() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(reparacion.getFechaEg()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            stmt.setString(4, reparacion.getTipoFalla().name());
            stmt.setString(5, reparacion.getDescripcion());
            stmt.setString(6, reparacion.getTipoOrden().name());
            stmt.setString(7, reparacion.getEstado().name());
            stmt.setInt(8, idCliente);
            stmt.setInt(9, idDispositivo);
            stmt.setNull(10, Types.INTEGER);
            stmt.setNull(11, Types.INTEGER);

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

        String sql = """
            SELECT o.ID AS idOrden, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden,
            d.ID AS idDispositivo, d.Marca, d.Nombre, d.Observaciones, d.tipo_dispo
            FROM Orden_reparacion o
            INNER JOIN Dispositivo d ON o.fk_dispositivo = d.id
            WHERE o.estado = "PENDIENTE";
            """;


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


                orden.setObservacionesDispositivo(rs.getString("observaciones"));

                lista.add(orden);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean enlazaOrdenConTecnico(int idOrden, int idTecnico) {
        String sql = "UPDATE Orden_reparacion SET Fk_tecnico = ?, estado = 'ASIGNADO' WHERE ID = ?";

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

        String sql = """
                SELECT o.ID AS idOrden, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden,
                d.ID AS idDispositivo, d.Nombre, d.Marca, d.Tipo_dispo, d.Observaciones
                FROM Orden_reparacion o
                INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID
                WHERE o.Estado NOT IN ('PENDIENTE');
                """;

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
                orden.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispo").toUpperCase()));
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
        OrdenCompleta ordenCompleta = new OrdenCompleta();
        String sql = """
            SELECT 
                o.ID AS o_ID, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Estado, o.Tipo_orden,
                d.ID AS d_ID, d.Nombre AS d_Nombre, d.Marca, d.Tipo_dispo, d.Observaciones,
                c.ID AS c_ID, c.Nombre AS c_Nombre, c.Correo, c.Telefono,
                t.ID AS t_ID, t.Nombre AS t_Nombre, t.Especialidad,
                g.ID AS g_ID, g.Fecha_inicio, g.Fecha_fin
            FROM Orden_reparacion o
            INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID
            INNER JOIN Clientes c ON o.Fk_cliente = c.ID
            LEFT JOIN Tecnicos t ON o.Fk_tecnico = t.ID
            LEFT JOIN Garantias g ON o.Fk_garantia = g.ID
            WHERE o.ID = ?
            """;

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idOrden);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                // Datos de la orden
                ordenCompleta.setId(rs.getInt("o_id"));
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
                disp.setTipoDispo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispo").toUpperCase()));
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
                    tec.setEspecialidad(Tecnico.Especialidad.valueOf(rs.getString("Especialidad").toUpperCase()));
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

        String sql = """
               SELECT
               o.ID, o.Fecha_ing, o.Fecha_eg, o.Tipo_falla, o.Descripcion, o.Tipo_orden, o.Estado, o.Fk_garantia,
               d.ID AS idDispositivo, d.Nombre AS nombreDispositivo, d.Marca, d.Tipo_dispo, d.Observaciones AS observacionesDispositivo,
               c.ID AS idCliente, c.Nombre AS nombreCliente, c.Correo, c.Telefono,
               t.ID AS idTecnico, t.Nombre AS nombreTecnico, t.Especialidad,
               g.ID AS idGarantia, g.Fecha_inicio, g.Fecha_fin, g.cobertura, g.duracion
               FROM Orden_reparacion o
               INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID
               INNER JOIN Clientes c ON o.Fk_cliente = c.ID
               LEFT JOIN Tecnicos t ON o.Fk_tecnico = t.ID
               LEFT JOIN Garantias g ON o.Fk_garantia = g.ID
               WHERE o.ID = ?
               """;

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
                ordenCompleta.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispo").toUpperCase()));
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
                    tec.setEspecialidad(Tecnico.Especialidad.valueOf(rs.getString("Especialidad").toUpperCase()));

                    ordenCompleta.setTecnico(tec);
                }

                // Garantía (puede ser null)
                int idGarantia = rs.getInt("idGarantia");
                if (idGarantia!=0) {
                    Garantia gar = new Garantia();
                    gar.setId(idGarantia);
                    gar.setFechaInicio(rs.getDate("Fecha_inicio").toLocalDate());
                    gar.setFechaFin(rs.getDate("Fecha_fin").toLocalDate());
                    gar.setDuracion(rs.getInt("duracion"));
                    gar.setCobertura(rs.getString("cobertura"));
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

            stmt.setString(1, OrdenReparacion.Estado.ENTREGADO.name());
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
        String sql = "UPDATE Orden_reparacion SET Estado = ?, fecha_eg=NULL, tipo_orden='REPARACION' WHERE id = ?";

        try (DB db = new DB()) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Estado destino
            stmt.setString(1, OrdenReparacion.Estado.ASIGNADO.name());

            // Tipo de orden a filtrar
            stmt.setInt(2, idRevision);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<HerramientaConCantidad> obtenerHerramientasConCantidad(int idRevision) {
        ArrayList<HerramientaConCantidad> lista = new ArrayList<>();

        String sql = """
            SELECT 
                h.id,
                h.nombre,
                h.descripcion,
                oh.cantidad_usada AS cantidadUsada,
                h.stock_disponible,
                h.stock_en_uso,
                h.stock_mantenimiento
            FROM 
                Herramientas h
            JOIN 
                Orden_herramientas oh ON h.id = oh.fk_herramienta
            JOIN 
                Orden_reparacion o ON oh.fk_orden = o.id
            WHERE 
                o.id = ?;
            """;

        try (DB db = new DB();
             Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Usamos PreparedStatement para evitar SQL injection
            pstmt.setInt(1, idRevision);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HerramientaConCantidad herramienta = new HerramientaConCantidad();

                    herramienta.setId(rs.getInt("id"));
                    herramienta.setNombre(rs.getString("nombre"));
                    herramienta.setDescripcion(rs.getString("descripcion"));
                    herramienta.setStockDisponible(rs.getInt("stock_disponible"));
                    herramienta.setStockEnUso(rs.getInt("cantidadUsada"));
                    herramienta.setStockMantenimiento(rs.getInt("stock_mantenimiento"));
                    // Agregamos la cantidad específica usada en esta orden
                    lista.add(herramienta);
                }
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
                "d.ID AS idDispositivo, d.Nombre, d.Marca, d.Tipo_dispo, d.Observaciones " +
                "FROM Orden_reparacion o " +
                "INNER JOIN Dispositivo d ON o.Fk_dispositivo = d.ID " +
                "WHERE estado NOT IN ('PENDIENTE')";

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
                orden.setTipoDispositivo(Dispositivo.TipoDispositivo.valueOf(rs.getString("Tipo_dispo").toUpperCase()));
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
        ArrayList<HerramientaConCantidad> herramientas = new ArrayList<>();
        double costoTotalOrden = 0.0d;

        try (DB db = new DB()) {
            Connection conn = db.getConnection();

            String sql = """
                    SELECT
                p.id,
                p.nombre,
                p.descripcion,
                op.cantidad,
                p.costo AS costo_unitario,
                (p.costo * op.cantidad) AS costo_total,
                (SELECT SUM(p2.costo * op2.cantidad)
                 FROM Piezas p2
                 JOIN Orden_piezas op2 ON p2.id = op2.fk_pieza
                 WHERE op2.fk_orden = ?) AS costo_total_orden
            FROM
                Orden_piezas op
            JOIN
                Piezas p ON op.fk_pieza = p.id
            WHERE
                op.fk_orden = ?;
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReparacion);
            stmt.setInt(2, idReparacion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                PiezaConCantidad pc = new PiezaConCantidad();
                pc.setId(rs.getString("id")); // Usa getInt si el id es int
                pc.setNombre(rs.getString("nombre"));
                pc.setDescripcion(rs.getString("descripcion"));
                pc.setCosto(rs.getInt("costo_unitario"));
                pc.setCantidad(rs.getInt("cantidad"));
                pc.setCostoTipoPieza(rs.getDouble("costo_total"));
                piezas.add(pc);
                costoTotalOrden = rs.getDouble("costo_total_orden");
            }

            resultado.setPiezas(piezas);
            resultado.setCostoTotalPiezas(costoTotalOrden);

            sql = """
            SELECT
                h.id,
                h.nombre,
                h.descripcion,
                oh.cantidad_usada
            FROM
                Orden_herramientas oh
            JOIN
                Herramientas h ON oh.fk_herramienta = h.id
            WHERE
                oh.fk_orden = ?;
            """;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReparacion);
            rs = stmt.executeQuery();
            while (rs.next()){
                HerramientaConCantidad h = new HerramientaConCantidad();
                h.setId(rs.getInt("id"));
                h.setNombre(rs.getString("nombre"));
                h.setDescripcion(rs.getString("descripcion"));
                h.setCantidad(rs.getInt("cantidad_usada"));
                herramientas.add(h);
            }
            resultado.setHerramientas(herramientas);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public boolean entregarReparacion(int idReparacion, Garantia garantia) {
        int idGarantiaGenerada = 0;
        String sql = """
        INSERT INTO Garantias(fecha_inicio,duracion,cobertura) VALUES(?,?,?);
        """;

        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1,java.sql.Date.valueOf(garantia.getFechaInicio()));
            stmt.setInt(2, garantia.getDuracion());
            stmt.setString(3, garantia.getCobertura());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                idGarantiaGenerada = rs.getInt(1);
            }

            sql = """
            UPDATE Orden_reparacion SET estado='ENTREGADO', fecha_eg=?, fk_garantia=? WHERE id=?;
            """;
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setInt(2, idGarantiaGenerada);
            stmt.setInt(3, idReparacion);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenesDeTecnico(int idTecnico) {
        ArrayList<OrdenConDispositivo> ordenesDeTecnico = new ArrayList<>();
        String sql = """
        SELECT *, o.id, d.id FROM Orden_reparacion o\s
        INNER JOIN Dispositivo d ON d.id = o.fk_dispositivo
        WHERE o.fk_tecnico=?
        """;
        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTecnico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                OrdenConDispositivo ord = new OrdenConDispositivo();
                ord.setId(rs.getInt("o.id"));
                ord.setFechaIng(rs.getDate("fecha_ing").toLocalDate());
                ord.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(rs.getString("tipo_falla")));
                ord.setDescripcion(rs.getString("descripcion"));
                ord.setEstado(OrdenReparacion.Estado.valueOf(rs.getString("estado")));
                ord.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(rs.getString("tipo_orden")));
                ord.setFkCliente(rs.getInt("fk_cliente"));
                ord.setFkTecnico(rs.getInt("fk_tecnico"));
                ord.setIdDispositivo(rs.getInt("d.id"));
                ord.setNombreDispositivo(rs.getString("nombre"));
                ordenesDeTecnico.add(ord);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ordenesDeTecnico;
    }

    @Override
    public boolean cambiarEstadoReparacion(int idReparacion, String estado) {
        String sql = """
        UPDATE Orden_reparacion SET estado=? WHERE id=?;
        """;
        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, estado);
            stmt.setInt(2, idReparacion);
            return stmt.executeUpdate()>0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarDescripcionReparacion(int idReparacion, String descripcion) {
        String sql = """
        UPDATE Orden_reparacion SET descripcion=? WHERE id=?;
        """;
        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, descripcion);
            stmt.setInt(2, idReparacion);
            return stmt.executeUpdate()>0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Pieza> obtenerPiezasReparacion(int idReparacion) {
        ArrayList<Pieza> piezas = new ArrayList<>();
        String sql = """
        SELECT *, op.id, p.id FROM Orden_piezas op
        INNER JOIN Piezas p ON op.fk_pieza = p.id
        WHERE fk_orden = ?;
        """;
        try(DB db = new DB()){
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReparacion);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Pieza p = new Pieza();
                p.setId(rs.getString("p.id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("cantidad"));
                p.setCosto(rs.getDouble("costo"));
                piezas.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return piezas;
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
