package com.tecnolofix.reparacion_electronicos.Models;

import java.math.BigDecimal;

public class Compra {
    private int id;
    private int cantidad;
    private BigDecimal precio;
    private BigDecimal total; // Calculado en la lógica de negocio
    private int fkProveedor;
    private int fkPieza;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public int getFkProveedor() { return fkProveedor; }
    public void setFkProveedor(int fkProveedor) { this.fkProveedor = fkProveedor; }

    public int getFkPieza() { return fkPieza; }
    public void setFkPieza(int fkPieza) { this.fkPieza = fkPieza; }
}

