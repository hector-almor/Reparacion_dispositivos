package com.tecnolofix.reparacion_electronicos.Models;


public class PiezaConCantidad {
    private String id;
    private String nombre;
    private String descripcion;
    private int stock;
    private double costo;

    public double getCostoTipoPieza() {
        return costoTipoPieza;
    }

    public void setCostoTipoPieza(double costoTipoPieza) {
        this.costoTipoPieza = costoTipoPieza;
    }

    private double costoTipoPieza;
    private int cantidad;

    // Constructor vacío
    public PiezaConCantidad() {
    }

    // Constructor completo
    public PiezaConCantidad(String id, String nombre, String descripcion, int stock, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
        this.costo = costo;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Pieza{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", costo=" + costo +
                '}';
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}