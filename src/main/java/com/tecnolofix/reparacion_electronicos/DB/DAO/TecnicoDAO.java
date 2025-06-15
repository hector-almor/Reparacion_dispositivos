package com.tecnolofix.reparacion_electronicos.DB.DAO;

import com.tecnolofix.reparacion_electronicos.Models.Tecnico;

public interface TecnicoDAO {

    //Devuelve verdadero si encuentra un empleado con el usuario y contraseña especificado
    boolean loginTecnico(Tecnico t);
}
