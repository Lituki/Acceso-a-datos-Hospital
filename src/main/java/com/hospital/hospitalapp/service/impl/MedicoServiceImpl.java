package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Medico;
import com.hospital.hospitalapp.repository.MedicoRepository;
import com.hospital.hospitalapp.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepo;

    @Override
    public Medico guardar(Medico medico) {
        return medicoRepo.save(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> obtenerPorId(Long id) {
        return medicoRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> obtenerParaFormulario(Long id) {
        return medicoRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> obtenerTodos() {
        return medicoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> obtenerPorEspecialidad(Long idEspecialidad) {
        return medicoRepo.findByEspecialidadId(idEspecialidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> obtenerPorNumColegiado(String numColegiado) {
        return medicoRepo.findByNumColegiado(numColegiado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorApellido(String apellido) {
        return medicoRepo.findByApellidoContaining(apellido);
    }

    @Override
    public void eliminar(Long id) {
        medicoRepo.deleteById(id);
    }
}
