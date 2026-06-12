package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.repository.PacienteRepository;
import com.hospital.hospitalapp.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepo;

    @Override
    public Paciente guardar(Paciente paciente) {
        return pacienteRepo.save(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> obtenerTodos() {
        return pacienteRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorDni(String dni) {
        return pacienteRepo.findByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorApellido(String apellido) {
        return pacienteRepo.findByApellidoContainingIgnoreCase(apellido);
    }

    @Override
    public void eliminar(Long id) {
        pacienteRepo.deleteById(id);
    }
}
