package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @EntityGraph(attributePaths = {"paciente", "medicos"})
    List<Cita> findAll();

    @EntityGraph(attributePaths = {"paciente", "medicos"})
    Optional<Cita> findById(Long id);

    List<Cita> findByPacienteIdOrderByFechaHoraDesc(Long idPaciente);
    
    List<Cita> findByFechaHoraAfter(LocalDateTime fecha);
    
    @Query("SELECT c FROM Cita c JOIN c.medicos m WHERE m.id = :idMedico")
    List<Cita> findCitasByMedicoId(@Param("idMedico") Long idMedico);
}
