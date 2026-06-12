package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Medicamento;
import com.hospital.hospitalapp.repository.MedicamentoRepository;
import com.hospital.hospitalapp.service.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepo;

    @Override
    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepo.save(medicamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> obtenerPorId(Long id) {
        return medicamentoRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> obtenerTodos() {
        return medicamentoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> obtenerPorNombre(String nombre) {
        return medicamentoRepo.findByNombre(nombre);
    }

    @Override
    public void eliminar(Long id) {
        medicamentoRepo.deleteById(id);
    }
}
