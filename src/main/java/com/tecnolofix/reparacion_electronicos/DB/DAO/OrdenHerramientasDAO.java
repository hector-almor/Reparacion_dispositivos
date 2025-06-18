package com.tecnolofix.reparacion_electronicos.DB.DAO;

public interface OrdenHerramientasDAO {
    //Inserta un nuevo registro en la tabla de Orden_herramientas
    boolean registrarHerramientaUsada(int idOrden,int idHerramienta,int cantidadUsada);
}
