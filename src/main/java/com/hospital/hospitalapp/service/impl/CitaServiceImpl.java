package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.model.Medico;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.repository.CitaRepository;
import com.hospital.hospitalapp.repository.MedicoRepository;
import com.hospital.hospitalapp.repository.PacienteRepository;
import com.hospital.hospitalapp.service.CitaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;

    @Override
    public Cita guardar(Cita cita, List<Long> medicoIds) {
        List<Medico> medicos = new ArrayList<>();
        if (medicoIds != null) {
            medicos = medicoIds.stream()
                .map(id -> medicoRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Medico no encontrado")))
                .collect(Collectors.toCollection(ArrayList::new));
        }
        cita.setMedicos(medicos);
        return citaRepo.save(cita);
    }

    @Override
    public Cita programarCita(Long idPaciente, Long idMedico, LocalDateTime fechaHora, String motivo) {
        Paciente paciente = pacienteRepo.findById(idPaciente)
            .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        Medico medico = medicoRepo.findById(idMedico)
            .orElseThrow(() -> new EntityNotFoundException("Medico no encontrado"));

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setFechaHora(fechaHora);
        cita.setMotivo(motivo);
        cita.setMedicos(new ArrayList<>(List.of(medico)));

        return citaRepo.save(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> obtenerPorId(Long id) {
        return citaRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> obtenerParaFormulario(Long id) {
        return citaRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerTodas() {
        return citaRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasDePaciente(Long idPaciente) {
        return citaRepo.findByPacienteIdOrderByFechaHoraDesc(idPaciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasDelMedico(Long idMedico) {
        return citaRepo.findCitasByMedicoId(idMedico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasAPartirDe(LocalDateTime fecha) {
        return citaRepo.findByFechaHoraAfter(fecha);
    }

    @Override
    public void eliminar(Long id) {
        citaRepo.deleteById(id);
    }
}
