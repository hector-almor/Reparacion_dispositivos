package com.tecnolofix.reparacion_electronicos.Models;

public class Dispositivo {
    private int id;
    private String nombre;
    private String marca;
    private TipoDispositivo tipoDispo;
    private String observaciones;

    public enum TipoDispositivo {
        LAPTOP, PC, CELULAR, TABLET
    }

    // Constructor vacío
    public Dispositivo() {
    }

    // Constructor completo
    public Dispositivo(int id, String nombre, String marca, TipoDispositivo tipoDispo, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.tipoDispo = tipoDispo;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public TipoDispositivo getTipoDispo() {
        return tipoDispo;
    }

    public void setTipoDispo(TipoDispositivo tipoDispo) {
        this.tipoDispo = tipoDispo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", tipoDispo=" + tipoDispo +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}

