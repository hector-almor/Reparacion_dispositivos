package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Herramienta;

import java.util.ArrayList;

public interface HerramientaDAO {
    //Obtiene la información de todas las herramientas registradas en la BD
    ArrayList<Herramienta> getAllHerramientas();

    //Suma al stock_disponible y stock_en_uso de un registro en la tabla Herramientas, los valores de los parámetros
    //Al hacer la suma utilizar paréntsis en ambos casos, por ejemplo:
    //UPDATE Herramientas SET stock_disponible = stock_disponible + (parámetro), stock_en_uso = stock_en_uso +(parámetro 2) WHERE id = parámetro 3
    boolean actualizarStockDisponible(int idHerramienta,int stockDisponible, int stockEnUso);
}
