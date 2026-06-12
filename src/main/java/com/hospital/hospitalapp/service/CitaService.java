package com.hospital.hospitalapp.service;

import com.hospital.hospitalapp.model.Cita;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    Cita programarCita(Long idPaciente, Long idMedico, LocalDateTime fechaHora, String motivo);
    Optional<Cita> obtenerPorId(Long id);
    List<Cita> obtenerTodas();
    List<Cita> obtenerCitasDePaciente(Long idPaciente);
    List<Cita> obtenerCitasDelMedico(Long idMedico);
    List<Cita> obtenerCitasAPartirDe(LocalDateTime fecha);
    void eliminar(Long id);
}
