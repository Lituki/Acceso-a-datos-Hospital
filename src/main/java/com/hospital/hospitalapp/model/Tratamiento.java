package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tratamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String descripcion;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer duracion;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String frecuencia;

    @Lob
    private String indicaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = true)
    private Cita cita;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tratamiento_medicamento",
        joinColumns = @JoinColumn(name = "id_tratamiento"),
        inverseJoinColumns = @JoinColumn(name = "id_medicamento")
    )
    private List<Medicamento> medicamentos;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tratamiento that = (Tratamiento) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
            "id=" + id +
            ", descripcion='" + descripcion + '\'' +
            ", duracion=" + duracion +
            ", frecuencia='" + frecuencia + '\'' +
            ", indicaciones='" + indicaciones + '\'' +
            '}';
    }
}
