package com.example.proy_grupo4;

import com.example.proy_grupo4.Entity.Incidencia;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDF {

    private List<Incidencia> lista;

    public PDF(List<Incidencia> lista) {
        super();
        this.lista = lista;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLACK);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Titulo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Urgencia", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Zona", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Tipo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Estado", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Incidencia incidencia : lista) {
            tabla.addCell(incidencia.getTitulo());
            tabla.addCell(incidencia.getNivel());
            String hora = String.valueOf(incidencia.getHoraCreacion());
            String charsToRemove = "TZ";
            for (char c : charsToRemove.toCharArray()) {
                hora = hora.replace(String.valueOf(c), " ");
            }
            tabla.addCell(hora);
            tabla.addCell(incidencia.getZona().getTitulo());
            tabla.addCell(incidencia.getTipo().getTitulo());
            tabla.addCell(incidencia.getEstado());
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de Incidencias", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 6f, 2.3f, 2.3f, 3f, 2.9f, 3.f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}
