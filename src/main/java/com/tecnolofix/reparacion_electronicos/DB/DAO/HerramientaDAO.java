package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Herramienta;

import java.util.ArrayList;

public interface HerramientaDAO {
    //Obtiene la información de todas las herramientas registradas en la BD
    ArrayList<Herramienta> getAllHerramientas();
}
