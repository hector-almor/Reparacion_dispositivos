package com.tecnolofix.reparacion_electronicos.Models;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;
import java.sql.*;
import java.awt.Desktop;
import java.io.File;


public class PdfOrdenReparacion {
    private static final String URL = "jdbc:mysql://localhost:3306/reparacion_dispositivos";
    private static final String USER = "admin";
    private static final String PASSWORD = "Administrador123";

    public void generarPDF(int idOrden, String destino) throws IOException {
        PdfWriter writer = new PdfWriter(destino);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);


        document.add(new Paragraph("Resumen de Orden de Reparación").setBold());

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT \n" +
                    "    o.id,\n" +
                    "    o.fecha_ing,\n" +
                    "    o.fecha_eg,\n" +
                    "    o.tipo_falla,\n" +
                    "    o.descripcion,\n" +
                    "    o.tipo_orden,\n" +
                    "    o.estado,\n" +
                    "    c.nombre AS nombre_cliente,\n" +
                    "    d.nombre AS nombre_dispositivo,\n" +
                    "    t.nombre AS nombre_tecnico,\n" +
                    "    g.cobertura AS cobertura_garantia    \n" +
                    "FROM Orden_reparacion o\n" +
                    "LEFT JOIN Clientes c ON o.fk_cliente = c.id\n" +
                    "LEFT JOIN Dispositivo d ON o.fk_dispositivo = d.id\n" +
                    "LEFT JOIN Tecnicos t ON o.fk_tecnico = t.id\n" +
                    "LEFT JOIN Garantias g ON o.fk_garantia = g.id\n" +
                    "WHERE o.id = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idOrden);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Table tabla = new Table(2); // 2 columnas

                        tabla.addCell("ID");
                        tabla.addCell(String.valueOf(rs.getInt("id")));

                        tabla.addCell("Fecha Ingreso");
                        tabla.addCell(rs.getString("fecha_ing"));

                        tabla.addCell("Fecha Egreso");
                        String fechaEgreso = rs.getString("fecha_eg");
                        if (fechaEgreso == null) {
                            fechaEgreso = "N/A";
                        }tabla.addCell(fechaEgreso);

                        tabla.addCell("Tipo Falla");
                        tabla.addCell(rs.getString("tipo_falla"));

                        tabla.addCell("Descripción");
                        tabla.addCell(rs.getString("descripcion"));

                        tabla.addCell("Tipo Orden");
                        tabla.addCell(rs.getString("tipo_orden"));

                        tabla.addCell("Estado");
                        tabla.addCell(rs.getString("estado"));


                        tabla.addCell("Nombre del Cliente");
                        tabla.addCell(String.valueOf(rs.getString("nombre_cliente")));

                        tabla.addCell("Nombre del Dispositivo");
                        tabla.addCell(String.valueOf(rs.getString("nombre_dispositivo")));

                        tabla.addCell("Nombre del Tecnico");
                        tabla.addCell(String.valueOf(rs.getString("nombre_tecnico")));


                        tabla.addCell("Cobertura Garantía");
                        String fk_garantia = rs.getString("cobertura_garantia");
                        if (fk_garantia == null) {
                            fk_garantia = "N/A";
                        }tabla.addCell(fk_garantia);


                        document.add(tabla);
                    } else {
                        document.add(new Paragraph("No se encontró la orden con ID: " + idOrden));
                    }
                }
            }
        } catch (SQLException e) {
            document.add(new Paragraph("Error al consultar la base de datos."));
            e.printStackTrace();
        }

        document.add(new Paragraph("\nDocumento generado por Tecnolofix - Reparación Electrónica"));

        document.close();
        File archivo = new File(destino);
        if (archivo.exists()) {
            Desktop.getDesktop().open(archivo);
        }
    }
}
