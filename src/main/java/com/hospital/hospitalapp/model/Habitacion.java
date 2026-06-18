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
@Table(name = "habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String numero;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer capacidad;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String planta;

    @NotNull
    @Column(nullable = false)
    private Boolean disponible = true;

    @OneToMany(mappedBy = "habitacion", fetch = FetchType.LAZY)
    private List<Ingreso> ingresos;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Habitacion that = (Habitacion) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Habitacion{" +
            "id=" + id +
            ", numero='" + numero + '\'' +
            ", tipo='" + tipo + '\'' +
            ", capacidad=" + capacidad +
            ", planta='" + planta + '\'' +
            ", disponible=" + disponible +
            '}';
    }
}
