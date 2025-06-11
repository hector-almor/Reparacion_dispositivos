package com.tecnolofix.reparacion_electronicos.Models;

import java.util.Date;

public class Garantia {
    private int id;
    private Date fechaInicio;
    private int duracion; // en días
    private Date fechaFin; // calculada en la lógica de negocio
    private String cobertura;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }
}

