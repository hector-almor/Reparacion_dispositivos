package com.tecnolofix.reparacion_electronicos.DB.Implementaciones;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.Models.*;

import java.time.LocalDate;
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

    @Override
    public ArrayList<OrdenConDispositivo> getAllOrdenesConDispositivo() {
        return null;
    }

    @Override
    public OrdenCompleta obtenerRevisionCompleta(int idOrden) {
        return null;
    }

    @Override
    public boolean cancelarRevision(int idRevision, LocalDate fechaEgreso) {
        return false;
    }

    @Override
    public boolean marcarParaReparacion(int idRevision) {
        return false;
    }

    @Override
    public ArrayList<HerramientaConCantidad> obtenerHerramientasConCantidad() {
        return null;
    }
}
