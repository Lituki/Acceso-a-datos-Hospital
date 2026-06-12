package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Medicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoService {
    Medicamento guardar(Medicamento medicamento);
    Optional<Medicamento> obtenerPorId(Long id);
    List<Medicamento> obtenerTodos();
    Optional<Medicamento> obtenerPorNombre(String nombre);
    void eliminar(Long id);
}
