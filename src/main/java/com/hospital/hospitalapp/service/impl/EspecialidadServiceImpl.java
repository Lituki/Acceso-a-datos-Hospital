package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Especialidad;
import com.hospital.hospitalapp.repository.EspecialidadRepository;
import com.hospital.hospitalapp.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepo;

    @Override
    public Especialidad guardar(Especialidad especialidad) {
        return especialidadRepo.save(especialidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Especialidad> obtenerPorId(Long id) {
        return especialidadRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especialidad> obtenerTodas() {
        return especialidadRepo.findAll();
    }

    @Override
    public void eliminar(Long id) {
        especialidadRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Especialidad> obtenerPorNombre(String nombre) {
        return especialidadRepo.findByNombre(nombre);
    }
}
