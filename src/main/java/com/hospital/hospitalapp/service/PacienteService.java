package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    Paciente guardar(Paciente paciente);
    Optional<Paciente> obtenerPorId(Long id);
    List<Paciente> obtenerTodos();
    Optional<Paciente> obtenerPorDni(String dni);
    List<Paciente> buscarPorApellido(String apellido);
    void eliminar(Long id);
}
