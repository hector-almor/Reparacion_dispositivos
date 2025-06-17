package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Pieza;

import java.util.ArrayList;

public interface PiezaDAO {
    //Obtiene todas las piezas de la base de datos
    ArrayList<Pieza> getAllPiezas();

    //Inserta las nuevas piezas a la BD y registra cada pieza en la tabla de compras con el costo, la cantidad, el id del proveedor
    //Y lo relaciona con cada pieza
    boolean registrarCompras(ArrayList<Pieza> piezas,int idProveedor);
}
