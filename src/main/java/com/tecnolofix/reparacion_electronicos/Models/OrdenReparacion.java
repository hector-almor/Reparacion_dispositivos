package com.tecnolofix.reparacion_electronicos.Models;

import java.time.LocalDate;


public class OrdenReparacion {
    private int id;
    private LocalDate fechaIng;
    private LocalDate fechaEg;
    private TipoFalla tipoFalla;
    private String descripcion;
    private TipoOrden tipoOrden;
    private Estado estado;
    private int fkCliente;
    private int fkDispositivo;
    private int fkTecnico;
    private int fkGarantia;

    public enum TipoFalla {
        SOFTWARE, HARDWARE
    }

    public enum TipoOrden {
        REVISION, REPARACION
    }

    public enum Estado {
        PENDIENTE,
        ASIGNADO,
        PROGRESO,
        COMPLETADO,
        ENTREGADO,
        CANCELADO
    }

    public OrdenReparacion() {
    }

    public OrdenReparacion(int id, LocalDate fechaIng, LocalDate fechaEg, TipoFalla tipoFalla, String descripcion,
                           TipoOrden tipoOrden, Estado estado, int fkCliente, int fkDispositivo,
                           int fkTecnico, int fkGarantia) {
        this.id = id;
        this.fechaIng = fechaIng;
        this.fechaEg = fechaEg;
        this.tipoFalla = tipoFalla;
        this.descripcion = descripcion;
        this.tipoOrden = tipoOrden;
        this.estado = estado;
        this.fkCliente = fkCliente;
        this.fkDispositivo = fkDispositivo;
        this.fkTecnico = fkTecnico;
        this.fkGarantia = fkGarantia;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaIng() {
        return fechaIng;
    }

    public void setFechaIng(LocalDate fechaIng) {
        this.fechaIng = fechaIng;
    }

    public LocalDate getFechaEg() {
        return fechaEg;
    }

    public void setFechaEg(LocalDate fechaEg) {
        this.fechaEg = fechaEg;
    }

    public TipoFalla getTipoFalla() {
        return tipoFalla;
    }

    public void setTipoFalla(TipoFalla tipoFalla) {
        this.tipoFalla = tipoFalla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoOrden getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(TipoOrden tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(int fkCliente) {
        this.fkCliente = fkCliente;
    }

    public int getFkDispositivo() {
        return fkDispositivo;
    }

    public void setFkDispositivo(int fkDispositivo) {
        this.fkDispositivo = fkDispositivo;
    }

    public int getFkTecnico() {
        return fkTecnico;
    }

    public void setFkTecnico(int fkTecnico) {
        this.fkTecnico = fkTecnico;
    }

    public int getFkGarantia() {
        return fkGarantia;
    }

    public void setFkGarantia(int fkGarantia) {
        this.fkGarantia = fkGarantia;
    }

    @Override
    public String toString() {
        return "OrdenReparacion{" +
                "id=" + id +
                ", fechaIng=" + fechaIng +
                ", fechaEg=" + fechaEg +
                ", tipoFalla=" + tipoFalla +
                ", descripcion='" + descripcion + '\'' +
                ", tipoOrden=" + tipoOrden +
                ", estado=" + estado +
                ", fkCliente=" + fkCliente +
                ", fkDispositivo=" + fkDispositivo +
                ", fkTecnico=" + fkTecnico +
                ", fkGarantia=" + fkGarantia +
                '}';
    }
}

