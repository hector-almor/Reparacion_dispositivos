package com.tecnolofix.reparacion_electronicos.Models;

import java.math.BigDecimal;

public class Compra {
    private int id;
    private int cantidad;
    private double precio;
    private double total; // Calculado en la lógica de negocio
    private int fkProveedor;
    private String fkPieza;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getFkProveedor() { return fkProveedor; }
    public void setFkProveedor(int fkProveedor) { this.fkProveedor = fkProveedor; }

    public String getFkPieza() { return fkPieza; }
    public void setFkPieza(String fkPieza) { this.fkPieza = fkPieza; }
}

