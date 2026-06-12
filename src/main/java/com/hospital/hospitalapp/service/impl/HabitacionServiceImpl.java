package com.hospital.hospitalapp.service.impl;

import com.hospital.hospitalapp.model.Habitacion;
import com.hospital.hospitalapp.repository.HabitacionRepository;
import com.hospital.hospitalapp.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepo;

    @Override
    public Habitacion guardar(Habitacion habitacion) {
        return habitacionRepo.save(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Habitacion> obtenerPorId(Long id) {
        return habitacionRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> obtenerTodas() {
        return habitacionRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> obtenerDisponibles() {
        return habitacionRepo.findDisponibles();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Habitacion> obtenerPorNumero(String numero) {
        return habitacionRepo.findByNumero(numero);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> obtenerPorPlanta(String planta) {
        return habitacionRepo.findByPlantaOrderByNumero(planta);
    }

    @Override
    public void eliminar(Long id) {
        habitacionRepo.deleteById(id);
    }
}
