package com.tecnolofix.reparacion_electronicos.Models;

public class Herramienta {
    private int id;
    private String nombre;
    private String descripcion;
    private int stockDisponible;
    private int stockEnUso;
    private int stockMantenimiento;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }

    public int getStockEnUso() { return stockEnUso; }
    public void setStockEnUso(int stockEnUso) { this.stockEnUso = stockEnUso; }

    public int getStockMantenimiento() { return stockMantenimiento; }
    public void setStockMantenimiento(int stockMantenimiento) { this.stockMantenimiento = stockMantenimiento; }
}

