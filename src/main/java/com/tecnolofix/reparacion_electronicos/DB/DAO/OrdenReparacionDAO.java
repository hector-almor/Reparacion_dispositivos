package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Dispositivo;
import com.tecnolofix.reparacion_electronicos.Models.OrdenReparacion;

public interface OrdenReparacionDAO {
    //Crea una orden de revision en la tabla "Orden_reparacion", sin fecha de egreso, sin tecnico y sin garantía (NULL), pero si debe
    //registrar antes el cliente y dispositivo correspondiente para poder referenciarlos con las llaves foraneas en el registro de orden de reparación
    boolean registrarRevision(OrdenReparacion reparacion, Dispositivo dispositivo, Cliente cliente);
}
