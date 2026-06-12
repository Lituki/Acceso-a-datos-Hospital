package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.model.Tratamiento;
import com.hospital.hospitalapp.repository.CitaRepository;
import com.hospital.hospitalapp.repository.TratamientoRepository;
import com.hospital.hospitalapp.service.TratamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TratamientoServiceImpl implements TratamientoService {

    private final TratamientoRepository tratamientoRepo;
    private final CitaRepository citaRepo;

    @Override
    public Tratamiento guardar(Tratamiento tratamiento) {
        return tratamientoRepo.save(tratamiento);
    }

    @Override
    public Tratamiento crearTratamiento(Long idCita, String descripcion, Integer duracion, String frecuencia) {
        Cita cita = citaRepo.findById(idCita)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

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
