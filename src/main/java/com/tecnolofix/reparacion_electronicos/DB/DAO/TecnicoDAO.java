package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

import java.util.ArrayList;

public interface TecnicoDAO {

    //Devuelve verdadero si encuentra un empleado con el usuario y contraseña especificado si no lo encuentra, devuelve NULL
    Tecnico loginTecnico(Tecnico t);

    //Obtiene la información de todos los técnicos en la BD
    ArrayList<Tecnico> getAllTecnicos();
}
