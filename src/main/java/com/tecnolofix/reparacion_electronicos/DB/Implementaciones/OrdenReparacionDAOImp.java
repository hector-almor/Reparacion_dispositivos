package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Dispositivo;
import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import com.tecnolofix.reparacion_electronicos.Models.OrdenReparacion;

import java.util.ArrayList;

public class OrdenReparacionDAOImp implements OrdenReparacionDAO {
    @Override
    public boolean registrarRevision(OrdenReparacion reparacion, Dispositivo dispositivo, Cliente cliente) {
        return true;
    }

    @Override
    public ArrayList<OrdenConDispositivo> obtenerOrdenesConDispositivo() {
        return null;
    }

    @Override
    public boolean enlazaOrdenConTecnico(int idOrden, int idTecnico) {
        return false;
    }
}
