package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.PiezaDAO;
import com.tecnolofix.reparacion_electronicos.Models.Pieza;

import java.util.ArrayList;

public class PiezaDAOImp implements PiezaDAO {
    @Override
    public ArrayList<Pieza> getAllPiezas() {
        return null;
    }

    @Override
    public boolean registrarCompras(ArrayList<Pieza> piezas, int idProveedor) {
        return false;
    }
}
