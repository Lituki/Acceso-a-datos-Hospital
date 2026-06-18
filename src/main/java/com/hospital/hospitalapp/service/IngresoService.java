package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Ingreso;

import java.util.List;
import java.util.Optional;

public interface IngresoService {
    Ingreso crearIngreso(Long idPaciente, Long idHabitacion, String motivo, String observaciones);
    Optional<Ingreso> obtenerPorId(Long id);
    List<Ingreso> obtenerTodos();
    List<Ingreso> obtenerIngresosActivos();
    List<Ingreso> obtenerIngresosDePaciente(Long idPaciente);
    Ingreso darDeAlta(Long idIngreso);
    void eliminar(Long id);
}
