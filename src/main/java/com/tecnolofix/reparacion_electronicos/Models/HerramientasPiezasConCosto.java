package com.tecnolofix.reparacion_electronicos.Models;

import java.util.ArrayList;

public class HerramientasPiezasConCosto {
    private ArrayList<HerramientaConCantidad> herramientas;
    private ArrayList<PiezaConCantidad> piezas;
    private double costoTotalPiezas;

    public double getCostoTotalPiezas() {
        return costoTotalPiezas;
    }

    public void setCostoTotalPiezas(double costoTotalPiezas) {
        this.costoTotalPiezas = costoTotalPiezas;
    }

    public ArrayList<HerramientaConCantidad> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(ArrayList<HerramientaConCantidad> herramientas) {
        this.herramientas = herramientas;
    }

    public ArrayList<PiezaConCantidad> getPiezas() {
        return piezas;
    }

    public void setPiezas(ArrayList<PiezaConCantidad> piezas) {
        this.piezas = piezas;
    }


}
