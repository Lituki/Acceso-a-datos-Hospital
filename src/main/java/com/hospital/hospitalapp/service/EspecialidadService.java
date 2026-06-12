package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Especialidad;

import java.util.List;
import java.util.Optional;

public interface EspecialidadService {
    Especialidad guardar(Especialidad especialidad);
    Optional<Especialidad> obtenerPorId(Long id);
    List<Especialidad> obtenerTodas();
    void eliminar(Long id);
    Optional<Especialidad> obtenerPorNombre(String nombre);
}
