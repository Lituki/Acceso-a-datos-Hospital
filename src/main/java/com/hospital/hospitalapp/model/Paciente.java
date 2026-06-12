package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, length = 50, unique = true)
    private String dni;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(length = 100)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String direccion;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Cita> citas;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Ingreso> ingresos;
}
