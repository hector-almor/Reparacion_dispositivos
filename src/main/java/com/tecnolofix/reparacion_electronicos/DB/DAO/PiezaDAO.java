package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Pieza;

import java.util.ArrayList;

public interface PiezaDAO {
    //Obtiene todas las piezas de la base de datos
    ArrayList<Pieza> getAllPiezas();
}
