package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Habitacion;
import com.hospital.hospitalapp.model.Ingreso;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.service.HabitacionService;
import com.hospital.hospitalapp.service.IngresoService;
import com.hospital.hospitalapp.service.PacienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingresos")
public class IngresoController {

    private final IngresoService ingresoService;
    private final PacienteService pacienteService;
    private final HabitacionService habitacionService;

    public IngresoController(IngresoService ingresoService,
                             PacienteService pacienteService,
                             HabitacionService habitacionService) {
        this.ingresoService = ingresoService;
        this.pacienteService = pacienteService;
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ingresos", ingresoService.obtenerTodos());
        model.addAttribute("activos", ingresoService.obtenerIngresosActivos());
        return "ingresos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("ingresoForm", new IngresoForm());
        populateFormModel(model);
        return "ingresos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("ingresoForm") IngresoForm ingresoForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            populateFormModel(model);
            return "ingresos/form";
        }
        ingresoService.crearIngreso(
            ingresoForm.getIdPaciente(),
            ingresoForm.getIdHabitacion(),
            ingresoForm.getMotivo(),
            ingresoForm.getObservaciones()
        );
        return "redirect:/ingresos";
    }

    @PostMapping("/alta/{id}")
    public String darAlta(@PathVariable Long id) {
        ingresoService.darDeAlta(id);
        return "redirect:/ingresos";
    }

    private void populateFormModel(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        model.addAttribute("habitaciones", habitacionService.obtenerDisponibles());
    }

    public static class IngresoForm {
        @NotNull
        private Long idPaciente;

        @NotNull
        private Long idHabitacion;

        @NotBlank
        private String motivo;

        private String observaciones;

        public Long getIdPaciente() {
            return idPaciente;
        }

        public void setIdPaciente(Long idPaciente) {
            this.idPaciente = idPaciente;
        }

        public Long getIdHabitacion() {
            return idHabitacion;
        }

        public void setIdHabitacion(Long idHabitacion) {
            this.idHabitacion = idHabitacion;
        }

        public String getMotivo() {
            return motivo;
        }

        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
    }
}
