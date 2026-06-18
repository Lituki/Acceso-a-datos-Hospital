package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String motivo;

    @Lob
    private String observaciones;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "cita_medico",
        joinColumns = @JoinColumn(name = "id_cita"),
        inverseJoinColumns = @JoinColumn(name = "id_medico")
    )
    private List<Medico> medicos;

    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    private List<Tratamiento> tratamientos;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cita cita = (Cita) o;
        return id != null && id.equals(cita.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Cita{" +
            "id=" + id +
            ", fechaHora=" + fechaHora +
            ", motivo='" + motivo + '\'' +
            ", observaciones='" + observaciones + '\'' +
            '}';
    }
}
