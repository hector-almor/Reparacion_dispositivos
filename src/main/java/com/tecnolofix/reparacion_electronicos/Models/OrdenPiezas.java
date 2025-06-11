package com.tecnolofix.reparacion_electronicos.Models;

public class OrdenPiezas {
    private int id;
    private int fkOrden;
    private int fkPieza;
    private int cantidad;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFkOrden() { return fkOrden; }
    public void setFkOrden(int fkOrden) { this.fkOrden = fkOrden; }

    public int getFkPieza() { return fkPieza; }
    public void setFkPieza(int fkPieza) { this.fkPieza = fkPieza; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
