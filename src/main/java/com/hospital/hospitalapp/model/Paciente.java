package com.hospital.hospitalapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotBlank
    @Column(nullable = false, length = 50, unique = true)
    private String dni;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(length = 100)
    private String telefono;

    @Email
    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String direccion;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Cita> citas;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Ingreso> ingresos;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paciente paciente = (Paciente) o;
        return id != null && id.equals(paciente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", dni='" + dni + '\'' +
            ", fechaNacimiento=" + fechaNacimiento +
            ", telefono='" + telefono + '\'' +
            ", email='" + email + '\'' +
            ", direccion='" + direccion + '\'' +
            '}';
    }
}
