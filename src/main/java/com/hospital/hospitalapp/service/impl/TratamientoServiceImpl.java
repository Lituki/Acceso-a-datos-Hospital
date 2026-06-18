package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.model.Medicamento;
import com.hospital.hospitalapp.model.Tratamiento;
import com.hospital.hospitalapp.repository.CitaRepository;
import com.hospital.hospitalapp.repository.MedicamentoRepository;
import com.hospital.hospitalapp.repository.TratamientoRepository;
import com.hospital.hospitalapp.service.TratamientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TratamientoServiceImpl implements TratamientoService {

    private final TratamientoRepository tratamientoRepo;
    private final CitaRepository citaRepo;
    private final MedicamentoRepository medicamentoRepo;

    @Override
    public Tratamiento guardar(Tratamiento tratamiento, List<Long> medicamentoIds) {
        List<Medicamento> medicamentos = new ArrayList<>();
        if (medicamentoIds != null) {
            medicamentos = medicamentoIds.stream()
                .map(id -> medicamentoRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado")))
                .collect(Collectors.toCollection(ArrayList::new));
        }
        tratamiento.setMedicamentos(medicamentos);
        return tratamientoRepo.save(tratamiento);
    }

    @Override
    public Tratamiento guardar(Tratamiento tratamiento) {
        return tratamientoRepo.save(tratamiento);
    }

    @Override
    public Tratamiento crearTratamiento(Long idCita, String descripcion, Integer duracion, String frecuencia) {
        Cita cita = citaRepo.findById(idCita)
            .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setCita(cita);
        tratamiento.setDescripcion(descripcion);
        tratamiento.setDuracion(duracion);
        tratamiento.setFrecuencia(frecuencia);
        tratamiento.setMedicamentos(new ArrayList<>());

        return tratamientoRepo.save(tratamiento);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tratamiento> obtenerPorId(Long id) {
        return tratamientoRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tratamiento> obtenerParaFormulario(Long id) {
        return tratamientoRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tratamiento> obtenerTodos() {
        return tratamientoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tratamiento> obtenerTratamientosDeCita(Long idCita) {
        return tratamientoRepo.findByCitaId(idCita);
    }

    @Override
    public void eliminar(Long id) {
        tratamientoRepo.deleteById(id);
    }
}
