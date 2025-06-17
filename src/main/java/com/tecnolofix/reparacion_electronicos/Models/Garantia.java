package com.tecnolofix.reparacion_electronicos.Models;

import java.time.LocalDate;
import java.util.Date;

public class Garantia {
    private int id;
    private LocalDate fechaInicio;
    private int duracion; // en días
    private LocalDate fechaFin; // calculada en la lógica de negocio
    private String cobertura;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }
}

