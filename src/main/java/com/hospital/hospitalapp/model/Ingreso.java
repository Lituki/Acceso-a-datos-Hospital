package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ingreso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @NotBlank
    @Column(length = 255)
    private String motivo;

    @Lob
    private String observaciones;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacion habitacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingreso ingreso = (Ingreso) o;
        return id != null && id.equals(ingreso.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Ingreso{" +
            "id=" + id +
            ", fechaIngreso=" + fechaIngreso +
            ", fechaAlta=" + fechaAlta +
            ", motivo='" + motivo + '\'' +
            ", observaciones='" + observaciones + '\'' +
            '}';
    }
}
