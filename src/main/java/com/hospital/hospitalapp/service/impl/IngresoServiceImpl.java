package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Ingreso;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.model.Habitacion;
import com.hospital.hospitalapp.repository.IngresoRepository;
import com.hospital.hospitalapp.repository.PacienteRepository;
import com.hospital.hospitalapp.repository.HabitacionRepository;
import com.hospital.hospitalapp.service.IngresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngresoServiceImpl implements IngresoService {

    private final IngresoRepository ingresoRepo;
    private final PacienteRepository pacienteRepo;
    private final HabitacionRepository habitacionRepo;

    @Override
    public Ingreso crearIngreso(Long idPaciente, Long idHabitacion, String motivo) {
        Paciente paciente = pacienteRepo.findById(idPaciente)
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Habitacion habitacion = habitacionRepo.findById(idHabitacion)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        Ingreso ingreso = new Ingreso();
        ingreso.setPaciente(paciente);
        ingreso.setHabitacion(habitacion);
        ingreso.setMotivo(motivo);
        ingreso.setFechaIngreso(LocalDate.now());

        return ingresoRepo.save(ingreso);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ingreso> obtenerPorId(Long id) {
        return ingresoRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingreso> obtenerTodos() {
        return ingresoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingreso> obtenerIngresosActivos() {
        return ingresoRepo.findByFechaAltaIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingreso> obtenerIngresosDePaciente(Long idPaciente) {
        return ingresoRepo.findByPacienteId(idPaciente);
    }

    @Override
    public Ingreso darDeAlta(Long idIngreso) {
        Ingreso ingreso = ingresoRepo.findById(idIngreso)
            .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));
        ingreso.setFechaAlta(LocalDate.now());
        return ingresoRepo.save(ingreso);
    }

    @Override
    public void eliminar(Long id) {
        ingresoRepo.deleteById(id);
    }
}
