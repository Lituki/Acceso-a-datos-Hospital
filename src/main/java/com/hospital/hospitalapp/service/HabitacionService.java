package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Habitacion;

import java.util.List;
import java.util.Optional;

public interface HabitacionService {
    Habitacion guardar(Habitacion habitacion);
    Optional<Habitacion> obtenerPorId(Long id);
    List<Habitacion> obtenerTodas();
    List<Habitacion> obtenerDisponibles();
    Optional<Habitacion> obtenerPorNumero(String numero);
    List<Habitacion> obtenerPorPlanta(String planta);
    void eliminar(Long id);
}
