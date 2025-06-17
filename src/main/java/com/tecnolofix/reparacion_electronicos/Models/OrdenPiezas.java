package com.tecnolofix.reparacion_electronicos.Models;

public class OrdenPiezas {
    private int id;
    private int fkOrden;
    private String fkPieza;
    private int cantidad;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFkOrden() { return fkOrden; }
    public void setFkOrden(int fkOrden) { this.fkOrden = fkOrden; }

    public String getFkPieza() { return fkPieza; }
    public void setFkPieza(String fkPieza) { this.fkPieza = fkPieza; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
