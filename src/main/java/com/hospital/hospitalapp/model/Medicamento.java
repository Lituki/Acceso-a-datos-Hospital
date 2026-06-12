package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "medicamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String nombre;

    @Column(length = 255)
    private String composicion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(length = 50)
    private String unidad;

    @ManyToMany(mappedBy = "medicamentos")
    private List<Tratamiento> tratamientos;
}
