package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Habitacion;
import com.hospital.hospitalapp.model.Ingreso;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.repository.HabitacionRepository;
import com.hospital.hospitalapp.repository.IngresoRepository;
import com.hospital.hospitalapp.repository.PacienteRepository;
import com.hospital.hospitalapp.service.IngresoService;
import jakarta.persistence.EntityNotFoundException;
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
    public Ingreso crearIngreso(Long idPaciente, Long idHabitacion, String motivo, String observaciones) {
        Paciente paciente = pacienteRepo.findById(idPaciente)
            .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        Habitacion habitacion = habitacionRepo.findById(idHabitacion)
            .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));

        Ingreso ingreso = new Ingreso();
        ingreso.setPaciente(paciente);
        ingreso.setHabitacion(habitacion);
        ingreso.setMotivo(motivo);
        ingreso.setObservaciones(observaciones);
        ingreso.setFechaIngreso(LocalDate.now());
        ingreso.setFechaAlta(null);

        habitacion.setDisponible(false);
        habitacionRepo.save(habitacion);

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
            .orElseThrow(() -> new EntityNotFoundException("Ingreso no encontrado"));
        ingreso.setFechaAlta(LocalDate.now());

        Habitacion habitacion = ingreso.getHabitacion();
        if (habitacion != null) {
            habitacion.setDisponible(true);
            habitacionRepo.save(habitacion);
        }

        return ingresoRepo.save(ingreso);
    }

    @Override
    public void eliminar(Long id) {
        ingresoRepo.deleteById(id);
    }
}
