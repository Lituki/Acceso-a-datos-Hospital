package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "habitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String numero;

    @Column(nullable = false, length = 50)
    private String tipo; // Individual, Doble, Triple...

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false, length = 50)
    private String planta;

    @Column(nullable = false)
    private Boolean disponible = true;

    @OneToMany(mappedBy = "habitacion")
    private List<Ingreso> ingresos;
}
