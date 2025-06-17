package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public interface OrdenReparacionDAO {
    //Crea una orden de revision en la tabla "Orden_reparacion", sin fecha de egreso, sin tecnico y sin garantía (NULL), pero si debe
    //registrar antes el cliente y dispositivo correspondiente para poder referenciarlos con las llaves foraneas en el registro de orden de reparación
    boolean registrarRevision(OrdenReparacion reparacion, Dispositivo dispositivo, Cliente cliente);

    //Obtiene toda la información de las órdenes de reparación y revisión junto con la información del dispositivo asociado a cada una
    //Solo en las que en el campo "estado" esté "PENDIENTE"
    ArrayList<OrdenConDispositivo> obtenerOrdenesConDispositivo();

    //Actualiza una orden de reparacion/revision para asignarle un tecnico (actualiza la llave foranea)
    boolean enlazaOrdenConTecnico(int idOrden, int idTecnico);

    //Obtiene toda la información de las órdenes de reparación y revisión junto con la información del dispositivo asociado a cada una
    //Solo en las que en el campo "estado" esté en cualquier otro menos "PENDIENTE"
    ArrayList<OrdenConDispositivo> getAllOrdenesConDispositivo();

    //Obtiene toda la información de una orden de revisión, junto con, el dispositivo, el técnico, y el cliente
    OrdenCompleta obtenerRevisionCompleta(int idOrden);

    //Obtiene toda la información de una orden de revisión, junto con el dispositivo, el técnico, y el cliente
    //Revisa si tiene garantía, si no la tiene regresa en el campo de FK_Garantía "-1" y si sí, solo regresa toda la info en los objetos
    OrdenCompleta obtenerReparacionCompleta(int idOrden);

    //Se marca una revisión como "CANCELADA" y se actualiza su fecha de egreso
    boolean cancelarRevision(int idRevision, LocalDate fechaEgreso);

    //Se marca una revisión como "tipo_orden"=REPARACION y se actualiza su estado a ASIGNADO
    boolean marcarParaReparacion(int idRevision);

    //Devuelve todas las herramientas usadas por una revision, checar tabla de Herramientas y Orden_herramientas
    //Devolver la cantidad usada en la propiedad stockEnUso
    ArrayList<HerramientaConCantidad> obtenerHerramientasConCantidad();

    //Devuelve todas las reparaciones con el dispositivo asociado donde (tipo_orden=REPARACION) y donde el estado no sea "ASIGNADO" ni "CANCELADO"
    ArrayList<OrdenConDispositivo> obtenerOrdenReparacionConDispositivo();

    //Devuelve todas las herramientas y piezas utilizadas en una reparación (checar tablas Orden_herramientas y Orden_piezas)
    //Y el total del costo de las piezas utilizadas
    HerramientasPiezasConCosto obtenerHerramientasPiezasConCosto(int idReparacion);

    //Actualiza el estado de una reparación a "ENTREGADO", crea una garantía y le asigna el id de la nueva garantía a la llave foranea
    //De orden de reparación
    boolean entregarReparacion(int idReparacion,Garantia garantia);
}
