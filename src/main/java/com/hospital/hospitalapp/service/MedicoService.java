package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoService {
    Medico guardar(Medico medico);
    Optional<Medico> obtenerPorId(Long id);
    Optional<Medico> obtenerParaFormulario(Long id);
    List<Medico> obtenerTodos();
    List<Medico> obtenerPorEspecialidad(Long idEspecialidad);
    Optional<Medico> obtenerPorNumColegiado(String numColegiado);
    List<Medico> buscarPorApellido(String apellido);
    void eliminar(Long id);
}
