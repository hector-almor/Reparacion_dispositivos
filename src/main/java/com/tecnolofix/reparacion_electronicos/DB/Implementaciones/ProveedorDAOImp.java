package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.ProveedorDAO;
import com.tecnolofix.reparacion_electronicos.Models.Proveedor;

import java.util.ArrayList;

public class ProveedorDAOImp implements ProveedorDAO {
    @Override
    public ArrayList<Proveedor> getAllProveedores() {
        return null;
    }

    @Override
    public boolean registrarProveedor(Proveedor proveedor) {
        return false;
    }
}
