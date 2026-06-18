package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    @EntityGraph(attributePaths = {"paciente", "habitacion"})
    List<Ingreso> findAll();

    @EntityGraph(attributePaths = {"paciente", "habitacion"})
    @Query("SELECT i FROM Ingreso i WHERE i.fechaAlta IS NULL")
    List<Ingreso> findByFechaAltaIsNull();
    
    List<Ingreso> findByPacienteId(Long idPaciente);
}
