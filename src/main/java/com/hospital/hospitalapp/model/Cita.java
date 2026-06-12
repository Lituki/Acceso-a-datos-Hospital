package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 255)
    private String motivo;

    @Lob
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToMany
    @JoinTable(
        name = "cita_medico",
        joinColumns = @JoinColumn(name = "id_cita"),
        inverseJoinColumns = @JoinColumn(name = "id_medico")
    )
    private List<Medico> medicos;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL)
    private List<Tratamiento> tratamientos;
}
