package com.tecnolofix.reparacion_electronicos.Models;

import java.util.Date;

public class OrdenReparacion {
    public enum TipoFalla { SOFTWARE, HARDWARE }
    public enum Estado { PREORDEN, PENDIENTE, PROGRESO, FINALIZADO, CANCELADO }

    private int id;
    private Date fechaIng;
    private Date fechaEg;  // puede ser null
    private TipoFalla tipoFalla;
    private String descripcion;
    private Estado estado;
    private int fkCliente;
    private int fkDispositivo;
    private int fkTecnico;
    private int fkGarantia;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFechaIng() { return fechaIng; }
    public void setFechaIng(Date fechaIng) { this.fechaIng = fechaIng; }

    public Date getFechaEg() { return fechaEg; }
    public void setFechaEg(Date fechaEg) { this.fechaEg = fechaEg; }

    public TipoFalla getTipoFalla() { return tipoFalla; }
    public void setTipoFalla(TipoFalla tipoFalla) { this.tipoFalla = tipoFalla; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public int getFkCliente() { return fkCliente; }
    public void setFkCliente(int fkCliente) { this.fkCliente = fkCliente; }

    public int getFkDispositivo() { return fkDispositivo; }
    public void setFkDispositivo(int fkDispositivo) { this.fkDispositivo = fkDispositivo; }

    public int getFkTecnico() { return fkTecnico; }
    public void setFkTecnico(int fkTecnico) { this.fkTecnico = fkTecnico; }

    public int getFkGarantia() { return fkGarantia; }
    public void setFkGarantia(int fkGarantia) { this.fkGarantia = fkGarantia; }
}
