package com.tecnolofix.reparacion_electronicos.Models;

public class OrdenHerramientas {
    private int id;
    private int fkOrden;
    private int fkHerramienta;
    private int cantidadUsada;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFkOrden() { return fkOrden; }
    public void setFkOrden(int fkOrden) { this.fkOrden = fkOrden; }

    public int getFkHerramienta() { return fkHerramienta; }
    public void setFkHerramienta(int fkHerramienta) { this.fkHerramienta = fkHerramienta; }

    public int getCantidadUsada() { return cantidadUsada; }
    public void setCantidadUsada(int cantidadUsada) { this.cantidadUsada = cantidadUsada; }
}
