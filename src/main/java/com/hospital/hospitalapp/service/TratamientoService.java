package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Tratamiento;

import java.util.List;
import java.util.Optional;

public interface TratamientoService {
    Tratamiento guardar(Tratamiento tratamiento);
    Tratamiento crearTratamiento(Long idCita, String descripcion, Integer duracion, String frecuencia);
    Optional<Tratamiento> obtenerPorId(Long id);
    List<Tratamiento> obtenerTodos();
    List<Tratamiento> obtenerTratamientosDeCita(Long idCita);
    void eliminar(Long id);
}
