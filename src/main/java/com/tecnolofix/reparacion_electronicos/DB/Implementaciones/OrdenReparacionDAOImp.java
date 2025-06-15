package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Dispositivo;
import com.tecnolofix.reparacion_electronicos.Models.OrdenReparacion;

public class OrdenReparacionDAOImp implements OrdenReparacionDAO {
    @Override
    public boolean registrarRevision(OrdenReparacion reparacion, Dispositivo dispositivo, Cliente cliente) {
        return true;
    }
}
