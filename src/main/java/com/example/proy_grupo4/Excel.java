package com.example.proy_grupo4;

import com.example.proy_grupo4.Entity.Incidencia;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Excel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Incidencia> lista;

    public Excel(List<Incidencia> listaEmpleados) {
        this.lista = listaEmpleados;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Incidencias");
    }

    private void escribirCabeceraDeTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("Titulo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Urgencia");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Fecha");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Zona");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Tipo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Estado");
        celda.setCellStyle(estilo);

    }

    private void escribirDatosDeLaTabla() {
        int nueroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        for(Incidencia incidencia : lista) {
            Row fila = hoja.createRow(nueroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(incidencia.getDescripcion());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(incidencia.getNivel());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(String.valueOf(incidencia.getHoraCreacion()));
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(incidencia.getZona().getTitulo());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(incidencia.getTipo().getTitulo());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(incidencia.getEstado());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirCabeceraDeTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outPutStream = response.getOutputStream();
        libro.write(outPutStream);

        libro.close();
        outPutStream.close();
    }
}
