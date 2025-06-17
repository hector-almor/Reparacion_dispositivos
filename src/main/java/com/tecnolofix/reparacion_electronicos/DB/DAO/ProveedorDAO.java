package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Proveedor;

import java.util.ArrayList;

public interface ProveedorDAO {
    //Obtiene todos los proveedores de la BD
    ArrayList<Proveedor> getAllProveedores();
}
