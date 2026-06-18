package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "medicamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150, unique = true)
    private String nombre;

    @Column(length = 255)
    private String composicion;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(length = 50)
    private String unidad;

    @ManyToMany(mappedBy = "medicamentos", fetch = FetchType.LAZY)
    private List<Tratamiento> tratamientos;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medicamento that = (Medicamento) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Medicamento{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", composicion='" + composicion + '\'' +
            ", precio=" + precio +
            ", unidad='" + unidad + '\'' +
            '}';
    }
}
