package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    @EntityGraph(attributePaths = {"cita", "medicamentos"})
    List<Tratamiento> findAll();

    @EntityGraph(attributePaths = {"cita", "medicamentos"})
    Optional<Tratamiento> findById(Long id);

    List<Tratamiento> findByCitaId(Long idCita);
    List<Tratamiento> findByDuracionAndFrecuencia(Integer duracion, String frecuencia );
    List <Tratamiento> findByFrecuenciaContainingIgnoreCase(String frecuencia);
}
