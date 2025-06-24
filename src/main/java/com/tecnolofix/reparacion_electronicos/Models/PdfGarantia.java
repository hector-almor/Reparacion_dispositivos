package com.tecnolofix.reparacion_electronicos.Models;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.properties.TextAlignment;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class PdfGarantia {

    public static void generarGarantia(int idOrden) {
        String url = "jdbc:mysql://localhost:3306/reparacion_dispositivos";
        String usuario = "admin";
        String contrasena = "Administrador123";

        String query = """
            SELECT o.id, o.fecha_eg, c.nombre AS cliente, d.nombre AS dispositivo, d.marca,
                   o.tipo_falla, o.descripcion, g.fecha_inicio, g.fecha_fin, g.cobertura
            FROM Orden_reparacion o
            JOIN Clientes c ON o.fk_cliente = c.id
            JOIN Dispositivo d ON o.fk_dispositivo = d.id
            LEFT JOIN Garantias g ON o.fk_garantia = g.id
            WHERE o.id = ?;
        """;

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idOrden);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userHome = System.getProperty("user.home");
                String escritorioNombre = System.getProperty("os.name").toLowerCase().contains("win") ? "Escritorio" : "Desktop";
                File escritorio = new File(userHome, escritorioNombre);
                if (!escritorio.exists()) escritorio = new File(userHome, "Desktop");
//                File archivoPDF = new File(escritorio, "Garantia_Orden_" + idOrden + ".pdf");
                File archivoPDF = new File("Garantia_Orden_" + idOrden + ".pdf");


                PdfWriter writer = new PdfWriter(new FileOutputStream(archivoPDF));
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

//                PdfFont bold = PdfFontFactory.createFont(PdfFontFactory.HELVETICA_BOLD);
                PdfFont bold = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);


                document.add(new Paragraph("========= TECNOLOFIX =========")
                        .setFont(bold).setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("      TICKET DE GARANTÍA")
                        .setFont(bold).setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph(" "));

                document.add(new Paragraph("Orden #: " + rs.getInt("id")));
                document.add(new Paragraph("Fecha de entrega: " + rs.getDate("fecha_eg")));
                document.add(new Paragraph(" "));

                document.add(new Paragraph("Cliente: " + rs.getString("cliente")));
                document.add(new Paragraph("Dispositivo: " + rs.getString("dispositivo") + " " + rs.getString("marca")));
                document.add(new Paragraph("Falla: " + rs.getString("tipo_falla")));
                document.add(new Paragraph("Descripción: " + rs.getString("descripcion")));
                document.add(new Paragraph(" "));

                Date inicio = rs.getDate("fecha_inicio");
                Date fin = rs.getDate("fecha_fin");
                String cobertura = rs.getString("cobertura");

                if (inicio != null && fin != null && cobertura != null) {
                    document.add(new Paragraph("Garantía válida desde: " + inicio));
                    document.add(new Paragraph("Hasta: " + fin));
                    document.add(new Paragraph("Cobertura: " + cobertura));
                } else {
                    document.add(new Paragraph("⚠ Esta orden no tiene garantía registrada."));
                }

                document.add(new Paragraph(" "));
                document.add(new Paragraph("* No cubre daños por mal uso, líquidos o golpes."));
                document.add(new Paragraph("=============================="));

                document.close();
                pdfDoc.close();

                System.out.println("✅ PDF generado en: " + archivoPDF.getAbsolutePath());

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(archivoPDF);
                }

            } else {
                System.out.println("⚠ No se encontró una orden ENTREGADA con el ID " + idOrden);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}