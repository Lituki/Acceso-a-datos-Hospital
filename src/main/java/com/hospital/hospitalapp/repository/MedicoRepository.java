package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @EntityGraph(attributePaths = {"especialidad"})
    List<Medico> findAll();

    @EntityGraph(attributePaths = {"especialidad"})
    Optional<Medico> findById(Long id);

    Optional<Medico> findByNumColegiado(String numColegiado);
    
    @EntityGraph(attributePaths = {"especialidad"})
    List<Medico> findByEspecialidadId(Long idEspecialidad);
    
    @Query("SELECT m FROM Medico m WHERE m.apellido LIKE %:apellido%")
    List<Medico> findByApellidoContaining(@Param("apellido") String apellido);
}
