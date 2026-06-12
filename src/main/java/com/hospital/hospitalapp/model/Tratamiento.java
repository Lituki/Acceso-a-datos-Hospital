package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tratamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = false)
    private Integer duracion; // en días

    @Column(nullable = false, length = 100)
    private String frecuencia; // 3 veces al día, cada 8 horas...

    @Lob
    private String indicaciones;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @ManyToMany
    @JoinTable(
        name = "tratamiento_medicamento",
        joinColumns = @JoinColumn(name = "id_tratamiento"),
        inverseJoinColumns = @JoinColumn(name = "id_medicamento")
    )
    private List<Medicamento> medicamentos;
}
