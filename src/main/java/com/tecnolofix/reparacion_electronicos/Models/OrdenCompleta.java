package com.tecnolofix.reparacion_electronicos.Models;

import java.time.LocalDate;

public class OrdenCompleta {

    // Datos de la orden de reparación
    private int id;
    private LocalDate fechaIng;
    private LocalDate fechaEg;
    private OrdenReparacion.TipoFalla tipoFalla;
    private String descripcion;
    private OrdenReparacion.TipoOrden tipoOrden;
    private OrdenReparacion.Estado estado;
    private int fkGarantia;

    // Datos del dispositivo
    private int idDispositivo;
    private String nombreDispositivo;
    private String marcaDispositivo;
    private Dispositivo.TipoDispositivo tipoDispositivo;
    private String observacionesDispositivo;

    // Datos del cliente
    private Cliente cliente;

    // Datos del técnico
    private Tecnico tecnico;

    private Garantia garantia;

    public OrdenCompleta() {
    }

    public OrdenCompleta(
            int id,
            LocalDate fechaIng,
            LocalDate fechaEg,
            OrdenReparacion.TipoFalla tipoFalla,
            String descripcion,
            OrdenReparacion.TipoOrden tipoOrden,
            OrdenReparacion.Estado estado,
            int fkGarantia,
            int idDispositivo,
            String nombreDispositivo,
            String marcaDispositivo,
            Dispositivo.TipoDispositivo tipoDispositivo,
            String observacionesDispositivo,
            Cliente cliente,
            Tecnico tecnico
    ) {
        this.id = id;
        this.fechaIng = fechaIng;
        this.fechaEg = fechaEg;
        this.tipoFalla = tipoFalla;
        this.descripcion = descripcion;
        this.tipoOrden = tipoOrden;
        this.estado = estado;
        this.fkGarantia = fkGarantia;
        this.idDispositivo = idDispositivo;
        this.nombreDispositivo = nombreDispositivo;
        this.marcaDispositivo = marcaDispositivo;
        this.tipoDispositivo = tipoDispositivo;
        this.observacionesDispositivo = observacionesDispositivo;
        this.cliente = cliente;
        this.tecnico = tecnico;
    }

    // Getters y setters

    // Orden
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFechaIng() { return fechaIng; }
    public void setFechaIng(LocalDate fechaIng) { this.fechaIng = fechaIng; }

    public LocalDate getFechaEg() { return fechaEg; }
    public void setFechaEg(LocalDate fechaEg) { this.fechaEg = fechaEg; }

    public OrdenReparacion.TipoFalla getTipoFalla() { return tipoFalla; }
    public void setTipoFalla(OrdenReparacion.TipoFalla tipoFalla) { this.tipoFalla = tipoFalla; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public OrdenReparacion.TipoOrden getTipoOrden() { return tipoOrden; }
    public void setTipoOrden(OrdenReparacion.TipoOrden tipoOrden) { this.tipoOrden = tipoOrden; }

    public OrdenReparacion.Estado getEstado() { return estado; }
    public void setEstado(OrdenReparacion.Estado estado) { this.estado = estado; }

    public int getFkGarantia() { return fkGarantia; }
    public void setFkGarantia(int fkGarantia) { this.fkGarantia = fkGarantia; }

    // Dispositivo
    public int getIdDispositivo() { return idDispositivo; }
    public void setIdDispositivo(int idDispositivo) { this.idDispositivo = idDispositivo; }

    public String getNombreDispositivo() { return nombreDispositivo; }
    public void setNombreDispositivo(String nombreDispositivo) { this.nombreDispositivo = nombreDispositivo; }

    public String getMarcaDispositivo() { return marcaDispositivo; }
    public void setMarcaDispositivo(String marcaDispositivo) { this.marcaDispositivo = marcaDispositivo; }

    public Dispositivo.TipoDispositivo getTipoDispositivo() { return tipoDispositivo; }
    public void setTipoDispositivo(Dispositivo.TipoDispositivo tipoDispositivo) { this.tipoDispositivo = tipoDispositivo; }

    public String getObservacionesDispositivo() { return observacionesDispositivo; }
    public void setObservacionesDispositivo(String observacionesDispositivo) { this.observacionesDispositivo = observacionesDispositivo; }

    // Cliente
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    // Técnico
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    public Garantia getGarantia() { return garantia; }
    public void setGarantia(Garantia garantia) { this.garantia = garantia; }

    @Override
    public String toString() {
        return "OrdenCompleta{" +
                "id=" + id +
                ", fechaIng=" + fechaIng +
                ", fechaEg=" + fechaEg +
                ", tipoFalla=" + tipoFalla +
                ", descripcion='" + descripcion + '\'' +
                ", tipoOrden=" + tipoOrden +
                ", estado=" + estado +
                ", fkGarantia=" + fkGarantia +
                ", idDispositivo=" + idDispositivo +
                ", nombreDispositivo='" + nombreDispositivo + '\'' +
                ", marcaDispositivo='" + marcaDispositivo + '\'' +
                ", tipoDispositivo=" + tipoDispositivo +
                ", observacionesDispositivo='" + observacionesDispositivo + '\'' +
                ", cliente=" + cliente +
                ", tecnico=" + tecnico +
                '}';
    }
}