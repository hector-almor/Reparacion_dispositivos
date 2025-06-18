package com.tecnolofix.reparacion_electronicos.DB.DAO;

public interface OrdenPiezasDAO {
    //Inserta un nuevo registro en la tabla de Orden_piezas
    boolean registrarHerramientaUsada(int idOrden,String idPieza,int cantidadUsada);
}