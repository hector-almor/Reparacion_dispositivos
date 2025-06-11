package com.tecnolofix.reparacion_electronicos.Models;

import java.util.Date;
import java.math.BigDecimal;

public class Pago {
    public enum MetodoPago { EFECTIVO, TARJETA, TRANSFERENCIA }

    private int id;
    private int fkOrden;
    private Date fechaPago;
    private BigDecimal monto;
    private MetodoPago metodoPago;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFkOrden() { return fkOrden; }
    public void setFkOrden(int fkOrden) { this.fkOrden = fkOrden; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
}
