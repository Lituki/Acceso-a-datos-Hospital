package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDni(String dni);
    
    List<Paciente> findByApellidoContainingIgnoreCase(String apellido);
    
    @Query("SELECT p FROM Paciente p WHERE p.nombre LIKE %:nombre% OR p.apellido LIKE %:nombre%")
    List<Paciente> searchByNombreOrApellido(@Param("nombre") String nombre);
}
