package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(String numero);
    
    @Query("SELECT h FROM Habitacion h WHERE h.disponible = true")
    List<Habitacion> findDisponibles();
    
    List<Habitacion> findByPlantaOrderByNumero(String planta);
}
