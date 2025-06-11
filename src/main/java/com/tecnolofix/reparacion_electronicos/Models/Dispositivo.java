package com.tecnolofix.reparacion_electronicos.Models;

public class Dispositivo {
    public enum TipoDispo {
        LAPTOP, PC, CELULAR, TABLET
    }

    private int id;
    private String nombre;
    private String marca;
    private String modelo;
    private TipoDispo tipoDispo;
    private String observaciones;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public TipoDispo getTipoDispo() { return tipoDispo; }
    public void setTipoDispo(TipoDispo tipoDispo) { this.tipoDispo = tipoDispo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
